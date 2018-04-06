package cn.xx.study.weixinpaydemo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.github.wxpay.sdk.WXPayConfig;

public class MyConfig implements WXPayConfig {

	private byte[] certData;

	public MyConfig() throws Exception {
		String certPath = "/path/to/apiclient_cert.p12";
		File file = new File(certPath);
		InputStream certStream = new FileInputStream(file);
		this.certData = new byte[(int) file.length()];
		certStream.read(this.certData);
		certStream.close();
	}

	/**
	 * appid是微信公众账号或开放平台APP的唯一标识
	 */
	@Override
	public String getAppID() {
		return "1234567890";
	}

	/**
	 * 交易过程生成签名的密钥
	 */
	@Override
	public String getKey() {
		return "1234567890";
	}

	/**
	 * 由微信支付分配的商户收款账号。
	 */
	@Override
	public String getMchID() {
		return "1234567890";
	}

	@Override
	public InputStream getCertStream() {
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
		return certBis;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		return 6000;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		return 6000;
	}

}
