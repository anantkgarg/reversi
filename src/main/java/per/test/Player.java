package per.test;

public class Player {
	
	private NodeValue playerMark;
	private int count;
	
	public Player(NodeValue marker) {
		playerMark = marker;
		count = 2;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void adjustCount(int diff) {
		this.count += diff;
	}
	
	public String getPlayerMark() {
		return playerMark.getValue();
	}
	
	public NodeValue getNode() {
		return playerMark;
	}
}
