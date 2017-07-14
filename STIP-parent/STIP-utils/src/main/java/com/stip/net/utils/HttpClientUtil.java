package com.stip.net.utils;

/**
 * Created by admin on 14-11-17.
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpClientUtil{
	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            请求参数
	 * @param readTimeout
	 *            数据读取时间（毫秒）
	 * @param connectTimeout
	 *            连接超时时间（毫秒）
	 * @return
	 * @throws Exception
	 */
	public static String sendGetRequest(String url, String param, int readTimeout, int connectTimeout) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter("http.socket.timeout", readTimeout);
		httpClient.getParams().setIntParameter("http.connection.timeout", connectTimeout);

		HttpGet httpGet = new HttpGet(url + "?" + param);


		HttpEntity httpEntity = null;

		try {

			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new Exception(httpResponse.getStatusLine().toString());
			} else {
				httpEntity = httpResponse.getEntity();
				String result = null;
				if (httpEntity != null) {
					result = EntityUtils.toString(httpEntity);
				}
				return result;
			}

		} catch (Exception e) {
			throw new Exception("异常情况", e);
		} finally {
			httpGet.abort();
			EntityUtils.consume(httpEntity);
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param mapparam
	 *            参数
	 * @param coding
	 *            编码
	 * @param readTimeout
	 *            数据读取时间（毫秒）
	 * @param connectTimeout
	 *            连接超时时间（毫秒）
	 * @return
	 * @throws Exception
	 */
	public static String sendPostRequest(String url, Map<String, String> mapparam, String coding, int readTimeout, int connectTimeout) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();

		httpClient.getParams().setIntParameter("http.socket.timeout", readTimeout);
		httpClient.getParams().setIntParameter("http.connection.timeout", connectTimeout);

		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		// 先迭代HashMap
		Iterator<Map.Entry<String, String>> it = mapparam.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			String key = entry.getKey();
			nvps.add(new BasicNameValuePair(key, entry.getValue()));
		}

		httpPost.setEntity(new UrlEncodedFormEntity(nvps, coding));

		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;

		try {
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new Exception(httpResponse.getStatusLine().toString());
			} else {
				httpEntity = httpResponse.getEntity();
				String result = null;
				if (httpEntity != null) {
					result = EntityUtils.toString(httpEntity);
				}
				return result;
			}
		} catch (Exception e) {
			throw new Exception("异常情况", e);
		} finally {
			httpPost.abort();
			EntityUtils.consume(httpEntity);
			httpClient.getConnectionManager().shutdown();
		}
	}

	public static String postData(String url, Map<String, String> params, String codePage) throws Exception {

		final org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30 * 1000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30 * 1000);

		final PostMethod method = new PostMethod(url);
		if (params != null) {
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, codePage);
			method.setRequestBody((org.apache.commons.httpclient.NameValuePair[]) assembleRequestParams(params));
		}
		String result = "";
		try {
			httpClient.executeMethod(method);
			result = new String(method.getResponseBody(), codePage);
		} catch (final Exception e) {
			throw e;
		} finally {
			method.releaseConnection();
		}
		return result;
	}

	/**
	 * 组装http请求参数
	 * 
	 * @param params
	 * @param menthod
	 * @return
	 */
	private static org.apache.commons.httpclient.NameValuePair[] assembleRequestParams(Map<String, String> data) {
		List<org.apache.commons.httpclient.NameValuePair> nameValueList = new ArrayList<org.apache.commons.httpclient.NameValuePair>();
		NameValuePair nvp[] = new NameValuePair[data.size()];
		Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			nameValueList.add(new org.apache.commons.httpclient.NameValuePair(entry.getKey(), entry.getValue()));
			// nvp[i]=new
			// org.apache.commons.httpclient.NameValuePair(entry.getKey(),entry.getValue());
		}

		return nameValueList.toArray(new org.apache.commons.httpclient.NameValuePair[nameValueList.size()]);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendPost(String url, ArrayList<String> params) {
		String result = "";
		try {
			// Post请求的url，与get不同的是不需要带参数
			URL postUrl = new URL(url);
			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();

			// 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true
			connection.setDoOutput(true);
			// Read from the connection. Default is true.
			connection.setDoInput(true);
			// 默认是 GET方式
			connection.setRequestMethod("POST");

			// Post 请求不能使用缓存
			connection.setUseCaches(false);

			connection.setInstanceFollowRedirects(true);

			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
			// 进行编码
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			// The URL-encoded contend
			// 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
			String contentStr = "";
			for (String cs : params) {
				contentStr += cs;
			}
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
			out.writeBytes(contentStr);

			out.flush();
			out.close();

			BufferedReader reader;

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String line;

			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				result += line;
			}

			reader.close();

			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 上传生活服务订单信息
	 * */
	public static boolean uploadOrderInfo(String url, JSONObject jsonObject) {
		PostMethod method = null;
		final org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
		try {
			method = new PostMethod(url);
			int status = 0;
			method.setParameter("uploadPars", jsonObject.toString());
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");// 设置编码格式
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			status = httpClient.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				JSONArray array = JSONArray.fromObject('[' + method.getResponseBodyAsString() + ']');
				Boolean b = (Boolean) array.getJSONObject(0).get("success");
				return b;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return false;

	}

	/**
	 * 上传用户浏览记录信息
	 * */
	public static boolean uploadUserScanRecordsInfo(String url, JSONObject jsonObject) {
		PostMethod method = null;
		final org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
		try {
			method = new PostMethod(url);
			method.setParameter("uploadPars", jsonObject.toString());
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");// 设置编码格式
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = httpClient.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				JSONArray array = JSONArray.fromObject('[' + method.getResponseBodyAsString() + ']');
				Boolean b = (Boolean) array.getJSONObject(0).get("success");
				return b;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return false;

	}

	/**
	 * 模拟用户登录yuguoAdmin
	 * */
	private static boolean loginYuguoAdmin(org.apache.commons.httpclient.HttpClient httpClient) {
		// 模拟登录页面
		PostMethod post = new PostMethod(ConstantUtils.SERVER_URL_ADMIN + "/system/login/login.do");
		org.apache.commons.httpclient.NameValuePair name = new org.apache.commons.httpclient.NameValuePair("userName", "admin");
		org.apache.commons.httpclient.NameValuePair pass = new org.apache.commons.httpclient.NameValuePair("pwd", "111111");
		org.apache.commons.httpclient.NameValuePair sbx = new org.apache.commons.httpclient.NameValuePair("Submit.x", "0");
		org.apache.commons.httpclient.NameValuePair sby = new org.apache.commons.httpclient.NameValuePair("Submit.y", "0");
		org.apache.commons.httpclient.NameValuePair sb = new org.apache.commons.httpclient.NameValuePair("Submit", "Save");
		post.setRequestBody(new org.apache.commons.httpclient.NameValuePair[] { name, pass, sbx, sby, sb });
		try {
			int status = httpClient.executeMethod(post);
			return status == HttpStatus.SC_OK ? true : false;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return false;
	}

	public static String doHunDun(String url, Map map) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String response = null;
		try {
			StringEntity s = new StringEntity(JSONObject.fromObject(map).toString(), "UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			org.apache.http.HttpResponse res = client.execute(post);
			if (((org.apache.http.HttpResponse) res).getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = ((org.apache.http.HttpResponse) res).getEntity();
				response = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}
}
