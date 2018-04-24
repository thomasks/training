package cn.freeexchange.yarn.statemachine;

import cn.freeexchange.yarn.statemachine.event.AbstractEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemoEvent extends AbstractEvent<DemoEventType> {

	public DemoEvent(Long id,DemoEventType type) {
		super(type);
		this.id = id;
	}

	private Long id;

	@Override
	public String toString() {
		return "DemoEvent [id=" + id + ", type=" + getType() + "]";
	}
	
}
