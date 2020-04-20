<%@ page pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>支付页面</title>
</head>
<body>
<form method="post" enctype="application/x-www-form-urlencoded" action="/sendRequest">
    <div>
        金额: <input type="number" name="amount" />
    </div>
    <div>
        通道:
        <input type="radio" name="channel" value="1">微信
        <input type="radio" name="channel" value="2">支付宝
        <input type="radio" name="channel" value="3">银行卡
        <input type="radio" name="channel" value="11">微信手机号
        <input type="radio" name="channel" value="12">支付宝飞行转卡
        <input type="radio" name="channel" value="13">微信赞赏码
        <input type="radio" name="channel" value="14">支付宝固码
    </div>
    <div>
        <button type="submit">支付</button>
    </div>
</form>
</body>
</html>