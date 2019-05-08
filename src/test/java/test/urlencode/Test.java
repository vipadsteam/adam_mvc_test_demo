/**
 * 
 */
package test.urlencode;

import org.adam.asyn.web.chain.adam.AdamTestStep0_0;
import org.springframework.adam.common.bean.ResultVo;

/**
 * @author USER
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		System.out.println(AdamTestStep0_0.class.getDeclaredMethod("doService", Object.class, ResultVo.class));
	}

}
