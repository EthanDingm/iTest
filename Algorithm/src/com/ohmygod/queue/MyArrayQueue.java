package com.ohmygod.queue;

import java.util.Arrays;

/**
 * @author: dingM
 * @description: ����ʵ�ּ򵥶���
 * @date: 2022/9/4 20:43
 */
public class MyArrayQueue {

    public int[] data;

    public int head = 0;

    public int tail = 0;

    public int n; //����Ĵ�С

    public MyArrayQueue(int capacity){
        data = new int[capacity];
        n = capacity;
    }

    /**
     * @author dingM
     * @description ���������
     * @date 2022/9/4 21:31
     */
    public void push(int m){
        //�����ǲ����Ѿ�����
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
        //�ж��ǲ����Ѿ�û����
        if (isEmpty()) return -1;
        int m = data[head];
        head ++;
        return m;
    }

    /**
     * @param :
     * @return boolean
     * @author dingM
     * @description �����ǲ��ǿյ�
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
