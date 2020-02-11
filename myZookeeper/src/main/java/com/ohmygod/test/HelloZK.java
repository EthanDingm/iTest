package com.ohmygod.test;


import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Author : Dingm
 * Description : idea作为Client端，CentOS作为Zookeeper的Server端
 *  * 1 通过java代码，新建连接zk.类似jdbc的connection, open.session
 *  * 2 新建一个znode节点,并设置为hello2020 等同于zk命令：create /ohmygod hello2020
 *  * 3 获取当前节点 /ohmygod的最新值  等同于zk命令： get /ohmygod
 *  * 4 关闭连接
 * Create : 2020-02-12 上午 5:30
 */
public class HelloZK {

    private static final Logger logger = Logger.getLogger(HelloZK.class);

    //实例常量
    private static final String CONNECTSTRING = "192.168.211.128:2181";
    private static final int SESSION_TIMEOUT = 20*1000;
    private static final String PATH = "/ohmygod";


    /**
     * 获取zk连接实例
     * @return
     * @throws IOException
     */
    public ZooKeeper startZK() throws IOException {
        return new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
            //这里的Watcher接口如果写成null传进去，虽然不影响主流程，但是会报空指针异常（zk3.4.9版本原因），所以用匿名内部类代替
            }
        });
    }

    /**
     * 创建znode节点
     * @param zk
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void createZnode(ZooKeeper zk,String path,String data) throws KeeperException, InterruptedException {
        zk.create(path,data.getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);

    }

    /**
     * 获取当前节点的值
     * @param zk
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getZnode(ZooKeeper zk, String path) throws KeeperException, InterruptedException {
        String result= "";
        byte[] byteArray = zk.getData(path,false,new Stat());
        result = new String(byteArray);
        return result;
    }

    /**
     * 关闭zk连接
     * @param zk
     */
    public void stopZK(ZooKeeper zk) throws InterruptedException {
        if(null != zk){
            zk.close();
        }
    }

    public static void main(String[] args) throws Exception {
        HelloZK helloZK = new HelloZK();
        ZooKeeper zk = helloZK.startZK();

        if (zk.exists(PATH,false) == null){
            helloZK.createZnode(zk,PATH,"hello2020_java");
            String result = helloZK.getZnode(zk,PATH);
            if (logger.isInfoEnabled()){
                logger.info("main(String[]) ----------- String result = " + result);
            }
        }else {
            logger.info("This node is exists......");
        }

        helloZK.startZK();

    }
}
