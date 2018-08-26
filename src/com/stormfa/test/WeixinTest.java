package com.stormfa.test;

import java.util.regex.Pattern;

import com.stormfa.menu.Menu;
import com.stormfa.po.AccessToken;
import com.stormfa.util.WeixinUtil;

public class WeixinTest {
	public static void main(String[] args) {
		try {
			AccessToken token = WeixinUtil.getAccessToken();
			System.out.println("票据" + token.getToken());
			System.out.println("有效时间" + token.getExpiresIn());

			String path = "D:/Coldplay - Fix You.mp3";
			String mediaId = WeixinUtil.upload(path, token.getToken(), "voice");
//			String path = "D:/stormfaimage.png";
//			String mediaId = WeixinUtil.upload(path, token.getToken(), "image");
//			String path = "D:/程序员 产品经理 打架.mp4";
//			String mediaId = WeixinUtil.upload(path, token.getToken(), "video");
			System.out.println(mediaId);

//			Menu stormfa = WeixinUtil.initMenu();
//			WeixinUtil.createMenu(token.getToken(), stormfa);

			// 翻译测试
//			String result = WeixinUtil.translate("现在几点");
//			//String result = WeixinUtil.translateFull("");
//			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
