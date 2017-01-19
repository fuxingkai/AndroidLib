package cn.infrastructure.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.text.format.Time;

@SuppressLint("SimpleDateFormat")
public class DateUtils {
    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 得到当前日期是星期几。
     *
     * @return 当为周日时，返回0，当为周一至周六时，则返回对应的1-6。
     */
    public static final int getCurrentDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 得到当前日期年份
     *
     * @return
     */
    public static final int getCurrentDayOfYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 得到当前日期月份
     *
     * @return
     */
    public static final int getCurrentDayOfMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * 得到当前时间秒数
     *
     * @return
     */
    public static final int getCurrentSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd HH:mm
     */
    public static String getStringDateEN() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String strToTimeStr(String longNumber) {
        long number = Long.parseLong(longNumber);
        return "" + (number / 3600000) + ":" + (number % 3600000 / 60000) + ":"
                + (number % 3600000 % 60000 / 1000);
    }

    /**
     * 将“00:00:00”格式的时间转换为以毫秒为单位的数字串
     *
     * @param timeStr
     * @return
     */
    public static String timeStrToStr(String timeStr) {
        String str[] = timeStr.split(":");
        return (Integer.parseInt(str[0]) * 3600000 + Integer.parseInt(str[1])
                * 60000 + Integer.parseInt(str[2]) * 1000)
                + "";
    }

    /**
     * 根据long型的数据获取时间值
     *
     * @param value
     * @return
     */
    public static String getNormalTimeShort(long value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(new Date(value));
        return time;
    }

    /**
     * 根据long型的数据获取时间值
     *
     * @param value
     * @return
     */
    public static String getNormalTime(long value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = format.format(new Date(value));
        return time;
    }

    /**
     * 根据long型的数据获取时间值：格式:yyyy-MM-dd HH:mm:ss
     *
     * @param value
     * @return
     */
    public static String getNormalTimeLong(long value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date(value));
        return time;
    }

    /**
     * 返回月日字符串，如xx月xx日
     *
     * @param date
     * @return
     */
    public static String toMonthDay(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = sdf.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            return month + "月" + day + "日";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据给出的时间与现在的时间比较返回时间差
     *
     * @param 0:yyyy-MM-dd HH:mm:ss;1:时间戳
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String dateCompareWithNow(String comparedstring, int type) {
        String result = null;
        // 设定时间的模板
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        SimpleDateFormat format_1 = new SimpleDateFormat("HH:mm");
        // SimpleDateFormat format_2 = new SimpleDateFormat("EEEE");
        Date comparedTime = null;
        switch (type) {
            case 0:
                try {
                    comparedTime = format.parse(comparedstring);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                long stringTime = Long.valueOf(comparedstring);
                comparedTime = new Date(stringTime * 1000L);
                break;
        }
        Date curTime = new Date(System.currentTimeMillis());
        long time = curTime.getDate() - comparedTime.getDate();

        if (time < 1) {
            result = format_1.format(comparedTime);
        } else if (time < 2) {
            result = "昨天" + format_1.format(comparedTime);
        } else if (time < 3) {
            result = "前天" + format_1.format(comparedTime);
        } else {
            // result=WEEK[comparedTime.getDay()];
            result = format.format(comparedTime);
        }

        return result;
    }

    public static String[] WEEK = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五",
            "星期六"};

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    /**
     * 格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式：yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentTimeNoDivider() {
        return getCurrentTime("yyyyMMddHHmmssSSS");
    }

    /**
     * 返回一个固定格式的当前时间格式的字符串
     *
     * @return "HH:mm AM" 或者 "HH:mm PM" 或者一个空得字符串
     */
    @SuppressLint("NewApi")
    public static String getCurrentTimeString() {
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。

        // int year = t.year;
        // int month = t.month;

        int hour = t.hour; // 0-23
        int minute = t.minute;
        // int second = t.second;
        StringBuilder sb = new StringBuilder();
        String strAP = "";

        if (hour < 12) {
            strAP = "AM";
            if (hour == 0) {
                sb.append("12");
            } else if (hour < 10) {
                sb.append("0");
                sb.append(hour);
            } else {
                sb.append(hour);
            }
        } else {
            strAP = "PM";
            if (hour == 12) {
                sb.append("12");
            } else if ((hour - 12) < 10) {
                sb.append("0");
                sb.append(hour - 12);
            } else {
                sb.append(hour - 12);
            }
        }

		/*
         * if (((hour > 12) && ((hour - 12) < 10 )) || (hour < 10)) {
		 * sb.append("0"); } if (hour > 11) { if(hour > 12){ sb.append(hour -
		 * 12); }else{ sb.append(hour); } strAP = "PM"; }else{ sb.append(hour);
		 * strAP = "AM"; }
		 */

        sb.append(":");
        if (minute < 10) {
            sb.append("0");
        }
        sb.append(minute);
        sb.append(" " + strAP);

        return sb.toString();
    }

    /**
     * 根据时间长度返回一个固定格式的时间长度字符串
     *
     * @return "HH:mm:ss" 或者 "mm:ss"
     */
    public static String getCountTimeString(long timeCount) {
        StringBuilder sb = new StringBuilder();

        int totalsec = (int) (timeCount / 1000);
        int tmpsec = totalsec;

        if (tmpsec > 3600) {
            int hours = tmpsec / 3600;
            if (hours < 10) {
                sb.append("0");
            }
            sb.append(hours);
            sb.append(":");
            tmpsec = tmpsec - hours * 3600;
        }

        if (tmpsec > 60) {
            int minutes = tmpsec / 60;
            if (minutes < 10) {
                sb.append("0");
            }
            sb.append(minutes);
            sb.append(":");
            tmpsec = tmpsec - minutes * 60;
        } else {
            sb.append("00:");
        }

        if (tmpsec < 10) {
            sb.append("0");
        }
        sb.append(tmpsec);

        return sb.toString();
    }

    public static String getCurrentTimeStringHM() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(d);
    }

    public static long getTimeMills(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        long l;
        try {
            d = sdf.parse(time);
            l = d.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            l = 0;
        }

        return l;
    }

    /**
     * 获取当前系统的日期
     *
     * @return
     */
    public static long curTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 计算两个毫秒数时间相差的秒数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getSecondsBetweenTwoDate(long date1, long date2) {
        return (int) ((date1 - date2) / 1000);
    }

    /**
     * 获取指定年的月的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

}
