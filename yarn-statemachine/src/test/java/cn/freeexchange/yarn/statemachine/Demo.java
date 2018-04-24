package cn.freeexchange.yarn.statemachine;

import java.util.EnumSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.freeexchange.yarn.statemachine.event.AsyncDispatcher;
import cn.freeexchange.yarn.statemachine.event.Dispatcher;
import cn.freeexchange.yarn.statemachine.event.EventHandler;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Demo implements EventHandler<DemoEvent> {
	private static Logger LOG = LoggerFactory.getLogger(Demo.class);
	
	private Long id;
	private DemoStateInternal status;
	private transient Lock readLock;
	private transient Lock writeLock;
	private transient StateMachine<DemoStateInternal, DemoEventType, DemoEvent> stateMachine;
	private Dispatcher dispatcher ;
	
	private DemoEvent nextEvent;
	
	public Demo(Long id, AsyncDispatcher dispatcher) {
		this(id,DemoStateInternal.Q0,dispatcher);
	}
	
	public Demo(Long id, DemoStateInternal status,AsyncDispatcher dispatcher) {
		this.id = id;
		this.status = status;
		this.dispatcher = dispatcher;
		this.restoreStateMachine();
	}
	

	protected static final StateMachineFactory<Demo, DemoStateInternal, DemoEventType, DemoEvent> stateMachineFactory = new StateMachineFactory<Demo, DemoStateInternal, DemoEventType, DemoEvent>(
			DemoStateInternal.Q0)
					.addTransition(DemoStateInternal.Q0, DemoStateInternal.Q1, DemoEventType.DEMO_A0,
							new DemoSingleTransition())
					.addTransition(DemoStateInternal.Q1, DemoStateInternal.Q2,
							EnumSet.of(DemoEventType.DEMO_A1, DemoEventType.DEMO_A2, DemoEventType.DEMO_A3),
							new DemoSingleTransition())
					.addTransition(DemoStateInternal.Q2,
							EnumSet.of(DemoStateInternal.Q3, DemoStateInternal.Q4, DemoStateInternal.Q5),
							DemoEventType.DEMO_A4, new DemoMultipleArcTransition())
					.installTopology();

	public static class DemoSingleTransition implements SingleArcTransition<Demo, DemoEvent> {
		
		private static Logger logger = LoggerFactory.getLogger(DemoSingleTransition.class);
		
		@Override
		public void transition(Demo operand, DemoEvent event) {
			logger.info("@@DemoSingleTransition was invoked,operand is : {},event is : {}", operand,event);
			DemoEventType det =null;
			
			if(event.getType() == DemoEventType.DEMO_A1 ||
					event.getType() == DemoEventType.DEMO_A2 ||
					event.getType() == DemoEventType.DEMO_A3) {
				det = DemoEventType.DEMO_A4;
			}
			
			if(event.getType().equals(DemoEventType.DEMO_A0)) {
				int nextInt = RandomUtils.nextInt(1, 4);
				switch (nextInt) {
					case 1:
						det=DemoEventType.DEMO_A1;
						break;
					case 2:
						det=DemoEventType.DEMO_A2;
						break;
					case 3:
						det=DemoEventType.DEMO_A3;
						break;
				}
				
				//operand.dispatcher.getEventHandler().handle(de);
			}
			
			DemoEvent de = new DemoEvent(event.getId(), det);
			logger.info("@@DemoSingleTransition was invoked,dispacther new event.,event is {}:",de);
			operand.nextEvent = de;
		}

	}
	
	public static class DemoMultipleArcTransition implements MultipleArcTransition<Demo, DemoEvent, DemoStateInternal> {
		private static Logger logger = LoggerFactory.getLogger(DemoMultipleArcTransition.class);
		
		@Override
		public DemoStateInternal transition(Demo operand, DemoEvent event) {
			logger.info("@@DemoMultipleArcTransition was invoked,operand is : {},event is : {}", operand,event);
			int stateIndex = RandomUtils.nextInt(3, 6);
			DemoStateInternal retState = null;
			switch (stateIndex) {
				case 3:
					retState =DemoStateInternal.Q3;
					break;
				case 4:
					retState =DemoStateInternal.Q4;
					break;
				case 5:
					retState =DemoStateInternal.Q5;
					break;
			}
			logger.info("@@DemoMultipleArcTransition retState is : {}", retState);
			return retState;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handle(DemoEvent event) {
		if(! this.id.equals(event.getId())) {
			return ;
		}
		try {
			Long demoId = event.getId();
			LOG.info("Processing " + demoId + " of type " + event.getType());
			//this.restoreStateMachine();
			this.writeLock.lock();

			DemoStateInternal oldState = stateMachine.getCurrentState();
			DemoStateInternal newState = null;
			try {
				newState = stateMachine.doTransition(event.getType(), event);
			} catch (InvalidStateTransitonException e) {
				LOG.warn("Can't handle this event at current state: Current: [" + oldState + "], eventType: ["
						+ event.getType() + "]", e);
			}
			if (newState != null && oldState != newState) {
				LOG.info("Demo " + demoId + " transitioned from " + oldState + " to " + newState);
				this.status = newState;
			}
			DummyDispatcher.syncDemo(this);
		} catch (Throwable t) {
			LOG.error("@@Demo handle event meet unexpected error.", t);
		} finally {
			this.writeLock.unlock();
		}
		if(nextEvent != null) {
			this.dispatcher.getEventHandler().handle(nextEvent);
			nextEvent = null;
		}
	}

	public enum DemoStateInternal {
		Q0, Q1, Q2, Q3, Q4, Q5
	}

	private void restoreStateMachine() {
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		this.readLock = readWriteLock.readLock();
		this.writeLock = readWriteLock.writeLock();
		if(this.status == null) {
			this.status = DemoStateInternal.Q0;
		}
		this.stateMachine = stateMachineFactory.make(this, this.getStatus());
	}
	
	public static Demo buildInitalDemo(Long id, AsyncDispatcher dispatcher) {
		return new Demo(id,dispatcher);
	}

	@Override
	public String toString() {
		return "Demo [id=" + id + ", status=" + status + "]";
	}
	
	

}
