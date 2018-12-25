/**
 * 
 */
package 加密解密技术;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @author admin
 * 
 */
public class DES {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException
	 */
	public static void main(String[] args) throws Exception {

		// String DESSTR="DES/";
		// TODO Auto-generated method stub
		// 生成秘钥
		// java 只支持56位秘钥
		// KeyGenerator kg = KeyGenerator.getInstance("DES");
		// 64位秘钥
		// KeyGenerator kg =
		// KeyGenerator.getInstance("DES/ECB/PKCS5Padding","BC");
		// kg.init(64);
		//加上这句话也相当于生成64位秘钥
		//Security.addProvider(new BouncyCastleProvider());
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(56);
		// 默认长度56
		// kg.init(new SecureRandom());
		SecretKey key = kg.generateKey();
		// 得到字节数据形式的秘钥 也可以转成字符串
		byte[] keyByte = key.getEncoded();
		// 把字节数组转成我们需要的秘钥对象
		DESKeySpec dks = new DESKeySpec(keyByte);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
		// 得到秘钥对象
		SecretKey keyByte2 = factory.generateSecret(dks);

		// 实例化
		Cipher cipher = Cipher.getInstance("DES");
		// 初始化
		// 加密
		cipher.init(Cipher.ENCRYPT_MODE, keyByte2);
		// 解密
		cipher.init(Cipher.DECRYPT_MODE, keyByte2);

		byte[] value = cipher.doFinal("JAVA加密解密艺术".getBytes("UTF-8"));

	}

}
