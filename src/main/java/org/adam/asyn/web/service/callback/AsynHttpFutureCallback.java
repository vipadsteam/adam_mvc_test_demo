package org.adam.asyn.web.service.callback;

import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.HttpResponseBodyPart;
import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.Response;
import org.springframework.adam.service.AbsCallbacker;

public abstract class AsynHttpFutureCallback<T1, T2> extends AbsCallbacker<Response, Throwable, T1, T2> implements AsyncHandler<Response> {

	private boolean isDone = false;

	private Response response;
	
	public AsynHttpFutureCallback(long motherThreadId) {
		super(motherThreadId);
	}

    private final Response.ResponseBuilder builder =
            new Response.ResponseBuilder();

	private Throwable t;

	@Override
	public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
        builder.accumulate(responseStatus);
		return State.CONTINUE;
	}

	@Override
	public State onHeadersReceived(HttpHeaders headers) throws Exception{
        builder.accumulate(headers);
		return State.CONTINUE;
	}

	@Override
	public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
        builder.accumulate(bodyPart);
		return State.CONTINUE;
	}

	@Override
	public Response onCompleted() throws Exception {

		if(isDone){
			return this.response;
		}
		isDone = true;
        this.response = builder.build();

		try {
			if (null != response) {
				onSuccess(response);
			} else {
				onFail(t);
			}
		} catch (Throwable t) {
			onComplete(response, t);
		}
		return response;
	}

	@Override
	public void onThrowable(Throwable t) {
		if(isDone){
			return;
		}
		isDone = true;
		try {
			onFail(t);
		} catch (Throwable t1) {
			onComplete(response, t1);
		}
		this.t = t;
	}

}
