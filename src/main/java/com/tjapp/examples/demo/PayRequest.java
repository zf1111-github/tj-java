package com.tjapp.examples.demo;

public class PayRequest {
    private int amount;
    private String appKey;
    private String callbackUrl;
    private int channel;
    private String mrn;
    private String notifyUrl;
    private String sign;

    public int getAmount() {
        return amount;
    }

    public PayRequest setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public PayRequest setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public PayRequest setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public int getChannel() {
        return channel;
    }

    public PayRequest setChannel(int channel) {
        this.channel = channel;
        return this;
    }

    public String getMrn() {
        return mrn;
    }

    public PayRequest setMrn(String mrn) {
        this.mrn = mrn;
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public PayRequest setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public PayRequest setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public String toSignContent(String key) {
        return String.format(
                "Amount=%d&AppKey=%s&CallBackUrl=%s&Channel=%d&MerchantReferenceNumber=%s&NotifyUrl=%s&Key=%s",
                this.amount,
                this.appKey,
                this.callbackUrl,
                this.channel,
                this.mrn,
                this.notifyUrl,
                key);
    }
}
