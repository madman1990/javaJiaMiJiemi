package 加密解密技术;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;

import java.security.*;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

import java.io.*;
import java.math.BigInteger;

public class Rsa前置版 {

	// 用途：手机终端-本机机构-用户密钥
	public static final String USER_RSA_KEY = "AC5146C8C0D25BC9E54A1031ABB2A8AA28817F5BD9049085592CCAD557E239CA"
			+ "8862C9EFA1CDBA101B12501F47A365EB31D42E9BD2197F5A2C3971692FDEED8C" + "C3683AC7189AA81AC36F64CFC28074825285309EA6D2D18679545C6A2A935BAB"
			+ "6CB5CB92AF6066D133579D49FC692EEBB7C0261FAE265DDC91025F0DC77810F9";
	// 预付卡RSA密钥
	public static final String CARD_RSA_KEY = "C0490D679FDA2D24944A5D80C5958458467303A73E6C9F8F4AD90C6EA67E131B"
			+ "233EDC2122D195DA80F4CEAE85AC991DBD6D27DD261629563F7E933FD2BDD15C" + "93989658D76BF7AA5C249319A8AB1D745F1B471F60685C6B5D4C0BB4219917A6"
			+ "4CC300923FDE14D37FA95854EA6CBD3AA63D92F4C453233B1720B4CBF5B3D5E3";

	private static final String exponent = "00010001";

	//获取龚玥
	private static PublicKey getPublicKey(String key) throws Exception {

		BigInteger e = new BigInteger(exponent, 16);
		BigInteger m = new BigInteger(key, 16);

		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}
	//公钥加密
	public static String EncryptByRSAPublicKey(String in, String key) {
		String resultStr = "";
		try {
			// 加解密类
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			// 明文
			byte[] plainText = in.getBytes();
			// 加密
			cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(key));
			byte[] enBytes = cipher.doFinal(plainText);
			resultStr = toHexString(enBytes, "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStr;
	}
	//二进制字节数字转字符串
	private static String toHexString(byte[] bytes, String separator) {
		String s1 = "", s2 = "";
		for (int i = 0; i < bytes.length; i++) {
			s1 = Integer.toHexString(0xFF & bytes[i]);
			if (s1.length() == 1) {
				s2 += "0";
			}
			s2 += s1;
			s2 += separator;
		}
		return s2;
	}

	/**
	 * 
	 * 加密
	 * 
	 * @param key
	 *            加密的密钥
	 * 
	 * @param data
	 *            待加密的明文数据
	 * 
	 * @return 加密后的数据
	 * 
	 * @throws Exception
	 * 
	 */

	public static byte[] encrypt(String key, byte[] data) {

		try {

			// Cipher cipher = Cipher.getInstance("RSA",
			// new org.bouncycastle.jce.provider.BouncyCastleProvider());

			java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(key));

			java.security.KeyFactory keyFactory;
			keyFactory = java.security.KeyFactory.getInstance("RSA");

			PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);

			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte

			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小

			int leavedSize = data.length % blockSize;

			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;

			byte[] raw = new byte[outputSize * blocksSize];

			int i = 0;

			while (data.length - i * blockSize > 0) {

				if (data.length - i * blockSize > blockSize)

					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);

				else

					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);

				// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到ByteArrayOutputStream中，
				// 而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了OutputSize所以只好用dofinal方法。

				i++;

			}

			return raw;

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		}
	}

	/**
	 * 
	 * 解密
	 * 
	 * @param key
	 *            解密的密钥
	 * 
	 * @param raw
	 *            已经加密的数据
	 * 
	 * @return 解密后的明文
	 * 
	 * @throws Exception
	 * 
	 */

	public static byte[] decrypt(Key key, byte[] raw) throws Exception {

		try {

			Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());

			cipher.init(cipher.DECRYPT_MODE, key);

			int blockSize = cipher.getBlockSize();

			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);

			int j = 0;

			while (raw.length - j * blockSize > 0) {

				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));

				j++;

			}

			return bout.toByteArray();

		} catch (Exception e) {

			throw new Exception(e.getMessage());

		}

	}

	/**
	 * 用户密码RSA加密
	 * 
	 * @param reqMap
	 * @return
	 */
	public static String encryptUserPassword(HashMap<String, String> reqMap) {
		String password = reqMap.get("123456");
		StringBuilder sb = new StringBuilder();
		sb.append("00000000");
		sb.append("001111111000000");
		sb.append(password);
		sb.append(reqMap.get("transDate"));
		sb.append(reqMap.get("transTime"));
		sb.append(reqMap.get("mobileNo"));
		String passwordRsa = Rsa前置版.EncryptByRSAPublicKey(sb.toString(), Rsa前置版.USER_RSA_KEY);
		System.out.println(passwordRsa);
		return passwordRsa;
		}

	
	

	
	

	public static void main(String[] args) {
		String passwordRsa = Rsa前置版.EncryptByRSAPublicKey("123456", Rsa前置版.USER_RSA_KEY);
		System.out.println(passwordRsa);
		String passwordRsa2 = Rsa前置版.EncryptByRSAPublicKey("123456", Rsa前置版.USER_RSA_KEY);
		System.out.println(passwordRsa2);
		String passwordRsa3 = Rsa前置版.EncryptByRSAPublicKey("123456", Rsa前置版.USER_RSA_KEY);
		System.out.println(passwordRsa3);
	}
}
