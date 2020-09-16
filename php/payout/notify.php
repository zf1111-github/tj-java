<?php
//Amount=实际金额&AppKey=商户Key&MerchantReferenceNumber=平台单号&Key=商户密钥
$request = array (
    'Amount' => $_GET['Amount'],
    'AppKey' => $_GET['AppKey'],
    'MerchantReferenceNumber' => $_GET['MerchantReferenceNumber']
);
ksort($request);
$signContent = '';
foreach ($request as $key => $value) {
    $signContent .= "$key=$value&";
}

$signContent .= 'Key='.$secret;
$sign = md5($signContent);
if ($sign != $_GET['Sign']) {
    echo '签名错误';
} else {
    echo '签名正确';
    echo '<br />';
    print_r($request);
}
?>