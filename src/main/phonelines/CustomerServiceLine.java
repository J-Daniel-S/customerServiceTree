package main.phonelines;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import main.queue.AVLTree;
import main.reps.ServiceRep;
import main.supervisor.Supervisor;

public class CustomerServiceLine {

	private static boolean left = true;
	private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	private CustomerServiceLine() {
	}

	public static synchronized void answer(AVLTree tree, String name) {

		if (tree.getRoot() != null) {
			int root = tree.getRoot().getKey();
			long callerId = tree.getRoot().getId();

			tree.delete(root);

			if (tree.getRoot() != null)
				System.out.println("-\n" + name.toUpperCase() + " answers customer in position " + root + " - ID - " + callerId + "\n-");
			else
				System.out.println("-\n" + name + " has answered customer" + callerId + "-\n-");
		} else {
			System.out.println("-\nNo customers in queue - " + name + " standing by for callers\n-");
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

		ScheduledFuture<?> future = executor.scheduleAtFixedRate(task, 1, 8, TimeUnit.SECONDS);

		System.out.println("----------------------------------------" + name
				+ "'s shift started----------------------------------------");
		return future;
	}

	public static ScheduledFuture<?> moreCallers(AVLTree tree) {
		Runnable callIn = () -> {
			int key = numberCaller(tree);
			tree.insert(key);
			System.out.println("New customer calling in - assigning id");
		};

		return executor.scheduleAtFixedRate(callIn, 1, 2, TimeUnit.SECONDS);

	}

	public static int numberCaller(AVLTree tree) {
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

	public static void openLines(AVLTree tree) {
		Stream.generate(() -> ThreadLocalRandom.current().nextInt(30)).limit(20).distinct().forEach(tree::insert);

	}
	
	public static void repLogsOn(AVLTree tree, int delay, String name) {
		try {
			Thread.sleep(delay);
			ServiceRep rep = new ServiceRep(tree, name);
			Supervisor.sendHome(rep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
