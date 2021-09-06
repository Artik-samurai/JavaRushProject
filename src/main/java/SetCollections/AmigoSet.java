package SetCollections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet <E> extends AbstractSet <E> implements Set <E> ,Serializable, Cloneable {

    private Object PRESENT = new Object();
    private transient HashMap < E, Object> map;

    public AmigoSet() {
        this.map = new HashMap<>();
    }

    public AmigoSet(Collection < ? extends E> collection) {
        int initialCapacity = Math.max(16, (int)Math.ceil(collection.size()/.75f));
        this.map = new HashMap<>(initialCapacity);
        addAll(collection);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        objectOutputStream.writeInt(HashMapReflectionHelper.<Integer>callHiddenMethod(map, "capacity"));
        objectOutputStream.writeFloat(HashMapReflectionHelper.<Float>callHiddenMethod(map, "loadFactor"));

        objectOutputStream.writeInt(map.size());

        for (E e: map.keySet()){
            objectOutputStream.writeObject(e);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();

        int capacity = objectInputStream.readInt();
        float loadFactor = objectInputStream.readFloat();

        map = new HashMap<>(capacity, loadFactor);

        int size = objectInputStream.readInt();

        for (int i = 0; i < size; i++){
            E e = (E) objectInputStream.readObject();
            map.put(e, PRESENT);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            AmigoSet<E> newAmigo = (AmigoSet<E>) super.clone();
            newAmigo.map = (HashMap<E, Object>) map.clone();
            return newAmigo;
        } catch (Exception e) {
            throw new InternalError();
        }
    }

    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) == PRESENT;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }
}
