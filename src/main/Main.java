package main;

import main.phonelines.CustomerServiceLine;
import main.queue.AVLTree;

public class Main {
	
	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		
		System.out.println("CUSTOMER SERVICE LINES OPEN");
		CustomerServiceLine.moreCallers(tree);
		CustomerServiceLine.moreCallers(tree);

		bringReps(tree);

	}

	private static void bringReps(AVLTree tree) {
		CustomerServiceLine.repLogsOn(tree, 2000, "jeff");
		CustomerServiceLine.repLogsOn(tree, 7500, "jordan");
		CustomerServiceLine.repLogsOn(tree, 9500, "morgan");
		CustomerServiceLine.repLogsOn(tree, 20000, "kevin");
		CustomerServiceLine.repLogsOn(tree, 22000, "regis");
		CustomerServiceLine.repLogsOn(tree, 28000, "stephen");
		CustomerServiceLine.repLogsOn(tree, 43000, "reuben");
		CustomerServiceLine.repLogsOn(tree, 48000, "reginald");
		CustomerServiceLine.repLogsOn(tree, 50000, "dingus");
	}


}
