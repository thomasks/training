package cn.freeexchange.yarn.statemachine;

import java.util.concurrent.ConcurrentHashMap;

import cn.freeexchange.yarn.statemachine.event.AsyncDispatcher;
import cn.freeexchange.yarn.statemachine.event.EventHandler;
import cn.freeexchange.yarn.statemachine.service.CompositeService;
import cn.freeexchange.yarn.statemachine.service.Configuration;
import cn.freeexchange.yarn.statemachine.service.Service;

public class DummyDispatcher extends CompositeService implements EventHandler<DemoEvent>  {
	
	protected static ConcurrentHashMap<Long, Demo> activeDemos = new ConcurrentHashMap<>();
	
	
	public static void syncDemo(Demo demo) {
		if(!activeDemos.containsKey(demo.getId())) {
			return ;
		}
		activeDemos.put(demo.getId(), demo);
	}
	
	
	protected final AsyncDispatcher dispatcher;
	
	public DummyDispatcher() {
		super("=====DummyDispatcher============");
		dispatcher = new AsyncDispatcher();
		dispatcher.register(DemoEventType.class, this);
		//add service.
		addService((Service) dispatcher);
	}
	
	
	
	@Override
	public void serviceInit(Configuration conf) throws Exception {
		super.serviceInit(conf);
	}
	
	
	


	@Override
	public void handle(DemoEvent event) {
		if(!activeDemos.containsKey(event.getId())) {
			activeDemos.put(event.getId(), Demo.buildInitalDemo(event.getId(),dispatcher));
		} 
		activeDemos.get(event.getId()).handle(event);
	}
	
	
	
	
}
