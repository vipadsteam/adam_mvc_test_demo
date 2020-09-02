/**
 * 
 */
package org.adam.asyn.web.common.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.adam.backpressure.AdamBPDicHolder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author nixiaorui
 *
 */
@Service
public class AppConfig implements InitializingBean {

	private static Map<String, String> conf = new HashMap<>();

	@Override
	public void afterPropertiesSet() throws Exception {
		conf.put("bp@req0@arf", "Y");
		conf.put("bp@req0@minRate", "1");
		conf.put("bp@req0@maxRate", "1000");
		conf.put("bp@req0@errorStep", "20");
		conf.put("bp@req0@fixStep", "5");
		AdamBPDicHolder.update(conf);
	}

}
