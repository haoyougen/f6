package com.f6.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.f6.auth.domain.UserVO;

@Component("passwordHelper")
public class PasswordHelper {
	private Logger logger = LoggerFactory.getLogger(PasswordHelper.class);
	private static final String SALT = "FRAMEWORK6";
	private static RandomNumberGenerator generator = new SecureRandomNumberGenerator();
	private static String algorithmName = "md5";
	private static final int hashIterations = 1;

	public static void encryptPassword(UserVO user) {

		if (user.getUserSalt() == null || "".equals(user.getUserSalt())) {
			String salt = generator.nextBytes().toHex();
			user.setUserSalt(salt);

		}

		String newPassword = new SimpleHash(algorithmName, user.getUserPassword(), ByteSource.Util.bytes(user.getUserSalt()), hashIterations).toHex();
		user.setUserPassword(newPassword);
	}

	public static String encryptString(String str) {

		String newPassword = "";
		try {
			// String salt = generator.nextBytes().toHex();
			newPassword = new SimpleHash(algorithmName, str, ByteSource.Util.bytes(SALT), hashIterations).toHex();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newPassword;
	}

	public static String generateToken(String userid, String encryptedpwd, String requestIP) {
		String source=userid + encryptedpwd + requestIP;
		String token = "";
		try {
			token = new SimpleHash(algorithmName, source, SALT, hashIterations).toHex();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	public static String[] parseTZToken(String token) {

		String[] decrypstr = new String[2];
		try {
			// String salt = generator.nextBytes().toHex();

			String decrypedstr = decodeStr(token);
			String userid = decrypedstr.substring(0, 18);
			String tokenstr = decrypedstr.substring(18);
			decrypstr[0] = userid;
			decrypstr[1] = tokenstr;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return decrypstr;
	}

	public static void main(String[] args) {

	 

	}

	public static String encodeStr(String plainText) {
		byte[] b = plainText.getBytes();
		Base64 base64 = new Base64();
		b = base64.encode(b);
		String s = new String(b);
		return s;
	}

	/**
	 * 
	 * 创建日期2011-4-25上午10:15:11 修改日期 作者：dh *TODO 使用Base64加密 return
	 */
	public static String decodeStr(String encodeStr) {
		byte[] b = encodeStr.getBytes();
		Base64 base64 = new Base64();
		b = base64.decode(b);
		String s = new String(b);
		return s;
	}
}
