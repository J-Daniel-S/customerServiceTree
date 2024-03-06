package main.queue.node;

import java.util.concurrent.ThreadLocalRandom;

public class Node {
	
	private int key, height;
	private Node right, left;
	private long id;
	
	public Node(int data) {
		key = data;
		left = right = null;
		id = ThreadLocalRandom.current().nextLong(Long.MIN_VALUE, Long.MAX_VALUE);
	}
	
	public Node(Node node) {
		key = node.getKey();
		left = node.getLeft();
		right = node.getRight();
		id = ThreadLocalRandom.current().nextLong();
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node node) {
		this.left = node;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}
	
	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Node [key=" + key + ", left=" + left + ", right=" + right + "]";
	}

}
