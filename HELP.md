# BBC对接文档说明

## 充值接口对接

### 充值接口参数说明

|参数|必须|描述|
|---|---|---|
|Amount|是|订单金额，精确到分，比如：100元则应该提交10000 |
|AppKey|是|商户用户接口调用的Key信息，如果不清楚请联系客服|
|Channel|是|支付通道ID，整数类型，请参考通道ID表|
|MerchantReferenceNumber|是|商户平台订单号，字符串，最长可接纳64个字符|
|CallBackUrl|是|客人充值完成后用于返回的网址|
|NotifyUrl|是|充值成功后的回调接口，请求部分请参考回调参数说明|
|Sign|是|请求信息的签名信息，请参考充值接口签名说明|
|Payer|否|充值者的账户实名信息，注意，该参数为可选，不参与签名。如果有中文，请使用UTF-8编码|

### 充值接口签名说明

请求数据签名格式

`Amount=金额&AppKey=Key&CallBackUrl=返回地址&Channel=通道ID&MerchantReferenceNumber=平台单号&NotifyUrl=回调地址&Key=商户密钥`

>其中**商户密钥**为商户接口的密钥信息，如果不清楚请联系客服

签名为以上数据格式的MD5哈希以后16个字节的HEX小写字符串

### 页面对接模式

页面对接模式即是将所有的充值接口参数按照`application/x-www-form-urlencoded`表单使用UTF-8编码以POST方式提交到BBC的网关API `/api/Pay/Channel`，提交以后网页的控制权交与BBC充值平台。

### WebAPI 对接模式

将所有的充值接口参数按照`application/x-www-form-urlencoded`以表单的形式用POST方式提交到BBC的网关API `/api/Pay/Create`，该接口将返回以下信息（JSON对象）

`{
    success: true/false,
    errorMsg: 错误信息,
    payUrl: 支付页面跳转地址
}`

其中，支付页面跳转地址仅包含URL中的Path和QueryString部分，没有Protocol和Host，比如/api/Pay?id=some-id&channel=SomeChannel，商户需要根据当前的BBC对接网关地址拼接最后的跳转地址，跳转后，充值控制权交与BBC充值平台。

### 充值回调参数说明

|参数|必须|描述|
|---|---|---|
|Amount|是|客人实际支付金额，精确到分|
|AppKey|是|商户用户接口调用的Key信息，如果不清楚请联系客服|
|Channel|是|支付通道ID，整数类型，请参考通道ID表|
|MerchantReferenceNumber|是|商户平台订单号|
|OriginAmount|是|客人充值时提交的金额|
|Sign|是|请求信息的签名信息，请参考回调签名说明|

### 回调签名说明

请求数据签名格式

`Amount=实际金额&AppKey=商户Key&Channel=通道ID&MerchantReferenceNumber=平台单号&OriginAmount=请求金额&Key=商户密钥`

>其中**商户密钥**为商户接口的密钥信息，如果不清楚请联系客服

签名为以上数据格式的MD5哈希以后16个字节的HEX小写字符串

### 充值回调说明

BBC平台将所有的充值参数按照`application/x-www-form-urlencoded`以表单的形式用POST方式提交到充值时所留下的notifyUrl参数所指定的地址中，IP白名单请联系客服，回调成功时，商户应返回HTTP Status 200并以`success`字样作为响应内容。

### 查单接口说明

HTTP WebAPI 请求

`GET /api/Payment/Query?AppKey=商户Key&MerchantReferenceNumber=平台单号&Sign=签名`

签名格式

`AppKey=商户Key&MerchantReferenceNumber=平台单号&Key=商户密钥`

>其中**商户密钥**为商户接口的密钥信息，如果不清楚请联系客服

签名为以上数据格式的MD5哈希以后16个字节的HEX小写字符串

JSON 响应内容

`{error: "success", "result": {merchantReferenceNumber: "平台单号", status: 1/2/3}}`

status 为1时表示等待客人支付，2表示支付成功，3表示支付失败。如果返回的status是2，那么BBC平台会再次回调notifyUrl，请参考充值回调说明。

### 充值通道表

|ID|名称|
|---|---|
|1|微信|
|2|支付宝|
|3|银行卡|
|4|支付宝红包|
|5|云闪付|
|6|支付宝转银行卡|
|7|支付宝wap|
|8|微信wap|
|9|支付宝跳转|
|10|微信跳转|
|11|微信手机转账 |
|12|支付宝转卡飞行模式|
|13|微信赞赏码|
|14|支付宝固码|

## 下发接口

### 下发接口参数说明

|参数|必须|描述|
|---|---|---|
|Amount|是|订单金额，必须是整数，单位：元 |
|AppKey|是|商户用户接口调用的Key信息，如果不清楚请联系客服|
|BankName|是|银行名称|
|AccountName|是|银行账户名|
|AccountNumber|是|银行卡卡号|
|MerchantReferenceNumber|是|商户平台订单号，字符串，最长可接纳64个字符|
|NotifyUrl|是|出款成功后的回调接口，请求部分请参考回调参数说明|
|Sign|是|请求信息的签名信息，请参考充值接口签名说明|

### 下发签名说明

请求数据签名格式

`AccountName=账户名&AccountNumber=卡号&Amount=金额&AppKey=Key&BankName=银行名称&MerchantReferenceNumber=平台单号&NotifyUrl=回调地址&Key=商户密钥`

>其中**商户密钥**为商户接口的密钥信息，如果不清楚请联系客服

签名为以上数据格式的MD5哈希以后16个字节的HEX小写字符串

### 下发WebAPI说明

将所有的下发接口参数按照`application/json`以JSON对象的形式用POST方式提交到BBC的网关API `/api/Pay/Payout`，该接口将返回以下信息（JSON对象）

`{
    error: "success",
    errorMessage: "错误信息"
}`

当error为success时表示下发请求成功，请等待回调。否则，请参考如下错误表

|错误代码|描述|
|---|---|
|invalid_data|无效的请求数据，请检查您的JSON数据和请求参数|
|sign_error|签名错误|
|mrn_duplication|平台单号冲突|
|insufficient_balance|商户余额不足|
|object_not_found|无效的商户账户|

### 下发回调参数说明

|参数|必须|描述|
|---|---|---|
|Amount|是|下发金额，单位：元|
|AppKey|是|商户用户接口调用的Key信息，如果不清楚请联系客服|
|MerchantReferenceNumber|是|商户平台订单号|
|Sign|是|请求信息的签名信息，请参考回调签名说明|

### 下发回调签名说明

请求数据签名格式

`Amount=实际金额&AppKey=商户Key&MerchantReferenceNumber=平台单号&Key=商户密钥`

>其中**商户密钥**为商户接口的密钥信息，如果不清楚请联系客服

签名为以上数据格式的MD5哈希以后16个字节的HEX小写字符串

### 下发回调说明

BBC平台将所有的充值参数按照`application/x-www-form-urlencoded`以表单的形式用POST方式提交到充值时所留下的notifyUrl参数所指定的地址中，IP白名单请联系客服，回调成功时，商户应返回HTTP Status 200并以`success`字样作为响应内容。

### 下发查单接口说明

HTTP WebAPI 请求

`GET /api/Merchant/QueryPayout?AppKey=商户Key&MerchantReferenceNumber=平台单号&Sign=签名`

签名格式

`AppKey=商户Key&MerchantReferenceNumber=平台单号&Key=商户密钥`

>其中**商户密钥**为商户接口的密钥信息，如果不清楚请联系客服

签名为以上数据格式的MD5哈希以后16个字节的HEX小写字符串

JSON 响应内容

`{error: "success", "result": {merchantReferenceNumber: "平台单号", status: 1/2/3}}`

status 为1时表示等待下发，2表示下发成功，3表示下发失败。如果返回的status是2，那么BBC平台会再次回调notifyUrl，请参考下发回调说明。
