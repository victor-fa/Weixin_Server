package com.stormfa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.stormfa.po.AccessToken;
import com.stormfa.po.Image;
import com.stormfa.po.ImageMessage;
import com.stormfa.util.CheckUtil;
import com.stormfa.util.MessageUtil;
import com.stormfa.util.WeixinUtil;

public class WeixinServlet extends HttpServlet {
	/**
	 * 接入验证
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");

		PrintWriter out = resp.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
	}

	/**
	 * 消息的接收与响应
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Pattern p = Pattern.compile("[a-zA-z]");
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			Image image = new Image();
			ImageMessage imageMessage = new ImageMessage();

			String message = null;
			if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
				if ("1".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				} else if ("2".equals(content)) {
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				} else if ("3".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.thirdMenu());
				} else if ("?".equals(content) || "？".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				} else if (content.startsWith("翻译")) {
					String word = content.replaceAll("^翻译", "").trim();

					if ("".equals(word)) {
						message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.thirdMenu());
					} else {
						if (p.matcher(word).find()) {
							// 英文
							message = MessageUtil.initText(toUserName, fromUserName, WeixinUtil.translate(word, "1"));
						} else {
							// 中文
							message = MessageUtil.initText(toUserName, fromUserName, WeixinUtil.translate(word, "2"));
						}
					}
				} else if ("音乐".equals(content)) {
					message = MessageUtil.initMusicMessage(toUserName, fromUserName);
				} else if ("图片".equals(content)) {
					message = MessageUtil.initImageMessage(toUserName, fromUserName);
				} else if ("视频".equals(content)) {
					message = MessageUtil.initVideoMessage(toUserName, fromUserName);
				} else {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			} else if (MessageUtil.MESSAGE_EVNET.equals(msgType)) {
				String eventType = map.get("Event");
				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				} else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				} else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
					String url = map.get("EventKey");
					message = MessageUtil.initText(toUserName, fromUserName, url);
				} else if (MessageUtil.MESSAGE_SCANCODE.equals(eventType)) {
					String key = map.get("EventKey");
					message = MessageUtil.initText(toUserName, fromUserName, key);
				}
			} else if (MessageUtil.MESSAGE_LOCATION.equals(msgType)) {
				String label = "您发送的位置是：" + map.get("Label");
				message = MessageUtil.initText(toUserName, fromUserName, label);
			}

			System.out.println(message);

			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
