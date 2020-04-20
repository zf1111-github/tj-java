<%@ page pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>正在跳转到支付页面...</title>
</head>
<body>
<form method="post" id="sendRequestForm" enctype="application/x-www-form-urlencoded" action="http://pay.tj9999.vip/api/Pay/Channel">
    <input type="hidden" name="Amount" value="${payRequest.amount}" />
    <input type="hidden" name="AppKey" value="${payRequest.appKey}" />
    <input type="hidden" name="CallBackUrl" value="${payRequest.callbackUrl}"/>
    <input type="hidden" name="Channel" value="${payRequest.channel}" />
    <input type="hidden" name="MerchantReferenceNumber" value="${payRequest.mrn}" />
    <input type="hidden" name="NotifyUrl" value="${payRequest.notifyUrl}" />
    <input type="hidden" name="Sign" value="${payRequest.sign}" />
    <script>
        window.onload = function () {
            document.getElementById('sendRequestForm').submit();
        }
    </script>
</form>
</body>
</html>