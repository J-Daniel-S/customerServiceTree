package main.supervisor;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import main.phonelines.CustomerServiceLine;
import main.queue.AVLTree;
import main.reps.ServiceRep;

public class Supervisor {

	private static boolean stop = false;
	private static int TIMER = 0;
	private static final ScheduledExecutorService supervisor = Executors.newScheduledThreadPool(1);
	private static ScheduledFuture<?> lines = null;
	private static ScheduledFuture<?> supervise = null;
	private static final Queue<String> repNames = new ArrayDeque<>();
	private static final Queue<ServiceRep> reps = new ArrayDeque<>();

	private Supervisor() {
	}

	public static void openSchedule(AVLTree tree) {
		System.out.println("********************CUSTOMER SERVICE LINES OPEN********************\n-");
		List.of("Jeff", "Morgan", "Jordan", "Reuben", "Eustace", "Eugene", "Jimbo", "Kevin", "Mitchel", "Pablo",
				"Regis", "Stephano").stream().forEach(repNames::add);
		reps.add(new ServiceRep(tree, repNames.remove()));
	}

	public static void openLines(AVLTree tree) {

		Runnable newCaller = () -> {

			if (ThreadLocalRandom.current().nextInt(1, 100) == 1) {
				stop = !stop;
			}

			if (!stop && ThreadLocalRandom.current().nextInt(20) < 6) {
				CustomerServiceLine.newCaller(tree);
				zeroTimer();
			}

		};
		lines = supervisor.scheduleAtFixedRate(newCaller, 1, 1, TimeUnit.SECONDS);
	}

	public static void closeLines(AVLTree tree) {
		lines.cancel(true);
		tree = null;
		reps.forEach(r -> r.getFuture().cancel(true));
		reps.clear();
		supervise.cancel(true);

		System.out.println("--\nLast service rep sent home\n--");
		System.out.println("********************CUSTOMER SERVICE LINES CLOSED********************");
		System.exit(0);
	}

	public static void manageStaff(AVLTree tree) {
		Runnable anotherRep = () -> {
			assessLineLoad(tree);
		};

		supervise = supervisor.scheduleAtFixedRate(anotherRep, 5, 10, TimeUnit.SECONDS);
	}

	private static void assessLineLoad(AVLTree tree) {
		int load = tree.totalNodes();

		if (load > 5) {
			if (repNames.isEmpty()) {
				System.out.println("-\n-The lines are loaded-\n---I've run out of employees to call in!!--");
			} else {
				System.out.println("-\n-Assessing lines - " + tree.totalNodes()
						+ " callers-\n-Assigning new rep to answer calls-");
				addRep(tree);
			}
		} else if (load <= 3) {
			if (reps.size() > 1) {
				System.out.println("-Lines are lightening up-");
				sendRepHome();
			} else {
				prepareClosing(tree);
			}
		}

	}

	private static void addRep(AVLTree tree) {
		String name = repNames.remove();
		reps.add(new ServiceRep(tree, name));
	}

	private static void sendRepHome() {
		ServiceRep rep = reps.remove();
		rep.getFuture().cancel(true);
		repNames.add(rep.getName());
		System.out.println("------------------------" + rep.getName() + " sent home------------------------");
		rep = null;
	}

	private static void prepareClosing(AVLTree tree) {
		TIMER++;
		if (TIMER > 10) {
			closeLines(tree);
		}
	}

	public static void zeroTimer() {
		TIMER = 0;
	}

}
