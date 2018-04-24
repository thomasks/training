/**
 * 
 */
package cn.freeexchange.yarn.statemachine;

/**
 * copy from org.apache.hadoop.yarn.state.StateMachine
 * 
 * 对于系统的状态的变化与处理进行分析与控制
 */
public interface StateMachine 
					<STATE extends Enum<STATE>,
						EVENTTYPE extends Enum<EVENTTYPE>, EVENT> {
	
	public STATE getCurrentState();
	
	public STATE doTransition(EVENTTYPE eventType, EVENT event)
	        throws InvalidStateTransitonException;

}
