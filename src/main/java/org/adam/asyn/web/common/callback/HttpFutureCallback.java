package org.adam.asyn.web.common.callback;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.springframework.adam.service.AbsCallbacker;

public abstract class HttpFutureCallback<T1, T2> extends AbsCallbacker<HttpResponse, Exception, T1, T2> implements FutureCallback<HttpResponse> {

	public HttpFutureCallback(long motherThreadId) {
		super(motherThreadId);
	}

	@Override
	public void completed(HttpResponse result) {
		if(null != result){
			onSuccess(result);
		}
	}

	@Override
	public void failed(Exception ex) {
		onFail(ex);
	}

	@Override
	public void cancelled() {
		onFail(null);
	}

}
