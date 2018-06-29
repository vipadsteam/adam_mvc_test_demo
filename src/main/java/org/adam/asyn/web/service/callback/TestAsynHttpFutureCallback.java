/**
 * 
 */
package org.adam.asyn.web.service.callback;

import org.adam.asyn.web.common.TestThreadPool;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.asynchttpclient.Response;
import org.springframework.web.context.request.async.DeferredResult;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

/**
 * @author USER
 *
 */
public class TestAsynHttpFutureCallback extends AsynHttpFutureCallback<RequestMsg, DeferredResult<ResponseMsg<String>>> {

	private CountDownLatch latch = new CountDownLatch(1);

	public TestAsynHttpFutureCallback() {
		super(Thread.currentThread().getId());
		this.tpe = TestThreadPool.instance().getThreadPoolExecutor();
	}

	@Override
	public void dealSuccess(Response response) {
		String json = "";
		try {
            json = response.getResponseBody(Charset.forName("UTF-8"));
			this.income.setParam(json);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	@Override
	public void dealFail(Throwable e) {
		if (null != e) {
			// e.printStackTrace();
		}
	}

	@Override
	public void dealComplete(Response response, Throwable e) {
		latch.countDown();
	}

	@Override
	public void dealException(Throwable t) {
		if (null != t) {
			// t.printStackTrace();
		}
	}
}
