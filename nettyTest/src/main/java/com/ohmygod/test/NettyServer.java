package com.ohmygod.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) {
        // ���������߳���bossGroup��workerGroup, ���е����߳�NioEventLoop�ĸ���Ĭ��Ϊcpu����������
        // bossGroupֻ�Ǵ����������� ,�����ĺͿͻ���ҵ�����ύ��workerGroup���
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // �����������˵���������
        ServerBootstrap bootstrap = new ServerBootstrap();
        // ��ʽ��������ò���
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)  // ʹ��NioServerSocketChannel��Ϊ��������ͨ��ʵ��
                .option(ChannelOption.SO_BACKLOG, 1024) // ��ʼ�����������Ӷ��д�С������˴���ͻ�������������˳�����,����ͬһʱ��ֻ�ܴ���һ���ͻ������ӡ�����ͻ���ͬʱ����ʱ��,����˽����ܴ���Ŀͻ�������������ڶ����еȴ�����
                .childHandler(new ChannelInitializer<SocketChannel>() { // ����ͨ����ʼ���������ó�ʼ������
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // ��workerGroup��SocketChannel���ô�����
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });
        System.out.println("netty server start!");

        try {
            // ��һ���˿ڲ���ͬ��, ������һ��ChannelFuture�첽����ͨ��isDone()�ȷ��������ж��첽�¼���ִ�����
            ChannelFuture cf = bootstrap.bind(9000).sync();
            // ��cfע����������������ǹ��ĵ��¼�
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("�����˿�9000�ɹ�");
                    } else {
                        System.out.println("�����˿�9000ʧ��");
                    }
                }
            });
            // ��ͨ���رս��м�����closeFuture���첽����������ͨ���ر�
            // ͨ��sync����ͬ���ȴ�ͨ���رմ�����ϣ�����������ȴ�ͨ���ر����
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
