package com.wyx.curatordemo.model;

/**
 * @author : Just wyx
 * @Description : TODO 2020/10/30
 * @Date : 2020/10/30
 */
public class Person {
	private String name;
	private Integer age;

	public String getName() {
		return name;
	}

	public Person setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getAge() {
		return age;
	}

	public Person setAge(Integer age) {
		this.age = age;
		return this;
	}


	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
