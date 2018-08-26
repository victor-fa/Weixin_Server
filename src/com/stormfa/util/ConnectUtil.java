package com.stormfa.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.stormfa.wechat.GdWechatConfig;
import com.stormfa.wechat.GdWechatScopeTypeEnum;

public class ConnectUtil {
	
	/**
	 *  组装获取code的url(微信)
	 * @param backUri
	 * @param scope
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getWeChatCode(String backUri, GdWechatScopeTypeEnum scope)
			throws UnsupportedEncodingException {
		backUri = URLEncoder.encode(backUri, "UTF-8");
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + GdWechatConfig.appid
				+ "&redirect_uri=" + backUri + "&response_type=code&scope=" + scope.getTypeCode()
				+ "&state=123#wechat_redirect";
		return url;

	}
	
    /**
     *  获取openID和token CodeWechat
     * @param code
     * @return
     */
//    public static Map<Object, Object> getOpenIdAndTokenByCodeWechat(String code) {
//        //微信
//        String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + GdWechatConfig.appid
//                + "&secret=" + GdWechatConfig.secret + "&code=" + code + "&grant_type=authorization_code";
//        if (StringUtils.isNotEmpty(code)) {
//            String resultStr = GDHttpClient.get(URL);
//            if (StringUtils.isNotEmpty(resultStr)) {
//                Map<Object, Object> map = GdJsonMapper.readStringValueToMap(resultStr);
//                return map;
//            }
//        }
//        return null;
//    }
    
    /**
     * 获取微信用户信息
     * @param openId
     * @param token
     * @return
     * @throws Exception
     */
//    public static Map<Object, Object> getUserInfoWechat(String openId, String token) throws Exception {
//        String userinfourl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openId + "&lang=zh_CN";
//        String resultStr = GDHttpClient.get(userinfourl);
//        if (StringUtils.isNotEmpty(resultStr)) {
//            return (Map<Object, Object>) GdJsonMapper.readStringValueToMap(resultStr);
//        }
//        return null;
//    }
}
