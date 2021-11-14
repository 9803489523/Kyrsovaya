import java.util.Comparator;
import java.util.Stack;

/**
 * @author Aleksanr Nozdriuhin, Aleksey Bukatov
 * class with subclass Entry,
 * fields comparator, rootEntry
 * and methods findByKey, delete, insert, print
 * @param <K> key of tree
 * @param <V> value of tree
 */
public class BinaryTree<K,V> {
    /**
     * subclass node of binary tree
     * @param <K> key
     * @param <V> value
     */
    private static class Entry<K,V>{
        K key;
        V value;
        Entry<K,V> left;
        Entry<K,V> right;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            left=right=null;
        }

        @Override
        public String toString() {
            return String.format("%S, %s",key,value);
        }
    }

    /**
     * head of tree
     */
    private Entry<K,V> rootEntry;
    /**
     * comparator of tree for compare values of keys
     */
    private final Comparator<K> comparator;

    /**
     * constructor
     */
    public BinaryTree(Comparator<K> comparator) {
        this.rootEntry = null;
        this.comparator=comparator;
    }

    /**
     * method, which find node by key
     * @param key, value for search
     * @return node of tree with input key
     */
    public Entry<K,V> findByKey(K key) {
        Entry<K,V> currentEntry = rootEntry;
        while (currentEntry.key != key) {
            if (comparator.compare(currentEntry.key,key)>0) {
                currentEntry = currentEntry.left;
            } else {
                currentEntry = currentEntry.right;
            }
            if (currentEntry == null) {
                return null;
            }
        }
        return currentEntry;
    }

    /**
     * method to add pair <key,value> to tree
     * @param key, key of pair
     * @param value, value of pair
     */
    public void insert(K key, V value) {
        Entry<K,V> newEntry = new Entry<>(key,value);
        if (rootEntry == null) {
            rootEntry = newEntry;
        }
        else {
            Entry<K,V> currentEntry= rootEntry;
            Entry<K,V> parentEntry;
            while (true)
            {
                parentEntry = currentEntry;
                if(key.equals(currentEntry.key)) {
                    return;
                }
                else  if (comparator.compare(currentEntry.key,key)>0) {
                    currentEntry = currentEntry.left;
                    if (currentEntry == null){
                        parentEntry.left=newEntry;
                        return;
                    }
                }
                else {
                    currentEntry = currentEntry.right;
                    if (currentEntry == null) {
                        parentEntry.right=newEntry;
                        return;
                    }
                }
            }
        }
    }

    /**
     * method to delete value by key
     */
    public boolean delete(K key)
    {
        Entry<K,V> currentEntry= rootEntry;
        Entry<K,V> parentEntry = rootEntry;
        boolean isLeftChild = true;
        while (!currentEntry.key.equals(key)) {
            parentEntry = currentEntry;
            if (comparator.compare(currentEntry.key,key)>0) {
                isLeftChild = true;
                currentEntry = currentEntry.left;
            }
            else {
                isLeftChild = false;
                currentEntry = currentEntry.right;
            }
            if (currentEntry == null)
                return false;
        }

        if (currentEntry.left == null && currentEntry.right == null) {
            if (currentEntry == rootEntry)
                rootEntry = null;
            else if (isLeftChild)
                parentEntry.left=null;
            else
                parentEntry.right=null;
        }
        else if (currentEntry.right == null) {
            if (currentEntry == rootEntry)
                rootEntry = currentEntry.left;
            else if (isLeftChild)
                parentEntry.left=currentEntry.left;
            else
                parentEntry.right=currentEntry.left;
        }
        else if (currentEntry.left == null) {
            if (currentEntry == rootEntry)
                rootEntry = currentEntry.right;
            else if (isLeftChild)
                parentEntry.left=currentEntry.right;
            else
                parentEntry.right=currentEntry.right;
        }
        else {
            Entry<K,V> heir = receiveHeir(currentEntry);
            if (currentEntry == rootEntry)
                rootEntry= heir;
            else if (isLeftChild)
                parentEntry.left=heir;
            else
                parentEntry.right=heir;
        }
        return true;
    }

    private Entry<K,V> receiveHeir(Entry<K,V> entry) {
        Entry<K,V> parentEntry = entry;
        Entry<K,V> heirEntry = entry;
        Entry<K,V> currentEntry = entry.right;
        while (currentEntry != null)
        {
            parentEntry = heirEntry;
            heirEntry = currentEntry;
            currentEntry = currentEntry.left;
        }
        if (!heirEntry.equals(entry.right) )
        {
            parentEntry.left=heirEntry.right;
            heirEntry.right=entry.right;
        }
        return heirEntry;
    }

    /**
     * method to print all pair in tree
     */
    public void printTree() {
        Stack<Entry<K,V>> globalStack = new Stack<>();
        globalStack.push(rootEntry);
        int gaps = 32;
        boolean isRowEmpty = false;
        String separator = "-----------------------------------------------------------------";
        System.out.println(separator);
        while (!isRowEmpty) {
            Stack<Entry<K,V>> localStack = new Stack<>();
            isRowEmpty = true;

            for (int j = 0; j < gaps; j++)
                System.out.print(' ');
            while (!globalStack.isEmpty()) {
                Entry<K,V> temp = (Entry<K, V>) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp);
                    localStack.push(temp.left);
                    localStack.push(temp.right);
                    if (temp.left != null ||
                            temp.right != null)
                        isRowEmpty = false;
                }
                else {
                    System.out.print("__");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < gaps * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            gaps /= 2;
            while (!localStack.isEmpty())
                globalStack.push(localStack.pop());
        }
        System.out.println(separator);
    }
}
