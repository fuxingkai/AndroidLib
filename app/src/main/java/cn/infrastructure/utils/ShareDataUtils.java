package cn.infrastructure.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 对SharedPreference文件中的各种类型的数据进行存取操作（未考虑加密、多进程访问等.）
 * 
 * @author Frank 2016-7-2
 */
public class ShareDataUtils {

	private static SharedPreferences sp;

	private static void init(Context context) {
		if (sp == null) {
			sp = PreferenceManager.getDefaultSharedPreferences(context);
		}
	}

	public static void setInt(Context context, String key, int value) {
		if (sp == null) {
			init(context);
		}
		sp.edit().putInt(key, value).commit();
	}

	public static int getInt(Context context, String key) {
		if (sp == null) {
			init(context);
		}
		return sp.getInt(key, 0);
	}

	public static void setLong(Context context, String key, long value) {
		if (sp == null) {
			init(context);
		}
		sp.edit().putLong(key, value).commit();
	}

	public static long getLong(Context context, String key) {
		if (sp == null) {
			init(context);
		}
		return sp.getLong(key, 0l);
	}

	public static void setFloat(Context context, String key, float value) {
		if (sp == null) {
			init(context);
		}
		sp.edit().putFloat(key, value).commit();
	}

	public static Float getFloat(Context context, String key) {
		if (sp == null) {
			init(context);
		}
		return sp.getFloat(key, 0f);
	}

	public static void setBoolean(Context context, String key, boolean value) {
		if (sp == null) {
			init(context);
		}
		sp.edit().putBoolean(key, value).commit();
	}

	public static Boolean getBoolean(Context context, String key) {
		if (sp == null) {
			init(context);
		}
		return sp.getBoolean(key, false);
	}

	public static void setString(Context context, String key, String value) {
		if (sp == null) {
			init(context);
		}
		sp.edit().putString(key, value).commit();
	}

	public static String getString(Context context, String key) {
		if (sp == null) {
			init(context);
		}
		return sp.getString(key, "");
	}

}