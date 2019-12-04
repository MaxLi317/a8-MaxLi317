package a8;

import JSpotBoard.Position;
import JSpotBoard.Spot;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Model {
    static int width = 25;
    static int height = 25;
    static boolean torus = false;
    static boolean[][] cells = new boolean[width][height];
    static int low = 2;
    static int high = 3;
    static Supplier<Stream<Position>> allPositions=() -> IntStream.range(0, width)
                .mapToObj(x -> IntStream.range(0, height).mapToObj(y -> new Position(x, y)))
                .flatMap(positionStream -> positionStream);

    static void nextState() {
        boolean[][] nextCells=new boolean[width][height];

        allPositions.get()
            .forEach(position -> {
                boolean cellIsAlive = getCellAlive(position);

                long aliveCell = Stream.of(position.getAround())
                        .map(Model::torus)
                        .filter(Model::inBounds)
                        .map(Model::getCellAlive)
                        .filter(cell1 -> cell1)
                        .count();

                if (cellIsAlive && aliveCell >= low && aliveCell <= high || !cellIsAlive && aliveCell == high)
                    nextCells[position.x][position.y] = true;
            });

        cells = nextCells;
    }

    private static Position torus(Position position) {
        if (!torus) return position;
        if (position.x < 0) position.x = width - 1;
        if (position.y < 0) position.y = height - 1;
        if (position.x == width) position.x = 0;
        if (position.y == height) position.y = 0;

        return position;
    }


    public static boolean inBounds(Position position) {
        if (position.x < 0 || position.y < 0 || position.x >= width || position.y >= height)
            return false;
        return true;
    }

    static boolean getCellAlive(Position position) {
        return getCellAlive(position.x,position.y);
    }

    public static boolean getCellAlive(Spot s) {
       return getCellAlive(s.getSpotX(),s.getSpotY());
    }
    public static boolean getCellAlive(int x,int y) {
       return cells[x][y];
    }

    public static void setCellAlive(Spot s,boolean isAlive) {
        setCellAlive(s.getSpotX(),s.getSpotY(),isAlive);
    }

    private static void setCellAlive(int x, int y,boolean isAlive) {
        cells[x][y] = isAlive;
    }
}
