package com.wyx.curatordemo.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description : CuratorService
 * @author : Just wyx
 * @Date : 2020/10/29
 */
@Service
public class CuratorService {
	private final CuratorFramework client;

	// 集群地址
	private static final String CLUSTER = "zk1:2181,zk2:2182,zk3:2183";
	// 节点名称
	private static final String ROOT_PATH = "curator";
	private static final String NODE_PATH_HOST = "/host";
	private static final String NODE_PATH_PORT = "/port";

	public CuratorService() {
		// 创建重试策略，重试间隔(baseSleepTimeMs) 1秒，最多重试（maxRetries）3次
		ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 3);

		this.client = CuratorFrameworkFactory.builder()
				.connectString(CLUSTER)
				.sessionTimeoutMs(15000) // 会话超时时间
				.connectionTimeoutMs(13000) // 连接超时
				.retryPolicy(retry) // 指定重试策略
				.namespace(ROOT_PATH)
				.build();// 指定客户端隔离命名空间
		this.client.start();
	}

	public void create() throws Exception {
		// 创建该节点的子节点(默认为持久节点，且当前没有指定数据内容)
//		client.create().forPath(NODE_PATH_HOST);

		// 包含数据内容的持久子节点
		client.create().forPath(NODE_PATH_HOST, "hello".getBytes());

		// 创建临时节点
//		client.create().withMode(CreateMode.EPHEMERAL).forPath(NODE_PATH_HOST, "hello".getBytes());

		// creatingParentContainersIfNeeded 如果父容器不存在，则先创建父节点，在创建子节点
//		String nodeName = client.create()
//				.creatingParentContainersIfNeeded()
//				.withMode(CreateMode.PERSISTENT)
//				.forPath(NODE_PATH_HOST, "hello".getBytes());
//		System.out.println("新创建的节点为："+ nodeName);
	}

	public void getValue() throws Exception {
		// 获取数据内容
		byte[] bytes = client.getData().forPath(NODE_PATH_HOST);
		System.out.println("当前节点数据内容:" + new String(bytes));
	}

	public void getChildren() throws Exception {
		// 获取子节点列表
		List<String> childrenList = client.getChildren().forPath("/");
		System.out.println("子节点列表:" + childrenList);
	}

	public void watcherValue() throws Exception {
		// 监听内容的变化
		byte[] data = client.getData()
				.usingWatcher((CuratorWatcher) event ->
						System.out.println(event.getPath() + "的数据内容发生了变化")
				)
				.forPath(NODE_PATH_HOST);
		System.out.println("当前节点的数据内容" + new String(data));
		// 更新数据内容
		client.setData().forPath(NODE_PATH_HOST, "world".getBytes());
	}

	public void watcherNode() throws Exception {
		// 监听子节点列表
		List<String> childrenList1 = client.getChildren()
				.usingWatcher((CuratorWatcher) event -> {
					System.out.println(event.getPath() + "的子节点列表发生了变化");
				})
				.forPath("/");
		System.out.println("子节点列表：" + childrenList1);

		// 创建一个子节点
		client.create().forPath(NODE_PATH_PORT);
		// 在删除一个子节点
		if (client.checkExists().forPath(NODE_PATH_PORT) != null) {
			client.delete().forPath(NODE_PATH_PORT);
			// 当明节点下还有子节点，也可以删除掉
			client.delete().deletingChildrenIfNeeded().forPath("/");
		}
	}

}
