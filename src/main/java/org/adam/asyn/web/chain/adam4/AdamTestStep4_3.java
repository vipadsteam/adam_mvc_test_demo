/**
 * 
 */
package org.adam.asyn.web.chain.adam4;

import org.adam.asyn.web.common.WebMVCConstants;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.adam.common.bean.ResultVo;
import org.springframework.adam.common.bean.annotation.service.ServiceErrorCode;
import org.springframework.adam.common.bean.annotation.service.ServiceOrder;
import org.springframework.adam.common.bean.annotation.service.ServiceType;
import org.springframework.adam.service.AbsCallbacker;
import org.springframework.adam.service.IService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;;

/**
 * @author user
 *
 */
@Component
@ServiceType(WebMVCConstants.ADAM_TEST4)
@ServiceOrder(30)
@ServiceErrorCode(WebMVCConstants.ADAM_TEST_ERROR)
public class AdamTestStep4_3 implements IService<RequestMsg, DeferredResult<ResponseMsg<String>>> {

	private static final Log log = LogFactory.getLog(AdamTestStep4_3.class);

	private static final String STEP = "step 4-3 ";

	@Override
	public AbsCallbacker doService(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		System.out.println(STEP+" doService");
		return null;
	}

	@Override
	public AbsCallbacker doSuccess(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		System.out.println(STEP+" doSuccess");
		return null;
	}

	@Override
	public AbsCallbacker doFail(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		System.out.println(STEP+" doFail");
		return null;
	}

	@Override
	public AbsCallbacker doComplate(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		System.out.println(STEP+" doComplate");
		return null;
	}

}