package com.stormfa.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.stormfa.po.AccessToken;
import com.stormfa.po.Image;
import com.stormfa.po.ImageMessage;
import com.stormfa.po.Music;
import com.stormfa.po.MusicMessage;
import com.stormfa.po.News;
import com.stormfa.po.NewsMessage;
import com.stormfa.po.TextMessage;
import com.stormfa.po.Video;
import com.stormfa.po.VideoMessage;
import com.thoughtworks.xstream.XStream;

/**
 * 消息封装类
 * 
 * @author stormfa
 *
 */
public class MessageUtil {

	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVNET = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE = "scancode_push";

	/**
	 * xml转为map集合
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();

		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);

		Element root = doc.getRootElement();

		List<Element> list = root.elements();

		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}

	/**
	 * 将文本消息对象转为xml
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 组装文本消息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName, String fromUserName, String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}

	/**
	 * 主菜单 ？
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
		sb.append("输入【1】：Hello Stormfa\n");
		sb.append("输入【2】：Stormfa荐文\n");
		sb.append("输入【3】：Stormfa 是谁？\n");
		sb.append("输入【音乐】：获取一首音乐\n");
		sb.append("输入【图片】：获取一张图片\n");
		sb.append("输入【视频】：获取一部视频\n\n");
		sb.append("回复【 ？】调出此菜单。\n");
		sb.append("输入【翻译+翻译的中文】，示例：”翻译现在几点“");
		return sb.toString();
	}

	// 1
	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("Hello Stormfa");
		return sb.toString();
	}

	// 2
	public static String secondMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("小米炫酷九号平衡车卡丁车 Ninebot 登场！");
		sb.append("");
		return sb.toString();
	}

	// 3
	public static String thirdMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("Stormfa 是谁？\n\n");
		sb.append("执意不改，始终如一\n");
		sb.append("坚持常常是成功的代名词\n");
		sb.append("不经一翻彻骨寒，怎得梅花扑鼻香\n");
		sb.append("不积跬步，无以至千里\n");
		sb.append("不积小流，无以成江海\n\n");
		sb.append("回复“？”显示主菜单。");
		return sb.toString();
	}

	/**
	 * 图文消息转为xml
	 * 
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 图片消息转为xml
	 * 
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 视频消息转为xml
	 * 
	 * @param videoMessage
	 * @return
	 */
	public static String videoMessageToXml(VideoMessage videoMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

	/**
	 * 音乐消息转为xml
	 * 
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息的组装
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName, String fromUserName) {
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();

		News news = new News();
		news.setTitle("平衡车秒变卡丁车");
		news.setDescription("小米炫酷九号平衡车卡丁车 Ninebot 登场！");
		news.setPicUrl(
				"https://mmbiz.qpic.cn/mmbiz_png/mQC9a6QOOib1w81AYaC5gjIicAk2BKRzvfF1iaPXp5KiaICV0IqfsXacCJwNRyg4uYM3BJMH086gpibpCibiaWpCRicVSQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1");
		news.setUrl(
				"https://mp.weixin.qq.com/s?__biz=MzI0NjA5ODMzMA==&mid=2650413304&idx=1&sn=bb57ce781ad0eae10c9716e0183f3f85&chksm=f14ac80dc63d411bd74760663fbf9633f165c60c6ee18502e821ec255ee6c7a0e674ff08f6c7#rd");

		News news2 = new News();
		news2.setTitle("飞冰（ICE）—— 前端的梦魇？");
		news2.setDescription("前端要失业啦！");
		news2.setPicUrl(
				"https://mmbiz.qpic.cn/mmbiz_png/mQC9a6QOOib3FfbTG47erLyqb9IgUiabic1H7VkRwgRicficROzScPZkeQfue9I1icosJHNlQ83ZZOdeuYZ1ysApGicrg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1");
		news2.setUrl(
				"https://mp.weixin.qq.com/s?__biz=MzI0NjA5ODMzMA==&mid=2650413369&idx=1&sn=b0166f6d213949acb467e125493b06b7&chksm=f14ac9ccc63d40dae0878684f7470d4a4d084188d6a0b357d6b3d10e7c81c8987422519661ff#rd");

		News news3 = new News();
		news3.setTitle("又一起产品与开发的恩怨…… 老板：开除！");
		news3.setDescription("产培育开发相爱相杀");
		news3.setPicUrl(
				"http://mmbiz.qpic.cn/mmbiz_jpg/mQC9a6QOOib1MLGNboMBzH1mJeWrGJoTgAa5Cx1wFL5oHkGeRY2HUicxs6K6vYu0bAvgfibEjVDmhgzoW25c5a4Ig/0?wx_fmt=jpeg");
		news3.setUrl(
				"https://mp.weixin.qq.com/s?__biz=MzI0NjA5ODMzMA==&mid=2650413342&idx=1&sn=d3a22945a507b787adef97a16c3a114c&chksm=f14ac9ebc63d40fd299da4adc1ac65e91903c68b1d6c5e98b2ecd207e3b190996edeecd39a8d#rd");

		newsList.add(news);
		newsList.add(news2);
		newsList.add(news3);

		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());

		message = newsMessageToXml(newsMessage);
		return message;
	}

	/**
	 * 组装图片消息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName, String fromUserName) {
		String message = null;
		Image image = new Image();
		image.setMediaId("J5pLPWO136jAuZHCvK5j4vSy5lOQ1aAeQdE1WlS0rSHoDT-kUbRftZoi4GnPBk6e");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}

	/**
	 * 组装视频消息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initVideoMessage(String toUserName, String fromUserName) {
		String message = null;
		Video video = new Video();
		video.setMediaId("2jGIEAp1E3Dng-GFjbxTea7TB6wgfrU3WKtb2BW0JYOsspOLGFcBOKwKPEYGT42I");
		video.setTitle("程序员 产品经理 打架");
		video.setDescription("某公司外包部门因为手机壳需求打了起来……");
		VideoMessage videoMessage = new VideoMessage();
		videoMessage.setFromUserName(toUserName);
		videoMessage.setToUserName(fromUserName);
		videoMessage.setMsgType(MESSAGE_VIDEO);
		videoMessage.setCreateTime(new Date().getTime());
		videoMessage.setVideo(video);
		message = videoMessageToXml(videoMessage);
		return message;
	}

	/**
	 * 组装音乐消息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName, String fromUserName) {
		String message = null;
		Music music = new Music();
//		music.setThumbMediaId("WOXU2ziPj0q84YMFsafDKgqDDMIOAGIpQIpbuyXrAqvTikfxzBQKJtxipFF00Nd-");	// 测试号
		music.setThumbMediaId("Mv-Lrw5AbJL0MKTjkSC3ccfi-vHj36ABZ1EyFGcHj4tZBjw2w6y0h5LlpWwxbVJU"); // stormfa
		music.setTitle("Fix You");
		music.setDescription("Coldplay");
		music.setMusicUrl("http://43.226.37.27/Weixin_Server/resource/Coldplay - Fix You.mp3");
		music.setHQMusicUrl("http://43.226.37.27/Weixin_Server/resource/Coldplay - Fix You.mp3");

		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}
}
