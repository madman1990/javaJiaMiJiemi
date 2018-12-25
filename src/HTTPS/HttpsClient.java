package HTTPS;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpsClient {
	public static void main(String[] args) throws Exception {
		HttpsMethod();
	}

	public static void HttpsMethod() throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		KeyStore trustStore = KeyStore.getInstance("jks");
		InputStream ksIn = new FileInputStream(new File("E://test.p12"));
		InputStream tsIn = new FileInputStream(new File("E://client.truststore"));
		try {
			keyStore.load(ksIn, "123456".toCharArray());
			trustStore.load(tsIn, "123456".toCharArray());
		} finally {
			try {
				ksIn.close();
				tsIn.close();
			} catch (Exception ignore) {
			}
		}

		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "123456".toCharArray()).loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpget = new HttpPost("https://180.168.215.67:8443/worthtech_open_system/exchangeInfo.action");
			StringEntity strEntity;
			strEntity = new StringEntity("{json:123….}", "UTF-8");
			httpget.setEntity(strEntity);
			System.out.println("Executing Request:" + httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				System.out.println("-------------------------------------");
				System.out.println(response.getStatusLine());
				System.out.println(EntityUtils.toString(entity));
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
		} finally {
			httpclient.close();
		}
	}

}
