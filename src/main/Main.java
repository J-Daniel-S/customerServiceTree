package main;

import main.phonelines.CustomerServiceLine;
import main.queue.AVLTree;

public class Main {
	
	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		
		System.out.println("CUSTOMER SERVICE LINES OPEN");
		CustomerServiceLine.moreCallers(tree);
		CustomerServiceLine.executeDailySchedule(tree);

	}

}
