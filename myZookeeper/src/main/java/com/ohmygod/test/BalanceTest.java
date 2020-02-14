package com.ohmygod.test;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : Dingm
 * Description :zookeeper:轮询 容错 故障恢复
 * Create : 2020-02-14 上午 7:16
 */
public class BalanceTest {

    private static final Logger logger = Logger.getLogger(WatchMore.class);
    private static final String PATH = "/bank";
    private static final String CONNECTSTRING = "192.168.211.128:2181";
    //若为zookeeper集群：
    //private static final String CONNECTSTRING = "192.168.211.128:2191,192.168.211.128:2192,192.168.211.128:2193";
    private static final int SESSION_TIMEOUT = 50*1000;

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    private @Setter@Getter ZooKeeper zk = null;
    private List<String> list = new ArrayList<>();
    private int index = 0;
    private int totalWindows = 5;
    private static final String SUB_PREFIX = "sub";

    public ZooKeeper startZK() throws IOException {
        return new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //盘点家底，看看当前可以办理业务的窗口是哪些 [sub4, sub5, sub2, sub3, sub1]
                //开启实时监控，实现节点宕机（容错）/恢复（故障恢复）
                try {
                    list = zk.getChildren(PATH,true);//
                    logger.info("******当前可以办理业务的窗口是：" + list);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String doRequest() throws KeeperException, InterruptedException {
        index = index + 1;
        //银行受理窗口有5个，他们来负责接待
        for (int i = index; i <= totalWindows; i++) {
            if(list.contains(SUB_PREFIX + index)){
                return new String(zk.getData(PATH + "/" + SUB_PREFIX + index, false, new Stat()));
            }else{
                index = index + 1;
            }

        }
        //当index=6时，上面代码出窗口数限制5
        for (int i = 1; i < totalWindows; i++) {
            if (list.contains(SUB_PREFIX + i)){
                index = i;
                return new String(zk.getData(PATH + "/" + SUB_PREFIX + index, false, new Stat()));
            }
        }
        //也可以直接这样写
        /*if (list.contains(SUB_PREFIX + 1)){
            index = 1;
            return new String(zk.getData(PATH + "/" + SUB_PREFIX + index, false, new Stat()));
        }*/
        return "******当前窗口不存在";
    }

    public static void main(String[] args) throws Exception {
        BalanceTest test = new BalanceTest();
        test.setZk(test.startZK());
        Thread.sleep(3000);//留充足的时间加载5个节点
        String result = null;
        //模拟15个客户来银行窗口办理业务，完成后都可以收到一个回执单
        for (int i = 1; i <= 15; i++) {
            result = test.doRequest();
            logger.info("客户ID=" + i + " 当前服务窗口=" + test.index + " result=" + result);
            Thread.sleep(2000);
        }
    }

}
