package com.tjapp.examples.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Controller
public class PayController {

    @Autowired
    private PaymentStore paymentStore;

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public String showPayPage(ModelMap modelMap) {
        return "prepare";
    }

    @RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
    public String createPayment(ModelMap modelMap, @RequestParam double amount, @RequestParam int channel) {
        Payment payment = new Payment()
                .setAmount(amount)
                .setId(UUID.randomUUID().toString());
        this.paymentStore.insert(payment);

        PayRequest payRequest =  new PayRequest()
                .setAmount((int)(amount * 100))
                .setChannel(channel)
                .setAppKey("1c65cef3dfd1e00c0b03923a1c591db4")
                .setCallbackUrl("https://www.source-merchant.com")
                .setNotifyUrl("http://localhost/notify")
                .setMrn(payment.getId());
        payRequest.setSign(DigestUtils.md5DigestAsHex(
                payRequest.toSignContent("d01c2326ffe3d0841720e480635625e6d13dd49ced357e0a0954836c7d3944e2")
                        .getBytes(StandardCharsets.UTF_8)));
        modelMap.put("payRequest", payRequest);
        return "sendRequest";
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST, produces = {"text/plain"})
    @ResponseBody
    public String processCallback(
            ModelMap modelMap,
            @RequestParam(value = "Amount")int amount,
            @RequestParam(value = "AppKey")String appKey,
            @RequestParam(value = "Channel")int channel,
            @RequestParam(value = "MerchantReferenceNumber")String mrn,
            @RequestParam(value = "OriginAmount")int originAmount,
            @RequestParam(value = "Sign")String sign
            ) {
        String localSign = DigestUtils.md5DigestAsHex(
                String.format(
                        "Amount=%d&AppKey=1c65cef3dfd1e00c0b03923a1c591db4&Channel=%d&MerchantReferenceNumber=%s&OriginAmount=%d&Key=d01c2326ffe3d0841720e480635625e6d13dd49ced357e0a0954836c7d3944e2",
                        amount,
                        channel,
                        mrn,
                        originAmount)
                .getBytes(StandardCharsets.UTF_8));
        if (!localSign.equals(sign)) {
            // log error and return
            return "bad signature";
        } else {
            Payment payment = this.paymentStore.getPayment(mrn);
            if (payment == null) {
                return "payment not found";
            } else {
                // set payment as completed and callback merchant
                return "success";
            }
        }
    }
}
