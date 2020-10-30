package com.wyx.curatordemo;

import com.wyx.curatordemo.model.Person;

/**
 * @Description : Fluent 风格测试
 * @author : Just wyx
 * @Date : 2020/10/30
 */
public class FluentTest {
	public static void main(String[] args) {
		Person person = new Person().setAge(11).setName("张三");
		System.out.println(person);
	}
}
