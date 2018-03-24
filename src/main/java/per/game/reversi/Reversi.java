package per.game.reversi;

import java.util.Scanner;

public class Reversi {

	private GameBoard board;
	private Player player1, player2;
	private int turn;

	public Reversi() {
		board = new GameBoard();
		player1 = new Player(NodeValue.X);
		player2 = new Player(NodeValue.O);
		turn = 0;
	}

	public static void main(String[] args) {
		Reversi game = new Reversi();
		game.start();
	}

	public void start() {
		board.display();
		Scanner scan = new Scanner(System.in);
		scan.useDelimiter("\\r\\n");

		do {
			// 1. Decide on the turns.
			Player player = (turn % 2 == 0) ? player1 : player2;
			turn++;
			
			if(!board.hasValidMovesForNode(player.getNode())) {
				System.out.println(String.format("No valid moves left for player '%s'", player.getPlayerMark()));
				continue;
			}
			// 2. Read player input
			String nodeName = null;
			do {
				nodeName = getUserInput(player, scan);
			}
			// 3. Validate input
			while (!board.validate(nodeName, NodeValue.valueOf(player.getPlayerMark()), false));
			// 4. Make move and flips.
			board.mark(nodeName, NodeValue.valueOf(player.getPlayerMark()), player,
						(player == player1 ? player2 : player1));
			// 5. Display and then repeat from Step 2 for another player.
			board.display();
		} while (!board.isFinished());
		scan.close();

		System.out.println("No further moves");
		System.out.println(String.format("Player '%s' wins. ( %d  Vs %d)",
				player1.getCount() > player2.getCount() ? player1.getPlayerMark() : player2.getPlayerMark(),
				player1.getCount(), player2.getCount()));
	}

	private String getUserInput(Player player, Scanner scan) {
		System.out.print(String.format("['%s' - %d] ['%s' - %d] Player '%s' move: ", player1.getPlayerMark(),
				player1.getCount(), player2.getPlayerMark(), player2.getCount(), player.getPlayerMark()));
		String input = null;
		input = scan.next();
		scan.nextLine();
		return input;
	}
}
