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
 * ��Ϣ��װ��
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
	 * xmlתΪmap����
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
	 * ���ı���Ϣ����תΪxml
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
	 * ��װ�ı���Ϣ
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
	 * ���˵� ��
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("��ӭ���Ĺ�ע���밴�ղ˵���ʾ���в�����\n\n");
		sb.append("���롾1����Hello Stormfa\n");
		sb.append("���롾2����Stormfa����\n");
		sb.append("���롾3����Stormfa ��˭��\n");
		sb.append("���롾���֡�����ȡһ������\n");
		sb.append("���롾ͼƬ������ȡһ��ͼƬ\n");
		sb.append("���롾��Ƶ������ȡһ����Ƶ\n\n");
		sb.append("�ظ��� ���������˲˵���\n");
		sb.append("���롾����+��������ġ���ʾ�������������ڼ��㡰");
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
		sb.append("С���ſ�ź�ƽ�⳵������ Ninebot �ǳ���");
		sb.append("");
		return sb.toString();
	}

	// 3
	public static String thirdMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("Stormfa ��˭��\n\n");
		sb.append("ִ�ⲻ�ģ�ʼ����һ\n");
		sb.append("��ֳ����ǳɹ��Ĵ�����\n");
		sb.append("����һ�����Ǻ�������÷���˱���\n");
		sb.append("�����Ͳ���������ǧ��\n");
		sb.append("����С�������Գɽ���\n\n");
		sb.append("�ظ���������ʾ���˵���");
		return sb.toString();
	}

	/**
	 * ͼ����ϢתΪxml
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
	 * ͼƬ��ϢתΪxml
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
	 * ��Ƶ��ϢתΪxml
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
	 * ������ϢתΪxml
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
	 * ͼ����Ϣ����װ
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
		news.setTitle("ƽ�⳵��俨����");
		news.setDescription("С���ſ�ź�ƽ�⳵������ Ninebot �ǳ���");
		news.setPicUrl(
				"https://mmbiz.qpic.cn/mmbiz_png/mQC9a6QOOib1w81AYaC5gjIicAk2BKRzvfF1iaPXp5KiaICV0IqfsXacCJwNRyg4uYM3BJMH086gpibpCibiaWpCRicVSQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1");
		news.setUrl(
				"https://mp.weixin.qq.com/s?__biz=MzI0NjA5ODMzMA==&mid=2650413304&idx=1&sn=bb57ce781ad0eae10c9716e0183f3f85&chksm=f14ac80dc63d411bd74760663fbf9633f165c60c6ee18502e821ec255ee6c7a0e674ff08f6c7#rd");

		News news2 = new News();
		news2.setTitle("�ɱ���ICE������ ǰ�˵����ʣ�");
		news2.setDescription("ǰ��Ҫʧҵ����");
		news2.setPicUrl(
				"https://mmbiz.qpic.cn/mmbiz_png/mQC9a6QOOib3FfbTG47erLyqb9IgUiabic1H7VkRwgRicficROzScPZkeQfue9I1icosJHNlQ83ZZOdeuYZ1ysApGicrg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1");
		news2.setUrl(
				"https://mp.weixin.qq.com/s?__biz=MzI0NjA5ODMzMA==&mid=2650413369&idx=1&sn=b0166f6d213949acb467e125493b06b7&chksm=f14ac9ccc63d40dae0878684f7470d4a4d084188d6a0b357d6b3d10e7c81c8987422519661ff#rd");

		News news3 = new News();
		news3.setTitle("��һ���Ʒ�뿪���Ķ�Թ���� �ϰ壺������");
		news3.setDescription("�����������మ��ɱ");
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
	 * ��װͼƬ��Ϣ
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
	 * ��װ��Ƶ��Ϣ
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initVideoMessage(String toUserName, String fromUserName) {
		String message = null;
		Video video = new Video();
		video.setMediaId("2jGIEAp1E3Dng-GFjbxTea7TB6wgfrU3WKtb2BW0JYOsspOLGFcBOKwKPEYGT42I");
		video.setTitle("����Ա ��Ʒ���� ���");
		video.setDescription("ĳ��˾���������Ϊ�ֻ������������������");
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
	 * ��װ������Ϣ
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName, String fromUserName) {
		String message = null;
		Music music = new Music();
//		music.setThumbMediaId("WOXU2ziPj0q84YMFsafDKgqDDMIOAGIpQIpbuyXrAqvTikfxzBQKJtxipFF00Nd-");	// ���Ժ�
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
