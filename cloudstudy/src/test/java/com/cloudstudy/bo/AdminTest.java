package com.cloudstudy.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cloudstudy.BaseTest;
import com.cloudstudy.bo.Admin;
import com.cloudstudy.util.Util;

public class AdminTest {

	private Admin test1;
	private Admin test2;

	@Before
	public void init() {
		String str = BaseTest.str;

		test1.setAccount(str);
		test1.setCreateTime(new Date());
		test1.setEmail(str);
		test1.setLastModifyTime(new Date());
		test1.setLevel(1);
		test1.setName(str);
		test1.setNo(str);
		test1.setPassword(str);
		test1.setPhone(str);
		test1.setSex(0);
		test1.setStatus(0);

		str = BaseTest.str = Util.generateSerialNo();
		test2.setAccount(str);
		test2.setCreateTime(new Date());
		test2.setEmail(str);
		test2.setLastModifyTime(new Date());
		test2.setLevel(1);
		test2.setName(str);
		test2.setNo(str);
		test2.setPassword(str);
		test2.setPhone(str);
		test2.setSex(0);
		test2.setStatus(0);
	}

	@Test
	public void findAdmin() {

		/** 数值匹配 **/
		// 测试变量是否大于指定值
		Assert.assertThat(test1.getLevel(), Matchers.greaterThan(50));
		// 测试变量是否小于指定值
		Assert.assertThat(test1.getLevel(), Matchers.lessThan(100));
		// 测试变量是否大于等于指定值
		Assert.assertThat(test1.getLevel(), Matchers.greaterThanOrEqualTo(50));
		// 测试变量是否小于等于指定值
		Assert.assertThat(test1.getLevel(), Matchers.lessThanOrEqualTo(100));

		// 测试所有条件必须成立
		Assert.assertThat(test1.getLevel(), Matchers.allOf(Matchers.greaterThan(50), Matchers.lessThan(100)));
		// 测试只要有一个条件成立
		Assert.assertThat(test1.getLevel(),
				Matchers.anyOf(Matchers.greaterThanOrEqualTo(50), Matchers.lessThanOrEqualTo(100)));
		// 测试无论什么条件成立(还没明白这个到底是什么意思)
		Assert.assertThat(test1.getLevel(), Matchers.anything());
		// 测试变量值等于指定值
		Assert.assertThat(test1.getLevel(), Matchers.is(100));
		// 测试变量不等于指定值
		Assert.assertThat(test1.getLevel(), Matchers.not(50));

		/** 字符串匹配 **/
		String url = "http://www.taobao.com";
		// 测试变量是否包含指定字符
		Assert.assertThat(url, Matchers.containsString("taobao"));
		// 测试变量是否已指定字符串开头
		Assert.assertThat(url, Matchers.startsWith("http://"));
		// 测试变量是否以指定字符串结尾
		Assert.assertThat(url, Matchers.endsWith(".com"));
		// 测试变量是否等于指定字符串
		Assert.assertThat(url, Matchers.equalTo("http://www.taobao.com"));
		// 测试变量再忽略大小写的情况下是否等于指定字符串
		Assert.assertThat(url, Matchers.equalToIgnoringCase("http://www.taobao.com"));
		// 测试变量再忽略头尾任意空格的情况下是否等于指定字符串
		Assert.assertThat(url, Matchers.equalToIgnoringWhiteSpace("http://www.taobao.com"));

		/** 集合匹配 **/

		List<Admin> user = new ArrayList<Admin>();
		user.add(test1);
		user.add(test2);

		// 测试集合中是否还有指定元素
		Assert.assertThat(user, Matchers.hasItem(test1));
		Assert.assertThat(user, Matchers.hasItem(test2));

		/** Map匹配 **/
		Map<String, Admin> userMap = new HashMap<String, Admin>();
		userMap.put(test1.getNo(), test1);
		userMap.put(test2.getNo(), test2);

		// 测试map中是否还有指定键值对
		Assert.assertThat(userMap, Matchers.hasEntry(test1.getNo(), test1));
		// 测试map中是否还有指定键
		Assert.assertThat(userMap, Matchers.hasKey(test2.getNo()));
		// 测试map中是否还有指定值
		Assert.assertThat(userMap, Matchers.hasValue(test2));
	}

}