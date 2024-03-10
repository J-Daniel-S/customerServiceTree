package main.queue;

import static java.lang.Math.max;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import main.queue.map.IDMap;
import main.queue.node.Node;

public class AVLTree extends BSTRecursive {

	// holds customers by customer id
	private IDMap customers;
	private Lock lock;
	

	public AVLTree() {
		super();
		this.customers = new IDMap();
		this.lock = new ReentrantLock();
	}
	
	@Override
	public Node getRoot() {
		acquireLock();
		try {
			return super.getRoot();
		} finally {
			releaseLock();
		}
	}

	private int height(Node node) {
		return node != null ? node.getHeight() : -1;
	}

	private void updateHeight(Node node) {
		int leftChildHeight = height(node.getLeft());
		int rightChildHeight = height(node.getRight());
		node.setHeight(max(leftChildHeight, rightChildHeight) + 1);
	}

	private int balanceFactor(Node node) {
		return height(node.getRight()) - height(node.getLeft());
	}
	
	private void acquireLock() {
		lock.lock();
	}
	
	private void releaseLock() {
		lock.unlock();
	}
	
	@Override
	public void insert(int key) {
		acquireLock();
		try {
			setRoot(insertNode(key, super.getRoot()));
			customers.register(getRoot());
		} finally {
//			super.printinOrder();
			releaseLock();
		}
	}

	@Override
	protected Node insertNode(int key, Node node) {

		node = super.insertNode(key, node);

		updateHeight(node);
//		System.out.println("New node id - " + node.getId() + " - position - " + node.getKey());
		return rebalance(node);
	}
	
	@Override
	public void delete(int key) {
		acquireLock();
		try {
			super.delete(key);
		} finally {
			releaseLock();
		}
	}

	@Override
	protected Node deleteNode(int key, Node node) {
		node = super.deleteNode(key, node);

		// Node is null if the tree doesn't contain the key
		if (node == null) {
			return null;
		}

		updateHeight(node);

		return rebalance(node);
	}

	private Node rebalance(Node node) {
		int balanceFactor = balanceFactor(node);

		// Left-heavy?
		if (balanceFactor < -1) {
			if (balanceFactor(node.getLeft()) <= 0) { // Case 1
				// rotate right
				node = rotateRight(node);
			} else { // Case 2
				// rotate left-right
				node.setLeft(rotateLeft(node.getLeft()));
				node = rotateRight(node);
			}
		}

		// Right heavy?
		if (balanceFactor > 1) {
			if (balanceFactor(node.getRight()) >= 0) { // Case 3
				// rotate left
				node = rotateLeft(node);
			} else { // Case 4
				// rotate right-left
				node.setRight(rotateRight(node.getRight()));
				node = rotateLeft(node);
			}
		}
		
		return node;
	}

	private Node rotateRight(Node node) {
		Node leftChild = node.getLeft();

		node.setLeft(leftChild.getRight());
		leftChild.setRight(node);

		updateHeight(node);
		updateHeight(leftChild);

		return leftChild;
	}

	private Node rotateLeft(Node node) {
		Node rightChild = node.getRight();

		node.setRight(rightChild.getLeft());
		rightChild.setLeft(node);

		updateHeight(node);
		updateHeight(rightChild);

		return rightChild;
	}

	public Node find(long id) {
		return customers.find(id);
	}

	public IDMap getCustomers() {
		return customers;
	}
	
}
