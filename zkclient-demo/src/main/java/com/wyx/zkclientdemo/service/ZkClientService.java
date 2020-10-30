package com.wyx.zkclientdemo.service;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Service;


/**
 * @Description : ZkClientService
 * @author : Just wyx
 * @Date : 2020/10/29
 */
@Service
public class ZkClientService {
	private ZkClient zkClient;

	// 集群地址
	private static final String CLUSTER = "zk1:2181,zk2:2182,zk3:2183";
	// 节点名称
	private static final String PATH = "/zkclient";

	public ZkClientService() {
		// 创建 ZkClient
		this.zkClient = new ZkClient(CLUSTER);
		// 指定序列化器
		this.zkClient.setZkSerializer(new SerializableSerializer());
	}

	/**
	 * 创建节点
	 *  PERSISTENT:持久型
	 *  PERSISTENT_SEQUENTIAL:持久顺序型
	 *  EPHEMERAL:临时型
	 *  EPHEMERAL_SEQUENTIAL:临时顺序型
	 */
	public void create() {
		String nodeName = zkClient.create(PATH, "hello world", CreateMode.PERSISTENT);
		System.out.println("新创建的节点名称为:" + nodeName);
	}

	/**
	 * 获取节点内容
	 */
	public String getPathValue() {
		return zkClient.readData(PATH);
	}

	/**
	 * 注册watcher
	 */
	public void subscribeDataChanges() {
		// 注册监听数据内容的变更
		zkClient.subscribeDataChanges(PATH, new IZkDataListener() {

			/**
			 * 数据内容变更时被触发
			 * @param dataPath 监听的路径
			 * @param data 变更后的内容
			 * @throws Exception
			 */
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("节点" + dataPath + "的数据更新成为：" + data);
			}

			/**
			 * 当数据被删除时被触发
			 * @param dataPath 监听的路径
			 * @throws Exception
			 */
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("节点" + dataPath + "被删除");
			}
		});

		// 调用更新
		this.update();
		// 调用删除
		this.delete();
	}

	/**
	 * 更新内容
	 */
	public void update() {
		// 判断是否存在
		if (zkClient.exists(PATH)) {
			zkClient.writeData(PATH, "new Hello world");
			System.out.println("更新后的内容:" + zkClient.readData(PATH));
		} else {
			System.out.println(PATH + "节点不存在");
		}

	}

	/**
	 * 删除节点
	 */
	public void delete() {
		boolean delete = zkClient.delete(PATH);
		System.out.println("删除节点完成状态：" + delete);
	}
}
