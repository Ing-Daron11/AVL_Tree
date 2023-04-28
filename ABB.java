package util;

public interface ABB<E> {

    public Node<E> search(Node<E> r, E s);

    public Node<E> delete(E d);

	public void add(E e);

}