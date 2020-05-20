import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrintTree {

    public static void main(String[] args) {
    	
    	AVLTree tree = new AVLTree();
//		tree.insert(1, "a");
//		tree.insert(2, "b");
//		assert tree.insert(3, "c") == 1;
//		BTreePrinter.printNode(tree.getRoot());
//		tree.insert(4, "d");
//		BTreePrinter.printNode(tree.getRoot());
//		tree.insert(5, "e");
//		BTreePrinter.printNode(tree.getRoot());
//
//		System.out.println(tree.search(1));
//		System.out.println(tree.search(2));
//		System.out.println(tree.search(3));
//		System.out.println(tree.search(4));
//		System.out.println(tree.search(5));
//		System.out.println(tree.search(6));
//		
//		System.out.println(tree.getRoot().getSubtreeSize());
//
//        BTreePrinter.printNode(tree.getRoot());
//        
//        tree.delete(1);
//        BTreePrinter.printNode(tree.getRoot());
//        tree.delete(4);
//        tree.delete(2);
//        tree.delete(5);
//        BTreePrinter.printNode(tree.getRoot());
//        tree.delete(3);
//        System.out.println("The tree is: " + tree.empty());
//        BTreePrinter.printNode(tree.getRoot());
    	
    	for (int i=1; i<=10; i++) {
    		System.out.println(tree.insert(1+(int)(Math.random()*(20)), ""+i));
    		//BTreePrinter.printNode(tree.getRoot());
    	}
    	
    	
    	
		int[] values = AVLTreeTest.randomArray(20, 0, 100);
		tree = AVLTreeTest.arrayToTree(values);
		Arrays.sort(values);
		values = Arrays.stream(values).distinct().toArray();
		
		BTreePrinter.printNode(tree.getRoot());
		
		int sum = 0;
		for (int i = 0; i < values.length; i++)
		{
			sum += values[i];
			System.out.println(i);
			if (i == 11) {
				System.out.println(tree.less(values[i]) == sum);
				tree.less(values[i]);
			}
			assert tree.less(values[i]) == sum;
		}
    	
    	System.out.println("End of insertion");
    	
		
    	for(int i=10; i>=0; i--) {
//    		if (i==2) {
//	    		System.out.println(tree.delete(i));
//	    		BTreePrinter.printNode(tree.getRoot());
//    		}
//    		else {
//    			System.out.println(tree.delete(i));
//	    		BTreePrinter.printNode(tree.getRoot());
//    		}
    		System.out.println(tree.delete(1+(int)(Math.random()*(30))));
    	}
    	
    	BTreePrinter.printNode(tree.getRoot());
    	
    	System.out.println("Good job!");

    }
}


class BTreePrinter {

    public static void printNode(AVLTree.IAVLNode root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static void printNodeInternal(List<AVLTree.IAVLNode> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<AVLTree.IAVLNode> newNodes = new ArrayList<AVLTree.IAVLNode>();
        for (AVLTree.IAVLNode node : nodes) {
            if (node != null) {
                System.out.print(node.getKey() == -1 ? "*" : Integer.toString(node.getKey()));
                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getLeft() != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).getRight() != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static int maxLevel(AVLTree.IAVLNode node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.getLeft()), BTreePrinter.maxLevel(node.getRight())) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}
