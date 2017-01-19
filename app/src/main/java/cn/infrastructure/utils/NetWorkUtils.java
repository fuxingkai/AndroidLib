package cn.infrastructure.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author: Frank
 * @date: 2016-07-12
 * 
 */
public class NetWorkUtils {

	/**
	 * 判断是否有网络可用
	 *
	 * @param context
	 * @return
	 */
	public static boolean isNetAvailable(Context context) {
		NetworkInfo networkInfo = getActiveNetworkInfo(context);
		if (networkInfo != null) {
			return networkInfo.isAvailable();
		} else {
			return false;
		}
	}

	/**
	 * 获取可用的网络信息
	 *
	 * @param context
	 * @return
	 */
	private static NetworkInfo getActiveNetworkInfo(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			return cm.getActiveNetworkInfo();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 此判断不靠谱
	 *
	 * 检测网络是否连接
	 */
	public static boolean isNetConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo[] infos = cm.getAllNetworkInfo();
			if (infos != null) {
				for (NetworkInfo ni : infos) {
					if (ni.isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 检测wifi是否连接
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null
					&& networkInfo.getType() == ConnectivityManager.TYPE_WIFI&&networkInfo.isConnected()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检测3G是否连接
	 */
	public static boolean is3gConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null
					&& networkInfo.getType() == ConnectivityManager.TYPE_MOBILE&&networkInfo.isConnected()) {
				return true;
			}
		}
		return false;
	}

}
