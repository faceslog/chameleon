package cameleon;

public class Board {

	private final int size;
	private final Integer[][] squares;
	private final Game gameRef;

	public Board(int n, Game _gameRef)
	{
		size = (int) (3 * Math.pow(2, n));
		squares = new Integer[size][size];
		gameRef = _gameRef;
	}

	public Board(int _size, Integer[][] _squares, Game _gameRef)
	{
		size = _size;
		squares = _squares;
		gameRef = _gameRef;
	}

	public boolean isFull()
	{
		return (gameRef.getPlayer1().getNumberSquare() + gameRef.getPlayer2().getNumberSquare()) == (size * size);
	}

	public void showGrid()
	{
		System.out.print("\t\t");
		for(int k = 0; k < size; k++)
			System.out.print("\t" + k + "\t");

		System.out.println();
		for(int i = 0; i < size; i++) //y
		{
			System.out.print("\t" + i + "\t");

			for(int j = 0; j < size; j++) //x
			{
				Integer squareId = squares[j][i];

				if(gameRef.getPlayer1().getPlayerId() == squareId)
					System.out.print(Globals.ANSI_RED +"\tR\t" + Globals.ANSI_RESET);
				else if(gameRef.getPlayer2().getPlayerId() == squareId)
					System.out.print(Globals.ANSI_BLUE + "\tB\t" + Globals.ANSI_RESET);
				else
					System.out.print("\t⊡\t");
			}
			System.out.println();
		}
	}

	public int getSize() {
		return size;
	}

	public Integer[][] getSquares() {	return squares;	}

	public boolean doesSquareExist(int x, int y)
	{
		return ((x >= 0) && (x < size) && (y >= 0) && (y < size));
	}

	// TO FIX: Maybe move it to another class ?? We'll decide later
	public void nextMove(int x, int y)//update color si la case est deja d'une couleur - check 8 cases autour
	{
		if (squares[x][y] == Globals.FREE_SQUARE)
		{
			squares[x][y] = gameRef.getCurrent().getPlayerId();
			gameRef.getCurrent().setNumberSquare(gameRef.getCurrent().getNumberSquare() + 1);
			updateColorBrave(x, y);
		}
	}

	private void updateColorBrave(int x, int y)
	{
		for (int i = x - 1; i <= y + 1; i++)
		{
			if(i < 0 || i >= size) continue;

			for (int j = y - 1; j <= y+ 1; j++)
			{
				if(j < 0 || j >= size) continue;

				if(squares[i][j] != Globals.FREE_SQUARE)
				{
					if(squares[i][j] == gameRef.getNotCurrent().getPlayerId())
					{
						gameRef.getNotCurrent().setNumberSquare(gameRef.getNotCurrent().getNumberSquare() - 1);
						gameRef.getCurrent().setNumberSquare(gameRef.getCurrent().getNumberSquare() + 1);
						squares[i][j] = gameRef.getCurrent().getPlayerId();
					}
				}
			}
		}
	}
}