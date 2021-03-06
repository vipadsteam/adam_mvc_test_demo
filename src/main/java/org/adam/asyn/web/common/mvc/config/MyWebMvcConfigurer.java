
package org.adam.asyn.web.common.mvc.config;

import java.util.List;

import org.springframework.adam.client.ILogService;
import org.springframework.adam.common.bean.contants.AdamSysConstants;
import org.springframework.adam.common.utils.AdamTimeUtil;
import org.springframework.adam.common.utils.ThreadLocalHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptorAdapter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

	@Autowired(required = false)
	private ILogService logService;

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		// nothing to do
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		// nothing to do
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		configurer.setDefaultTimeout(6000);

		configurer.registerDeferredResultInterceptors(new DeferredResultProcessingInterceptorAdapter() {
			@Override
			public <T> boolean handleTimeout(NativeWebRequest req, DeferredResult<T> result) {
				return result.setErrorResult("my web mvc request timeout");
			}

			@Override
			public <T> void afterCompletion(NativeWebRequest request, DeferredResult<T> deferredResult)
					throws Exception {

				String result = ILogService.obj2Str(deferredResult.getResult());
//				System.out.println(Thread.currentThread().getId() + " my mvc response is : " + result);
				if (ThreadLocalHolder.getStatus() >= 0 && null != logService && logService.isNeedLog()) {
					StringBuilder argSB = new StringBuilder(2048);
					argSB.append("used time:");
					argSB.append(AdamTimeUtil.getNow() - ThreadLocalHolder.getBegin());
					argSB.append(AdamSysConstants.LINE_SEPARATOR);
					argSB.append(ILogService.obj2Str(deferredResult.getResult()));
					logService.sendEndRequestLog(argSB);
					ThreadLocalHolder.setStatus(-1);
				}
			}
		});
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// nothing to do
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		// nothing to do
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// nothing to do
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// nothing to do
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// nothing to do
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// nothing to do
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// nothing to do
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		// nothing to do
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// nothing to do
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// nothing to do
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		// nothing to do
	}

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		// nothing to do
	}

	@Override
	public Validator getValidator() {
		// nothing to do
		return null;
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		// nothing to do
		return null;
	}

}
