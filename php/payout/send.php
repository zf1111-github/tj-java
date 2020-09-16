<?php
function send_post($postUrl, $post_data = ''){
    if (empty($postUrl) || empty($post_data)) {
        return false;
    }
    
    $ch = curl_init();//初始化curl
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POST, 1);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $post_data);
    curl_setopt($ch, CURLOPT_URL,$postUrl);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array(                                                                          
        'Content-Type: application/json',                                                                                
        'Content-Length: ' . strlen($post_data))                                                                       
    );
    $data = curl_exec($ch);//运行curl
    curl_close($ch);
    return $data;
}
date_default_timezone_set('Asia/Shanghai');
$secret = 'd01c2326ffe3d0841720e480635625e6d13dd49ced357e0a0954836c7d3944e2';
$request = array(
    "Amount" => 5000,
    "AppKey" => '1c65cef3dfd1e00c0b03923a1c591db4',
    "BankName" => '中国银行',
    "AccountName" => '张三',
    'AccountNumber' => '6000123488881234',
    'MerchantReferenceNumber' => date('YmdHis').rand(10000, 100000),
    'NotifyUrl' => 'https://callback.example.com/notify.php'
    );
ksort($request);
$signContent = '';
foreach ($request as $key => $value) {
    $signContent .= "$key=$value&";
}

$signContent .= 'Key='.$secret;
$sign = md5($signContent);
$request['Sign'] = $sign;
$response = send_post('https://pay.tj9999.vip/api/Pay/Payout', json_encode($request));
$result = json_decode($response, true);
if ($result['error'] == 'success') {
    echo '请求成功！';
} else {
    echo '请求失败，错误:'. $result['error'];
}
?>