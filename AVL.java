package util;

import java.util.*;

public class AVL<E> implements ABB<E>{

    private Node<E> root;
    private Comparator<E> comparator;
    
    public AVL(Comparator<E> c){
    	comparator = c;
    }

	@Override
	public void add(E e) {
		Node<E> n = new Node<>(e);
		if(root == null) {
			root = n;
		}else {
			add(n, root);
		}
	}
	
	//mayores e iguales a la derecha*
	public void add(Node<E> n, Node<E> r) {
		if(comparator.compare(n.getElement(), r.getElement()) >= 0) {
			if(r.getRight() == null) {
				r.setRight(n);
				n.setParent(r);
			}else {
				add(n, r.getRight());
			}
		}else {
			if(r.getLeft() == null) {
				r.setLeft(n);
				n.setParent(r);
			}else {
				add(n,r.getLeft());
			}
		}
		balance(n);
	}
	
	public List<E> searchList(){
		List<E> l = new ArrayList<>();
		return searchList(l, root);
	}
	
	private List<E> searchList(List<E> l, Node<E> r){
		if(r!=null) {
			searchList(l, r.getLeft());
			l.add(r.getElement());
			searchList(l, r.getRight());
		}
		return l;
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

	private void balance(Node<E> n) {
		do {
			if(n.fb()==-2) {
				if(n.getLeft()!=null) {
					if(n.getLeft().fb()==-1 || n.getLeft().fb()==0) {
						rotateRight(n);
					}else {
						rotateLeft(n.getLeft());
						rotateRight(n);
					}

				}
			}else if(n.fb() == 2) {
				if(n.getRight() != null) {
					if(n.getRight().fb() == 1 || n.getRight().fb() == 0) {
						rotateLeft(n);
					}else {
						rotateRight(n.getRight());
						rotateLeft(n);
					}
				}
			} else {
				
			}
			n = n.getParent();
		}while(n != null);
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

    public int fb(Node<E> n){
    	return getheight(n.getRight())-getheight(n.getLeft());
    }
	
	private void rotateLeft(Node<E> n) {
		if(!n.equals(root)) {	
			Node<E> p = n.getParent();

			n.setParent(n.getRight());
			n.getRight().setParent(p);
			n.setRight(n.getRight().getLeft());
			if(n.getRight() != null) {
				n.getRight().setParent(n);
			}
			n.getParent().setParent(p);
			n.getParent().setLeft(n);

			if(p.getLeft() == n) {
				p.setLeft(n.getParent());
			} else {
				p.setRight(n.getParent());
			}			
		} else {
			Node<E> left = root;
			Node<E> aux = n.getRight();
			
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
	
	private void rotateRight(Node<E> n) {
		if(!n.equals(root)) {
			Node<E> p = n.getParent();
			
			n.setParent(n.getLeft());
			n.getLeft().setParent(p);
			n.setLeft(n.getLeft().getRight());
			if(n.getLeft() != null) {
				n.getLeft().setParent(n);
			}
			n.getParent().setParent(p);
			n.getParent().setRight(n);
			
			if(p.getLeft() == n) {
				p.setLeft(n.getParent());
			} else {
				p.setRight(n.getParent());
			}
		} else {
			Node<E> right = root;
			Node<E> aux = n.getLeft();
			
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
	
	@SuppressWarnings("unused") //este metodo no se usa
	private Node<E> max(Node<E> r){
		if(r.getRight()==null) {
			return r;	
		}else {
			return max(r.getRight());
		}
	}
	
	private Node<E> min(Node<E> r){
		if(r.getLeft()==null) {
			return r;
		}else {
			return min(r.getLeft());
		}
	}
	
	public Node<E> getRoot(){
        return root;
    }
	
	public String printWithRelations(){
        return printWithRelations(root);
    }

    private String printWithRelations(Node<E> c){
        String s = "";
        if(c != null){
            s += printWithRelations(c.getLeft());
            if(c.getParent() != null){
                s += "Parent: " + c.getParent().getElement() + "\n";
            } else{
                s += "Parent: null"  + "\n";
            }
            
            s += "Current: " + c.getElement() + "\n";
            
            if(c.getLeft() != null){
                s += "Left: " + c.getLeft().getElement() + "\n";
            } else{
                s += "Left: null"  + "\n";
            }
            
            if(c.getRight() != null){
                s += "Right: " + c.getRight().getElement() + "\n";
            } else{
                s += "Right: null"  + "\n";
            }
            
            s += "\n===================================\n";
            s += printWithRelations(c.getRight());
        }
        return s;
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