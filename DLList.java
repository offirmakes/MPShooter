import java.io.Serializable;

public class DLList<E> implements Serializable {
    private Node<E> head;
    private Node<E> tail;
    
    private int size;
    
    public DLList(){
        head = new Node<E>(null);
        tail = new Node<E>(null);
        head.setNext(tail);
        head.setPrev(null);
		tail.setNext(null);
        tail.setPrev(head);
        size = 0;
    }
    public Node<E> getNode(int position) {
        Node<E> current = head.next();
        for(int i = 0; i<position; i++){
            current = current.next();
        }
        return current;
    }
    public E get(int position){
        return getNode(position).get();
    }
    public void set(int position, E e){
        getNode(position).set(e);
    }
    public void add(int i, E e){

        Node<E> current = getNode(i);
        Node<E> newNode = new Node<E>(e);
        Node<E> before = current.prev();
        Node<E> after = current;

        newNode.setNext(after);
        newNode.setPrev(before);
        
        before.setNext(newNode);
        after.setPrev(newNode);
        size++;
    }
    public void add(E e){
        Node<E> newNode = new Node<E>(e);
        Node<E> before = tail.prev();
        Node<E> after = tail;

        before.setNext(newNode);
        after.setPrev(newNode);

        newNode.setNext(after);
        newNode.setPrev(before);

        size++;
    }
    public void remove(int i){
        if (i >= 0 && i < size){
            Node<E> current = head.next();
            for (int j = 0; j<size; j++){
                if (j == i){
                    break;
                }
                current = current.next();
            }
            Node<E> before = current.prev();
            Node<E> after = current.next();

            before.setNext(after);
            after.setPrev(before);

            size--;
        }
    }
    public void remove(E e){
        Node<E> current = head.next();
        for (int j = 0; j<size; j++){
            if (current.get().equals(e)){
                break;
            }
            current = current.next();
        }
        Node<E> before = current.prev();
        Node<E> after = current.next();

        before.setNext(after);
        after.setPrev(before);

        size--;
    }
    public String toString(){
        String list = "[";
        Node<E> current = head.next();
        for (int i = 0; i < size-1; i++){
            list += current.get() + ", ";
            current = current.next();
        }
        list+=current.get() + "]";
        return list;
    }
    public int size(){
        return size;
    }
}
