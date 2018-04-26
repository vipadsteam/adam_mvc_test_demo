/**
 * 
 */
package org.adam.asyn.web.service.callback;

import java.util.concurrent.CountDownLatch;

import org.adam.asyn.web.common.TestThreadPool;
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

	private CountDownLatch latch = new CountDownLatch(1);

	public TestHttpFutureCallback() {
		super(Thread.currentThread().getId());
		this.tpe = TestThreadPool.instance().getThreadPoolExecutor();
	}

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
			this.income.setParam(json);
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
		latch.countDown();
	}

	@Override
	public void dealException(Throwable t) {
		if (null != t) {
			t.printStackTrace();
		}
	}

}
