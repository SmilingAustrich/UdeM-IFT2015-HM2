package ift2015_a25_tp2;
import java.util.*;

/**
 * IFT2015 - Automne 2025, TP2 - Question 2
 *
 * Ici, on gèree une hiérarchie d’employés. En gros, c’est comme un arbre :
 * chaque personne connaît son boss et ses subordonnés directs.
 * On peut ajouter du monde, bouger du monde, en enlever,
 * puis afficher toute la structure de façon lisible.
 *
 * Auteur : Tarek
 * Cours : IFT2015 (Structures de données)
 * Université de Montréal - Automne 2025
 */
public class Q2 {
	
	// Bon, ici chaque Node représente un employé.
	// Il a un nom, un niveau, un boss (s’il en a un) et une petite liste de personnes qui reportent à lui.
	class Node {
		String name;
		Node boss = null;
		int level;
		List<Node> reports = new ArrayList<>();
		
		Node(String name, int level) {
			this.name = name;
			this.level = level;
		}
	}
	
	Node root; // le grand boss du top
	Map<String, Node> nameToNode = new HashMap<>(); // pratique pour retrouver un employé rapidement
	
	// Bon la, on ajoute un employé dans la hiérarchie.
	// Si c’est le niveau 1, c’est la racine, sinon on le plug sous son boss.
	public void addEmployee(String name, int level, String bossName) {
		Node newEmp = new Node(name, level);
		nameToNode.put(name, newEmp);

		if (level == 1) {
			// C’est notre grand patron, le CEO du système
			root = newEmp;
			return;
		}

		Node boss = nameToNode.get(bossName);
		if (boss != null) {
			newEmp.boss = boss;
			boss.reports.add(newEmp);
		}
	}
	
	// Ici on veut afficher la hiérarchie complète de façon lisible.
	// On fait ça récursivement : on part du boss, puis on descend dans les enfants.
	public void printChart(Node root) {
		printChartRec(root, "");
	}

	private void printChartRec(Node node, String indent) {
		if (node == null) return;

		System.out.println(indent + node.name);
		for (Node child : node.reports) {
			// On garde une indentation simple, genre “|  ” pour voir la hiérarchie clairement.
			printChartRec(child, indent + "|  ");
		}
	}
	
	// Okk guys, ici on veut déplacer un employé sous un nouveau boss.
	// Donc on le retire de son ancien boss, puis on l’ajoute dans la liste du nouveau.
	public void move(String employee, String boss) {
		Node emp = nameToNode.get(employee);
		Node newBoss = nameToNode.get(boss);

		if (emp == null || newBoss == null || emp.boss == null) return;

		emp.boss.reports.remove(emp);
		emp.boss = newBoss;
		newBoss.reports.add(emp);
	}
	
	// Bon la, on enlève un employé et on redistribue ses subordonnés à un autre boss.
	// C’était un peu mélangeant au début mais le truc c’est juste de relier ses “reports” au nouveau boss.
	public void remove(String employee, String boss) {
		Node emp = nameToNode.get(employee);
		Node newBoss = nameToNode.get(boss);

		if (emp == null || newBoss == null) return;

		for (Node report : emp.reports) {
			report.boss = newBoss;
			newBoss.reports.add(report);
		}
		emp.reports.clear();

		if (emp.boss != null)
			emp.boss.reports.remove(emp);

		nameToNode.remove(employee);
	}
	
	// Ici, on veut imprimer la chaîne de commandement d’un employé jusqu’au grand boss.
	// Donc on monte avec .boss jusqu’à la racine.
	public void printAllBoss(String employee) {
		Node emp = nameToNode.get(employee);
		if (emp == null) return;

		List<String> chain = new ArrayList<>();
		while (emp != null) {
			chain.add(emp.name);
			emp = emp.boss;
		}

		for (int i = 0; i < chain.size(); i++) {
			System.out.print(chain.get(i));
			if (i < chain.size() - 1)
				System.out.print(" -> ");
		}
		System.out.println();
	}

	// Bon ici on veut afficheer tous les employés par niveau.
	// On fait un parcourss en largeur avec une file (queue), niveau par niveau.
	public void printLevelsQueue(Node root) {
		if (root == null) return;

		Queue<Node> queue = new LinkedList<>();
		queue.add(root);

		int level = 1;
		while (!queue.isEmpty()) {
			int size = queue.size();
			System.out.print(level + ": [");

			for (int i = 0; i < size; i++) {
				Node cur = queue.poll();
				System.out.print(cur.name);
				if (i < size - 1) System.out.print(", ");
				queue.addAll(cur.reports);
			}

			System.out.println("]");
			level++;
		}
	}
    
	// Ici on cherche le boss commun le plus bas entre deux employés.
	// C’est genre “leur plus petit ancêtre commun” dans l’arbre.
	public String lowestCommonBoss(String e1, String e2) {
		Node n1 = nameToNode.get(e1);
		Node n2 = nameToNode.get(e2);
		if (n1 == null || n2 == null) return null;

		Set<Node> ancestors = new HashSet<>();
		while (n1 != null) {
			ancestors.add(n1);
			n1 = n1.boss;
		}
		while (n2 != null) {
			if (ancestors.contains(n2))
				return n2.name;
			n2 = n2.boss;
		}
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
