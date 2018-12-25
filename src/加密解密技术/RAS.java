/**
 * 
 */
package 加密解密技术;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * @author admin RSA的加密解密艺术
 */
public class RAS {

	/**
	 * 非对称加密秘钥算法
	 */
	public static final String KEY_ALORITHM = "RSA";
	// 公钥
	public static final String PUBLIC_KEY = "RSAPublicKey";
	// 私钥
	public static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * RSA秘钥长度 默认1024位 秘钥长度 必须是64位数 范围在512-65536
	 */
	public static final int KEY_SIZE = 512;

	/**
	 * 私钥解密
	 */

	public static byte[] decryByPrivateKey(byte[] data, byte[] key) throws Exception {
		// 获取私钥
		PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory factory = KeyFactory.getInstance(KEY_ALORITHM);
		// 生成私钥
		PrivateKey privateKey = factory.generatePrivate(encodedKeySpec);
		Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
		// 对数据解密
		cipher.init(cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);

	}

	/**
	 * 私钥加密
	 */

	public static byte[] encrtyByPrivateKey(byte[] data, byte[] key) throws Exception {
		// 获取私钥
		PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory factory = KeyFactory.getInstance(KEY_ALORITHM);
		// 生成私钥
		PrivateKey privateKey = factory.generatePrivate(encodedKeySpec);
		Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
		// 对数据解密
		cipher.init(cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);

	}

	/**
	 * 公钥解密
	 */

	public static byte[] decryByPublicKey(byte[] data, byte[] key) throws Exception {
		// 获取公钥
		X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(key);
		KeyFactory factory = KeyFactory.getInstance(KEY_ALORITHM);
		// 生成公钥
		PublicKey publicKey = factory.generatePublic(encodedKeySpec);
		Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
		// 对数据解密
		cipher.init(cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);

	}

	/**
	 * 公钥加密
	 */

	public static byte[] encrtyByPublicKey(byte[] data, byte[] key) throws Exception {
		// 获取公钥
		X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(key);
		KeyFactory factory = KeyFactory.getInstance(KEY_ALORITHM);
		// 生成公钥
		PublicKey publicKey = factory.generatePublic(encodedKeySpec);
		Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
		// 对数据解密
		cipher.init(cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);

	}

	/**
	 * 生成一堆秘钥包括公钥和私钥
	 */

	public static Map<String, Object> initKey() throws Exception {
		// 实例化密钥生成器
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALORITHM);
		// 初始化密钥生成器
		keyPairGenerator.initialize(KEY_SIZE);
		// 生成密钥对
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		// 将密钥保存在map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PUBLIC_KEY, publicKey);
		map.put(PRIVATE_KEY, privateKey);
		return map;

	}

	/**
	 * 获取公钥
	 */
	public static byte[] getPublicKey(Map<String, Object> map) throws Exception {
		Key k = (Key) map.get(PUBLIC_KEY);
		return k.getEncoded();
	}

	/**
	 * 获取私钥
	 */
	public static byte[] getPrivateKey(Map<String, Object> map) throws Exception {
		Key k = (Key) map.get(PRIVATE_KEY);
		return k.getEncoded();
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> initKey = initKey();
		Map<String, Object> initKey2 = initKey();
		String d = "1中国2中国3";
		byte[] encrtyByPublicKey = encrtyByPublicKey(d.getBytes(), getPublicKey(initKey));
		byte[] decryByPrivateKey = decryByPrivateKey(encrtyByPublicKey, getPrivateKey(initKey2));
		System.out.println(new String(decryByPrivateKey));
	}

}
