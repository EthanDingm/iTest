package com.ohmygod.test;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Author : Dingm
 * Description :zk通知机制：一次性通知改成持续通知（递归的思想）
 * 1    初始化zk的多个操作
 *  1.1 简历zk连接
 *  1.2 创建/ohmygod 节点并赋值
 *  1.3 获取该节点的值
 * 2    watchmore
 *  2.1 获得值之后设置一个观察者watcher.如果/ohmygod该节点的值发生的变化，要通知client端，一次性通知
 *  2.2 又再次获得新的值得同时再设置一个观察者，继续观察并获得值
 *  2.3 又再次获得新的值得同时再设置一个观察者，继续观察并获得值。。。。。。重复上述过程
 *
 * Create : 2020-02-13 下午 4:52
 */
public class WatchMore {

    private static final Logger logger = Logger.getLogger(WatchMore.class);

    private static final String PATH = "/ohmygod";
    private static final String CONNECTSTRING = "192.168.211.128:2181";
    private static final int SESSION_TIMEOUT = 20*1000;

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    private @Setter@Getter
    ZooKeeper zk = null;

    private @Setter@Getter String oldValue = "";
    private @Setter@Getter String newValue = "";

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
        oldValue = result;
        return result;

    }

    private boolean triggerValue(String path) throws KeeperException, InterruptedException {
        String result = "";
        byte[] byteArray = zk.getData(path, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    triggerValue(path);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, new Stat());
        result = new String(byteArray);
        newValue = result;
        if(newValue.equals(oldValue)){
            logger.info("******该节点更新后值没有变化");
            return false;
        }else{
            logger.info("******该节点更新后值变更为：" + newValue);
            logger.info("\t ******观察者watcher通过回调方法得到了该节点最新的值： " + result + "\t oldvalue:" +oldValue + "\t newValue:" + newValue);
            oldValue = newValue;
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        WatchMore more = new WatchMore();
        more.setZk(more.startZK());
        if(more.getZk().exists(PATH,false) == null){
            more.createZnode(PATH,"AAA");
            String result = more.getZnode((PATH));
            logger.info("******main方法中得到的初始值： " + result);
        }else{
            logger.info("znode节点"+ PATH +"已经存在，不能创建同名节点");
        }
        Thread.sleep(Long.MAX_VALUE);
    }


}
