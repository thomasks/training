package cn.freeexchange.fabric.e2eexample;

import java.util.Map;

public class App {
	
	public static void main(String[] args) throws Exception {
		ChaincodeManager manager = FabricManager.obtain().getManager();
		Map<String, String> querya = manager.query("query", new String[] {"a"});
		System.out.println(querya.toString());
		Map<String, String> queryb = manager.query("query", new String[] {"b"});
		System.out.println(queryb.toString());
	}
	
}
