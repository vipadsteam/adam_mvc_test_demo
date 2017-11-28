/**
 * 
 */
package org.adam.asyn.web.service.client;

import java.nio.charset.CodingErrorAction;

import org.adam.asyn.web.common.log.LogService;
import org.adam.asyn.web.service.callback.TestHttpFutureCallback;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.protocol.HTTP;
import org.springframework.adam.common.utils.AdamExceptionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author USER
 *
 */
@Component
public class HttpTestClient implements InitializingBean {

	@Autowired
	private LogService logService;

	private CloseableHttpAsyncClient httpAsyncClient;

	private Header header;

	private volatile RequestConfig config;

	/**
	 * 调用VRE
	 * 
	 * @param slots
	 * @param userType
	 * @param warehouse
	 * @param client
	 * @return
	 */
	public TestHttpFutureCallback call(String url) {
		CloseableHttpAsyncClient httpAsyncClient = getHttpClient();
		HttpPost httppost = new HttpPost(url);
		httppost.setConfig(config);
		httppost.setHeader(header);

		String vreInfoStr = "";
		String queryStr = "";
		TestHttpFutureCallback callback = null;
		try {
			// 请求发送
			callback = new TestHttpFutureCallback();
			httpAsyncClient.execute(httppost, callback);
		} catch (Exception e) {
			vreInfoStr = queryStr;
			String logStr = AdamExceptionUtils.getStackTrace(e);
			logService.sendTechnologyErrorAccountLog(vreInfoStr, logStr, url, "test http发送系统异常");
		}

		return callback;
	}

	/**
	 * 获取httpclient
	 * 
	 * @return
	 */
	private CloseableHttpAsyncClient getHttpClient() {
		if (null == this.httpAsyncClient) {
			for (int i = 0; i < 3; i++) {
				try {
					init();
				} catch (Exception e) {
					String logStr = AdamExceptionUtils.getStackTrace(e);
					logService.sendTechnologyErrorAccountLog("", logStr, "", "HttpClient系统异常");
				}
			}
		}
		return this.httpAsyncClient;
	}

	private synchronized void init() throws Exception {
		if (null != this.httpAsyncClient) {
			return;
		}
		this.httpAsyncClient = initAsynHttpClient(40, false);
		this.header = new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
		refresh();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}

	public void refresh() {
		Integer timeout = 180000;
		this.config = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).build();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private static CloseableHttpAsyncClient initAsynHttpClient(int timeout, boolean isSetTimeout) throws Exception {
		Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder.<SchemeIOSessionStrategy> create().register("http", NoopIOSessionStrategy.INSTANCE).build();
		ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
		PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(ioReactor, sessionStrategyRegistry);
		// Create message constraints
		MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(20000).build();
		// Create connection configuration
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).setMessageConstraints(messageConstraints).build();

		connectionManager.setDefaultConnectionConfig(connectionConfig);

		// 设置连接池的属性
		connectionManager.setMaxTotal(500); // 连接池最大数
		connectionManager.setDefaultMaxPerRoute(500); // 每个路由基础连接数

		CloseableHttpAsyncClient httpAsyncClient = null;
		if (isSetTimeout) {
			// 设置socket的timeout 与 连接timeout
			RequestConfig config = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).build();
			httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(config).build();
		} else {
			httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(connectionManager).build();
		}
		httpAsyncClient.start();
		return httpAsyncClient;
	}
}
