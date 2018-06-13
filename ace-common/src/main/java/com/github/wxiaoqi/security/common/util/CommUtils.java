/**  
 * Project Name:ace-common  
 * File Name:CommUtils.java  
 * Package Name:com.github.wxiaoqi.security.common.util  
 * Date:2017年11月21日下午7:14:09  
 * Copyright (c) 2017, suveen@163.com All Rights Reserved.  
 *  
*/

package com.github.wxiaoqi.security.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:CommUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年11月21日 下午7:14:09 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 * @see
 */
public class CommUtils {

	public static boolean checkPhone(String phone) {
		String regex = "^((13[0-9])|(14[0-9])|(15([0-9]))|(17[0-9])|(18[0-9]))\\d{8}$";
		if (phone.length() != 11) {
			return false;
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			boolean isMatch = m.matches();
			if (isMatch) {
				return true;
			} else {
				return false;
			}
		}
	}
}
