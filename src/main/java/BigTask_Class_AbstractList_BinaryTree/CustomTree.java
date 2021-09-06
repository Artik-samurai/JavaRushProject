package BigTask_Class_AbstractList_BinaryTree;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomTree extends AbstractList<String> implements Serializable, Cloneable {
    Entry<String> root;
    private transient List<Entry> entryList = new ArrayList<>();
    int count = 0;

    public CustomTree() {
        root = new Entry<String>(null);
    }

    static class Entry<T> implements Serializable {

        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        boolean newLineRootElement;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String name) {

            elementName = name;
            newLineRootElement = false;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        private void checkChild() {
            if (this.leftChild != null) {
                availableToAddLeftChildren = false;
            }

            if (this.rightChild != null) {
                availableToAddRightChildren = false;
            }
        }

        public boolean isAvailableToAddChildren() {
            return this.availableToAddRightChildren || this.availableToAddLeftChildren;
        }
    }

    @Override
    public boolean add(String s) {
        Entry entry = new Entry(s);

        if (entryList.size() == 0) {
            entry.parent = root;
            root.leftChild = entry;
            count++;
        } else {

            if (entryList.size() == 1) {
                entry.parent = root;
                root.rightChild = entry;
                count++;
            } else {

                for (Entry ent : entryList) {
                    if (ent.isAvailableToAddChildren()) {
                        entry.parent = ent;
                        count++;
                        if (ent.leftChild == null) {
                            ent.leftChild = entry;
                            ent.availableToAddLeftChildren = false;
                        } else {

                            if (ent.rightChild == null) {
                                ent.rightChild = entry;
                                ent.availableToAddRightChildren = false;
                            }
                        }
                        break;
                    }
                }
            }
        }
        entryList.add(entry);
        return true;
    }

    public String getParent(String s){
        String parentName = null;

        for (Entry entry :entryList){
            if (entry.elementName.equals(s)){
                parentName = entry.parent.elementName;
            }
        }
        return parentName;
    }

    @Override
    public boolean remove(Object o) {
        boolean door = true;

        try {
            String str = (String) o;
            for (Entry entry:entryList){
                if ( entry.elementName.equals(o) && door){
                    door = false;
                    count--;
                    if (entry.leftChild != null){
                        remove(entry.leftChild.elementName);
                        entry.leftChild = null;
                    }

                    if (entry.rightChild != null){
                        remove(entry.rightChild.elementName);
                        entry.rightChild = null;
                    }

                    if (entry.parent.rightChild == entry){
                        entry.parent.availableToAddRightChildren = true;
                    }

                    if (entry.parent.leftChild == entry){
                        entry.parent.availableToAddLeftChildren = true;
                    }
                    entry = null;
                }
            }

        } catch (Exception e){
            throw new UnsupportedOperationException();
        }

        return true;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }
}