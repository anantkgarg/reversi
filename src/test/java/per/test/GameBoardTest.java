package per.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameBoardTest {

	private GameBoard board;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		board = new GameBoard();
	}

	@Test
	public void testGetNodeByName() {
		Node node = board.getNodeByName("4D", false);
		assertNotNull("Node is null", node);
		assertEquals("Nodes not equal", node, new Node(NodeValue.O, 3, 3));
		
		node = board.getNodeByName("4E", false);
		assertNotNull("Node is null", node);
		assertEquals("Nodes not equal", node, new Node(NodeValue.X, 3, 4));
		
		node = board.getNodeByName("2B", false);
		assertNotNull("Node is null", node);
		assertEquals("Nodes not equal", node, new Node(NodeValue.B, 1, 1));
	}

	@Test
	public void testValidate() {
		assertTrue("Starting position validation failed.", board.validate("3D", NodeValue.X, true));
		assertTrue("Starting position validation failed.", board.validate("3E", NodeValue.O, true));
		assertFalse("Starting position validation failed.", board.validate("3C", NodeValue.X, true));
		assertFalse("Starting position validation failed.", board.validate("3D", NodeValue.O, true));
	}

}
