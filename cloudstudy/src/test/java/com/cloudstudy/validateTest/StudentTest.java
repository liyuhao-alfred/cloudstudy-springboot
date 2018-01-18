package com.cloudstudy.validateTest;

import com.cloudstudy.util.ValidateUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class StudentTest implements Serializable {
	/**
	 * @Field @serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		Student xiaoming = getBean();
		List<String> validate = ValidateUtil.validate(xiaoming);
		for (String string : validate) {
			System.out.println(string.toString());
		}

	}

	private static Student getBean() {
		Student bean = new Student();
		bean.setName(null);
		bean.setPassword("  ");
		bean.setAddress("北京");
		bean.setBirthday(new Date());
		bean.setFriendName(null);
		bean.setWeight(new BigDecimal(30));
		bean.setEmail("xiaogangfan163.com");
		return bean;
	}

}