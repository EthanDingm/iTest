package com.ohmygod.test;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Author : Dingm
 * Description :zk通知机制：一次性通知
 * (znode节点的值变更后，触发watcher的回调方法，但是这种回调只触发一次。
 * 若要多次调用回调方法，需要去看watchmore)
 * 1    初始化zk的多个操作
 *  1.1 创建zk连接
 *  1.2 创建/ohmygod节点并赋值
 *  1.3 获取该节点的值
 * 2    watch
 *  2.1 获得值之后（getZnode方法被调用之后）设置一个观察者watcher,如果/ohmygod节点发生了变化，（A-->B）
 *      要求通知Client端idea, 一次性通知
 * Create : 2020-02-13 上午 6:36
 */
public class WatchOne {

    private static final Logger logger = Logger.getLogger(WatchOne.class);

    private static final String CONNECTSTRING = "192.168.211.128:2181";
    private static final String PATH = "/ohmygod";
    private static final int SESSION_TIMEOUT = 20*1000;

    private @Setter@Getter ZooKeeper zk;

    public void setZk(ZooKeeper zk){
        this.zk = zk;
    }
    public ZooKeeper getZk(){
        return this.zk;
    }

    public ZooKeeper startZK() throws IOException {
        return new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    public void createZnode(String path,String data) throws KeeperException, InterruptedException {
        zk.create(PATH,data.getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
    }

    public String getZnode(String path) throws KeeperException, InterruptedException {
        String result = "";
        byte[] byteArray = zk.getData(PATH, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //业务逻辑，也既触发了/ohmygod节点变更后，需要立即获取最新值
                //需要将该部分业务逻辑提取出来，新编程一个方法
                try {
                    triggerValue(path);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, new Stat());
        result = new String(byteArray);
        return result;

    }

    private void triggerValue(String path) throws KeeperException, InterruptedException {
        String result = "";
        byte[] byteArray = zk.getData(path,null,new Stat());
        result = new String(byteArray);
        logger.info("******观察者watcher通过回调方法得到了该节点最新的值： " + result);
    }

    public static void main(String[] args) throws Exception {
        WatchOne one = new WatchOne();
        one.setZk(one.startZK());
        if(one.getZk().exists(PATH,false) == null){
            one.createZnode(PATH,"AAA");
            String result = one.getZnode((PATH));
            logger.info("******main方法中的获取的初始值： " + result);
        }else{
            logger.info("znode节点"+ PATH +"已经存在，不能创建同名节点");
        }
        Thread.sleep(Long.MAX_VALUE);
    }
}
