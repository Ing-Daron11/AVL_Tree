package util;

import java.util.*;

public class AVL<E> implements ABB<E>{

    private Node<E> root;
    private Comparator<E> comparator;
    
    public AVL(Comparator<E> c){
    	comparator = c;
    }

	@Override
	public void add(E element) {
		Node<E> node = new Node<>(element);
		if(root == null) {
			root = node;
		}else {
			add(node, this.root);
		}
	}
	
	//mayores e iguales a la derecha
	public void add(Node<E> node, Node<E> current) {
		if(comparator.compare(node.getElement(), current.getElement()) >= 0) {
			if(current.getRight() == null) {
				current.setRight(node);
				node.setParent(current);
			}else {
				add(node, current.getRight());
			}
		}else {
			if(current.getLeft() == null) {
				current.setLeft(node);
				node.setParent(current);
			}else {
				add(node,current.getLeft());
			}
		}
		balance(node);
	}
	
	public E search(E nodeSearched) {
		if(root == null) {
			return null;
		}else {
			return search(root, nodeSearched).getElement();
		}
	}

	@Override
	public Node<E> search(Node<E> current, E nodeSearched) {
		if(current.getElement()==null) {
			return current;
		}else if(comparator.compare(nodeSearched, current.getElement())==0) {
			return current;
		}else if(comparator.compare(nodeSearched, current.getElement())>0) {
			return search(current.getRight(), nodeSearched);
		}else {
			return search(current.getLeft(), nodeSearched);
		}
	}

	@Override
	public Node<E> delete(E nodeDelete) {
		Node<E> removeNode = search(root, nodeDelete);
		removeNode(removeNode);
		balance(removeNode);
		return removeNode;
	}
	
	private void removeNode(Node<E> removeNode) {
		if(removeNode!=null) {
			if(isleaf(removeNode)) {
				if(removeNode==root) {
					root=null;
				}else if(removeNode==removeNode.getParent().getLeft()) {
					removeNode.getParent().setLeft(null);
				}else {
					removeNode.getParent().setRight(null);
				}
			}else if(removeNode.getLeft()==null || removeNode.getRight()==null) {
				Node<E> aux;
				if(removeNode.getLeft()!=null) {
					aux=removeNode.getLeft();
				}else {
					aux=removeNode.getRight();
				}
				aux.setParent(removeNode.getParent());
				if(removeNode==root) {
					root=aux;
				}else if(removeNode==removeNode.getParent().getLeft()) {
					removeNode.getParent().setLeft(aux);
				}else {
					removeNode.getParent().setRight(aux);
				}
			}else {
				Node<E> successor = min(removeNode.getRight());
				removeNode.setElement(successor.getElement());
				removeNode(successor);
			}
		}
	}
	
	private boolean isleaf(Node<E> removeNode) {
		if(removeNode.getRight()==null && removeNode.getLeft()==null) {
			return true;
		}else {
			return false;
		}
	}

	private void balance(Node<E> node) {
		do {
			if(node.fb()<=-2) {
				if(node.getLeft()!=null) {
					if(node.getLeft().fb()<=-1 || node.getLeft().fb()==0) {
						rotateRight(node);
					}else {
						rotateLeft(node.getLeft());
						rotateRight(node);
					}

				}
			}else if(node.fb() >= 2) {
				if (node.getRight() != null) {
					if (node.getRight().fb() >= 1 || node.getRight().fb() == 0) {
						rotateLeft(node);
					} else {
						rotateRight(node.getRight());
						rotateLeft(node);
					}
				}
			}
			node = node.getParent();
		}while(node != null);
	}
	
	public int getheight(Node<E> node){
    	if(node==null) {
    		return 0;
    	}else {
    		return 1+max(getheight(node.getRight()), getheight(node.getLeft()));
    	}
    }

    private int max(int left, int right) {
		if(left>=right) {
			return left;
		}else {
			return right;
		}
		
	}

    public int fb(Node<E> node){
    	return getheight(node.getRight())-getheight(node.getLeft());
    }
	
	private void rotateLeft(Node<E> node) {
		if(!node.equals(root)) {
			Node<E> parent = node.getParent();

			node.setParent(node.getRight());
			node.getRight().setParent(parent);
			node.setRight(node.getRight().getLeft());
			if(node.getRight() != null) {
				node.getRight().setParent(node);
			}
			node.getParent().setParent(parent);
			node.getParent().setLeft(node);

			if(parent.getLeft() == node) {
				parent.setLeft(node.getParent());
			} else {
				parent.setRight(node.getParent());
			}			
		} else {
			Node<E> left = root;
			Node<E> aux = node.getRight();
			
			root.setRight(aux.getLeft());
			if(aux.getLeft() != null) {
				aux.getLeft().setParent(root);
			}
			root = aux;
			root.setParent(left.getParent());
			root.setLeft(left);
			left.setParent(aux);
		}
	}
	
	private void rotateRight(Node<E> node) {
		if(!node.equals(root)) {
			Node<E> p = node.getParent();
			
			node.setParent(node.getLeft());
			node.getLeft().setParent(p);
			node.setLeft(node.getLeft().getRight());
			if(node.getLeft() != null) {
				node.getLeft().setParent(node);
			}
			node.getParent().setParent(p);
			node.getParent().setRight(node);
			
			if(p.getLeft() == node) {
				p.setLeft(node.getParent());
			} else {
				p.setRight(node.getParent());
			}
		} else {
			Node<E> right = root;
			Node<E> aux = node.getLeft();
			
			root.setLeft(aux.getRight());
			if(aux.getRight() != null) {
				aux.getRight().setParent(root);
			}
			root = aux;
			root.setParent(right.getParent());
			root.setRight(right);
			right.setParent(aux);
		}
	}
	
	private Node<E> min(Node<E> removeNode){
		if(removeNode.getLeft()==null) {
			return removeNode;
		}else {
			return min(removeNode.getLeft());
		}
	}
	public String inOrderString(){
		return inOrderString(root);
	}

	public String inOrderString(Node current){
		if(current == null){
			return "";
		}
		return inOrderString(current.getLeft()) + " " + current.getElement() + " " + inOrderString(current.getRight());
	}

	public String printByLevel() {
		if (root == null) {
			return "";
		}
		String msj = "";
		Queue<Node<E>> queue = new LinkedList<>();
		queue.add(this.root);

		while (!queue.isEmpty()) {
			int levelSize = queue.size();
			for (int i = 0; i < levelSize; i++) {
				Node<E> node = queue.poll();
				msj = msj + (node.getElement() + " ");
				if (node.left != null) {
					queue.add(node.left);
				}
				if (node.right != null) {
					queue.add(node.right);
				}
			}

		}
		msj = msj + "\n";
		return msj;
	}

}