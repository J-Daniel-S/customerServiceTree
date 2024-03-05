package main.phonelines;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import main.queue.AVLTree;

public class CustomerServiceLine {

	public static void answer(AVLTree tree) {
		synchronized (tree) {
			
			int root = tree.getRoot().getKey();
			
			if (tree.getRoot() == null) {
				System.out.println("No customers in queue");
				return;
			}
			
			tree.delete(tree.getRoot().getKey());
			
			System.out.println("customer" + root + " answered - " + "customer" + tree.getRoot().getKey() + " next in line");
		}
		
		
	}
	
	public static void startShift(AVLTree tree) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		
		Runnable task = () -> CustomerServiceLine.answer(tree);
		
		executor.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);
		
		System.out.println("----------------------------------------Rep shift started----------------------------------------");
	}

}
