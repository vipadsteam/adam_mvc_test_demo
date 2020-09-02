/**
 * 
 */
package org.adam.asyn.web.chain.adam;

import org.adam.asyn.web.common.WebMVCConstants;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.springframework.adam.backpressure.AdamBackPressureHolder;
import org.springframework.adam.backpressure.AdamBackPressureUtils;
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
@ServiceType(WebMVCConstants.ADAM_TEST_BP)
@ServiceOrder(10)
@ServiceErrorCode(WebMVCConstants.ADAM_TEST_ERROR)
public class AdamTestStepBp implements IService<RequestMsg, DeferredResult<ResponseMsg<String>>> {

	private volatile long index = 0;

	@Override
	public AbsCallbacker doService(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output)
			throws Exception {
		// 模拟接口错误增加时候触发背压统计
		if (index++ % 100 == 1) {
			AdamBackPressureUtils.errIncrease();
		}
		System.out.println("rate:" + AdamBackPressureHolder.get().getRate());
		return null;
	}

	@Override
	public AbsCallbacker doComplate(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output)
			throws Exception {
		ResponseMsg<String> result = new ResponseMsg<String>();
		result.setMsg(output.getResultMsg());
		result.setData(income.getParam());
		output.getData().setResult(result);
		return null;
	}

}
