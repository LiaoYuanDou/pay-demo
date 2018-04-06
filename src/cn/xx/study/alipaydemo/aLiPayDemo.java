package cn.xx.study.alipaydemo;

import org.junit.Test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;

public class aLiPayDemo {

	public static final String serverdevUrl = "https://openapi.alipaydev.com/gateway.do"; // 阿里支付调试接口
	public static final String serverUrl = "https://openapi.alipay.com/gateway.do"; // 阿里支付线上接口
	public static final String APP_ID = "2016091000481529"; // 支付宝申请的生成应用唯一标识
	public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJaAkDvEvLkGHu8slbm/cEJQ/eD4cEIosX89M3UflmUQgGYJE911pCHxF6I31kadRFaTMESLUMDo62+MI/1FDjB71flwIHxP6ZHZkIdRYDeray/dpMGCDqVwIZlNKHyOC93Ov+OMjXFInc3u/jZQyE9nYpmHcyewUR4MUXpIHm/U93DsjalJ94mLevNvG0Ht8rBBoF6Y6jfAcuVVESKGaV28ITH0NRVge82HV5XZyj8GcrZXUXv3N9RjuLpM6i2OVlz4N4AFKwxK9nVJAszrHhtSJIFVPaX8YN4xJXpi3JB21PVqFsaQ/txl7smxadcoUzRR1Jv/jfQJnQG6KV0Y09AgMBAAECggEAWDHHf3dPqxQ8V9PL/Jnp/eYP/jc3MSc+4hvlWV+rVcny/azj7K6aR1CLtjqrVFU9I2Q3k6tbUabSYYzfmXqchGxGV+GkMvp1dC/0NuJ4Q7lzJQYdJLRkBCXeui60xuTfo932N5GZD7wmFPefP/RvCbTr+RzcfTFK+sOrndZAbtL8Y0NwP8ZUsye8aewYwpKt3jlvaE37vUZI5mxqxiz3W8Ck49O2rC+k24GNK0kXWPE4ICaSVYPBKc0JHqrPnplsEwQgVxXzKsEC8qFJAfbsG0puJBNZOGbijbDYvJD/Qi2PEV75oCdjs0n1kC5MgFp94DB/XgciGlJcpgU7srSVQQKBgQDOKvKeRAu/m3hsLeSsdFXvQ44WThLPFwrR9KJx5whOx87ckGtiXQhFD0Z2DbuG3FEHfJkfxvCVsvaq8JZQ2CZZ/twKuuPDBv1ZtKdtajAHQaz8mp2LlRLQdObQYq9v+sgLt+BwxGmq7xpxZPtwtHk8Aom5Vw316hq/tbbIRv4vcQKBgQCqnlYqzLs//54wVxObVDl5MdoqPjFwWJ8OsqV4NgbSSD3PeI9cW2nzIFWOK6CC1mOvAlu7AMqKk1L/bJd/61j807WjNdo69Nu662N+VQCfmKib/rqXM8dmdjJHY5ZfoGRc/b7r9W76mS/A8bfzYOnEAp4SVw9FFVbJyOGW5UosjQKBgGBlvml34ZJmrEjQsJJeFuw3xxDw0mSYHowK+YWOg9jqHfQNZthoLXy05ro+M4ecLhr1DFfKWiV0t1c4BOCqIEyGGPa/ac1/0CgIixxiwTAzaiH2XWGtg02Ky312Fxehx7WK9Vtkyw+Mmu2YTOTmzaTcfu71IGbyXi1zVpKQdztRAoGAe4KXefAu9QtEJlZCuUTlPhiah83x2BQvQGKOalEh6wOkyxXJIeCC+B9GD98BGPW9v6kDYoIWHO69PG4N1eo2xv8zE+0eB2PN1KglpARAqUfHdvWVeXNgO4oAp7Et4Bepz69YkFNhfUN7UD8rHKHhV6VVbI1dXnMuGnCy7ZsacNkCgYEAhRl3sABvyiY3+3xgTK8I7qKBID/DXdDCQ5gW8NYU2N/r+xyIENJgV+Gx9Rxlii3u4ts2WAkA6quetY1cKcGrvglouKcJVFRDPod1DJuPVc6scW+4Jm0+A2GNXPGzNGqaQY4llV6dkDuofA8kWhZO2G1n5JAYCRbtMF5HQffP9/E="; // RSA密钥包含应用私钥
	public static final String FORMAT = "json"; // 参数返回格式
	public static final String CHARSET = "UTF-8"; // 请求和签名使用的字符编码格式
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuZkWSioqKtgVBzKZ9+/fDu1ke9P8+XbKjwBGwyvX5wTtLHW/klgM4iKWGDsZhzVJidBLwDB1tPHo8u1MxqY0YzZ9KbHZUU09BfeQVjbFd3sHHt0ijZJG/to5iogbeGR+XH1LH2ENmKXPrrkj61Sar5Dq9rmexVymCe1fTFABsG/2oQEurc68TF/AfGbm2BzCMpL7fb8P3L6iyS5+ctGbzLFPCHJK0YiBrWWoE3ZoTN6/Vt3wBV+3tiPEaQqpcmWQ54cOWd3QU6tsTYoXtwmEDrW6mxaQT1UG570J+9f7DGXp/3QKLtSAcLoxKwweFegd7GTFI2NDHt0EdxZ6eRhk3QIDAQAB"; // 支付宝公钥
	public static final String SIGN_TYPE = "RSA2"; // 签名算法类型
	public static AlipayClient alipayClient = null;
	// 获得初始化的AlipayClient
	static {
		alipayClient = new DefaultAlipayClient(serverdevUrl, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET,
				ALIPAY_PUBLIC_KEY, SIGN_TYPE);
	}

	public static void main(String[] args) throws Exception {
		/** 支付方法 */
		// payDemo();
		payDemoTest();
	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	private static String pjBaoWen(String... args) {
		StringBuffer sb = new StringBuffer();
		String fenGeFu = "\"";
		String lianJieFu = ":";
		sb.append("{");
		for (int i = 0; i < args.length; i = i + 2) {
			if (i == args.length - 2) {
				sb.append(fenGeFu).append(args[i]).append(fenGeFu).append(lianJieFu).append(fenGeFu).append(args[i + 1])
						.append(fenGeFu);
			} else {
				sb.append(fenGeFu).append(args[i]).append(fenGeFu).append(lianJieFu).append(fenGeFu).append(args[i + 1])
						.append(fenGeFu).append(",");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 
	 * @throws AlipayApiException
	 */
	private static void payDemo() throws AlipayApiException {

		String OUT_TRADE_NO = "out_trade_no"; // 商户订单号
		String SCENE = "scene"; // 条码支付固定传入bar_code
		String BAR_CODE = "bar_code";
		String AUTH_CODE = "auth_code"; // 用户付款码
		String SUBJECT = "subject"; // 订单标题
		String STORE_ID = "store_id"; // 商户门店编号
		String TOTAL_AMOUNT = "total_amount"; // 订单金额
		String TIMEOUT_EXPRESS = "timeout_express"; // 交易超时时间

		AlipayTradePayRequest payRequest = new AlipayTradePayRequest();

		// 拼接报文
		String pjBaoWen = pjBaoWen(OUT_TRADE_NO, "20180406112500001", SCENE, BAR_CODE, AUTH_CODE, "1234567890123456",
				SUBJECT, "demo", STORE_ID, "xx", TOTAL_AMOUNT, "10000", TIMEOUT_EXPRESS, "6000");

		// 设置发送报文
		payRequest.setBizContent(pjBaoWen);

		// 发送支付请求
		AlipayTradePayResponse payResponse = alipayClient.execute(payRequest);

		// 返回结果
		if ("10000".equals(payResponse.getCode())) {
			System.out.println("支付成功");
		} else if ("40004".equals(payResponse.getCode())) {
			System.out.println("支付失败" + payResponse.getMsg());
		} else if ("10003".equals(payResponse.getCode())) {
			System.out.println("等待用户付款");
		} else if ("20000".equals(payResponse.getCode())) {
			System.out.println("未知异常" + payResponse.getMsg());
		}
	}

	private static void payDemoTest() {
		AlipayTradePayRequest payRequest = new AlipayTradePayRequest();
		AlipayTradePayModel payModel = new AlipayTradePayModel();
		payModel.setOutTradeNo(System.currentTimeMillis() + "");
		payModel.setSubject("Iphone6 16G");
		payModel.setTotalAmount("1");
		payModel.setAuthCode("284851788288785120");// 沙箱钱包中的付款码
		payModel.setScene("bar_code");
		payRequest.setBizModel(payModel);

		AlipayTradePayResponse payResponse = null;

		try {
			payResponse = alipayClient.execute(payRequest);
			System.out.println(payResponse.getBody());
			System.out.println(payResponse.getTradeNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPJ() {
		String pjBaoWen = pjBaoWen("out_trade_no", "20150320010101001", "trade_no", "2014112611001004680073956707");
		System.out.println(pjBaoWen);
		String OUT_TRADE_NO = "out_trade_no"; // 商户订单号
		String SCENE = "scene"; // 条码支付固定传入bar_code
		String BAR_CODE = "bar_code";
		String AUTH_CODE = "auth_code"; // 用户付款码
		String SUBJECT = "subject"; // 订单标题
		String STORE_ID = "store_id"; // 商户门店编号
		String TOTAL_AMOUNT = "total_amount"; // 订单金额
		String TIMEOUT_EXPRESS = "timeout_express"; // 交易超时时间
		String pjBaoWen2 = pjBaoWen(OUT_TRADE_NO, "20180406112500001", SCENE, BAR_CODE, AUTH_CODE, "1234567890123456",
				SUBJECT, "demo", STORE_ID, "xx", TOTAL_AMOUNT, "10000", TIMEOUT_EXPRESS, "6000");
		System.out.println(pjBaoWen2);
	}

}
