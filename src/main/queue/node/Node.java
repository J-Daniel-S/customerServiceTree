package main.queue.node;
import java.util.concurrent.atomic.AtomicLong;

public class Node {
	
	private static AtomicLong idGenerator = new AtomicLong(System.nanoTime());
	private int key, height;
	private Node right, left;
	private long id;
	
	public Node(int data) {
		key = data;
		left = right = null;
		id = 0;
	}
	
	public Node(Node node) {
		key = node.getKey();
		left = node.getLeft();
		right = node.getRight();
		id = 0;
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

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Node [key=" + key + ", left=" + left + ", right=" + right + "]";
	}

}
