package com.dx.springlearn.handlers.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * <b>时间工具类</b>
 * <p>
 * 所需方法不满足时，可以直接添加。
 * <br>方法注释清楚
 * 
 * 
 */
public class DateUtil {
	private static Logger log =Logger.getLogger(DateUtil.class);

	private DateUtil() {}

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.sss";
	
	public static final String DATE_FORMAT_SS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String YYYYMMddHHMMSSSSS = "yyyyMMddHHmmsssss";
	
	public static final String YYYYMMdd = "yyyyMMdd";
	
	/**
	 * 获取当前时间
	 * <br> 时间格式：yyyy-MM-dd HH:mm:ss
	 * @return 当前时间
	 */
	public static String getCurrent() {
		return getCurrent(DATE_FORMAT);
	}
	/**
	 * 根据传入format获取当前时间
	 * @param format
	 * @return
	 */
	public static String getCurrent(String format) {
		return format(format, new Date());
	}
	/**
	 * 获取时间特定格式字符串
	 * <br> 把传入时间转换成传入时间格式的字符串
	 * @param format
	 * @param date
	 * @return
	 */
	public static String format(String format, Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	public static String defaultFormat(String dateStr){
		return format(DateUtil.YYYYMMddHHMMSSSSS, DateUtil.DATE_FORMAT_SS, dateStr);
	}
	
	/**
	 * 格式化指定字符串格式的日期
	 * 
	 * @param oldFormat
	 *            与传入的dateStr格式对应 如:yyyyMMddHHmmss
	 * @param newFormat
	 *            转换后的格式 如:yyyy年MM月dd日HH时
	 * @param dateStr
	 *            传入的时间字符串
	 * @return 转换后的时间字符串
	 */
	public static String format(String oldFormat, String newFormat,
			String dateStr){
		try{
			return new SimpleDateFormat(newFormat).format(new SimpleDateFormat(
				oldFormat).parse(dateStr));
		}catch(Exception e){
			log.error("转换异常：" + oldFormat + ":" + newFormat + ":" + dateStr);
		}
		return dateStr;
	}

	/**
	 * 将时间字符串转换为时间对象
	 * 
	 * @param dateStr
	 *            时间字符串
	 * @param format
	 *            时间字符串的格式
	 * @return 转换后的时间对象
	 * @throws ParseException
	 */
	public static Date convert(String dateStr, String format)
			throws ParseException {
		return new SimpleDateFormat(format).parse(dateStr);
	}

	/**
	 * 将时间对象转换成时间字符串
	 * 
	 * @param date
	 *            时间对象
	 * @param format
	 *            时间字符串格式
	 * @return 转换后的时间字符串
	 * @throws ParseException
	 */
	public static String convert(Date date, String format)
			throws ParseException {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param date
	 *            时间
	 * @param day
	 *            间隔天数
	 * @return 几天前的时间
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param date
	 *            时间
	 * @param day
	 *            间隔天数
	 * @return 几天后的时间
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
	
	
	/**
	 * 得到几天后的时间
	 * @param d 时间
	 * @param formatStr 时间格式
	 * @param day 间隔天数
	 * @return
	 */
	public static String getDateAfter(String d, String formatStr,int day) {
		return dateToFormatStrDate(getDateAfter(StringToDate(d,formatStr),day), formatStr);
	}
	
	/**
	 * 得到几天前的时间
	 * @param d 时间
	 * @param formatStr 时间格式
	 * @param day 间隔天数
	 * @return
	 */
	public static String getDateBefore(String d, String formatStr,int day) {
		return dateToFormatStrDate(getDateBefore(StringToDate(d,formatStr),day), formatStr);
	}
	
	 /**
	   * @author liaohongwei
	   * @Title getComputeTime
	   * @Description 获取计算时间(去除毫秒数)
	   * @return Calendar
	   */
	  public static Calendar getCurrentTime() {
	    Calendar cal = new GregorianCalendar();
	    cal.set(Calendar.MILLISECOND, 0);
	    return cal;
	  }

	/**
	 * 得到几月前的时间
	 * 
	 * @param month
	 *            间隔月数
	 * @param d
	 *            时间
	 * @return 几月后的时间
	 */
	public static Date getMonthBefore(Date d, int month) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) - month);
		return now.getTime();
	}

	/**
	 * 得到几月后的时间
	 * 
	 * @param month
	 *            间隔月数
	 * @param d
	 *            时间
	 * @return 几月后的时间
	 */
	public static Date getMonthAfter(Date d, int month) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
		return now.getTime();
	}

	/**
	 * @Title: startTime
	 * @Description: 开始时间转化
	 * @param startTime
	 *            开始时间
	 * @param startQuarte
	 *            开始季度
	 * @param timeType
	 *            时间类型（时间粒度）
	 * @return
	 * @return: String
	 */
	public static String startTime(String startTime, String startQuarte,
			String timeType) {
		if ("4".equals(timeType)) {
			startTime = startTime.substring(0, 4);
			if ("1".equals(startQuarte)) {
				return startTime + "-01-01";
			} else if ("2".equals(startQuarte)) {
				return startTime + "-04-01";
			} else if ("3".equals(startQuarte)) {
				return startTime + "-07-01";
			} else {
				return startTime + "-10-01";
			}
		} else {
			return startTime;
		}

	}

	/**
	 * @Title: endTime
	 * @Description:结束时间转化
	 * @param endTime
	 *            结束时间
	 * @param endQuarter
	 *            结束季度
	 * @param timeType
	 *            时间类型（时间粒度）
	 * @return
	 * @return: String
	 */
	public static String endTime(String endTime, String endQuarter,
			String timeType) {
		if ("4".equals(timeType)) {
			endTime = endTime.substring(0, 4);
			if ("1".equals(endQuarter)) {
				return endTime + "-03-31";
			} else if ("2".equals(endQuarter)) {
				return endTime + "-06-30";
			} else if ("3".equals(endQuarter)) {
				return endTime + "-09-30";
			} else {
				return endTime + "-12-31";
			}
		} else {
			return endTime;
		}
	}

	  
	  /**
	   * 
	   * @Title: daysBetween
	   * @Description: 用于统计开始日期到结束日期之间的所有日期(list中包括开始日期和结束日期)
	   * @param @param startTime 开始时间，日期格式：yyyy-MM-dd
	   * @param @param endTime 结束时间，日期格式：yyyy-MM-dd
	   * @param @return date_format 返回的日期格式
	   * @param @throws ParseException   
	   * @return List<String>
	   */
	  public static List<String> daysBetweens(String startTime, String endTime, String date_format) throws ParseException {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(sdf.parse(startTime));
	    long time1 = cal.getTimeInMillis();
	    cal.setTime(sdf.parse(endTime));
	    long time2 = cal.getTimeInMillis();
	    long between_days = (time2 - time1) / (1000 * 3600 * 24);
	    int days = Integer.parseInt(String.valueOf(between_days));
	    List<String> list = null;
	    if (days > 0) {
	      list = new ArrayList<String>();
	      for (int i = 0; i <= days; i++) {
	        Calendar calTwo = Calendar.getInstance();
	        calTwo.setTime(sdf.parse(startTime));
	        calTwo.add(Calendar.DAY_OF_YEAR, i);
	        String str = new SimpleDateFormat(date_format).format(calTwo.getTime());
	        list.add(str);
	      }
	    } else if (startTime.equals(endTime)) {
	      list = new ArrayList<String>();
	      String date = new SimpleDateFormat(date_format).format(sdf.parse(startTime));
	      list.add(date);
	    }

	    return list;
	  }
	  
	  
	  /**
	   * 两个时间相差多少秒
	   * 
	   * @param time1 第一个时间
	   * @param time2 第二个时间
	   * @param DateFormat 时间转化格式 默认：yyyy-MM-dd HH:mm:ss
	   * @return
	   * @throws ParseException
	   */
	  public static long getDistanceTimesSecond(String time1, String time2, DateFormat df) throws ParseException {
	    if (df == null) {
	      df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    }
	    Date one = df.parse(time1);
	    Date two = df.parse(time2);
	    long sec = 0;
	    long t1 = one.getTime();
	    long t2 = two.getTime();
	    long diff;
	    if (t1 < t2) {
	      diff = t2 - t1;
	    } else {
	      diff = t1 - t2;
	    }
	    sec = diff / 1000;
	    return sec;
	  }
	  

	  /**
	   * 获取相隔时间的下一个时间
	   * 
	   * @param time1 第一个时间
	   * @param second 相隔秒（正数则为时间加，负数则时间减）
	   * @param DateFormat 时间转化格式 默认：yyyy-MM-dd HH:mm:ss
	   * @return
	   * @throws ParseException
	   */
	  public static String getDisTimeBySecond(String time1, int second, DateFormat df) throws ParseException {
	    if (df == null) {
	      df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    }
	    Calendar oneCal = Calendar.getInstance();
	    Date one = df.parse(time1);

	    oneCal.setTime(one);
	    oneCal.add(Calendar.SECOND, second);
	    return df.format(oneCal.getTime());
	  }

	  /**
	   * 
	   * @param time1
	   * @param time2
	   * @param df
	   * @return 0:表示两时间相等； <br>
	   *         1：time1在time2之前; time1小于time2<br>
	   *         -1:time1在time2之后,也就是time1大于time2
	   * @throws ParseException
	   */
	  public static long compareTime(String time1, String time2, DateFormat df) throws ParseException {
	    if (df == null) {
	      df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    }

	    Calendar oneCal = Calendar.getInstance();
	    Calendar twoCal = Calendar.getInstance();

	    Date one = df.parse(time1);
	    Date two = df.parse(time2);

	    oneCal.setTime(one);
	    twoCal.setTime(two);

	    if (oneCal.equals(twoCal)) {
	      return 0;
	    } else if (oneCal.before(twoCal)) {
	      return 1;
	    } else {
	      return -1;
	    }
	  }
	  
	  /**
	   * 比较两个时间大小
	   * @param date1
	   * @param date2
	   * @return 0:表示两时间相等； <br>
	   *         1：date1在date2之前; date1小于date2<br>
	   *         -1:date1在date2之后,也就是date1大于date2
	   * @throws ParseException
	   */
	  public static long compareTime(Date date1, Date date2) throws ParseException {
	    Calendar oneCal = Calendar.getInstance();
	    Calendar twoCal = Calendar.getInstance();
	    oneCal.setTime(date1);
	    twoCal.setTime(date2);
	    if (oneCal.equals(twoCal)) {
	      return 0;
	    } else if (oneCal.before(twoCal)) {
	      return 1;
	    } else {
	      return -1;
	    }
	  }
	  /**
	   * 比较两个时间大小
	   * <br> 可以对时间加上一个毫秒数再比较
	   * @param date1
	   * @param date2
	   * @param addMill1 date1添加多少毫秒
	   * @param addMill2 date2添加多少毫秒
	   * @return 0:表示两时间相等； <br>
	   *         1：date1在date2之前; date1小于date2<br>
	   *         -1:date1在date2之后,也就是date1大于date2
	   * @throws ParseException
	   */
	  public static long compareTime(Date date1, Date date2,int addMill1,int addMill2) throws ParseException {
		    Calendar oneCal = Calendar.getInstance();
		    Calendar twoCal = Calendar.getInstance();
		    oneCal.setTime(date1);
		    oneCal.add(Calendar.MILLISECOND, addMill1);
		    twoCal.setTime(date2);
		    oneCal.add(Calendar.MILLISECOND, addMill2);
		    if (oneCal.equals(twoCal)) {
		      return 0;
		    } else if (oneCal.before(twoCal)) {
		      return 1;
		    } else {
		      return -1;
		    }
	 }
	  
	  /**
		 * @description 日期转化成相应格式日期,默认格式 yyyy-MM-dd
		 * @param date
		 * @param formatStr
		 *            需要转换的字符串
		 * @return Date 返回转换后的时间
		 */
		public static Date dateToFormatDate(Date date, String formatStr) {
			if (date == null) {
				return null;
			}
			if (formatStr == null || formatStr.equals("")) {
				formatStr = "yyyy-MM-dd";
			}
			DateFormat sdf = new SimpleDateFormat(formatStr);
			String strDate = sdf.format(date);
			try {
				date = sdf.parse(strDate);
			} catch (ParseException e) {
				log.error("data format meet exception ", e);
			}
			return date;
		}

		/**
		 * @description 日期转化成相应格式日期,默认格式 yyyy-MM-dd
		 * @param date
		 * @param formatStr
		 *            需要转换的字符串
		 * @return Date 返回转换后的时间(字符串类型)
		 */
		public static String dateToFormatStrDate(Date date, String formatStr) {
			if (date == null) {
				return null;
			}
			if (formatStr == null || formatStr.equals("")) {
				formatStr = "yyyy-MM-dd";
			}
			DateFormat sdf = new SimpleDateFormat(formatStr);
			return sdf.format(date);
		}
		
		/**
		 * @description 字符串转换到时间格式
		 * @param dateStr
		 *            需要转换的字符串
		 * @param formatStr
		 *            需要格式的目标字符串 举例 yyyy-MM-dd
		 * @return Date 返回转换后的时间
		 */
		public static Date StringToDate(String dateStr, String formatStr) {
			if (dateStr == null || dateStr.equals("")) {
				return null;
			}
			DateFormat sdf = new SimpleDateFormat(formatStr);
			Date date = null;
			try {
				date = sdf.parse(dateStr);
			} catch (ParseException e) {
				log.error("data format meet exception ", e);
			}
			return date;
		}

		/**
		 * 字符时间转换为字符时间
		 * @param dateStr 时间字符串
		 * @param dateStrFormat 时间字符串格式
		 * @param outFormat 转化后的时间字符串
		 * @return
		 */
		public static String StrDateFormatStrDate(String dateStr, String dateStrFormat, String outFormat){
			Date newDate = StringToDate(dateStr, dateStrFormat);
			return dateToFormatStrDate(newDate,outFormat);
		}
		
		/**
		 * 获取传入时间的下一个时间。
		 * <br>传入时间为年、则返回下一年。传入时间为月，则返回下一月，适用于：日、小时、分钟、秒、毫秒
		 * @param dateStr 传入时间
		 * @param formatStr 传入时间格式
		 * @return
		 */
		public static String getNextDateStr(String dateStr,String formatStr){
			if(formatStr == null || formatStr.length() == 0){
				log.error("formatStr is null ");
				return null;
			}
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(format.parse(dateStr));
			} catch (ParseException e) {
				log.error("date format exception ", e);
				return null;
			}
			char[] chars = formatStr.toCharArray();
			
			char flag = chars[chars.length-1];
			if('y' == flag){
				c.add(Calendar.YEAR, 1);
			}else if('M' == flag){
				c.add(Calendar.MONTH, 1);
			}else if('d' == flag){
				c.add(Calendar.DATE, 1);
			}else if('H' == flag){
				c.add(Calendar.HOUR, 1);
			}else if('m' == flag){
				c.add(Calendar.MINUTE, 1);
			}else if('s' == flag){
				c.add(Calendar.SECOND, 1);
			}else{
				c.add(Calendar.MILLISECOND, 1);
			}
	        return format.format(c.getTime());
		}
		
}
