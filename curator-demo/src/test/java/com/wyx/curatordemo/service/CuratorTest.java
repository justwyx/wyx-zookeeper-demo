package com.wyx.curatordemo.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Description : CuratorTest
 * @author : Just wyx
 * @Date : 2020/10/30
 */
@SpringBootTest
public class CuratorTest {
	@Resource
	private CuratorService curatorService;

	@Test
	public void create() throws Exception {
		curatorService.create();
	}

	@Test
	public void getValue() throws Exception {
		curatorService.getValue();
	}

	@Test
	public void getChildren() throws Exception {
		curatorService.getChildren();
	}

	@Test
	public void watcherValue() throws Exception {
		curatorService.watcherValue();
	}

	@Test
	public void watcherNode() throws Exception {
		curatorService.watcherNode();
	}
}
