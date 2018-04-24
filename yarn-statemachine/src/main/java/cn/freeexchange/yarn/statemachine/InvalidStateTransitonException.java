/**
 * 
 */
package cn.freeexchange.yarn.statemachine;

/**
 *
 */
public class InvalidStateTransitonException extends RuntimeException {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 8315974864900889654L;
	
	private Enum<?> currentState;
	private Enum<?> event;
	
	public InvalidStateTransitonException(Enum<?> currentState, Enum<?> event) {
		super("Invalid event: " + event + " at " + currentState);
	    this.currentState = currentState;
	    this.event = event;
	}

	public Enum<?> getCurrentState() {
	  return currentState;
	}
	  
	public Enum<?> getEvent() {
	  return event;
	}
}
