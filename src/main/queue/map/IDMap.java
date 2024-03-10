package main.queue.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.queue.node.Node;

public class IDMap {

	private Map<Long, Node> customers;

	public IDMap() {
		this.customers = new ConcurrentHashMap<>();
	}

	public Node find(long id) {
		// consider using an optional here
		return customers.get(id);
	}

	public void register(Node node) {
		customers.putIfAbsent(node.getId(), node);
	}
	
	// TODO UPDATE TO ADD ID AND MAP AFTER DELETE... give each rep their own seed

}
