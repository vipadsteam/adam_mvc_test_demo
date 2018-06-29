package org.adam.asyn.web.service.callback;

import java.io.IOException;

import org.springframework.adam.service.AbsCallbacker;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public abstract class OkHttpFutureCallback<T1, T2> extends AbsCallbacker<Response, Exception, T1, T2> implements Callback {

	public OkHttpFutureCallback(long motherThreadId) {
		super(motherThreadId);
	}

	@Override
	public void onFailure(Request arg0, IOException exp) {
		onFail(exp);
	}

	@Override
	public void onResponse(Response result) throws IOException {
		if (null != result) {
			onSuccess(result);
		}
	}
}
