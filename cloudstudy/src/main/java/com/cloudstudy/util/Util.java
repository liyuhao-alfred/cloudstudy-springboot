package com.cloudstudy.util;

import java.util.Date;
import java.util.Random;

public class Util {

	/**
	 * 生成订单id
	 * 
	 * @return
	 */
	public static String generateSerialNo() {
		String ExpressOrderRandom = generateRandom(4);
		String ExpressOrderDate = DateUtil.dateToString(new Date(), DateUtil.formatYMDHMSSStr);
		return ExpressOrderDate + ExpressOrderRandom;
	}

	/**
	 * 产生指定长度的随机数
	 * 
	 * @param length
	 * @return
	 */
	public static String generateRandom(int length) {
		if (length <= 0)
			throw new RuntimeException("随机数生成：无效长度(" + length + ")");
		int bound = 0;
		if (length >= 10)
			bound = Integer.MAX_VALUE;
		else {
			for (int i = 0; i < length; i++)
				bound = bound * 10 + 9;
		}
		Random random = new Random();
		int randomInt = random.nextInt(bound);
		StringBuffer sb = new StringBuffer();
		sb.append(randomInt);
		for (int i = 0; i < length - sb.length(); i++) {
			sb.insert(0, '0');
		}
		return sb.toString();
	}
}
