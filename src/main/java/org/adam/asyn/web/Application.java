/**
 * 
 */
package org.adam.asyn.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author USER
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.springframework.adam", "org.adam.asyn" })
public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
