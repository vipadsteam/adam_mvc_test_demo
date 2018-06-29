/**
 * 
 */
package org.adam.asyn.web.service.callback;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.adam.asyn.web.common.TestThreadPool;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.springframework.web.context.request.async.DeferredResult;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

/**
 * @author USER
 *
 */
public class TestOkHttpFutureCallback extends OkHttpFutureCallback<RequestMsg, DeferredResult<ResponseMsg<String>>> {

	private CountDownLatch latch = new CountDownLatch(1);

	public TestOkHttpFutureCallback() {
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
	public void dealSuccess(Response response) {
		ResponseBody responseBody = response.body();
		if (!response.isSuccessful()){
			// test
		}

		try {
			String json = responseBody.string();
			this.income.setParam(json);
		} catch (IOException e) {
			// test
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
//			e.printStackTrace();
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
	public void dealComplete(Response result, Exception e) {
		latch.countDown();
	}

	@Override
	public void dealException(Throwable t) {
		if (null != t) {
//			t.printStackTrace();
		}
	}

}
