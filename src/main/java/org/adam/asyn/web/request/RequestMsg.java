/**
 * 
 */
package org.adam.asyn.web.request;

import java.io.Serializable;

/**
 * @author USER
 *
 */
public class RequestMsg implements Serializable {

	private static final long serialVersionUID = 1L;

	private String param;

	public RequestMsg() {
		super();
	}

	public RequestMsg(String param) {
		super();
		this.param = param;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}
