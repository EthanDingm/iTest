package com.ohmygod.queue;

import java.util.Arrays;

/**
 * @author: dingM
 * @description: 数组实现简单队列
 * @date: 2022/9/4 20:43
 */
public class MyArrayQueue {

    public int[] data;

    public int head = 0;

    public int tail = 0;

    public int n; //数组的大小

    public MyArrayQueue(int capacity){
        data = new int[capacity];
        n = capacity;
    }

    /**
     * @author dingM
     * @description 往队里插入
     * @date 2022/9/4 21:31
     */
    public void push(int m){
        //队列是不是已经满了
        if (tail == n) return;
        data[tail] = m;
        tail ++;
    }

    /**
     * @param :
     * @return int
     * @author dingM
     * @description TODO
     * @date 2022/9/4 21:29
     */
    public int pop(){
        //判断是不是已经没有了
        if (isEmpty()) return -1;
        int m = data[head];
        head ++;
        return m;
    }

    /**
     * @param :
     * @return boolean
     * @author dingM
     * @description 队列是不是空的
     * @date 2022/9/4 21:37
     */
    private boolean isEmpty() {
        return tail == head;
    }

    @Override
    public String toString() {
        return "MyArrayQueue{" +
                "data=" + Arrays.toString(data) +
                ", head=" + head +
                ", tail=" + tail +
                ", n=" + n +
                '}';
    }

    public static void main(String[] args) {
        MyArrayQueue myArrayQueue = new MyArrayQueue(5);
        myArrayQueue.push(11);
        System.out.println(myArrayQueue);
        myArrayQueue.push(12);
        System.out.println(myArrayQueue);
        myArrayQueue.push(13);
        System.out.println(myArrayQueue);
        myArrayQueue.pop();
        System.out.println(myArrayQueue);

    }
}
