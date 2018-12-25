package 加密解密技术;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.*;

public class MD5{
	public final static String md5(String s){
		char hexDigits[]={
				'0','1','2','3','4','5','6','7','8','9',
				'a','b','c','d','e','f'};
		try{
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j*2];
			int k=0;
			for(int i=0;i<j;i++){
				byte byte0 = md[i];
				str[k++]=hexDigits[byte0>>>4&0xf];
				str[k++]=hexDigits[byte0&0xf];
			}
			return new String(str);
		}catch(Exception e){
			return null;
		}
	}
	
	
	public static boolean reqXmlMD5IsCorrect(String requestXml,String key){
		
//		System.out.println("签名字符串:"+requestXml);
		
		String signStr = requestXml.substring(requestXml.indexOf("<sign>"), requestXml.indexOf("</sign>")+7);
		try {
			requestXml = URLEncoder.encode(requestXml.replace(signStr,"<sign>"+key+"</sign>"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		String localSign = MD5.md5(requestXml).toUpperCase();
		
		String sign = signStr.substring(signStr.indexOf("<sign>")+6, signStr.indexOf("</sign>"));
		
		if(sign.equals(localSign))
			return true;
		else
			return false;
	}
	/**
	 * 返回报文签名处理
	 * @param responseXml
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String respXmlMD5(String responseXml) throws UnsupportedEncodingException{
		String signStr = responseXml.substring(responseXml.indexOf("<sign>"), responseXml.indexOf("</sign>")+7);
		String key = MD5.md5(URLEncoder.encode(responseXml,"UTF-8")).toUpperCase();
		//签名
		responseXml = responseXml.replace(signStr,"<sign>"+key+"</sign>");
		return responseXml;
	}
}
