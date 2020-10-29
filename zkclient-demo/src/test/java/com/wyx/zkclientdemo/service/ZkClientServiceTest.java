package com.wyx.zkclientdemo.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author : Just wyx
 * @Description : TODO 2020/10/29
 * @Date : 2020/10/29
 */
@SpringBootTest
public class ZkClientServiceTest {
	@Resource
	private ZkClientService zkClientService;

	@Test
	public void create() {
		zkClientService.create();
	}

	@Test
	public void getPathValue() {
		System.out.println("节点的内容:" + zkClientService.getPathValue());
	}

	@Test
	public void subscribeDataChanges() {
		zkClientService.subscribeDataChanges();
	}
}
