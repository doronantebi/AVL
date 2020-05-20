import java.util.Random;

public class Measurements {
	
	private static int MAX = 1000000;
	private static int MIN = 1;
	private static int N = 10000;

	public static void main(String[] args) {
		
		Measurements m = new Measurements();
		System.out.println("================================================================");
		System.out.println("PART A");
		System.out.println("================================================================");
		m.part1();
		System.out.println();
		System.out.println("=============================================");
		System.out.println("PART B: time in microseconds (10^-6 second)");
		System.out.println("=============================================");
		m.part2();

	}
	
	
	private void part1() {
		
		Random rnd = new Random();
		String[] title = {"#", "#Ops", "avg ins", "avg del", "max ins", "max del"};
		System.out.format("%-5s%-10s%-15.7s%-15.7s%-12s%-12s\n", (Object[])title);
		
		for(int i = 1; i <= 10; i++) {
			
			AVLTree tree = new AVLTree();
			
			int maxInsert = -1;
			int sumInsert = 0;
			
			while(tree.size() < i*N) {
				int k = rnd.nextInt(MAX-MIN+1) + MIN;
				int rotations = tree.insert(k, ":)");
				sumInsert += rotations;
				maxInsert = Math.max(maxInsert, rotations);
			}
			
			int[] keys = tree.keysToArray();
			
			int maxDelete = -1;
			int sumDelete = 0;
			for(int k : keys) {
				int rotations = tree.delete(k);
				sumDelete += rotations;
				maxDelete = Math.max(maxDelete, rotations);
			}
			
			String[] res = {Integer.toString(i),
							Integer.toString(i*N), 
							Double.toString((double)sumInsert/(N*i)), 
							Double.toString((double)sumDelete/(N*i)), 
							Integer.toString(maxInsert), 
							Integer.toString(maxDelete)};
			
			System.out.format("%-5s%-10s%-15.7s%-15.7s%-12s%-12s\n", (Object[])res);
		}
		
	}
	
	private void part2() {
		
		Random rnd = new Random();
		String[] title = {"#", "#Ops", "in-order", "del-min"};
		System.out.format("%-5s%-10s%-12s%-12s\n", (Object[])title);
		
		for(int i = 1; i <= 10; i++) {
			
			AVLTree tree = new AVLTree();
			
			while(tree.size() < i*N) {
				int k = rnd.nextInt(MAX-MIN+1) + MIN;
				tree.insert(k, ":)");
			}
			
			long startTime = System.nanoTime();
			int[] keys = tree.keysToArray();
			long endTime = System.nanoTime();
			long inOrderTime = endTime - startTime;
			
			
			startTime = System.nanoTime();
			for(int k : keys) {
				tree.delete(k);
			}
			endTime = System.nanoTime();
			long delMinTime = endTime - startTime;
			
			String[] res = {Integer.toString(i),
							Integer.toString(i*N),
							Long.toString(inOrderTime/1000),
							Long.toString(delMinTime/1000)};
			
			System.out.format("%-5s%-10s%-12s%-12s\n", (Object[])res);
			
			
		}
		
	}

}
