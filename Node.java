package util;

public class Node<E>{

    E element;
    Node<E> parent;
    Node<E> left;
    Node<E> right;

    public Node(E e){
        element = e;
    }

    public int height(){
    	if(isLeaf()) {
    		return 1;
    	}else {
    		if(left!=null && right==null) {
    			return left.height() + 1;
    		}else if(left==null && right!=null) {
    			return right.height() + 1;
    		}else {
    			return Math.max(left.height(), right.height()) + 1;
    		}
    	}
    }
    
    public boolean isLeaf() {
    	return (left == null && right == null);
    }
    
    public int fb(){
    	if(isLeaf() == true) {
    		return 0;
    	} else if(left == null) {
    		return right.height() - 0;
    	} else if(right == null) {
    		return 0 - left.height();
    	} else {
    		return (right.height() - left.height());
    	}
    }
    
    public E getElement(){
        return element;
    }

    public void setElement(E element){
        this.element = element;
    }

    public Node<E> getParent(){
        return parent;
    }

    public void setParent(Node<E> up){
        this.parent = up;
    }

    public Node<E> getLeft(){
        return left;
    }

    public void setLeft(Node<E> left){
        this.left = left;
    }

    public Node<E> getRight(){
        return right;
    }

    public void setRight(Node<E> right){
        this.right = right;
    }

}