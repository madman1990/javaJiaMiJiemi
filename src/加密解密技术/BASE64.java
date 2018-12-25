package 加密解密技术;

/**
 * 
 */


import org.bouncycastle.util.encoders.UrlBase64;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;



/**
 * @author admin
 * 
 */
public class BASE64 {

	/**
	 * Base64加密解密
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "JAVA加密解密艺术";
		try {
			System.out.println("ԭ原始数据" + str);
			System.out.println("算法一");
			System.out.println("加密数据" + Base64.encode(str.getBytes()));
			System.out.println("解密数据" + new String(Base64.decode(Base64.encode(str.getBytes("UTF-8"))), "UTF-8"));
			System.out.println("算法二");
			System.out.println("加密数据" + org.apache.commons.codec.binary.Base64.encodeBase64String(str.getBytes("UTF-8")));
			System.out.println("解密数据"
					+ new String(org.apache.commons.codec.binary.Base64.decodeBase64(org.apache.commons.codec.binary.Base64.encodeBase64String(str.getBytes("UTF-8"))), "UTF-8"));
			System.out.println("算法三");
			System.out.println("加密数据" + new String(UrlBase64.encode(str.getBytes("UTF-8")), "UTF-8"));
			System.out.println("解密数据" + new String(UrlBase64.decode(UrlBase64.encode(str.getBytes("UTF-8"))), "UTF-8"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
