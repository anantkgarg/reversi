package per.game.reversi;

public class Node {
	private NodeValue value;
	private String name;
	
	public Node(NodeValue value) {
		this.value = value;
	}
	
	public Node(NodeValue value, int row, int col) {
		this.value = value;
		BoardColumns colName = BoardColumns.values()[col];
		name = (row+1) + colName.name();
	}

	public NodeValue getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}

	public void setValue(NodeValue value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return " " + value.getValue() + " ";
	}
	
}
