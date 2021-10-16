package com.ohmygod.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public static void main(String[] args) throws Exception {
        // �ͻ�����Ҫһ���¼�ѭ����
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // �����ͻ����������� (ע��ͻ���ʹ�õĲ��� ServerBootstrap ���� Bootstrap)
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)  // ������ز���
                    .channel(NioSocketChannel.class)    // ʹ�� NioSocketChannel ��Ϊ�ͻ��˵�ͨ��ʵ��
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("netty client start");
            // �����ͻ���ȥ���ӷ�������
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
            // �Թر�ͨ�����м���
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
