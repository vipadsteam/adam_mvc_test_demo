/**
 * 
 */
package org.adam.asyn.web.chain.netty;

import org.adam.asyn.web.common.WebMVCConstants;
import org.adam.asyn.web.request.RequestMsg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.adam.common.bean.ResultVo;
import org.springframework.adam.common.bean.annotation.service.ServiceErrorCode;
import org.springframework.adam.common.bean.annotation.service.ServiceOrder;
import org.springframework.adam.common.bean.annotation.service.ServiceType;
import org.springframework.adam.service.AbsCallbacker;
import org.springframework.adam.service.IService;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;;

/**
 * @author user
 *
 */
@Component
@ServiceType(WebMVCConstants.NETTY_TEST)
@ServiceOrder(10)
@ServiceErrorCode(WebMVCConstants.ADAM_TEST_ERROR)
public class NettyTestStep1 implements IService<RequestMsg, ChannelHandlerContext> {

	private static final Log log = LogFactory.getLog(NettyTestStep1.class);

	private static final String STEP = "step 1 ";

	@Override
	public AbsCallbacker doService(RequestMsg income, ResultVo<ChannelHandlerContext> output) throws Exception {
		return null;
	}

	@Override
	public AbsCallbacker doSuccess(RequestMsg income, ResultVo<ChannelHandlerContext> output) throws Exception {
		return null;
	}

	@Override
	public AbsCallbacker doFail(RequestMsg income, ResultVo<ChannelHandlerContext> output) throws Exception {
		return null;
	}

	@Override
	public AbsCallbacker doComplate(RequestMsg income, ResultVo<ChannelHandlerContext> output) throws Exception {
		// 向客户端发送消息
		String response = income.getParam();
		ChannelHandlerContext ctx = output.getData();
		// 在当前场景下，发送的数据必须转换成ByteBuf数组
		ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
		encoded.writeBytes(response.getBytes());
		ctx.write(encoded);
		ctx.flush();
		return null;
	}

}
