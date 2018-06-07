package cn.freeexchange.fabric.e2eexample.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Fabric创建的peer信息，包含有cli、org、ca、couchdb等节点服务器关联启动服务信息集合
 * */
public class Peers {
	
	private String orgName; 
	
	private String orgMSPID;
	
	private String orgDomainName;
	
	private List<Peer> peers;
	
	
	public Peers() {
		peers = new ArrayList<>();
	}
	
	/** 新增节点  */
	public void addPeer(String peerName, String peerEventHubName,
			String peerLocation, String peerEventHubLocation, String caLocation) {
		peers.add(new Peer(peerName, peerEventHubName, peerLocation, peerEventHubLocation, caLocation));
	}
	
	//获取节点集合
	public List<Peer> get() {
		return peers;
	}

	public String getOrgMSPID() {
		return orgMSPID;
	}

	public void setOrgMSPID(String orgMSPID) {
		this.orgMSPID = orgMSPID;
	}

	public String getOrgDomainName() {
		return orgDomainName;
	}

	public void setOrgDomainName(String orgDomainName) {
		this.orgDomainName = orgDomainName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
