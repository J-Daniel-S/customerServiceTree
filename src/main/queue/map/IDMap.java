package main.queue.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import main.queue.node.Node;

public class IDMap extends ConcurrentHashMap<Long, Node> {

	private Map<Long, Node> customers;
	private final AtomicLong idGenerator;

	public IDMap() {
		this.customers = new ConcurrentHashMap<>();
		this.idGenerator = new AtomicLong();
	}

	public Node find(long id) {
		// consider using an optional here
		return customers.get(id);
	}

	public synchronized void register(Node node) {
		long id = idGenerator.incrementAndGet() + ThreadLocalRandom.current().nextLong(Long.MIN_VALUE, Long.MAX_VALUE/2);
		node.setId(id);
		Node put = customers.putIfAbsent(id, node);
		if (put == null) {
			System.out.println("TOTAL CUSTOMERS - " + customers.size());
		} else {
			register(node);
		}
	}

}
