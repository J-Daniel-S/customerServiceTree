package main.phonelines;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import main.queue.AVLTree;

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
				System.out.println("customer" + callerId + " answered by " + name + " - " + "customer" + callerId
						+ " next in line - position- " + root);
			else
				System.out.println(name + " has answered customer" + callerId + " - position- " + root);
		} else {
			System.out.println("No customers in queue - " + name + " standing by for callers");
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

		return executor.scheduleAtFixedRate(callIn, 1, 3, TimeUnit.SECONDS);

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
		Stream.generate(() -> new Random().nextInt(30)).limit(20).distinct().forEach(tree::insert);

	}

}
