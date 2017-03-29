package cn.infrastructure.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import cn.infrastructure.http.encryp.Hex;

/**
 * 设备信息和uuid工具类
 *
 * @author Frank
 * @version 1.0 Create by 2016.1.21
 */
public class DeviceUtil {

	/**
	 * 获取手机mac地址 错误返回12个0
	 */
	public static String getMacAddress(Context context) {
		if (VERSION.SDK_INT <= VERSION_CODES.ICE_CREAM_SANDWICH) {
			return getDeviceId(context);
		}
		// 获取mac地址：
		String macAddress = "000000000000";
		try {
			WifiManager wifiMgr = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr
					.getConnectionInfo());
			if (null != info) {
				if (!TextUtils.isEmpty(info.getMacAddress()))
					macAddress = info.getMacAddress().replace(":", "");
				else
					return macAddress;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return macAddress;
		}
		return macAddress;
	}

	/**
	 * 获得设备id
	 *
	 * @return
	 */
	public static String getDeviceId(Context context) {
		TelephonyManager mTm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTm.getDeviceId();
	}

	/**
	 * 获得屏幕尺寸
	 *
	 * @return
	 */
	public static String getDisplay(Context context) {
		String display = "480*720";
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		int height = metric.heightPixels; // 屏幕高度（像素）
		display = width + "*" + height;
		return display;
	}

	/**
	 * 获得屏幕宽
	 *
	 * @return
	 */
	public static int getDisplayWidth(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		return width;
	}

	/**
	 * 获得屏幕高
	 *
	 * @return
	 */
	public static int getDisplayHeight(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		int height = metric.heightPixels; // 屏幕高度
		return height;
	}

	/**
	 * 获得手机型号
	 *
	 * @return
	 */
	public static String getModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获得sdk版本号
	 *
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getVersionSdk() {
		return VERSION.SDK;
	}

	/**
	 * 获得系统版本号
	 *
	 * @return
	 */
	public static String getVersionRelease() {
		return VERSION.RELEASE;
	}

	/**
	 * 获得器识别码
	 * 以下是生成机器识别码的方法，ios可以使用设备id代替wifi-mac 
	 * 安卓3.x 2.x 1.x 直接使用设备号ime等，安卓4.x以上使用wifi-mac 
	 * wifi-mac也可以考虑使用蓝牙mac，看你们的设计
	 * 生成规则
	 * m1 = md5([wifi-mac])   [wifi-mac]  ios可以使用系统识别码，安卓2.x系统使用系统的设备码
	 * m2 = md5([m1]+[wifi-mac]+[手机型号])
	 * m3 = md5([系统版本号]+[m1]+[m2])
	 * m4 = md5([m3]+[m1]+[屏幕尺寸]+[m2])
	 * m5 = md5([m1]+[m2]+[m4]+[wifi-mac]+[m3])
	 * [m4(0,4)]+[(-)]+[m1(8,8)]+[(-)]+[m1(0,4)]+[(-)]+[m1(4,4)]+[(-)]+[m2(0,4)]+[(-)]+[m3(8,8)]+[m4(0,4)]+[(-)]+[m5(8,8)]
	 * 格式
	 * 0000-00000000-0000-0000-0000-000000000000-00000000
	 * @return
	 */
	public static String getUid(Context context) {
		StringBuilder sbUid = new StringBuilder();

		String macAddress = getMacAddress(context);

		String m1 = get32MD5(macAddress);

		String m2 = get32MD5(new StringBuilder().append(m1).append(macAddress).append(getModel()).toString());

		String m3 = get32MD5(new StringBuilder().append(getVersionRelease()).append(m1).append(m2).toString());

		String m4 = get32MD5(new StringBuilder().append(m3).append(m1).append(getDisplay(context)).append(m2).toString());

		String m5 = get32MD5(new StringBuilder().append(m1).append(m2).append(m4).append(macAddress).append(m3).toString());

		sbUid.append(m4.substring(4, 8))
				.append("-")
				.append(m1.substring(8, 16))
				.append("-")
				.append(m1.substring(0, 4))
				.append("-")
				.append(m1.substring(4, 8))
				.append("-")
				.append(m2.substring(0, 4))
				.append("-")
				.append(m3.substring(8, 16))
				.append(m4.substring(0, 4))
				.append("-")
				.append(m5.substring(8, 16));
		return sbUid.toString();
	}

	/**
	 * md5签名
	 *
	 * @param s
	 * @return
	 */
	public static String get32MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				// System.out.println((int)b);
				// 将没个数(int)b进行双字节加密
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * md5签名-->base64
	 *
	 * @param s
	 * @return
	 */
	public static String get128MD5ToBase64(String s) {
		try {
			byte[] strTemp = s.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] encodeBytes = Base64.encode(mdTemp.digest(), Base64.DEFAULT);
			return new String(encodeBytes).toString().trim();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * md5签名-->base64
	 *
	 * @param s
	 * @return
	 */
	public static String get128MD5ToBase64(ByteArrayOutputStream out) {
		try {
			byte[] strTemp = out.toByteArray();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] encodeBytes = Base64.encode(mdTemp.digest(), Base64.DEFAULT);
			return new String(encodeBytes).toString().trim();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得uuid
	 *
	 * @return
	 */
	public static String getUUId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 产生HmacSHA256摘要算法的密钥
	 *
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] initHmacSHA256Key() throws NoSuchAlgorithmException {
		// 初始化HmacMD5摘要算法的密钥产生器
		KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
		// 产生密钥
		SecretKey secretKey = generator.generateKey();
		// 获得密钥
		byte[] key = secretKey.getEncoded();
		return key;
	}

	/**
	 * HmacSHA1摘要算法 对于给定生成的不同密钥，得到的摘要消息会不同，所以在实际应用中，要保存我们的密钥
	 *
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String encodeHmacSHA256(byte[] data, byte[] key)
			throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, "HmacSHA256");// 还原密钥
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());// 实例化Mac
		mac.init(secretKey);// 初始化mac
		byte[] digest = mac.doFinal(data);// 执行消息摘要
		return Hex.toHexString(digest);// 转为十六进制的字符串
	}
}
