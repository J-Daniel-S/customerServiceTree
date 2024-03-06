package main.queue;

import main.queue.node.Node;

public abstract class BSTRecursive {
	
	private Node root;

	public Node searchNode(int key) {
		return searchNode(key, root);
	}
	
	public synchronized int findMin() {
		Node node = null;
		if (root != null)
			node = minNode(root.getKey(), root.getLeft());
		return node == null || root == null ? 0 : node.getKey();
	}

	public synchronized int findMax() {
		Node node = null;
		if (root != null)
			node = maxNode(root.getKey(), root.getRight());
		return node  == null || root == null ? 0 : node.getKey();
	}
	
	public synchronized Node getRoot() {
		return root;
	}
	
	public void setRoot(Node root) {
		this.root = root;
	}

	// traverse left until you can't anymore then return that key
	private Node minNode(int key, Node node) {
		if (node == null) {
			return new Node(key);
		} 
		
		return minNode(node.getKey(), node.getLeft());
		
	}

	// traverse right until you can't anymore then return that key
	private Node maxNode(int key, Node node) {
		if (node == null) {
			return new Node(key);
		}
		
		return maxNode(node.getKey(), node.getRight());
	}

	private Node searchNode(int key, Node node) {
		if (node == null) {
			return null;
		}

		if (key == node.getKey()) {
			return node;
		} else if (key < node.getKey()) {
			return searchNode(key, node.getLeft());
		} else {
			return searchNode(key, node.getRight());
		}
	}

	public synchronized void insert(int key) {
		root = insertNode(key, root);
	}

	protected Node insertNode(int key, Node node) {
		// No node at current position --> store new node at current position
		if (node == null) {
			node = new Node(key);
		}

		// Otherwise, traverse the tree to the getgetLeft()() or getRight() depending on
		// the key
		else if (key < node.getKey()) {
			node.setLeft(insertNode(key, node.getLeft()));
		} else if (key > node.getKey()) {
			node.setRight(insertNode(key, node.getRight()));
		} else {
			 throw new IllegalArgumentException("BST already contains a node with key " + key);
		}

		return node;
	}

	public synchronized void delete(int key) {
		root = deleteNode(key, root);
	}

	protected Node deleteNode(int key, Node node) {
		// No node at current position --> go up the recursion
		if (node == null) {
			return null;
		}

		// Traverse the tree to the getLeft() or getRight() depending on the key
		if (key < node.getKey()) {
			node.setLeft(deleteNode(key, node.getLeft()));
		} else if (key > node.getKey()) {
			node.setRight(deleteNode(key, node.getRight()));
		}

		// At this point, "node" is the node to be deleted

		// Node has no children --> just delete it
		else if (node.getLeft() == null && node.getRight() == null) {
			node = null;
		}

		// Node has only one child --> replace node by its single child
		else if (node.getLeft() == null) {
			node = node.getRight();
		} else if (node.getRight() == null) {
			node = node.getLeft();
		}

		// Node has two children
		else {
			deleteNodeWithTwoChildren(node);
		}

		return node;
	}

	private void deleteNodeWithTwoChildren(Node node) {
		// Find minimum node of getRight() subtree ("inorder successor" of current node)
		Node inOrderSuccessor = findMinimum(node.getRight());

		// Copy inorder successor's getKey() to current node
		node.setKey(inOrderSuccessor.getKey());

		// Delete inorder successor recursively
		node.setRight(deleteNode(inOrderSuccessor.getKey(), node.getRight()));
	}

	private Node findMinimum(Node node) {
		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		return node;
	}
	
	// inorder traversal of BST
	public void printinOrder() {
		inOrderRecursive(root);
	}

	private void inOrderRecursive(Node root) {
		if (root != null) {
			inOrderRecursive(root.getLeft());
			System.out.println("customer" + root.getKey() + (root == this.root ? "root" : " "));
			inOrderRecursive(root.getRight());
		}
		
	}

}
