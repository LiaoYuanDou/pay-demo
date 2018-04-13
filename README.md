# pay-demo
 随着智能手机的普及支付的发展也不断的变化；变得越来越简单、方便，从现金到刷卡到现在只需要一部手机就可以完成一系列的交易，所以移动支付不仅是一种趋势，也必将成为一种形式。
 
 移动支付的优势：支付便捷 通过支付宝微信等第三支付平台轻松完成支付。资金安全，资金都是通过第三方支付平台，安全有保障 。
 
 移动支付两大巨头：支付宝和微信。
 
## 支付宝和微信支付

### 支付宝支付接口接入

1、在支付宝开放平台创建一个应用；

	1）您需要先去蚂蚁金服开放平台（open.alipay.com），在开发者中心创建登记您的应用，此时您将获得应用唯一标识（APPID）；
	
	2）请在【功能信息】中点击【添加功能】，选择当面付、手机网站支付和电脑网站支付；
	
	3）提交审核，等待审核通过，该应用正式可以使用。
	
2、配置密钥；

	1）开发者调用接口前需要先生成RSA密钥，RSA密钥包含应用私钥(APP_PRIVATE_KEY)、应用公钥(APP_PUBLIC_KEY）；
	
	2）生成密钥后在开放平台管理中心进行密钥配置，配置完成后可以获取支付宝公钥(ALIPAY_PUBLIC_KEY)。
	
3、搭建和配置开发环境

	1）下载服务端SDK；
	
		alipay-sdk-java-3.0.0.jar、commons-logging-1.1.1.jar 
		
	2）接口调用配置;获得初始化的AlipayClient（URL，应用唯一标识，应用私钥，参数返回格式，编码格式，支付宝公钥，签名算法）.
	
		AlipayClient alipayClient = new DefaultAlipayClient(serverdevUrl, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET,
				ALIPAY_PUBLIC_KEY, SIGN_TYPE);

	URL			支付宝网关（固定）		https://openapi.alipay.com/gateway.do
	APPID			APPID 		       	       即创建应用后生成	
	APP_PRIVATE_KEY		开发者私钥		   	   由开发者自己生成	
	FORMAT			参数返回格式，只支持json	       json（固定）
	CHARSET			编码集，支持GBK/UTF-8		  开发者根据实际工程编码配置
	ALIPAY_PUBLIC_KEY	支付宝公钥			   由支付宝生成	      
	SIGN_TYPE		商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2	RSA2

4、接口调用

	1）当面付
		
		// 获取支付请求;
		AlipayTradePayRequest payRequest = new AlipayTradePayRequest(); 
		
		// 支付请求中设置请求报文;
		AlipayTradePayModel payModel = new AlipayTradePayModel();
	  	payModel.setOutTradeNo(System.currentTimeMillis() + "");
	  	payModel.setSubject("");
		payModel.setTotalAmount("");
		payModel.setAuthCode("");// 沙箱钱包中的付款码
		payModel.setScene("");
	  	payRequest.setBizModel(payModel);
		
		// 通过AlipayClient.execute(request)得到response;
		AlipayTradePayResponse payResponse = alipayClient.execute(payRequest);
		
 	2）手机网站支付
	
		手机网站支付产品包含两类API：
		
		a、页面跳转类：需要从前端页面以Form表单的形式发起请求，浏览器会自动跳转至支付宝的相关页面（一般是收银台或签约页面），
		用户在该页面完成相关业务操作后再回跳到商户指定页面。例如本产品中的手机网站支付接口alipay.trade.wap.pay。
		
		b、系统调用类：直接从服务端发起HTTP请求，支付宝会同步返回请求结果。例如本产品中的交易查询等配套API。
	
		对于页面跳转类API，SDK不会也无法像系统调用类API一样自动请求支付宝并获得结果，而是在接受request请求对象后，
		为开发者生成前台页面请求需要的完整form表单的html（包含自动提交脚本），商户直接将这个表单的String输出到http response中即可。
	
		public void doPost(HttpServletRequest httpRequest,
		HttpServletResponse httpResponse) throws ServletException, IOException {
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, 						APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
			AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
			alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
			alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
			alipayRequest.setBizContent("{" +
			" \"out_trade_no\":\"20150320010101002\"," +
			" \"total_amount\":\"88.88\"," +
			" \"subject\":\"Iphone6 16G\"," +
			" \"product_code\":\"QUICK_WAP_PAY\"" +
			" }");//填充业务参数
			String form="";
			try {
			form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
			} catch (AlipayApiException e) {
			e.printStackTrace();
			}
			httpResponse.setContentType("text/html;charset=" + CHARSET);
			httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
			httpResponse.getWriter().flush();
			httpResponse.getWriter().close();
		}

			out_trade_no		商户订单号，需要保证不重复
			subject			订单标题
			total_amount		订单金额
		
		异步通知验签：
		
		Map<String, String> paramsMap = ... //将异步通知中收到的所有参数都存放到map中
		boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE) //调用SDK验证签名
		if(signVerified){
		// TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户				自身业务处理，校验失败返回failure
		}else{
		// TODO 验签失败则记录异常日志，并在response中返回failure.
		}
		
			trade_no	支付宝28位交易号
			trade_status	交易状态
			total_amount	订单金额
		
	3）电脑网站支付
	
		统一收单下单并支付页面接口alipay.trade.page.pay：
		
		public void doPost(HttpServletRequest httpRequest,
                      HttpServletResponse httpResponse) throws ServletException, IOException {
			    AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, 						APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE); //获得初始化的AlipayClient
			    AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
			    alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
			    alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
			    alipayRequest.setBizContent("{" +
				"    \"out_trade_no\":\"20150320010101001\"," +
				"    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
				"    \"total_amount\":88.88," +
				"    \"subject\":\"Iphone6 16G\"," +
				"    \"body\":\"Iphone6 16G\"," +
				"    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
				"    \"extend_params\":{" +
				"    \"sys_service_provider_id\":\"2088511833207846\"" +
				"    }"+
				"  }");//填充业务参数
			    String form="";
			    try {
				form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
			    } catch (AlipayApiException e) {
				e.printStackTrace();
			    }
			    httpResponse.setContentType("text/html;charset=" + CHARSET);
			    httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
			    httpResponse.getWriter().flush();
			    httpResponse.getWriter().close();
			}
			
		异步通知验签：
		
		Map<String, String> paramsMap = ... //将异步通知中收到的所有参数都存放到map中
		boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE) //调用SDK验证签名
		if(signVerfied){
		    // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续						商户自身业务处理，校验失败返回failure
		}else{
		    // TODO 验签失败则记录异常日志，并在response中返回failure.
		}
		
### 微信支付接口接入

1. 导包(SDK);
   wxpay-sdk-0.0.3.jar
2. 写一个配置类实现WXPayConfig接口;
   重写里面的getAppID、getKey、getMchID、getCertStream、getHttpConnectTimeoutMs和getHttpReadTimeoutMs方法
3. new一个配置类和WXPay（配置类，签名加密算法，是否沙箱环境）;
4. new一个hashMap<String,String>(),将数据封装在map中;
5. 以map为参数调用wxPay类的方法可以得到一个返回数据的map;
6. 解析返回数据map.

