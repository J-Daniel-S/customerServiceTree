package main.phonelines;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import main.queue.AVLTree;
import main.queue.map.IDMap;
import main.queue.node.Node;
import main.supervisor.Supervisor;

public class CustomerServiceLine {

	public static final IDMap customers = new IDMap();
	private static boolean left = true;
	private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	private static final Lock answerLock = new ReentrantLock();
	private static AtomicLong idGenerator = new AtomicLong(System.nanoTime());

	private CustomerServiceLine() {}
	
	
	private synchronized static long nextId() {
		return idGenerator.incrementAndGet();
	}
	
	private static void acquireLock() {
		answerLock.lock();
	}
	
	private static void releaseLock() {
		answerLock.unlock();
	}

	public static void answer(AVLTree tree, String name) {
		acquireLock();
		try {
			if (tree.getRoot() != null) {
				
				Node node = tree.getRoot();
				tree.delete(node.getKey());
				node.setId(nextId());
				customers.register(node);
				
				if (tree.getRoot() != null) {
					System.out.println("-\n" + name.toUpperCase() + " answers customer in position " + node.getKey() + " - ID - " + node.getId() + "\n-");
					Supervisor.zeroTimer();
				}
				else
					System.out.println("-\n" + name + " has answered customer" + node.getId() + "\n-");
			} else {
				System.out.println("-\nNo customers in queue - " + name + " standing by for callers\n-");
			}
			
		} finally {
			releaseLock();
		}

	}

	public static ScheduledFuture<?> startShift(AVLTree tree, String name) {
		Runnable task = () -> {
			try {
				CustomerServiceLine.answer(tree, name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		ScheduledFuture<?> future = executor.scheduleAtFixedRate(task, 1, ThreadLocalRandom.current().nextInt(8, 17), TimeUnit.SECONDS);

		System.out.println("------------------------" + name
				+ "'s shift started------------------------");
		return future;
	}

	public static int callerPosition(AVLTree tree) {
		int key;
		if (left) {
			key = tree.findMin() - 1;
			left = false;
		} else {
			key = tree.findMax() + 1;
			left = true;
		}
		return key;

	}
	
	public static void newCaller(AVLTree tree) {
		Runnable newCaller = () -> {
			int key = CustomerServiceLine.callerPosition(tree);
			tree.insert(key);
			System.out.println("New customer calling in");
		};
		
		executor.schedule(newCaller, ThreadLocalRandom.current().nextLong(5, 18), TimeUnit.SECONDS);
	}
	
	public static void addCaller(AVLTree tree) {
		int key = CustomerServiceLine.callerPosition(tree);
		tree.insert(key);
		System.out.println("New chaotic customer calling in!");
	}

}
