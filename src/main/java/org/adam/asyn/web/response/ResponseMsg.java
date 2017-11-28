/**
 * 
 */
package org.adam.asyn.web.response;

import java.io.Serializable;

/**
 * @author USER
 *
 */
public class ResponseMsg<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Integer STATUS_SUCCESS = 0;

	public static final Integer STATUS_FAILED = -1;

	private int status;

	private String msg;

	private T data;

	/**
	 * 空构造函数
	 */
	public ResponseMsg() {
		super();
	}

	/**
	 * 全字段构造函数
	 * 
	 * @param status
	 * @param msg
	 * @param data
	 */
	public ResponseMsg(int status, String msg, T data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * 失败
	 * 
	 * @param message
	 */
	public void fail(String message) {
		this.setStatus(STATUS_FAILED);
		this.setMsg(message);
	}

	/**
	 * 成功
	 * 
	 * @param message
	 * @param data
	 */
	public void success(String message, T data) {
		this.setStatus(STATUS_SUCCESS);
		this.setMsg(message);
		this.setData(data);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseMsg [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}
}
