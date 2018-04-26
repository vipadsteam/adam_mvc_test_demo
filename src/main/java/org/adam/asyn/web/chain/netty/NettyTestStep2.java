/**
 * 
 */
package org.adam.asyn.web.chain.netty;

import org.adam.asyn.web.chain.adam1.AdamTestStep1_2;
import org.adam.asyn.web.common.WebMVCConstants;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.service.callback.TestHttpFutureCallback;
import org.adam.asyn.web.service.client.HttpTestClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.adam.common.bean.ResultVo;
import org.springframework.adam.common.bean.annotation.service.ServiceErrorCode;
import org.springframework.adam.common.bean.annotation.service.ServiceOrder;
import org.springframework.adam.common.bean.annotation.service.ServiceType;
import org.springframework.adam.service.AbsCallbacker;
import org.springframework.adam.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;;

/**
 * @author user
 *
 */
@Component
@ServiceType(WebMVCConstants.NETTY_TEST)
@ServiceOrder(20)
@ServiceErrorCode(WebMVCConstants.ADAM_TEST_ERROR)
public class NettyTestStep2 implements IService<RequestMsg, ChannelHandlerContext> {

	private static final Log log = LogFactory.getLog(NettyTestStep2.class);

	private static final String STEP = "step 2 ";

	@Autowired
	private HttpTestClient httpTestClient;

	@Override
	public AbsCallbacker doService(RequestMsg income, ResultVo<ChannelHandlerContext> output) throws Exception {
		String url = AdamTestStep1_2.urlBase1 + AdamTestStep1_2.ip + AdamTestStep1_2.urlBase2;
		if ("Y".equals(AdamTestStep1_2.type)) {
			TestHttpFutureCallback callback = httpTestClient.call(url);
			return callback;
		} else {
			String result = httpTestClient.callSyn(url);
			income.setParam(result);
			return null;
		}
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
