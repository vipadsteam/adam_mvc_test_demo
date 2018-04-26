/**
 * 
 */
package org.adam.asyn.web.netty;

import org.adam.asyn.web.common.WebMVCConstants;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.springframework.adam.common.bean.ResultVo;
import org.springframework.adam.common.utils.context.SpringContextUtils;
import org.springframework.adam.service.chain.ServiceChain;
import org.springframework.web.context.request.async.DeferredResult;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author USER
 *
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

	private volatile static ServiceChain serviceChain;
	
	private volatile static boolean isTest = false;
	

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf result = (ByteBuf) msg;
		byte[] result1 = new byte[result.readableBytes()];
		// msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
		result.readBytes(result1);
		String resultStr = new String(result1);
		// 释放资源，这行很关键
		result.release();

		if (null == serviceChain) {
			init();
		}
		if(isTest){
			System.out.println(resultStr + "-");
			return;
		}
		ResultVo<ChannelHandlerContext> output = new ResultVo<ChannelHandlerContext>();
		output.setData(ctx);
		RequestMsg req = new RequestMsg();
		req.setParam(resultStr);
		serviceChain.doServer(req, output, WebMVCConstants.NETTY_TEST);
	}

	private synchronized void init() {
		if (null != serviceChain) {
			return;
		}

		try{
			serviceChain = SpringContextUtils.getBean(ServiceChain.class);
		}catch(Exception e){
			isTest = true;
		}
	}

}
