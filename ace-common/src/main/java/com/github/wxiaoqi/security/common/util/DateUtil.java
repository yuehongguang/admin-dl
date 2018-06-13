package com.github.wxiaoqi.security.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class DateUtil {

	public static final String DATE_FROMAT_PATTERN_ONE = "yyyy-MM-dd";

	public static final String DATE_FROMAT_PATTERN_TWO = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_FROMAT_PATTERN_THREE = "yyyy.MM.dd";

	public static final String DATE_FROMAT_PATTERN_FOUR = "HH:mm:ss SSS";
	
	public static final String DATE_FROMAT_PATTERN_FIVE = "yyyy-MM-dd HH:mm";

	public static final String DATE_FROMAT_PATTERN_SIX = "HH:mm:ss";
	
	public static final String[] DATE_NAME_ONE =  {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
	
	public static final String [] DATE_NAME_TWO =  {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};

	/**
	 * 
	 * 两个日期相差的天数
	 * 
	 **/
	public static int daysBetween(Date smdate, Date bdate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 
	 * 两个日期是否是同一天
	 * 
	 **/
	public static boolean isSameDay(Date date1, Date date2) {
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
	 * 
	 * 根据pattern将Date类型格式化为 yyyy-MM-dd HH:mm:ss yyyy-MM-dd HH:mm
	 * 
	 */
	public static String formateDateToString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 获得两个时间相差秒数
	 * 
	 */
	public static long getDateSpace(Date startDate, Date endDate) {
		long diff = endDate.getTime() - startDate.getTime();// 这样得到的差值是毫秒级别
		return diff / 1000;
	}

	public static boolean isSameMonth(Date startDate, Date endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean flag = true;
		String[] startDateString = sdf.format(startDate).split("-");
		String[] endDateString = sdf.format(endDate).split("-");
		if (startDateString[0].equals(endDateString[0])) {
			if (!startDateString[1].equals(endDateString[1])) {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	public static int getDaysOfCtMonth() {
		return getDaysByMonth(new Date());
	}

	public static int getDaysByMonth(Date date) {
		int days = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] startDateString = sdf.format(date).split("-");
		int month = Integer.valueOf(startDateString[1]);
		switch (month) {
		case 1:
			days = 31;
			break;
		case 2:
			if (isleapyear()) {
				days = 28;
			} else {
				days = 29;
			}
			break;
		case 3:
			days = 31;
			break;
		case 4:
			days = 30;
			break;
		case 5:
			days = 31;
			break;
		case 6:
			days = 30;
			break;
		case 7:
			days = 31;
			break;
		case 8:
			days = 31;
			break;
		case 9:
			days = 30;
			break;
		case 10:
			days = 31;
			break;
		case 11:
			days = 30;
			break;
		case 12:
			days = 31;
			break;
		default:
			break;
		}
		return days;
	}

	/**
	 * 判断当前日期是否是平年
	 * 
	 */
	public static boolean isleapyear() {
		boolean isleap = true;
		int year = getYear();
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			isleap = false;
		}
		return isleap;
	}

	public static int getYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	public static Date randomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date start = format.parse(beginDate);// 构造开始日期
			Date end = format.parse(endDate);// 构造结束日期
			// getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
			if (start.getTime() >= end.getTime()) {
				return null;
			}
			long date = random(start.getTime(), end.getTime());

			return new Date(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static long random(long begin, long end) {
		long rtn = begin + (long) (Math.random() * (end - begin));
		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
		if (rtn == begin || rtn == end) {
			return random(begin, end);
		}
		return rtn;
	}

	public static Date formateStringToDate(String dateString) {
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			return sdf.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date formateStringToDateForWechat(String dateString) {
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(dateString.substring(0, 4)).append("/").append(dateString.substring(4, 6)).append("/")
					.append(dateString.substring(6, 8)).append(" ").append(dateString.substring(8, 10)).append(":")
					.append(dateString.substring(10, 12)).append(":").append(dateString.substring(12, 14));
			return sdf.parse(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获得当前时间点之前指定的时间
	public static Date getDateBefore(int seconds) {
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss");
		c.setTime(date);// 设置参数时间
		c.add(Calendar.SECOND, -seconds);// 把日期往后增加SECOND 秒.整数往后推,负数往前移动
		date = c.getTime();
		return date;
	}

	// public static void main(String[] args) {
	// /*try {
	// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// Date d1=sdf.parse("2012-09-09 23:59:59");
	// Date d2=sdf.parse("2012-09-09 00:00:00");
	//
	// System.out.println(isSameDay(d1, d2));
	// System.out.println(daysBetween(d1,d2));
	// } catch (ParseException e) {
	// e.printStackTrace();
	// } */
	// //Date randomDate = randomDate("2015-05-21", "2016-01-16");
	// //System.out.println(randomDate.toString());
	// System.out.println(getDateBefore(1800));
	// }

	// 获得当前系统时间
	public static Date getCurrentDateTime(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("日期格式化异常.........");
		}
	}

	/**
	 * 
	 * 根据pattern将String类型日期转成对应的Date类型
	 * 
	 * @throws ParseException
	 */
	public static Date formateStringToDate(String date, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(date);
	}

	/**
	 * 获取两个日期中间日期
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static List<String> getIntervalDate(Calendar startDay, Calendar endDay) {
		DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
		List<String> dateList = new ArrayList<String>();
		if (startDay.compareTo(endDay) >= 0) {
			return null;
		}
		dateList.add(FORMATTER.format(startDay.getTime()));
		Calendar currentPrintDay = startDay;
		while (true) {
			currentPrintDay.add(Calendar.DATE, 1);
			if (currentPrintDay.compareTo(endDay) == 0) {
				break;
			}
			dateList.add(FORMATTER.format(currentPrintDay.getTime()));
		}
		dateList.add(FORMATTER.format(endDay.getTime()));
		HashSet<String> hs = new HashSet<String>(dateList);
		Iterator<String> it = hs.iterator();
		dateList.clear();
		while (it.hasNext()) {
			dateList.add(it.next());
		}
		return dateList;
	}

	public static String formatDateByPattern(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String formatTimeStr = null;
		if (date != null) {
			formatTimeStr = sdf.format(date);
		}
		return formatTimeStr;
	}

	public static String getCron(Date date) {
		String dateFormat = "ss mm HH dd MM ? yyyy";
		return formatDateByPattern(date, dateFormat);
	}

	public static Date getCurDateWeek(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (Calendar.SUNDAY == day) {
			c.add(Calendar.WEEK_OF_MONTH, 1);
		}
		c.add(Calendar.WEEK_OF_MONTH, c.get(Calendar.WEEK_OF_MONTH));
		c.set(Calendar.DAY_OF_WEEK, day);
		return c.getTime();
	}

	public static Map<String,Date> getMondyToSundayByNow(Date date, String[] dateStrs){
        Map<String,Date> dateMap = new LinkedHashMap<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        DateTime dateTime = new DateTime(cal.getTime());
        int todayOfWeek = dateTime.getDayOfWeek();
        DateTime monday = null;
        switch(todayOfWeek){
            case DateTimeConstants.MONDAY:
                monday = dateTime.dayOfWeek().setCopy(DateTimeConstants.MONDAY);
                break;
            case DateTimeConstants.TUESDAY:
                monday = dateTime.minusDays(1);
                break;
            case DateTimeConstants.WEDNESDAY:
                monday = dateTime.minusDays(2);
                break;
            case DateTimeConstants.THURSDAY:
                monday = dateTime.minusDays(3);
                break;
            case DateTimeConstants.FRIDAY:
                monday = dateTime.minusDays(4);
                break;
            case DateTimeConstants.SATURDAY:
                monday = dateTime.minusDays(5);
                break;
            case DateTimeConstants.SUNDAY:
                monday = dateTime.minusDays(6);
                break;
        }
        dateMap.put(dateStrs[0],monday.toDate());
        dateMap.put(dateStrs[1],monday.plusDays(1).toDate());
        dateMap.put(dateStrs[2],monday.plusDays(2).toDate());
        dateMap.put(dateStrs[3],monday.plusDays(3).toDate());
        dateMap.put(dateStrs[4],monday.plusDays(4).toDate());
        dateMap.put(dateStrs[5],monday.plusDays(5).toDate());
        cal.setTime(monday.plusDays(6).toDate());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        dateMap.put(dateStrs[6],cal.getTime());
        return dateMap;
    }
	
    /**
     * 日期转星期
     * 
     * @param datetime
     * @return
     */
    public static String dateToWeek(Date datetime) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        cal.setTime(datetime);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
	/**
	 * 得到几天后的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	} 

}