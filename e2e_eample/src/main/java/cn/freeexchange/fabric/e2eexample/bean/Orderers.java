package cn.freeexchange.fabric.e2eexample.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Fabric创建的orderer信息，涵盖单机和集群两种方案
 * Copy from https://www.cnblogs.com/aberic/archive/2018/01/05/8206551.html
 * */
public class Orderers {
	// orderer 排序服务器所在根域名
	private String ordererDomainName; // freeexchange.cn
	
	//orderer 排序服务器集合
	private List<Orderer> orderers;

	public Orderers() {
		orderers = new ArrayList<>();
	}

	public String getOrdererDomainName() {
		return ordererDomainName;
	}

	public void setOrdererDomainName(String ordererDomainName) {
		this.ordererDomainName = ordererDomainName;
	}
	
	
	/**
	 *  新增排序服务器 
	 *  */
	public void addOrderer(String name, String location) {
		orderers.add(new Orderer(name, location));
	}
	
	/** 
	 * 获取排序服务器集合
	 *  */
	public List<Orderer> get() {
		return orderers;
	}
	
}
