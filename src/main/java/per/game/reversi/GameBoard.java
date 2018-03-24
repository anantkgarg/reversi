package per.game.reversi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameBoard {
	
	private enum RowColIdentifier {ROW, COL;};
	
	private Node[][] board;
	private int nodesFilled;
	
	private List<Node> validX, validO;
	private List<String> allNodeNames;
	
	public GameBoard() {
		init();
		setStartPosition();
		calculateValidNodes();
		nodesFilled = 4;
	}

	private void setStartPosition() {
		getNodeByName("D4", true).setValue(NodeValue.O);
		getNodeByName("5E", true).setValue(NodeValue.O);
		getNodeByName("D5", true).setValue(NodeValue.X);
		getNodeByName("4E", true).setValue(NodeValue.X);
		
		calculateValidNodes();
	}

	private void calculateValidNodes() {
		validO.clear();
		validX.clear();
		for(String name : allNodeNames) {
			
			if(validate(name, NodeValue.X, true))
				validX.add(getNodeByName(name, false));
			
			if(validate(name, NodeValue.O, true))
				validO.add(getNodeByName(name, false));
		}
	}

	private void init() {
		validX = new ArrayList<>();
		validO = new ArrayList<>();
		allNodeNames = new ArrayList<>();
		board = new Node[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				board[i][j]=new Node(NodeValue.B, i, j);
				allNodeNames.add(board[i][j].getName());
			}
		}
	}
	
	private int getInt(String nodeName, RowColIdentifier identifier) {
		Pattern colPattern = Pattern.compile("[a-hA-H]");
		Pattern rowPattern = Pattern.compile("\\d");
		
		Matcher colMatcher = colPattern.matcher(nodeName);
		Matcher rowMatcher = rowPattern.matcher(nodeName);
		
		switch (identifier ) {
		case ROW:
			if(rowMatcher.find()) 
				return Integer.parseInt(rowMatcher.group())-1;
		case COL:
			if(colMatcher.find())
				if(BoardColumns.valueOf(colMatcher.group().toUpperCase())!= null) 
					return BoardColumns.valueOf(colMatcher.group().toUpperCase()).ordinal();
		default: return -1;
		}
	}
	
	public Node getNodeByName(String nodeName, boolean updateNamesList) {
		Objects.requireNonNull(nodeName, "Nodename to get Node cannot be NULL");
		
		int row = getInt(nodeName, RowColIdentifier.ROW);
		if(row >=0 && row <8) {
			int col = getInt(nodeName, RowColIdentifier.COL);
			if(col >=0 && col < 8) {
				if(updateNamesList) {
					allNodeNames.remove(board[row][col].getName());
				}
				return board[row][col];
			}
		}
		return null;
	}
	
	public void display() {
		System.out.println("  A  B  C  D  E  F  G  H");
		for(int i=0;i<8;i++) {
			System.out.print((i+1));
			for(int j=0;j<8;j++) {
				System.out.print(board[i][j]);
			}
			System.out.println("");
		}
	}

	public boolean validate(String nodeName, NodeValue nodeValue, boolean isCalculating) {
		if(nodeName != null) {
			if(nodeName.equals("PASS")) {
				return true;
			}
			if(getNodeByName(nodeName, false) != null) {
				if(getNodeByName(nodeName, false).getValue()==NodeValue.B) {
					int row = getInt(nodeName, RowColIdentifier.ROW);
					int col = getInt(nodeName, RowColIdentifier.COL);
					if(checkUpAndAcross(row, col, nodeValue, null) || 
							checkDownAndAcross(row, col, nodeValue, null) || 
							checkRightAndAcross(row, col, nodeValue, null) || 
							checkLeftAndAcross(row, col, nodeValue, null))
						return true;
				}
			}
		}
		if(!isCalculating)
			System.out.println("Incorrect move. Please re-enter.");
		return false;
	}

	private boolean checkLeftAndAcross(int row, int col, NodeValue nodeValue, ArrayList<String> nodeNames) {
		boolean sameNode = false; 
		boolean otherNode = false;
		boolean sameNodeAcross = false; 
		boolean otherNodeAcross = false;
		List<String> nodes = new ArrayList<>();
		
		int i=col-1;
		for(;i>=0;i--) {
			Node node = board[row][i];
			if(node.getValue() != NodeValue.B) {
				if(node.getValue() != nodeValue) {
					otherNode = true;
					nodes.add(node.getName());
				}
				else if(node.getValue() == nodeValue) {
					if(otherNode)
						sameNode = true;
					break;
				}
			} else 
				break;
		}
		if(sameNode && otherNode && nodeNames != null) {
			nodeNames.addAll(nodes);
		}
		
		nodes.clear();
		i=row-1;
		int j=col-1;
		for(;i>=0 && j>=0;i--, j--) {
			Node node = board[i][j]; 
			if(node.getValue() != NodeValue.B) {
				if(node.getValue() != nodeValue) {
					otherNodeAcross = true;
					nodes.add(node.getName());
				}
				else if(node.getValue() == nodeValue) {
					if(otherNodeAcross)
						sameNodeAcross = true;
					break;
				}
			} else
				break;
		}
		if(sameNodeAcross && otherNodeAcross && nodeNames != null) {
			nodeNames.addAll(nodes);
		}
		
		return (sameNode && otherNode) || (sameNodeAcross && otherNodeAcross);
	}

	private boolean checkRightAndAcross(int row, int col, NodeValue nodeValue, ArrayList<String> nodeNames) {
		boolean sameNode = false; 
		boolean otherNode = false;
		boolean sameNodeAcross = false; 
		boolean otherNodeAcross = false;
		List<String> nodes = new ArrayList<>();
		
		for(int i=col+1;i<8;i++) {
			Node node = board[row][i];
			if(node.getValue() != NodeValue.B) {
				if(node.getValue() != nodeValue) {
					otherNode = true;
					nodes.add(node.getName());
				}
				else if(node.getValue() == nodeValue) {
					if(otherNode)
						sameNode = true;
					break;
				}
			} else 
				break;
		}
		if(sameNode && otherNode && nodeNames != null) {
			nodeNames.addAll(nodes);
		}
		
		nodes.clear();
		for(int i=row+1, j=col+1;i<8 && j<8;i++, j++) {
			Node node = board[i][j];
			if(node.getValue() != NodeValue.B) {
				if(node.getValue() != nodeValue) {
					otherNodeAcross = true;
					nodes.add(node.getName());
				}
				else if(node.getValue() == nodeValue) {
					if(otherNodeAcross)
						sameNodeAcross = true;
					break;
				}
			} else
				break;
		}
		if(sameNodeAcross && otherNodeAcross && nodeNames != null) {
			nodeNames.addAll(nodes);
		}
		
		return (sameNode && otherNode) || (sameNodeAcross && otherNodeAcross);
	}

	private boolean checkDownAndAcross(int row, int col, NodeValue nodeValue, ArrayList<String> nodeNames) {
		boolean sameNode = false; 
		boolean otherNode = false;
		boolean sameNodeAcross = false; 
		boolean otherNodeAcross = false;
		List<String> nodes = new ArrayList<>();
		
		for(int i=row+1;i<8;i++) {
			Node node = board[i][col];
			if(node.getValue() != NodeValue.B) {
				if(node.getValue() != nodeValue) {
					otherNode = true;
					nodes.add(node.getName());
				}
				else if(node.getValue() == nodeValue) {
					if(otherNode)
						sameNode = true;
					break;
				}
			} else 
				break;
		}
		if(sameNode && otherNode && nodeNames != null) {
			nodeNames.addAll(nodes);
		}
		
		nodes.clear();
		for(int i=row+1, j=col-1;i<8 && j>=0;i++, j--) {
			Node node = board[i][j];
			if(node.getValue() != NodeValue.B) {
				if(node.getValue() != nodeValue) {
					otherNodeAcross = true;
					nodes.add(node.getName());
				}
				else if(node.getValue() == nodeValue) {
					if(otherNodeAcross)
						sameNodeAcross = true;
					break;
				}
			} else 
				break;
		}
		if(sameNodeAcross && otherNodeAcross && nodeNames != null) {
			nodeNames.addAll(nodes);
		}
		
		return (sameNode && otherNode) || (sameNodeAcross && otherNodeAcross);
	}

	private boolean checkUpAndAcross(int row, int col, NodeValue nodeValue, ArrayList<String> nodeNames) {
		boolean sameNode = false; 
		boolean otherNode = false;
		boolean sameNodeAcross = false; 
		boolean otherNodeAcross = false;
		List<String> nodes = new ArrayList<>();
		
		for(int i=row-1;i>=0;i--) {
			Node node = board[i][col];
			if(node.getValue() != NodeValue.B) {
				if(node.getValue() != nodeValue) {
					otherNode = true;
					nodes.add(node.getName());
				}
				else if(node.getValue() == nodeValue) {
					if(otherNode)
						sameNode = true;
					break;
				}
			} else 
				break;
		}
		if(sameNode && otherNode && nodeNames != null) {
			nodeNames.addAll(nodes);
		}
		
		nodes.clear();
		for(int i=row-1, j=col+1;i>=0 && j<8;i--, j++) {
			Node node = board[i][j];
			if(node.getValue() != NodeValue.B) {
				if(node.getValue() != nodeValue) {
					otherNodeAcross = true;
					nodes.add(node.getName());
				}
				else if(node.getValue() == nodeValue) {
					if(otherNodeAcross)
						sameNodeAcross = true;
					break;
				}
			} else
				break;
		}
		if(sameNodeAcross && otherNodeAcross && nodeNames != null) {
			nodeNames.addAll(nodes);
		}
		
		return (sameNode && otherNode) || (sameNodeAcross && otherNodeAcross);
	}

	public void mark(String nodeName, NodeValue nodeValue, Player currentPlayer, Player otherPlayer) {
		int row = getInt(nodeName, RowColIdentifier.ROW);
		int col = getInt(nodeName, RowColIdentifier.COL);
		
		ArrayList<String> nodeNames = new ArrayList<>();
		checkDownAndAcross(row, col, nodeValue, nodeNames);
		checkRightAndAcross(row, col, nodeValue, nodeNames);
		checkUpAndAcross(row, col, nodeValue, nodeNames);
		checkLeftAndAcross(row, col, nodeValue, nodeNames);
		
		if(nodeNames != null && nodeNames.size() > 0) {
			nodeNames.stream().forEach(node -> getNodeByName(node, false).setValue(nodeValue));
			currentPlayer.adjustCount(nodeNames.size());
			otherPlayer.adjustCount(-1 * nodeNames.size());
		}
		
		//Add mark for player and add count.
		getNodeByName(nodeName, true).setValue(nodeValue);
		currentPlayer.adjustCount(1);
		calculateValidNodes();
		nodesFilled++;
	}

	public boolean isFinished() {
		return nodesFilled == 64 && !hasValidMovesForNodes();
	}

	private boolean hasValidMovesForNodes() {
		return hasValidMovesForNode(NodeValue.X) && hasValidMovesForNode(NodeValue.O);
	}

	public boolean hasValidMovesForNode(NodeValue x) {
		return x == NodeValue.O ? !validO.isEmpty() : !validX.isEmpty();
	}
}
