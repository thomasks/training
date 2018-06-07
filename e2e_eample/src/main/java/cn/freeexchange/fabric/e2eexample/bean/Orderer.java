package cn.freeexchange.fabric.e2eexample.bean;

/**
 * 排序服务器对象
 * */
public class Orderer {
	
	//orderer 排序服务器的域名
	private String ordererName;
	
	//orderer 排序服务器的访问地址
	private String ordererLocation;

	public Orderer(String ordererName, String ordererLocation) {
		this.ordererName = ordererName;
		this.ordererLocation = ordererLocation;
	}

	public String getOrdererName() {
		return ordererName;
	}

	public void setOrdererName(String ordererName) {
		this.ordererName = ordererName;
	}

	public String getOrdererLocation() {
		return ordererLocation;
	}

	public void setOrdererLocation(String ordererLocation) {
		this.ordererLocation = ordererLocation;
	}
	
	
}
