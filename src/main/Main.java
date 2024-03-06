package main;

import java.util.concurrent.ScheduledFuture;

import main.phonelines.CustomerServiceLine;
import main.queue.AVLTree;
import main.reps.ServiceRep;
import main.supervisor.Supervisor;

public class Main {
	
	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		
		System.out.println("CUSTOMER SERVICE LINES OPEN");
		CustomerServiceLine.moreCallers(tree);

		bringReps(tree);

	}

	private static void bringReps(AVLTree tree) {
		ServiceRep jeff = new ServiceRep(tree, "jeff");
		try {
			Thread.sleep(5000);
			ServiceRep jordan = new ServiceRep(tree, "jordan");
			Supervisor.sendHome(jordan);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(10000);
			ServiceRep morgan = new ServiceRep(tree, "morgan");
			Supervisor.sendHome(morgan);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Supervisor.sendHome(jeff);
	}


}
