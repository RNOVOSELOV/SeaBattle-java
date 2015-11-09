import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Роман on 07.11.2015.
 */
public class Computer extends Player {
    protected static int count;
    ArrayList<Point> currentFiredShipCoordinates;

    private enum WORLD_SIDE {NORTH, SOUTH, EAST, WEST;}

    static {
        count = 1;
    }

    public Computer() {
        destroyedShipsCount = 0;
        currentFiredShipCoordinates = new ArrayList<Point>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName() {
        name = "Компьютер - " + count++;
    }

    @Override
    public void playerDestroyShip() {
        destroyedShipsCount++;
    }

    @Override
    public int getDestroyedShipsCount() {
        return destroyedShipsCount;
    }

    @Override
    public Point getShoot() {
        Point p = new Point();
        if (currentFiredShipCoordinates.size() == 0) {
            p.setX(getRandomCoordinate('X'));
            p.setY(getRandomCoordinate('Y'));
        } else {
            p = getNextShipPoint();
        }
        return p;
    }

    private int getRandomCoordinate(char ch) {
        Random rnd = new Random();
        rnd.nextInt();
        return rnd.nextInt(Field.SIZE);
    }

    private Point getNextShipPoint() {
        ArrayList<WORLD_SIDE> ws = new ArrayList<WORLD_SIDE>();
        if (currentFiredShipCoordinates.size() == 1) {
            if (currentFiredShipCoordinates.get(0).getX() != 0) {
                ws.add(WORLD_SIDE.WEST);
            }
            if (currentFiredShipCoordinates.get(0).getX() != Field.SIZE - 1) {
                ws.add(WORLD_SIDE.EAST);
            }
            if (currentFiredShipCoordinates.get(0).getY() != 0) {
                ws.add(WORLD_SIDE.NORTH);
            }
            if (currentFiredShipCoordinates.get(0).getY() != Field.SIZE - 1) {
                ws.add(WORLD_SIDE.SOUTH);
            }
        } else {
            if (opponentShipIsHorizontal()) {
                if (getMinXCoordinate() != 0) {
                    ws.add(WORLD_SIDE.WEST);
                }
                if (getMaxXCoordinate() != Field.SIZE) {
                    ws.add(WORLD_SIDE.EAST);
                }
            } else {
                if (getMinYCoordinate() != 0) {
                    ws.add(WORLD_SIDE.NORTH);
                }
                if (getMaxYCoordinate() != Field.SIZE) {
                    ws.add(WORLD_SIDE.SOUTH);
                }
            }
        }
        WORLD_SIDE sd = getRandomWorldSide(ws);
        Point p = new Point();
        int x = 0;
        int y = 0;
        switch (sd) {
            case NORTH:
                x = getMinXCoordinate();
                y = getMinYCoordinate() - 1;
                break;
            case SOUTH:
                x = getMinXCoordinate();
                y = getMinYCoordinate() + 1;
                break;
            case WEST:
                x = getMinXCoordinate() - 1;
                y = getMinYCoordinate();
                break;
            case EAST:
                x = getMinXCoordinate() + 1;
                y = getMinYCoordinate();
                break;
        }
        p.setX(x);
        p.setY(y);
        return p;
    }

    private WORLD_SIDE getRandomWorldSide(ArrayList<WORLD_SIDE> ws) {
        Random rnd = new Random();
        rnd.nextInt();
        return ws.get(rnd.nextInt(ws.size()));
    }

    private boolean opponentShipIsHorizontal() {
        if (currentFiredShipCoordinates.get(0).getX() == currentFiredShipCoordinates.get(1).getX()) {
            return false;
        }
        return true;
    }

    private int getMinXCoordinate() {
        int minX = Field.SIZE - 1;
        for (Point c : currentFiredShipCoordinates) {
            if (c.getX() < minX) {
                minX = c.getX();
            }
        }
        return minX;
    }

    private int getMaxXCoordinate() {
        int maxX = 0;
        for (Point c : currentFiredShipCoordinates) {
            if (c.getX() > maxX) {
                maxX = c.getX();
            }
        }
        return maxX;
    }

    private int getMinYCoordinate() {
        int minY = Field.SIZE - 1;
        for (Point c : currentFiredShipCoordinates) {
            if (c.getY() < minY) {
                minY = c.getY();
            }
        }
        return minY;
    }

    private int getMaxYCoordinate() {
        int maxY = 0;
        for (Point c : currentFiredShipCoordinates) {
            if (c.getY() > maxY) {
                maxY = c.getY();
            }
        }
        return maxY;
    }
}
