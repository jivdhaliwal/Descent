package descent.engine;

import descent.GameplayState;
import java.util.ArrayList;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * Singleton that holds all the collision blocks
 * Walls , spikes and checkpoints
 * 
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */
public class CollisionBlocks {

    private static CollisionBlocks collisionBlocks = null;
    private TiledMap map;
    
    private ArrayList<Polygon> wallBlocks;
    private ArrayList<Rectangle> spikeBlocks;
    private ArrayList<Rectangle> checkpoints;
    private final int TILESIZE;
    private Vector2f startPoint;
    
    // Offset to compensate for resized map
    private int mapOffset = - (2*GameplayState.TILESIZE);

    private CollisionBlocks() {
        this.TILESIZE = GameplayState.TILESIZE;
        wallBlocks = new ArrayList<Polygon>();
        spikeBlocks = new ArrayList<Rectangle>();
        checkpoints = new ArrayList<Rectangle>();
    }

    public static CollisionBlocks getInstance() {
        if (collisionBlocks == null) {
            collisionBlocks = new CollisionBlocks();
        }
        return collisionBlocks;
    }

    private void generateWallBlocks(TiledMap map) {

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int tileID = map.getTileId(x, y, 1);

                String value = map.getTileProperty(tileID, "Type", "false");
                if ("collision".equals(value)) {
                    int squareX = (x * TILESIZE)+mapOffset;
                    int squareY = (y * TILESIZE)+mapOffset;
                    wallBlocks.add(new Polygon(new float[]{squareX, squareY,
                                squareX + TILESIZE, squareY, 
                                squareX + TILESIZE, squareY + TILESIZE, 
                                squareX, squareY + TILESIZE}));
                }
            }
        }

    }

    private void generateSpikeBlocks(TiledMap map) {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int tileID = map.getTileId(x, y, 1);

                String value = map.getTileProperty(tileID, "Type", "false");
                if ("spike".equals(value)) {
                    int squareX = (x * TILESIZE)+mapOffset;
                    int squareY = (y * TILESIZE)+mapOffset;
                    spikeBlocks.add(new Rectangle(squareX+2, squareY+1,6, 6));
                }
            }
        }
    }

    private void generateCheckpoints(TiledMap map) {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int tileID = map.getTileId(x, y, 1);

                String value = map.getTileProperty(tileID, "Type", "false");
                if ("checkpoint".equals(value)) {
                    int squareX = (x * TILESIZE)+mapOffset;
                    int squareY = (y * TILESIZE)+mapOffset;
                    checkpoints.add(new Rectangle(squareX + 4, squareY-4, 2, 12));
                }
            }
        }
    }
    
    private void getStartPoint(TiledMap map) {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int tileID = map.getTileId(x, y, 0);

                String value = map.getTileProperty(tileID, "Type", "false");
                if ("start".equals(value)) {
                    int squareX = (x * TILESIZE)+mapOffset;
                    int squareY = (y * TILESIZE)+mapOffset;
                    startPoint = new Vector2f(squareX,squareY);
                    break;
                }
            }
        }
    }

    /**
     * @param map the map to set
     */
    public void setMap(TiledMap map) {
        this.map = map;
        generateWallBlocks(map);
        generateSpikeBlocks(map);
        generateCheckpoints(map);
        getStartPoint(map);
    }

    /**
     * @return the wallBlocks
     */
    public ArrayList<Polygon> getWallBlocks() {
        return wallBlocks;
    }

    /**
     * @return the spikeBlocks
     */
    public ArrayList<Rectangle> getSpikeBlocks() {
        return spikeBlocks;
    }

    /**
     * @return the checkpoints
     */
    public ArrayList<Rectangle> getCheckpoints() {
        return checkpoints;
    }

    /**
     * @return the startPoint
     */
    public Vector2f getStartPoint() {
        return startPoint;
    }
}
