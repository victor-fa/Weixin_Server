package com.stormfa.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.stormfa.menu.Button;
import com.stormfa.menu.ClickButton;
import com.stormfa.menu.Menu;
import com.stormfa.menu.ViewButton;
import com.stormfa.po.AccessToken;
import com.stormfa.trans.Data;
import com.stormfa.trans.Parts;
import com.stormfa.trans.Symbols;
import com.stormfa.trans.TransResult;

/**
 * ΢�Ź�����
 * 
 * @author stormfa
 *
 */
public class WeixinUtil {
	public static final String TOKEN = "stormfa";
	private static final String APPID = "wx6d394b8081cc975a";
	private static final String APPSECRET = "10749a8ccf53f689ab83f632ce1f7a0f";

	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	private static final String BAIDU_TRACSLATE_URL = "http://api.fanyi.baidu.com/api/trans/vip/translate";
	/**
	 * get����
	 * 
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ParseException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			String result = EntityUtils.toString(entity, "UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}

	/**
	 * POST����
	 * 
	 * @param url
	 * @param outStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doPostStr(String url, String outStr) throws ParseException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr, "UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}

	/**
	 * �ļ��ϴ�
	 * 
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken, String type)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("�ļ�������");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

		URL urlObj = new URL(url);
		// ����
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);

		// ��������ͷ��Ϣ
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// ���ñ߽�
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// ��������
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// �����ͷ
		out.write(head);

		// �ļ����Ĳ���
		// ���ļ������ļ��ķ�ʽ ���뵽url��
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// ��β����
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// ����������ݷָ���

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			// ����BufferedReader����������ȡURL����Ӧ
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if (!"image".equals(type)) {
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}

	/**
	 * ��ȡaccessToken
	 * 
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ParseException, IOException {
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if (jsonObject != null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}

	/**
	 * ��װ�˵�
	 * 
	 * @return
	 */
	public static Menu initMenu() {
		Menu menu = new Menu();
		ClickButton button11 = new ClickButton();
		button11.setName("stormfa");
		button11.setType("click");
		button11.setKey("11");

		ViewButton button21 = new ViewButton();
		button21.setName("���ģ�");
		button21.setType("view");
		button21.setUrl(
				"https://mp.weixin.qq.com/s?__biz=MzI0NjA5ODMzMA==&mid=2650413304&idx=1&sn=bb57ce781ad0eae10c9716e0183f3f85&chksm=f14ac80dc63d411bd74760663fbf9633f165c60c6ee18502e821ec255ee6c7a0e674ff08f6c7#rd");

		ClickButton button31 = new ClickButton();
		button31.setName("ɨ���¼�");
		button31.setType("scancode_push");
		button31.setKey("31");

		ClickButton button32 = new ClickButton();
		button32.setName("����λ��");
		button32.setType("location_select");
		button32.setKey("32");

		Button button = new Button();
		button.setName("�˵�");
		button.setSub_button(new Button[] { button31, button32 });

		menu.setButton(new Button[] { button11, button21, button });
		return menu;
	}

	public static int createMenu(String token, Menu menu) throws ParseException, IOException {
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		String jsonMenu = JSONObject.fromObject(menu).toString();
		JSONObject jsonObject = doPostStr(url, jsonMenu);
		System.out.println(menu);
		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
			System.out.println(jsonObject);
			System.out.println(result);
		}
		return result;
	}

	/**
	 * ��ѯ�˵�
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject queryMenu(String token) throws ParseException, IOException {
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}

	/**
	 * ɾ���˵�
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int deleteMenu(String token) throws ParseException, IOException {
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		int result = 0;
		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}

	/**
	 * ����
	 * @param source
	 * @param flage
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String translate(String source, String flage) throws ParseException, IOException {
		// ����������е� APPID(appid),����query(q, ע��ΪUTF-8����) , �����(salt), �Լ�ƽ̨�������Կ����
		// appid+q+salt+��Կ ��˳��ƴ�ӵõ��ַ���1��
		String appid = "20180810000193101";
		String salt = String.valueOf(System.currentTimeMillis());
		String secretKey = "cpcok30G1vlsuRIJUUbh";
		String from = "auto";
		String to = "";
		if ("1".equals(flage)) {
			to = "zh";
		} else if ("2".equals(flage)) {
			to = "en";
		}

		// ǩ��
		String enCodeAll = appid + source + salt + secretKey;
		String sign = EnCodeUtil.md5(enCodeAll);
		String url = BAIDU_TRACSLATE_URL + "?q=" + source + "&from=" + from + "&to=" + to + "&appid=" + appid + "&salt="
				+ salt + "&sign=" + sign;
		JSONObject jsonObject = doGetStr(url);
		Object obj = jsonObject.get("trans_result");
		StringBuffer dst = new StringBuffer();
		if (!"[]".equals(obj.toString())) {
			List<Map> list = (List<Map>) jsonObject.get("trans_result");
			for (Map map : list) {
				dst.append(map.get("dst"));
			}
		} else {
			dst.append("�����������ˣ�");
//			dst.append(translateFull(source));
		}
		return dst.toString();
	}

//	public static String translateFull(String source) throws ParseException, IOException {
//		String url = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=jNg0LPSBe691Il0CG5MwDupw&q=KEYWORD&from=auto&to=auto";
//		url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8"));
//		JSONObject jsonObject = doGetStr(url);
//		StringBuffer dst = new StringBuffer();
//		List<Map> list = (List<Map>) jsonObject.get("trans_result");
//		for (Map map : list) {
//			dst.append(map.get("dst"));
//		}
//		return dst.toString();
//	}

}
