package com.cloudstudy.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * 日期处理类
 * </p>
 * 
 * @author $Author: LinEnChuan $
 * @version $Revision: 1.0 $Date: 2012-08-28 $
 * 
 */
public class DateUtil {

	public static final String formatDateTimeStr = "yyyy-MM-dd HH:mm:ss";
	public static final String formatDateStr = "yyyy-MM-dd";
	public static final String formatDStr = "dd";
	public static final String formatMStr = "MM";
	public static final String formatYStr = "yyyy";
	public static final String formatYMStr = "yyyyMM";
	public static final String formatYMDStr = "yyyyMMdd";
	public static final String formatYMDHMSStr = "yyyyMMddHHmmss";
	public static final String formatYMDHMSSStr = "yyyyMMddHHmmssSS";
	public static final String formatHmsStr = "HH:mm:ss";
	public static final String formatMDStr = "MM月dd日";
	public static final String formatYYMMDDStr = "yyyy年MM月dd日";

	public static void main(String[] args) {
		String ss = DateUtil.dateToString(new Date(), DateUtil.formatMDStr);
		System.out.println(ss);

		ss = DateUtil.getYearAsString() + "年" + ss;
		System.out.println(ss);

		Date date = DateUtil.stringToDate(ss, DateUtil.formatYYMMDDStr);
		System.out.println(date);

		String sss = DateUtil.dateToString(date, DateUtil.formatDateTimeStr);
		System.out.println(sss);

	}

	public static Date getNow() {
		return new Date();
	}

	public static java.sql.Timestamp getNowAsTimestamp() {
		return new java.sql.Timestamp(DateUtil.getNow().getTime());
	}

	/**
	 * 获取x月x日的字符串
	 * 
	 * @return
	 */
	public static String getMonthAndDay() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		String dateStr = (ca.get(Calendar.MONTH) + 1) + "月" + (ca.get(Calendar.DATE)) + "日";
		return dateStr;
	}

	/**
	 * 当前日期的yyyy-MM-dd HH:mm:ss格式字符串
	 * 
	 * @return
	 */
	public static String getNowAsString() {
		String dateString = null;
		try {
			SimpleDateFormat formatDateTime = new SimpleDateFormat(formatDateTimeStr);
			dateString = formatDateTime.format(new Date());
		} catch (Exception exception) {
		}
		return dateString;
	}

	/**
	 * 当前日期的yyyy-MM-dd格式字符串
	 * 
	 * @return
	 */
	public static String getNowDateAsString() {
		String dateString = null;
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat(formatDateStr);
			dateString = formatDate.format(new Date());
		} catch (Exception exception) {
		}
		return dateString;
	}

	/**
	 * 当前日期的dd格式字符串
	 * 
	 * @return
	 */
	public static String getDayAsString() {
		String dateString = null;
		try {
			SimpleDateFormat formatD = new SimpleDateFormat(formatDStr);
			dateString = formatD.format(new Date());
		} catch (Exception exception) {
		}
		return dateString;
	}

	/**
	 * 当前日期的MM格式字符串
	 * 
	 * @return
	 */
	public static String getMonthAsString() {
		String dateString = null;
		try {
			SimpleDateFormat formatM = new SimpleDateFormat(formatMStr);
			dateString = formatM.format(new Date());
		} catch (Exception exception) {
		}
		return dateString;
	}

	/**
	 * 当前日期的yyyy格式字符串
	 * 
	 * @return
	 */
	public static String getYearAsString() {
		String dateString = null;
		try {
			SimpleDateFormat formatY = new SimpleDateFormat(formatYStr);
			dateString = formatY.format(new Date());
		} catch (Exception exception) {
		}
		return dateString;
	}

	/**
	 * 当前日期的yyyyMM格式字符串
	 * 
	 * @return
	 */
	public static String getYearMonthAsString() {
		String dateString = null;
		try {
			SimpleDateFormat formatYM = new SimpleDateFormat(formatYMStr);
			dateString = formatYM.format(new Date());
		} catch (Exception exception) {
		}
		return dateString;
	}

	/**
	 * 当前日期的yyyyMMdd格式字符串
	 * 
	 * @return
	 */
	public static String getYearMonthDayAsString() {
		String dateString = null;
		try {
			SimpleDateFormat formatYMD = new SimpleDateFormat(formatYMDStr);
			dateString = formatYMD.format(new Date());
		} catch (Exception exception) {
		}
		return dateString;
	}

	/**
	 * 将格式为yyyy-MM-dd或yyyy-MM-dd HH:mm:ss的字符串parse成java.util.Date对象
	 * 
	 * @param sDate
	 * @return
	 */
	public static Date stringToDate(String sDate) {
		Date date = null;
		try {
			if (sDate.indexOf(':') > -1) {
				date = _strToDateTime(sDate);
			} else {
				date = _strToDate(sDate);
			}
		} catch (Exception e) {
		}
		return date;
	}

	public static Date stringToDate(String sDate, String format) {
		Date date = null;
		try {
			SimpleDateFormat f = new SimpleDateFormat(format);
			date = f.parse(sDate);
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 字符串转换日期，字符串格式为yyyy-MM-dd
	 * 
	 * @param sDate
	 * @return
	 */
	@SuppressWarnings("null")
	private static Date _strToDate(String sDate) {

		Date date = null;
		try {
			sDate = sDate.trim();
			if (sDate != null || sDate.length() != 0) {
				SimpleDateFormat formatDate = new SimpleDateFormat(formatDateStr);
				date = formatDate.parse(sDate);
			}
		} catch (Exception exception) {
		}
		return date;
	}

	/**
	 * 字符串转换日期，字符串格式为yyyy-MM-dd HH:mm:ss
	 */
	@SuppressWarnings("null")
	private static Date _strToDateTime(String sDateTime) {
		Date date = null;
		try {
			sDateTime = sDateTime.trim();
			if (sDateTime != null || sDateTime.length() != 0) {
				SimpleDateFormat formatDateTime = new SimpleDateFormat(formatDateTimeStr);
				date = formatDateTime.parse(sDateTime);
			}
		} catch (Exception exception) {
		}
		return date;
	}

	/**
	 * 指定日期的yyyy-MM-dd格式字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		String dateString = null;
		try {
			if (date != null) {
				SimpleDateFormat formatDate = new SimpleDateFormat(formatDateStr);
				dateString = formatDate.format(date);
			}
		} catch (Exception exception) {
			return null;
		}
		return dateString;
	}

	/**
	 * 指定日期、指定格式，使用SimpleDateFormat对日期进行格式化
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		String dateString = null;
		try {
			if (date != null) {
				dateString = new SimpleDateFormat(format).format(date);
			}
		} catch (Exception exception) {
			return null;
		}
		return dateString;
	}

	/**
	 * 日期转换字符串，格式为yyyy-MM-dd HH:mm:ss
	 */
	public static String dateTimeToString(Date date) {
		String dateString = null;
		try {
			if (date != null) {
				SimpleDateFormat formatDateTime = new SimpleDateFormat(formatDateTimeStr);
				dateString = formatDateTime.format(date);
			}
		} catch (Exception exception) {
		}
		return dateString;
	}

	/**
	 * 将格式为yyyy-MM-dd或yyyy-MM-dd HH:mm:ss的字符串java.sql.Timestamp对象
	 * 
	 * @param sTime
	 * @return
	 */
	public static java.sql.Timestamp stringToTimestamp(String sTime) {
		Date date = DateUtil.stringToDate(sTime);
		if (date != null) {
			return new java.sql.Timestamp(date.getTime());
		} else {
			return null;
		}
	}

	/**
	 * 判断字符串是否满足yyyy-MM-dd或yyyy-MM-dd HH:mm:ss格式
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isDate(String value) {
		boolean result = false;

		if (DateUtil.stringToDate(value) != null || DateUtil.stringToTimestamp(value) != null)
			result = true;

		return result;
	}

	/**
	 * yyyy-MM-dd或yyyy-MM-dd HH:mm:ss字符串转换为Date
	 * 
	 * @param value
	 * @return
	 */
	public static Date string2Time(String value) {
		Date result = null;
		result = DateUtil.stringToDate(value);
		if (result == null)
			result = DateUtil.stringToTimestamp(value);
		return result;
	}

	/**
	 * java.util.Date转换为java.sql.Timestamp
	 * 
	 * @param param
	 * @return
	 */
	public static java.sql.Timestamp parseSQLTimestamp(Date param) {
		if (param == null) {
			param = new Date();
		}
		try {
			return new java.sql.Timestamp(param.getTime());
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * java.util.Date增加second秒数
	 * 
	 * @param time
	 * @param second
	 * @return
	 */
	public static Date dateAddSecond(Date time, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * java.util.Date增加day天数
	 * 
	 * @param time
	 * @param day
	 * @return
	 */
	public static Date timeAdd(Date time, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * java.util.Date增加month月数
	 * 
	 * @param time
	 * @param month
	 * @return
	 */
	public static Date dateAddMonth(Date time, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}

	/**
	 * java.util.Date增加year年数
	 * 
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date dateAddYear(Date date, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}

	/**
	 * 判断两个日期是否同年月日
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
		return isSameDate;
	}

	/**
	 * 计算量java.util.Date之间相差的天数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getIntervalDates(Date start, Date end) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(start);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(end);
		long time1 = cal1.getTimeInMillis();
		long time2 = cal2.getTimeInMillis();
		return (int) (Math.abs(time2 - time1) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 计算量java.util.Date之间相差的月数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getIntervalMonths(Date d1, Date d2) {
		Calendar c = Calendar.getInstance();
		Long date = Math.abs(d1.getTime() - d2.getTime());
		c.setTimeInMillis(date);
		int year = (c.get(Calendar.YEAR) - 1970) > 0 ? (c.get(Calendar.YEAR) - 1970) : 0;
		int month = c.get(Calendar.MONTH) > 0 ? c.get(Calendar.MONTH) : 0;
		return month + year * 12;
	}

	/**
	 * java.util.Date转换java.util.Calendar
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * java.util.Date转换javax.xml.datatype.XMLGregorianCalendar
	 * 
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) {
		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (DatatypeConfigurationException e) {
			return null;
		}
	}

	/**
	 * 获取间隔月份，【不忽略天数，月份进1处理】
	 * 
	 * @param start
	 * @param end
	 * @return 正数表示end在start后，负数表示end在start前
	 */
	public static int getIntervalMonthByDates(Date start, Date end) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(start);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(end);
		int year = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
		int month = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
		int day = cal2.get(Calendar.DAY_OF_MONTH) - cal1.get(Calendar.DAY_OF_MONTH);
		if (year > 0) {
			if (day > 0) {
				return year * 12 + month + 1;
			} else {
				return year * 12 + month;
			}
		} else if (year == 0) {
			if (month > 0) {
				if (day > 0) {
					return month + 1;
				} else {
					return month;
				}
			} else if (month == 0) {
				if (day > 0) {
					return 1;
				} else if (day == 0) {
					return 0;
				} else {
					return -1;
				}
			} else {
				if (day > 0) {
					return month - 1;
				} else {
					return month;
				}
			}
		} else {
			if (day > 0) {
				return year * 12 + month - 1;
			} else {
				return year * 12 + month;
			}
		}
	}

	/**
	 * 当Date对象时间部分设置为 00:00:00.000
	 * 
	 * @param date
	 * @return
	 */
	public static Date setBeginTime(Date date) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Date对象时间部分设置为 23:59:59.000
	 * 
	 * @param date
	 * @return
	 */
	public static Date setEndTime(Date date) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 月份间隔 转为 年份间隔 (将月间隔转换为年间隔，如果月数不能整除6，余数小于等于6的部分忽略，大于6的部分向上进1年)
	 * 
	 * @param month
	 * @return
	 */
	public static int getYearIntervalByMonthInterval(int month) {
		int year = month / 12;
		if (month % 12 >= 6) {
			year = year + 1;
		}
		return year;
	}

}
