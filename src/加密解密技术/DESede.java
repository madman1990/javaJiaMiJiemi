/**
 * 
 */
package 加密解密技术;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.bouncycastle.jce.provider.symmetric.AES.KeyGen;

/**
 * @author admin
 * 
 */
public class DESede {
	/**
	 * 秘钥算法 JAVA6支持 112 和168 BC 支持128和 192
	 */

	public static final String KEY_ALGORITHM = "DESede";

	/**
	 * 加密 解密算法 工作模式 填充方式 java6 支持PKC5Padding BC支持PKC7Padding
	 */
	public static final String CIPHER_ALGORITHM = "DESede/ECB/PKC5Padding";

	/**
	 * 转换秘钥
	 */
	private static Key toKey(byte[] key) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		return factory.generateSecret(dks);
	}

	/**
	 * DESede 解密艺术
	 */
	public static byte[] decrypt(byte[] date, byte[] key) throws Exception {
		Key k = toKey(key);
		/**
		 * 如果需要PKC7Padding 填充方式 则使用下面代码 Cipher
		 * cipher=Cipher.getInstance(CIPHER_ALGORITHM，"BC");
		 */
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 設置解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(date);
	}

	/**
	 * DESede 加密艺术
	 */
	public static byte[] ecrypt(byte[] date, byte[] key) throws Exception {
		Key k = toKey(key);
		/**
		 * 如果需要PKC7Padding 填充方式 则使用下面代码 Cipher
		 * cipher=Cipher.getInstance(CIPHER_ALGORITHM，"BC");
		 */
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 設置加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(date);
	}

	/**
	 * 生成秘钥
	 */
	public static byte[] initKey() throws Exception {
		/**
		 * 如果需要使用128位和192長度秘鑰則需要使用下面代碼 KeyGenerator
		 * generator=KeyGenerator.getInstance(KEY_ALGORITHM。"BC");
		 */
		KeyGenerator generator = KeyGenerator.getInstance(KEY_ALGORITHM);
		/**
		 * 生成128和就192则需要使用下面代码 generator.init(128); generator.init(192);
		 */
		generator.init(168);
		SecretKey secretKey = generator.generateKey();
		return secretKey.getEncoded();

	}

	/**
	 * @param args
	 *            3DES DESede加密解密艺术
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
