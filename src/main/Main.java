package main;

import java.util.Random;
import java.util.stream.Stream;

import main.phonelines.CustomerServiceLine;
import main.queue.AVLTree;

public class Main {
	
	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		
		Stream.generate(() -> new Random().nextInt(30)).limit(20).distinct().forEach(tree::insert);
		
		System.out.println("customer" + tree.getRoot().getKey() + " - first in line");
		
		CustomerServiceLine.startShift(tree);
		
		
		

	}


}
