package cn.easy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by superlee on 2017/11/16.
 * 日期格式化
 */
public final class DateUtil {

    public final static String Format_Year_To_Day = "yyyy-MM-dd";
    public final static String Format_Year_To_Second = "yyyy-MM-dd HH:mm:ss";

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType) {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType){
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 两个时间戳相隔的分钟数
     *
     * @param time1  time1
     * @param time2  time2
     * @param minute minute
     * @return boolean
     */
    public static boolean isTwoTimeDeltaMinute(long time1, long time2, int minute) {
        long deltaSecond = Math.abs(time1 - time2);
        long deltaMinute = deltaSecond / (1000 * 60);
        int curMinute = new Long(deltaMinute).intValue();
        return curMinute < minute;
    }

    /**
     * 计算两个Date之间的天数
     *
     * @param date1 Date1
     * @param date2 Date2
     * @return int
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        //同一年
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                //闰年
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else {
                    //不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {
            //不同年
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /**
     * 两个毫秒时间戳之间相隔的天数
     *
     * @param timestamp1 时间戳1
     * @param timestamp2 时间戳2
     * @return int
     */
    public static int differentDays(long timestamp1, long timestamp2) {
        return differentDays(longToDate(timestamp1, "yyyy-MM-dd"), longToDate(timestamp2, "yyyy-MM-dd"));
    }

    /**
     * 某个时间戳n秒之后的时间戳
     *
     * @param timestamp timestamp
     * @param seconds   seconds
     * @return long
     */
    public static long timestampSecondAfter(long timestamp, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);

        cal.add(Calendar.SECOND, seconds);
        return cal.getTimeInMillis();
    }

    /**
     * 计算某一天最后一秒的时间戳
     *
     * @param dateString 格式为"yyyy-MM-dd"
     * @return Long
     */
    public static Long lastSecondOfDay(String dateString) {
        return stringToLong(dateString + " 23:59:59", Format_Year_To_Second);
    }

    /**
     * 计算某一天最后一秒的时间戳
     * 该方法比lastSecondOfDay通用，推荐使用该方法
     *
     * @param dateString 格式为"yyyy-MM-dd HH:mm:ss"
     * @return Long
     */
    public static Long lastSecondOfDaySec(String dateString) {
        if (dateString.isEmpty()) {
            return 0L;
        }
        String sub = dateString.substring(0, 10) + " 23:59:59";
        return stringToLong(sub, Format_Year_To_Second);
    }

    /**
     * 计算某一个时间戳对应的这天 23：59：59时的时间戳
     *
     * @param second 一天中某个时间戳
     * @return Long
     */
    public static Long lastSecondOfSec(Long second) {
        String date = longToString(second, Format_Year_To_Day);
        return lastSecondOfDay(date);
    }
}
