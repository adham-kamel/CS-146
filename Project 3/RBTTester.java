package sjsu.kamel.cs146.project3;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.Test;

public class RBTTester {

	@Test
	// Test the Red Black Tree
	public void test() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");
		assertEquals("DBACFEHGIJ", makeString(rbt));
		String str = "Color: 1, Key:D Parent: \n" + "Color: 1, Key:B Parent: D\n" + "Color: 1, Key:A Parent: B\n"
				+ "Color: 1, Key:C Parent: B\n" + "Color: 1, Key:F Parent: D\n" + "Color: 1, Key:E Parent: F\n"
				+ "Color: 0, Key:H Parent: F\n" + "Color: 1, Key:G Parent: H\n" + "Color: 1, Key:I Parent: H\n"
				+ "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));

	}

	// add tester for spell checker
	@Test
	public void spellCheck() throws IOException{
		RedBlackTree dictionary = new RedBlackTree();
		RedBlackTree poemTree = new RedBlackTree();
		BufferedReader document = new BufferedReader(new FileReader("Document.txt"));
		BufferedReader poem = new BufferedReader(new FileReader("Poem.txt"));
		long start = System.nanoTime();
		while(document.readLine() != null){
			dictionary.insert(document.readLine());
		}
		long end = System.nanoTime();
		System.out.println(((end - start) / 1000000) + " milliseconds for creation of dictionary");
		long startPoem = System.nanoTime();
		while(poem.readLine() != null){
			dictionary.lookup(poem.readLine());
		}
		long endPoem = System.nanoTime();
		System.out.println(((endPoem - startPoem) / 1000) + " microseconds for lookup in dictionary");
	}
	
	public static String makeString(RedBlackTree t) {
		class MyVisitor implements RedBlackTree.Visitor {
			String result = "";

			public void visit(RedBlackTree.Node n) {
				result = result + n.key;
			}
		}
		;
		MyVisitor v = new MyVisitor();
		t.preOrderVisit(v);
		return v.result;
	}

	public static String makeStringDetails(RedBlackTree t) {
		{
			class MyVisitor implements RedBlackTree.Visitor {
				String result = "";

				public void visit(RedBlackTree.Node n) {
					if (n.parent == null){
						result = result + "Color: " + n.color + ", Key:" + n.key + " Parent: \n";
					}
					else if (!(n.key).equals(""))
						result = result + "Color: " + n.color + ", Key:" + n.key + " Parent: "+ n.parent.key +"\n";

				}
			}
			;
			MyVisitor v = new MyVisitor();
			t.preOrderVisit(v);
			return v.result;
		}
	}
}
