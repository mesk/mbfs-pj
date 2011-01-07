import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TreeTest {

	@Test
	public void testBasicTree() {
		Tree<String> tree = new Tree<String>("ROOT");
		assertEquals("ROOT", tree.toString());
	}

	@Test
	public void testVerySimpleTree() {
		Tree<String> tree = new Tree<String>("ROOT");
		tree.addLeaf("ROOT", "FOO");
		String actual = tree.toString();
		String expected =
			"ROOT\n" +
			"`-- FOO";
		assertEquals(expected, actual);
	}

	@Test
	public void testSimpleNestedTree() {
		Tree<String> tree = new Tree<String>("ROOT");
		tree.addLeaf("ROOT", "FOO");
		tree.addLeaf("FOO", "BAR");
		tree.addLeaf("BAR", "BAZ");
		String actual = tree.toString();
		String expected =
			"ROOT\n" +
			"`-- FOO\n" +
			"    `-- BAR\n" +
			"        `-- BAZ";
		assertEquals(expected, actual);
	}

	@Test
	public void testSimpleTree() {
		Tree<String> tree = new Tree<String>("ROOT");
		tree.addLeaf("ROOT", "FOO");
		tree.addLeaf("ROOT", "BAR");
		String actual = tree.toString();
		String expected =
			"ROOT\n" +
			"|-- FOO\n" +
			"`-- BAR";
		assertEquals(expected, actual);
	}

	@Test
	public void testWideShallowTree() {
		Tree<String> tree = new Tree<String>("ROOT");
		tree.addLeaf("ROOT", "FOO");
		tree.addLeaf("ROOT", "BAR");
		tree.addLeaf("ROOT", "BAZ");
		tree.addLeaf("ROOT", "QUUX");
		String actual = tree.toString();
		String expected =
			"ROOT\n" +
			"|-- FOO\n" +
			"|-- BAR\n" +
			"|-- BAZ\n" +
			"`-- QUUX";
		assertEquals(expected, actual);
	}

	@Test
	public void testSmallTree() {
		Tree<String> tree = new Tree<String>("ROOT");
		tree.addLeaf("ROOT", "FOO");
		tree.addLeaf("FOO", "BAR");
		tree.addLeaf("ROOT", "QUUX");
		String actual = tree.toString();
		String expected =
			"ROOT\n" +
			"|-- FOO\n" +
			"|   `-- BAR\n" +
			"`-- QUUX";
		assertEquals(expected, actual);
	}

	@Test
	public void testMediumTree() {
		Tree<String> tree = new Tree<String>("ROOT");
		tree.addLeaf("ROOT", "FOO");
		tree.addLeaf("FOO", "BAR");
		tree.addLeaf("BAR", "BAZ");
		tree.addLeaf("ROOT", "QUUX");
		String actual = tree.toString();
		String expected =
			"ROOT\n" +
			"|-- FOO\n" +
			"|   `-- BAR\n" +
			"|       `-- BAZ\n" +
			"`-- QUUX";
		assertEquals(expected, actual);
	}

	@Test
	public void testDirectoryTree() {
		Tree<String> tree = new Tree<String>(".");
		tree.addLeaf(".", "build");
		tree.addLeaf("build", "classes");
		tree.addLeaf("classes", "Query.class");
		tree.addLeaf("classes", "SelectTest.class");
		tree.addLeaf("classes", "Tree.class");
		tree.addLeaf("build", "test-classes");
		tree.addLeaf("test-classes", "TreeTest.class");
		tree.addLeaf(".", "build.properties");
		tree.addLeaf(".", "build.xml");
		tree.addLeaf(".", "lib");
		tree.addLeaf("lib", "jsqlparser.jar");
		String actual = tree.toString();
		String expected =
			".\n" +
			"|-- build\n" +
			"|   |-- classes\n" +
			"|   |   |-- Query.class\n" +
			"|   |   |-- SelectTest.class\n" +
			"|   |   `-- Tree.class\n" +
			"|   `-- test-classes\n" +
			"|       `-- TreeTest.class\n" +
			"|-- build.properties\n" +
			"|-- build.xml\n" +
			"`-- lib\n" +
			"    `-- jsqlparser.jar";
		assertEquals(expected, actual);
	}
}
