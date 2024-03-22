package main.chaosagent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import main.phonelines.CustomerServiceLine;
import main.queue.AVLTree;

public class ChaosAgent {
	
	private ChaosAgent(AVLTree tree) {}

	public static void beginChaos(AVLTree tree) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("...enter adds more callers...");
		
			try {
				String input;
				while ((input = reader.readLine()) != null) {
					if (input.isEmpty()) {
						CustomerServiceLine.addCaller(tree);
				} else {
					break;
				}
				}
			} catch (IOException e) {
				beginChaos(tree);
			}
		}

}


