/**
 * 
 */
package org.adam.asyn.web;

import org.adam.gensource.VersionAnnotation;
import org.adam.version.Version;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author USER
 *
 */
@SpringBootApplication
// @ComponentScan(basePackages = { "org.springframework.adam", "org.adam.asyn" })
@ComponentScan()
@VersionAnnotation(value="1.1.1", path="D:\\")
public class Application {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Version.getVersion());
		SpringApplication.run(Application.class, args);
	}

}
