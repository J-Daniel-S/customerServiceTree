package main.reps;

import java.util.concurrent.ScheduledFuture;

import main.phonelines.CustomerServiceLine;
import main.queue.AVLTree;

public class ServiceRep {
	
	private ScheduledFuture<?> future;
	private String name;
	
	public ServiceRep(AVLTree tree, String name) {
		this.future = CustomerServiceLine.startShift(tree, name);
		this.name = name;
	}

	public ScheduledFuture<?> getFuture() {
		return future;
	}

	public void setFuture(ScheduledFuture<?> future) {
		this.future = future;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ServiceRep [future=" + future + ", name=" + name + "]";
	}
	
}
