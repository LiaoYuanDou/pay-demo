# pay-demo
## 支付宝和微信支付

### 支付宝支付接口接入
    
1.导包(SDK);

   alipay-sdk-java-3.0.0.jar、commons-logging-1.1.1.jar 
   
2.获得初始化的AlipayClient（URL，应用唯一标识，应用私钥，参数返回格式，编码格式，支付宝公钥，签名算法）;
    

   ``
   
    AlipayClient alipayClient = new DefaultAlipayClient(serverdevUrl, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET,
				ALIPAY_PUBLIC_KEY, SIGN_TYPE);
				
   ``  
3. 获取支付请求;

  ``
  
    AlipayTradePayRequest payRequest = new AlipayTradePayRequest();
    
  `` 
4. 支付请求中设置请求报文;

  ``
  
    AlipayTradePayModel payModel = new AlipayTradePayModel();
	  	payModel.setOutTradeNo(System.currentTimeMillis() + "");
	  	payModel.setSubject("");
		payModel.setTotalAmount("");
		payModel.setAuthCode("");// 沙箱钱包中的付款码
		payModel.setScene("");
	  	payRequest.setBizModel(payModel);
		
  ``
5. 通过AlipayClient.execute(request)得到response;

  ``
  
    AlipayTradePayResponse payResponse = alipayClient.execute(payRequest);
    
  ``
6. 解析response.
 

### 微信支付接口接入

1. 导包(SDK);
   wxpay-sdk-0.0.3.jar
2. 写一个配置类实现WXPayConfig接口;
   重写里面的getAppID、getKey、getMchID、getCertStream、getHttpConnectTimeoutMs和getHttpReadTimeoutMs方法
3. new一个配置类和WXPay（配置类，签名加密算法，是否沙箱环境）;
4. new一个hashMap<String,String>(),将数据封装在map中;
5. 以map为参数调用wxPay类的方法可以得到一个返回数据的map;
6. 解析返回数据map.

