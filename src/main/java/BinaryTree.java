import java.util.Comparator;
import java.util.Stack;

public class BinaryTree<K,V> {
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
    private Entry<K,V> rootEntry;
    private final Comparator<K> comparator;

    public BinaryTree(Comparator<K> comparator) {
        this.rootEntry = null;
        this.comparator=comparator;
    }

    public Entry<K,V> findByKey(K key) { // поиск узла по значению
        Entry<K,V> currentEntry = rootEntry; // начинаем поиск с корневого узла
        while (currentEntry.key != key) { // поиск покуда не будет найден элемент или не будут перебраны все
            if (comparator.compare(currentEntry.key,key)>0) { // движение влево?
                currentEntry = currentEntry.left;
            } else { //движение вправо
                currentEntry = currentEntry.right;
            }
            if (currentEntry == null) { // если потомка нет,
                return null; // возвращаем null
            }
        }
        return currentEntry; // возвращаем найденный элемент
    }

    public void insert(K key, V value) { // метод вставки нового элемента
        Entry<K,V> newEntry = new Entry<>(key,value); // создание нового узла
        if (rootEntry == null) { // если корневой узел не существует
            rootEntry = newEntry;// то новый элемент и есть корневой узел
        }
        else { // корневой узел занят
            Entry<K,V> currentEntry= rootEntry; // начинаем с корневого узла
            Entry<K,V> parentEntry;
            while (true) // мы имеем внутренний выход из цикла
            {
                parentEntry = currentEntry;
                if(key.equals(currentEntry.key)) {   // если такой элемент в дереве уже есть, не сохраняем его
                    return;    // просто выходим из метода
                }
                else  if (comparator.compare(currentEntry.key,key)>0) {   // движение влево?
                    currentEntry = currentEntry.left;
                    if (currentEntry == null){ // если был достигнут конец цепочки,
                        parentEntry.left=newEntry; //  то вставить слева и выйти из методы
                        return;
                    }
                }
                else { // Или направо?
                    currentEntry = currentEntry.right;
                    if (currentEntry == null) { // если был достигнут конец цепочки,
                        parentEntry.right=newEntry;  //то вставить справа
                        return; // и выйти
                    }
                }
            }
        }
    }

    public boolean delete(K key) // Удаление узла с заданным ключом
    {
        Entry<K,V> currentEntry= rootEntry;
        Entry<K,V> parentEntry = rootEntry;
        boolean isLeftChild = true;
        while (!currentEntry.key.equals(key)) { // начинаем поиск узла
            parentEntry = currentEntry;
            if (comparator.compare(currentEntry.key,key)>0) { // Определяем, нужно ли движение влево?
                isLeftChild = true;
                currentEntry = currentEntry.left;
            }
            else { // или движение вправо?
                isLeftChild = false;
                currentEntry = currentEntry.right;
            }
            if (currentEntry == null)
                return false; // yзел не найден
        }

        if (currentEntry.left == null && currentEntry.right == null) { // узел просто удаляется, если не имеет потомков
            if (currentEntry == rootEntry) // если узел - корень, то дерево очищается
                rootEntry = null;
            else if (isLeftChild)
                parentEntry.left=null; // если нет - узел отсоединяется, от родителя
            else
                parentEntry.right=null;
        }
        else if (currentEntry.right == null) { // узел заменяется левым поддеревом, если правого потомка нет
            if (currentEntry == rootEntry)
                rootEntry = currentEntry.left;
            else if (isLeftChild)
                parentEntry.left=currentEntry.left;
            else
                parentEntry.right=currentEntry.left;
        }
        else if (currentEntry.left == null) { // узел заменяется правым поддеревом, если левого потомка нет
            if (currentEntry == rootEntry)
                rootEntry = currentEntry.right;
            else if (isLeftChild)
                parentEntry.left=currentEntry.right;
            else
                parentEntry.right=currentEntry.right;
        }
        else { // если есть два потомка, узел заменяется преемником
            Entry<K,V> heir = receiveHeir(currentEntry);// поиск преемника для удаляемого узла
            if (currentEntry == rootEntry)
                rootEntry= heir;
            else if (isLeftChild)
                parentEntry.left=heir;
            else
                parentEntry.right=heir;
        }
        return true; // элемент успешно удалён
    }

    // метод возвращает узел со следующим значением после передаваемого аргументом.
    // для этого он сначала переходим к правому потомку, а затем
    // отслеживаем цепочку левых потомков этого узла.
    private Entry<K,V> receiveHeir(Entry<K,V> entry) {
        Entry<K,V> parentEntry = entry;
        Entry<K,V> heirEntry = entry;
        Entry<K,V> currentEntry = entry.right; // Переход к правому потомку
        while (currentEntry != null) // Пока остаются левые потомки
        {
            parentEntry = heirEntry;// потомка задаём как текущий узел
            heirEntry = currentEntry;
            currentEntry = currentEntry.left; // переход к левому потомку
        }
        // Если преемник не является
        if (!heirEntry.equals(entry.right) ) // правым потомком,
        { // создать связи между узлами
            parentEntry.left=heirEntry.right;
            heirEntry.right=entry.right;
        }
        return heirEntry;// возвращаем приемника
    }

    public void printTree() { // метод для вывода дерева в консоль
        Stack<Entry<K,V>> globalStack = new Stack<>(); // общий стек для значений дерева
        globalStack.push(rootEntry);
        int gaps = 32; // начальное значение расстояния между элементами
        boolean isRowEmpty = false;
        String separator = "-----------------------------------------------------------------";
        System.out.println(separator);// черта для указания начала нового дерева
        while (!isRowEmpty) {
            Stack<Entry<K,V>> localStack = new Stack<>(); // локальный стек для задания потомков элемента
            isRowEmpty = true;

            for (int j = 0; j < gaps; j++)
                System.out.print(' ');
            while (!globalStack.isEmpty()) { // покуда в общем стеке есть элементы
                Entry<K,V> temp = (Entry<K, V>) globalStack.pop(); // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    System.out.print(temp); // выводим его значение в консоли
                    localStack.push(temp.left); // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.right);
                    if (temp.left != null ||
                            temp.right != null)
                        isRowEmpty = false;
                }
                else {
                    System.out.print("__");// - если элемент пустой
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < gaps * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            gaps /= 2;// при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (!localStack.isEmpty())
                globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный
        }
        System.out.println(separator);// подводим черту
    }
}
