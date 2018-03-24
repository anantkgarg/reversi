package per.game.reversi;

public enum NodeValue {
	X("X"),
	O("O"),
	B("-");
	
	private String value;
	
	NodeValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
