import java.io.Serializable;

public class Node<E> implements Serializable{
    private Node<E> next;
    private Node<E> prev;
    private E e;
    public Node(E e){
        this.e = e;
        next = null;
        prev = null;
    }
    public void setNext(Node<E> next){
        this.next = next;
    }
    public void setPrev(Node<E> prev){
        this.prev = prev;
    }
    public Node<E> next(){
        return next;
    }
    public Node<E> prev(){
        return prev;
    }
    public E get(){
        return e;
    }
    public void set(E e){
        this.e = e;
    }
}