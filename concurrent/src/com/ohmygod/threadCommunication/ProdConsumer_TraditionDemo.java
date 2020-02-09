package com.ohmygod.threadCommunication;

/**
 * Author : Dingm
 * Description :线程间的通信，等待唤醒机制
 *
 * lambda表达式：拷贝小括号 + 写死右箭头 + 落地大括号
 *
 * Create : 2020-02-10 上午 4:06
 */
public class ProdConsumer_TraditionDemo {

    public static void main(String[] args) {

        ShareData shareData = new ShareData();

        //线程 操作 资源类
        new Thread(() -> {
            for (int i = 1; i < 5; i++) {
                try{
                    shareData.increment();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"AA").start();

        new Thread(() -> {
            for (int i = 1; i < 5; i++) {
                try{
                    shareData.decreace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"BB").start();
    }
}
