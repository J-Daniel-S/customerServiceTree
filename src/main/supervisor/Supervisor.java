package main.supervisor;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import main.phonelines.CustomerServiceLine;
import main.queue.AVLTree;
import main.reps.ServiceRep;

public class Supervisor {

	private static int TIMER = 0;
	private static final ScheduledExecutorService supervisor = Executors.newScheduledThreadPool(1);
	private static ScheduledFuture<?> lines = null;
	private static ScheduledFuture<?> supervise = null;
	private static final Queue<String> repNames = new ArrayDeque<>();
	private static final Queue<ServiceRep> reps = new ArrayDeque<>();

	private Supervisor() {}
	
	public static void openSchedule(AVLTree tree) {
		System.out.println("********************CUSTOMER SERVICE LINES OPEN********************");
		List.of("Jeff", "Morgan", "Jordan", "Reuben", "Eustace", "Eugene", "Jimbo", "Kevin", "Mitchel", "Pablo", "Regis", "Stephano").stream().forEach(repNames::add);
		reps.add(new ServiceRep(tree, repNames.remove()));
	}
	
	public static void openLines(AVLTree tree) {
		Runnable newCaller = () -> {
//			if (ThreadLocalRandom.current().nextInt(20) < 6) {
//				CustomerServiceLine.newCaller(tree);
//				TIMER = 0;
//			}
		};
		
		System.out.println("--CUSTOMER SERVICE LINES OPEN--");

		lines = supervisor.scheduleAtFixedRate(newCaller, 1, 1, TimeUnit.SECONDS);
	}
	
	public static void closeLines(AVLTree tree) {
		lines.cancel(true);
		tree = null;
		System.out.println("tree set to null");
		reps.forEach(r -> r.getFuture().cancel(true));
		reps.clear();
		supervise.cancel(true);
		
		
		System.out.println("--\nLast service rep sent home\n--");
		System.out.println("--CUSTOMER SERVICE LINES CLOSED--");
	}
	
	public static void manageStaff(AVLTree tree) {
		Runnable anotherRep = () -> {
			assessLineLoad(tree);
			// TODO perhaps prevent this from running a final time after the thread is cancelled.  It might not matter
			// TODO ask claude how to get the program to terminate after 
			System.out.println("Assessing line load - " + tree.totalNodes());
		};
		
		supervise = supervisor.scheduleAtFixedRate(anotherRep, 5, 10, TimeUnit.SECONDS);
	}
	
	private static void assessLineLoad(AVLTree tree) {
		int load = tree.totalNodes();
		
		if (load > 6) {
			addRep(tree);
			System.out.println(reps.size() + " REPS WORKING");
		} else if (load <= 3) {
			if (reps.size() > 1) {
				sendRepHome();
			}
			else {
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
		System.out.println("----------------------------------------" + rep.getName() + " sent home----------------------------------------");
		rep = null;
	}
	
	private static void prepareClosing(AVLTree tree) {
		TIMER++;
		if (TIMER > 1) {
			System.out.println("close lines reached");
			closeLines(tree);
		}
	}
	

}
