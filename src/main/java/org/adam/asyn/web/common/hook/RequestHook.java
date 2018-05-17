/**
 * 
 */
package org.adam.asyn.web.common.hook;

import java.util.Map;

import org.springframework.adam.common.utils.ThreadLocalHolder;
import org.springframework.adam.service.IRequestHook;
import org.springframework.stereotype.Component;

/**
 * @author USER
 *
 */
@Component
public class RequestHook implements IRequestHook {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.adam.service.IRequestHook#doBefore(java.lang.String,
	 * java.util.Map, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public Object doBefore(String url, Map<String, String> headersMap, Object[] income, Object output) throws Exception {
		ThreadLocalHolder.setRequestLogFlag(2);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.adam.service.IRequestHook#doAfter(java.lang.String,
	 * java.util.Map, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public Object doAfter(String url, Map<String, String> headersMap, Object[] income, Object output) throws Exception {
		return output;
	}

}
