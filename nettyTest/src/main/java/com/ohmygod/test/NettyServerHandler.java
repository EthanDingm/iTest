package com.ohmygod.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * �Զ���Handler��Ҫ�̳�netty�涨�õ�ĳ��HandlerAdapter(�淶)
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * ��ȡ�ͻ��˷��͵�����
     *
     * @param ctx �����Ķ���, ����ͨ��channel���ܵ�pipeline
     * @param msg ���ǿͻ��˷��͵�����
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("��������ȡ�߳�" + Thread.currentThread().getName());
        // Channel channel = ctx.channel();
        // ChannelPipeline pipeline = ctx.pipeline();
        ByteBuf buf = (ByteBuf) msg;    // �� msg ת��һ�� ByteBuf������NIO �� ByteBuffer
        System.out.println("�ͻ��˷��͵���Ϣ�ǣ�" + buf.toString(CharsetUtil.UTF_8));
    }

    /**
     * ���ݶ�ȡ��ϴ�������
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = Unpooled.copiedBuffer("hello client", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

    /**
     * �����쳣, һ������Ҫ�ر�ͨ��
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}