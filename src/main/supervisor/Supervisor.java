package main.supervisor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import main.reps.ServiceRep;

public class Supervisor {

	private static final ScheduledExecutorService supervisor = Executors.newScheduledThreadPool(1);

	private Supervisor() {}
	
	public static void sendHome(ServiceRep rep) {
		Runnable goHome = () -> {
			try {
				rep.getFuture().cancel(true);
				System.out.println("----------------------------------------" + rep.getName() + "sent home----------------------------------------");
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		Runnable sternlyEye = () -> {
			try {
				System.out.println("The supervisor takes note of " + rep.getName() + "'s arrival");

			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		supervisor.schedule(sternlyEye, 3, TimeUnit.SECONDS);
		supervisor.schedule(goHome, 10, TimeUnit.MINUTES);
	}
	
	// add eye angrilly as a method that increases the frequency of call pickup

}
