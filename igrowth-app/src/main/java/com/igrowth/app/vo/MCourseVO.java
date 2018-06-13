package com.igrowth.app.vo;

import java.util.List;

import com.github.wxiaoqi.security.api.entity.MLesson;
import com.github.wxiaoqi.security.common.util.DateUtil;

/**
 * ClassName: MCourseVO <br/>
 * 我的课程封装
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
public class MCourseVO implements Comparable<MCourseVO> {

	private String date;

	private String week;

	private List<MLesson> list;

	/**
	 * date.
	 * 
	 * @return the date
	 * @since JDK 1.8
	 */
	public String getDate() {
		return date;
	}

	/**
	 * date.
	 * 
	 * @param date
	 *            the date to set
	 * @since JDK 1.8
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * week.
	 * 
	 * @return the week
	 * @since JDK 1.8
	 */
	public String getWeek() {
		return week;
	}

	/**
	 * week.
	 * 
	 * @param week
	 *            the week to set
	 * @since JDK 1.8
	 */
	public void setWeek(String week) {
		this.week = week;
	}

	/**
	 * list.
	 * 
	 * @return the list
	 * @since JDK 1.8
	 */
	public List<MLesson> getList() {
		return list;
	}

	/**
	 * list.
	 * 
	 * @param list
	 *            the list to set
	 * @since JDK 1.8
	 */
	public void setList(List<MLesson> list) {
		this.list = list;
	}

	@Override
	public int compareTo(MCourseVO m) {
		try {
			return DateUtil.formateStringToDate(date, DateUtil.DATE_FROMAT_PATTERN_ONE)
					.compareTo(DateUtil.formateStringToDate(m.getDate(), DateUtil.DATE_FROMAT_PATTERN_ONE));
		} catch (Exception e) {
			return 0;
		}
	}

}
