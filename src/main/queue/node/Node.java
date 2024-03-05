package main.queue.node;

public class Node {
	
	private int key, height;
	private Node right, left;
	
	public Node(int data) {
		key = data;
		left = right = null;
	}
	
	public Node(Node node) {
		key = node.getKey();
		left = node.getLeft();
		right = node.getRight();
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

	@Override
	public String toString() {
		return "Node [key=" + key + ", left=" + left + ", right=" + right + "]";
	}

}
