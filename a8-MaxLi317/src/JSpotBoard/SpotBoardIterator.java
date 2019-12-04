package JSpotBoard;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class SpotBoardIterator implements Iterator<Spot> {

	private SpotBoard _board;
	int x;
	int y;
	
	public SpotBoardIterator(SpotBoard board) {
		_board = board;
		x = 0;
		y = 0;
	}

	@Override
	public boolean hasNext() {
		return (y < _board.getSpotHeight());
	}

	@Override
	public Spot next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		Spot s = _board.getSpotAt(x, y);
		if (x < _board.getSpotWidth()-1) {
			x++;
		} else {
			x = 0;
			y++;
		}
		return s;
	}

}
