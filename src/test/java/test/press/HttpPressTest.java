package test.press;

import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;

public class HttpPressTest {

	public static volatile int press_times = 1000;

	public static int threads = 20;

	public static int time = 1000;

	private static AtomicLong errorCount = new AtomicLong(0);

	private static final String addr = "127.0.0.1:8080";

	private static CloseableHttpAsyncClient httpAsyncClient;

	private static Header header;

	private static RequestConfig config;
	
	public static void main(String[] args) throws Exception {
		call();
	}

	public static void call() throws Exception {
		HttpPressTest a = new HttpPressTest();
		int i = 0;

		httpAsyncClient = initAsynHttpClient(40, false);

		refresh();
		
		int request = 1;

		a.new Worker(0, request).call();
		
//		while (true) {
//			errorCount = new AtomicLong(0);
//			a.testReq2(request, threads, time);
//			i++;
//			if (i >= press_times) {
//				return;
//			}
//		}
	}

	private static void refresh() {
		Integer timeout = 10000;
		config = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout).build();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private static CloseableHttpAsyncClient initAsynHttpClient(int timeout, boolean isSetTimeout) throws Exception {
		Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder.<SchemeIOSessionStrategy>create()
				.register("http", NoopIOSessionStrategy.INSTANCE).build();
		ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
		PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(ioReactor,
				sessionStrategyRegistry);
		// Create message constraints
		MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
				.setMaxLineLength(20000).build();
		// Create connection configuration
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
				.setMessageConstraints(messageConstraints).build();

		connectionManager.setDefaultConnectionConfig(connectionConfig);

		// 设置连接池的属性
		connectionManager.setMaxTotal(500); // 连接池最大数
		connectionManager.setDefaultMaxPerRoute(500); // 每个路由基础连接数

		CloseableHttpAsyncClient httpAsyncClient = null;
		if (isSetTimeout) {
			// 设置socket的timeout 与 连接timeout
			RequestConfig config = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
					.setConnectionRequestTimeout(timeout).build();
			httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(config).build();
		} else {
			httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(connectionManager).build();
		}
		httpAsyncClient.start();
		return httpAsyncClient;
	}

	public void testReq2(int request, int threads, int times) {
		ExecutorService executor = Executors.newFixedThreadPool(threads);
		List<Future<Long>> results = new ArrayList<Future<Long>>();
		for (int index = 1; index < times; index++) {
			results.add(executor.submit(new Worker(index, request)));
		}
		executor.shutdown();
		try {
			while (!executor.awaitTermination(1, TimeUnit.SECONDS))
				;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long sum = 0;
		for (Future<Long> result : results) {
			try {
				sum += result.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("---------------------------------");
		System.out.println("now:" + new Date());
		System.out.println("number of threads :" + threads + " times:" + times);
		System.out.println("running time: " + sum + "ms");
		System.out.println("running time per request: " + ((double) sum / (double) times) + "ms");
		System.out.println("success: " + ((double) (times - errorCount.get()) / (double) times) * 100 + "%"
				+ " error count:" + errorCount.get());
		System.out.println("QPS: " + ((double) times / (((double) sum / (double) threads) / (double) 1000)));
		System.out.println("---------------------------------");
	}

	class Worker implements Callable<Long> {

		private int request;

		private int i;

		public Worker(int i, int request) {
			super();
			this.request = request;
		}

		@Override
		public Long call() {
			String url = "http://" + addr + "/adam/request0?param=" + request;
			long begin = System.currentTimeMillis();
			try {
				HttpPost httppost = new HttpPost(url);
				httppost.setConfig(config);
				httppost.setHeader(header);
				Future<HttpResponse> future = httpAsyncClient.execute(httppost, new FutureCallback<HttpResponse>() {
					@Override
					public void completed(HttpResponse result) {
						// nothing to do
					}

					@Override
					public void failed(Exception ex) {
						ex.printStackTrace();
					}

					@Override
					public void cancelled() {
						// nothing to do
					}
				});
				HttpResponse httpResponse = future.get();
			} catch (Exception e) {
				e.printStackTrace();
				errorCount.incrementAndGet();
			}
			Long end = System.currentTimeMillis();
			Long spent = end - begin;
			return spent;
		}
	}
}