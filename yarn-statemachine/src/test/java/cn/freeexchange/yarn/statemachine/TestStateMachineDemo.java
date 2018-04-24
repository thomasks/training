package cn.freeexchange.yarn.statemachine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.freeexchange.yarn.statemachine.service.Configuration;

public class TestStateMachineDemo {
	
	private DummyDispatcher dd ;
	
	@Before
	public void startDispatcher() throws Exception {
		dd = new DummyDispatcher();
		Configuration conf = new Configuration();
		dd.serviceInit(conf);
		dd.init(conf);
		dd.start();
	}
	
	@After
	public void stopDispatcher() {
		//dd.stop();
	}
	
	@Test
	public void testDemo() {
		Long id = System.currentTimeMillis();
		DemoEvent de = new DemoEvent(id, DemoEventType.DEMO_A0);
		dd.handle(de);
	}

}
