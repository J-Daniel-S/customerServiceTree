package main;

import main.chaosagent.ChaosAgent;
import main.queue.AVLTree;
import main.supervisor.Supervisor;

public class Main {
	
	public static void main(String[] args) {
		AVLTree tree = new AVLTree();

		Supervisor.openSchedule(tree);
		Supervisor.openLines(tree);
		Supervisor.manageStaff(tree);
		ChaosAgent.beginChaos(tree);
		
	}

}
