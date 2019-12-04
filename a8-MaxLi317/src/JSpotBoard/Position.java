package JSpotBoard;

import a8.Model;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position[] getAround() {
        return new Position[]{
                new Position(x + 1, y + 1),
                new Position(x + 1, y),
                new Position(x + 1, y - 1),

                new Position(x, y + 1),
                new Position(x, y - 1),

                new Position(x - 1, y + 1),
                new Position(x - 1, y),
                new Position(x - 1, y - 1),
        };
    }

    public static Position Parse(String s) {
        String[] xAndY = s.split(",");
        if (xAndY.length != 2)
            throw new IllegalArgumentException();

        int x = Integer.parseInt(xAndY[0]);
        int y = Integer.parseInt(xAndY[1]);

        Position position = new Position(x,y);
        if (!Model.inBounds(position))
            throw new IllegalArgumentException();

        return position;
    }
}
