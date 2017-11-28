/**
 * 
 */
package org.adam.asyn.web.service.callback;

import org.adam.asyn.web.common.callback.HttpFutureCallback;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author USER
 *
 */
public class TestHttpFutureCallback extends HttpFutureCallback<RequestMsg, DeferredResult<ResponseMsg<String>>> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.adam.service.AbsCallbacker#dealSuccess(java.lang.
	 * Object)
	 */
	@Override
	public void dealSuccess(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String json;
		try {
			json = EntityUtils.toString(entity, "utf-8");
			System.out.println("---- http send success");
			System.out.println(json);
			this.income.setParam("json");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.adam.service.AbsCallbacker#dealFail(java.lang.
	 * Throwable)
	 */
	@Override
	public void dealFail(Exception e) {
		if (null != e) {
			e.printStackTrace();
		}
		System.out.println("---- http send fail");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.adam.service.AbsCallbacker#dealComplete(java.lang.
	 * Object, java.lang.Throwable)
	 */
	@Override
	public void dealComplete(HttpResponse result, Exception e) {
		System.out.println("---- http send complete");
	}

}
