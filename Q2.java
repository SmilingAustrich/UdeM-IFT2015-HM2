package ift2015_a25_tp2;
import java.util.*;

public class Q2 {
	
	// Node class represents one employee in the hierarchy
	class Node {
		String name;
		Node boss = null;
		int level;
		List<Node> reports = new ArrayList<>(); //employee's direct reports
		
		Node(String name, int level) {
			this.name = name;
			this.level = level;
		}
	}
	
	Node root; // top-most boss
	Map<String, Node> nameToNode = new HashMap<>(); // maps employee names to nodes for quick look-up
	
	// 1. Add an employee to the hierarchy
	public void addEmployee(String name, int level, String bossName) {
		// TODO
	}
	
	// 2. Print chart recursively
	public void printChart(Node root) {
		// TODO
	}
	
	// 3. Move employee under new boss
	public void move(String employee, String boss) {
		// TODO
	}
	
	// 4. Remove employee and reassign reports to a new boss
	public void remove(String employee, String boss) {
		// TODO
	}
	
	// 5. Print chain of supervisors
	public void printAllBoss(String employee) {
		// TODO
	}

	 // 6. Print all levels using queue        
	public void printLevelsQueue(Node root) {
		// TODO
	}
    
	// 7. Get lowest common boss
	public String lowestCommonBoss(String e1, String e2) {
		// TODO
		return null; 
	}
	
    // some tests
	public static void main(String[] args) {
        Q2 h = new Q2();
        
        // add to hierarchy
        h.addEmployee("Claude", 1, null); // Claude is the root boss at level 1.
        h.addEmployee("Bob", 2, "Claude"); // Bob reports to Claude.
        h.addEmployee("Elaine", 2, "Claude"); // Elaine also reports to Claude.
        h.addEmployee("Alice", 3, "Bob"); // Alice reports to Bob.
        h.addEmployee("David", 3, "Elaine"); // David reports to Elaine.
        
        // print the hierarchy chart
        h.printChart(h.root);
        
        // reassign Alice to Elaine
//        h.move("Alice", "Elaine");
//        h.printChart(h.root); 
        
        // remove Bob and reassign his reports to Elaine
//        h.remove("Bob", "Elaine");
//        h.printChart(h.root);
        
        // print chain from Alice up to root
//        h.printAllBoss("Alice");
        
        // print all levels 
//        h.printLevelsQueue(h.root);
        
        // lowest common boss between Alice and David
//        System.out.println(h.lowestCommonBoss("Alice", "David"));

	}
	
}
