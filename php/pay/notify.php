<?php
$mer_no	= '<please replace with your app key>';		// 商户号
$secretkey = '<please replace with your own secret>';     
$data = $_POST;

if ($data) {
    $prestr = 'Amount=' . $data['Amount'] . '&AppKey=' . $mer_no . '&Channel=' . $data['Channel'] .
        '&MerchantReferenceNumber=' . $data['MerchantReferenceNumber'] . 
        '&OriginAmount='.$data['OriginAmount'].'&Key=' . $secretkey;
    $sign = md5($prestr);
    
    if ($sign == $data['Sign']) {
        $money = $data['Amount'] / 100.0;
        $orderid = $data['MerchantReferenceNumber'];

        // deal with your own logic to handle when payment is done successfully
        exit('ok');
    } else {
        exit('sign error');
    }
} else {
    exit('result empty');
}

?>