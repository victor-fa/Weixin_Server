package com.stormfa.wechat;

/**
 * 
 * @author administrator
 *
 */
public class GdWechatConfig {
    //΢��appid
    public static String appid;
    //΢��secret
    public static String secret;
    //΢���̻���
    public static String partner;
    //΢���̻�֧����Կ
    public static String partnerkey;
    //΢��appid
    public static String weimengAppid;
    //΢��secret
    public static String weimengSecret;

    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String appid) {
        GdWechatConfig.appid = appid;
    }

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
        GdWechatConfig.secret = secret;
    }

    public static String getPartner() {
        return partner;
    }

    public static void setPartner(String partner) {
        GdWechatConfig.partner = partner;
    }

    public static String getPartnerkey() {
        return partnerkey;
    }

    public static void setPartnerkey(String partnerkey) {
        GdWechatConfig.partnerkey = partnerkey;
    }

    public static String getWeimengAppid() {
        return weimengAppid;
    }

    public static void setWeimengAppid(String weimengAppid) {
        GdWechatConfig.weimengAppid = weimengAppid;
    }

    public static String getWeimengSecret() {
        return weimengSecret;
    }

    public static void setWeimengSecret(String weimengSecret) {
        GdWechatConfig.weimengSecret = weimengSecret;
    }
}