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

import io.netty.channel.ChannelHandlerContext;;

/**
 * @author user
 *
 */
@Component
@ServiceType(WebMVCConstants.NETTY_TEST)
@ServiceOrder(30)
@ServiceErrorCode(WebMVCConstants.ADAM_TEST_ERROR)
public class NettyTestStep3 implements IService<RequestMsg, ChannelHandlerContext> {

	private static final Log log = LogFactory.getLog(NettyTestStep3.class);

	private static final String STEP = "step 3 ";

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
		return null;
	}

}
