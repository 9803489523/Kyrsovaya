import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        BinaryTree<Integer,String> tree=new BinaryTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1,o2);
            }
        });
        System.out.println("Вставка");
        tree.insert(1,"Иванов");
        tree.insert(6,"Петров");
        tree.insert(8,"Смирнов");
        tree.insert(4,"Злобин");
        tree.insert(5,"Белкин");
        tree.insert(7,"Усманов");
        tree.printTree();
        System.out.println("Удаление");
        tree.delete(4);
        tree.printTree();
        System.out.println("Получение значения по ключу");
        System.out.println(tree.findByKey(5));
        System.out.println(tree.findByKey(6));
        System.out.println(tree.findByKey(4));

        BinaryTree<String,Integer> tree1=new BinaryTree<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("Вставка");
        tree1.insert("Иванов",35);
        tree1.insert("Петров",48);
        tree1.insert("Баранов",40);
        tree1.insert("Алексеев",33);
        tree1.insert("Сурков",12);
        tree1.insert("Терехов",29);
        tree1.insert("Пауков",18);
        tree1.insert("Чеграков",20);
        tree1.insert("Барсуков",60);
        tree1.insert("Ильюхин",19);
        tree1.printTree();
        tree1.delete("Терехов");
        tree1.delete("Сурков");
        System.out.println("Удаление");
        tree1.printTree();
        System.out.println("Получение значения по ключу");
        System.out.println(tree1.findByKey("Иванов"));
        System.out.println(tree1.findByKey("Петров"));
        System.out.println(tree1.findByKey("Баранов"));
        BinaryTree<Integer,Car> tree2=new BinaryTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1,o2);
            }
        }
        );
        System.out.println("Вставка");
        tree2.insert(1,new Car("Ferrari",400,500000));
        tree2.insert(10,new Car("Lada",200,50000));
        tree2.insert(8,new Car("Chevrolet",250,80000));
        tree2.insert(6,new Car("Renault",270,90000));
        tree2.insert(9,new Car("Nissan",220,60000));
        tree2.insert(4,new Car("BMW",370,150000));
        tree2.insert(5,new Car("Wolksvagen",210,55000));
        tree2.printTree();
        tree2.delete(6);
        tree2.delete(10);
        System.out.println("Удаление");
        tree2.printTree();
        System.out.println("Получение значения по ключу");
        System.out.println(tree2.findByKey(10));
        System.out.println(tree2.findByKey(1));
        System.out.println(tree2.findByKey(6));
    }
}
