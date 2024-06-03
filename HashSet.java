import java.io.Serializable;

public class HashSet<E> implements Serializable {
    private Object[] hashArray;
    private int size;

    public HashSet() {
        hashArray = new Object[17575];
        size = 0;
    }

    public boolean add(E obj) {
        int location = obj.hashCode();
        if (location > hashArray.length) {
            return false;
        }
        if (hashArray[location] == null) {
            hashArray[location] = obj;
            size++;
            return true;
        }
        return false;
    }

    public boolean contains(E obj) {
        if (hashArray[obj.hashCode()] != null) {
            return true;
        }
        return false;
    }
    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        return false;
    }
    public boolean remove(E obj) {
        if (hashArray[obj.hashCode()] != null) {
            hashArray[obj.hashCode()] = null;
            size--;
            return true;
        }
        return false;
    }

    public void clear() {
        hashArray = new Object[1000];
        size = 0;
    }

    public int size() {
        return size;
    }

    public DLList<E> toDLList() {
        DLList<E> list = new DLList<E>();
        for (int i = 0; i < hashArray.length; i++){
            if (hashArray[i] != null){
                list.add((E) hashArray[i]);
            }
        }
        return list;
    }
}
