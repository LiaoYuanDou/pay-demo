# pay-demo
## 支付宝和微信支付
### 支付宝

1. 导包(SDK)
2. 获得初始化的AlipayClient（URL，应用唯一标识，应用私钥，参数返回格式，编码格式，支付宝公钥，签名算法）
3. 获取支付请求
4. 支付请求中设置请求报文
5. 通过AlipayClient.execute(request)得到response
6. 解析response执行下一步逻辑
 
