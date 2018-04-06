package cn.xx.study.weixinpaydemo;

import java.util.HashMap;
import java.util.Map;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants.SignType;

public class WeiXinPayDemo {

	public static void main(String[] args) throws Exception {

		MyConfig myConfig = new MyConfig();
		WXPay wxPay = new WXPay(myConfig, SignType.MD5, true);

		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("out_trade_no", "");

		try {
			Map<String, String> queryMap = wxPay.orderQuery(dataMap); // ������ѯ
			// wxpay.unifiedOrder(dataMap); // ͳһ�µ�
			// wxpay.refundQuery(dataMap); // �˿��ѯ
			// wxpay.downloadBill(dataMap); // ����
			System.out.println(queryMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
