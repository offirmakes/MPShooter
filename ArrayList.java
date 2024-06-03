import java.io.Serializable;

public class ArrayList<E> implements Serializable{

    private Object[] list;
    private int capacity = 20;
    
    public ArrayList(){
        list = new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    public E get(int i) {
        return (E) list[i];
    }

    public boolean add(E e){
        for (int i = 0; i < list.length; i++){
            if (list[i] == null){
                list[i] = e;
                return true;
            } else if (i == capacity-1) {
                capacity += 10;
                Object[] newList = new Object[capacity];
                for (int k = 0; k < list.length; k++){
                    newList[k] = list[k];
                }
                list = newList;
            }
        }
        return false;
    }
    public void add(int i, E e) {
        boolean added = false;
        int size = 0;
        for (int j = 0; j < list.length; j++){
            if (list[j] != null){
                size++;
            }
        }
        if (i >= 0 && i <= size) {
            if (size == capacity) {
                capacity *= 2;
                Object[] newList = new Object[capacity];
        
                for (int k = 0; k < size; k++) {
                    if (k == i) {
                        newList[k] = e;
                    }
                    else if (added){
                        newList[k + 1] = list[k];
                    } else if (!added){
                        newList[k] = list[k];
                    }
                    
                }
        
                list = newList;
            } else {
                for (int k = size; k > i; k--) {
                    list[k] = list[k - 1];
                }
                list[i] = e;
            }
        }
    }
    public E remove (int i){
        E remove = null;
        for (int j = i; j < capacity; j++){
            remove = (E)list[i];
            if (j == capacity || i == j){
                list[j] = null;
            }
            else {
                list[j-1] = list[j];
            }
        }
        return remove;
    }
    public E remove (E e){
        E remove = null;
        for (int i = 0; i < capacity; i++){
            if (list[i].equals(e)){
                for (int j = i; j < capacity; j++){
                    remove = (E)list[i];
                    if (j == capacity || i == j){
                        list[j] = null;
                    }
                    else {
                        list[j-1] = list[j];
                    }
                }
            break;
            }
        }
        return remove;
    }
    public E set(int i, E e){
        E previous = null;
        if (i < capacity && i >= 0){
            previous = (E)list[i];
            list[i] = e;
        }
        return previous;

    }
    public void clear(){
        for (int i =0; i < capacity; i++){
            if (list[i] != null){
                list[i] = null;
            }
        }
    }
    public int size(){
        int counter = 0;
        for (int i =0; i < capacity; i++){
            if (list[i] != null){
                counter++;
            }
        }
        return counter;
    }
    public String toString(){
        String valuesString = "";
        for(int i = 0; i < list.length; i++){
            valuesString += ", " + list[i];
        }
        return valuesString;
    }
}
