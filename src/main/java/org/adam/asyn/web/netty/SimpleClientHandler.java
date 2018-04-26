/**
 * 
 */
package org.adam.asyn.web.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author USER
 *
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("SimpleClientHandler.channelRead");
		ByteBuf result = (ByteBuf) msg;
		byte[] result1 = new byte[result.readableBytes()];
		result.readBytes(result1);
		System.out.println("Server said:" + new String(result1));
		result.release();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}

	// 连接成功后，向server发送消息
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 1000; i++) {
			String msg = "hello Server!";
			ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
			encoded.writeBytes(msg.getBytes());
			ctx.writeAndFlush(encoded);
		}
	}
}
