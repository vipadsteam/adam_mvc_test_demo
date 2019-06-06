/**
 * 
 */
package org.adam.asyn.web.service;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author USER
 *
 */
public class BeanService implements InitializingBean {

	public String result;

	public BeanService() {
		super();
	}

	public BeanService(String result) {
		super();
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		result = result + "ed";
	}

}
