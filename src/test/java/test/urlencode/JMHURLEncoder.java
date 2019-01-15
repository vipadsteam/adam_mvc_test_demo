/**
 * 
 */
package test.urlencode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.net.URLCodec;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.adam.common.utils.encode.AdamURLEncoder;

/**
 * @author USER
 *
 */
@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JMHURLEncoder {

	private String text = "https://mst.vip.com/uploadfiles/exclusive_subject/te/v1/a-2QHdGWieJKpYzBz135yQ.php?wapid=mst_5833392&_src=mst&page_msg=VIP_NH-all-onsale&extra_banner=20833392&mst_cdi=1&mst_page_type=guide&extra_type=1";

	private AdsCache cache = new AdsCache();
	
	private final URLCodec urlCodec = new URLCodec();

	
	@Setup
	public void prepare() throws Exception {
		cache.refresh("30");
//		File file = new File("test_urlencode");
//		BufferedReader reader = new BufferedReader(new FileReader(file));
//		String tempString = null;
//		while ((tempString = reader.readLine()) != null) {
//			// 显示行号
//			text = text + tempString;
//		}
//		reader.close();
	}

	/**
	 * @param args
	 * @throws RunnerException
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(JMHURLEncoder.class.getSimpleName()).forks(2) // 进程数
				.warmupIterations(3) // 预热的迭代次数
				.measurementIterations(10) // 实际测量的迭代次数
				.build();
		new Runner(opt).run();
	}

	@Benchmark
	public void testUrlEncode0() throws Exception {
		for (int i = 0; i < 20000; i++) {
			urlCodec.encode(text, CharEncoding.UTF_8);
		}
	}
	
	@Benchmark
	public void testUrlEncode1() throws Exception {
		for (int i = 0; i < 20000; i++) {
			URLEncoder.encode(text, CharEncoding.UTF_8);
		}
	}
	
	@Benchmark
	public void testUrlEncode2() throws Exception {
		AdamURLEncoder.setRealTimeCache(null);
		for (int i = 0; i < 20000; i++) {
			AdamURLEncoder.encode(text, true);
		}
	}

	@Benchmark
	public void testUrlEncode3() throws Exception {
		AdamURLEncoder.setCache(cache);
		for (int i = 0; i < 20000; i++) {
			AdamURLEncoder.encode(text, true);
		}
	}
}
