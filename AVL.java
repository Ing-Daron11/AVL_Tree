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
	
	public E search(E s) {
		if(root == null) {
			return null;
		}else {
			return search(root, s).getElement();
		}
	}

	@Override
	public Node<E> search(Node<E> r, E s) {
		if(r.getElement()==null) {
			return r;
		}else if(comparator.compare(s, r.getElement())==0) {
			return r;
		}else if(comparator.compare(s, r.getElement())>0) {
			return search(r.getRight(), s);
		}else {
			return search(r.getLeft(), s);
		}
	}

	@Override
	public Node<E> delete(E d) {
		Node<E> remove = search(root, d);
		removeNode(remove);
		
		balance(remove.getParent());
		return remove;
	}
	
	private void removeNode(Node<E> d) {
		if(d!=null) {	
			if(isleaf(d)) {
				if(d==root) {
					root=null;
				}else if(d==d.getParent().getLeft()) {
					d.getParent().setLeft(null);
				}else {
					d.getParent().setRight(null);
				}
			}else if(d.getLeft()==null || d.getRight()==null) {
				Node<E> aux;
				if(d.getLeft()!=null) {
					aux=d.getLeft();
				}else {
					aux=d.getRight();
				}
				aux.setParent(d.getParent());
				if(d==root) {
					root=aux;
				}else if(d==d.getParent().getLeft()) {
					d.getParent().setLeft(aux);
				}else {
					d.getParent().setRight(aux);
				}
			}else {
				Node<E> succesor = min(d.getRight());
				d.setElement(succesor.getElement());
				removeNode(succesor);
			}
		}
	}
	
	private boolean isleaf(Node<E> d) {
		if(d.getRight()==null && d.getLeft()==null) {
			return true;
		}else {
			return false;
		}
	}

	private void balance(Node<E> node) {
		do {
			if(node.fb()==-2) {
				if(node.getLeft()!=null) {
					if(node.getLeft().fb()==-1 || node.getLeft().fb()==0) {
						rotateRight(node);
					}else {
						rotateLeft(node.getLeft());
						rotateRight(node);
					}

				}
			}else if(node.fb() == 2) {
				if(node.getRight() != null) {
					if(node.getRight().fb() == 1 || node.getRight().fb() == 0) {
						rotateLeft(node);
					}else {
						rotateRight(node.getRight());
						rotateLeft(node);
					}
				}
			} else {
				
			}
			node = node.getParent();
		}while(node != null);
	}
	
	public int getheight(Node<E> n){
    	if(n==null) {
    		return 0;
    	}else {
    		return 1+max(getheight(n.getRight()), getheight(n.getLeft()));
    	}
    }

    private int max(int l, int r) {
		if(l>=r) {
			return l;
		}else {
			return r;
		}
		
	}

    public int fb(Node<E> node){
    	return getheight(node.getRight())-getheight(node.getLeft());
    }
	
	private void rotateLeft(Node<E> node) {
		if(!node.equals(root)) {
			Node<E> p = node.getParent();

			node.setParent(node.getRight());
			node.getRight().setParent(p);
			node.setRight(node.getRight().getLeft());
			if(node.getRight() != null) {
				node.getRight().setParent(node);
			}
			node.getParent().setParent(p);
			node.getParent().setLeft(node);

			if(p.getLeft() == node) {
				p.setLeft(node.getParent());
			} else {
				p.setRight(node.getParent());
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
	
	private Node<E> min(Node<E> r){
		if(r.getLeft()==null) {
			return r;
		}else {
			return min(r.getLeft());
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
		String msg = "";
		Queue<Node<E>> queue = new LinkedList<>();
		queue.add(this.root);

		while (!queue.isEmpty()) {
			int levelSize = queue.size();
			for (int i = 0; i < levelSize; i++) {
				Node<E> node = queue.poll();
				msg = msg + (node.getElement() + " ");
				if (node.left != null) {
					queue.add(node.left);
				}
				if (node.right != null) {
					queue.add(node.right);
				}
			}

		}
		msg = msg + "\n";
		return msg;
	}

}