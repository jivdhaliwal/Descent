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
    private ArrayList<Polygon> onWallBlocks;
    private ArrayList<Rectangle> spikeBlocks;
    private ArrayList<Rectangle> checkpoints;
    private final int TILESIZE;
    private Vector2f startPoint;
    private Rectangle endPoint;
    
    // Offset to compensate for resized map
    private int mapOffset = - (2*GameplayState.TILESIZE);
    

    private CollisionBlocks() {
        this.TILESIZE = GameplayState.TILESIZE;
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
                    wallBlocks.add(new Polygon(new float[]{squareX+1, squareY,
                                squareX + TILESIZE, squareY, 
                                squareX + TILESIZE, squareY-1 + TILESIZE, 
                                squareX+1, squareY-1 + TILESIZE}));
                }
            }
        }
    }
    
    // Collision boxes for checking if player is on top of a wall
    private void generateOnWallBlocks(TiledMap map) {

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int tileID = map.getTileId(x, y, 1);

                String value = map.getTileProperty(tileID, "Type", "false");
                if ("collision".equals(value)) {
                    int squareX = (x * TILESIZE)+mapOffset;
                    int squareY = (y * TILESIZE)+mapOffset;
                    onWallBlocks.add(new Polygon(new float[]{squareX+2, squareY-4,
                                squareX-1 + TILESIZE, squareY-4, 
                                squareX-1 + TILESIZE, squareY, 
                                squareX+2, squareY}));
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
                    spikeBlocks.add(new Rectangle(squareX+3, squareY+2,7, 7));
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
                    checkpoints.add(new Rectangle(squareX+1, squareY-6, 10, 15));
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
    
    
    private void getEndPoint(TiledMap map) {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int tileID = map.getTileId(x, y, 0);

                String value = map.getTileProperty(tileID, "Type", "false");
                if ("end".equals(value)) {
                    int squareX = (x * TILESIZE)+mapOffset;
                    int squareY = (y * TILESIZE)+mapOffset;
                    endPoint = new Rectangle(squareX-5, squareY, 20, 10);
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
        wallBlocks = new ArrayList<Polygon>();
        onWallBlocks = new ArrayList<Polygon>();
        spikeBlocks = new ArrayList<Rectangle>();
        checkpoints = new ArrayList<Rectangle>();
        generateWallBlocks(map);
        generateOnWallBlocks(map);
        generateSpikeBlocks(map);
        generateCheckpoints(map);
        getStartPoint(map);
        getEndPoint(map);
    }

    /**
     * @return the wallBlocks
     */
    public ArrayList<Polygon> getWallBlocks() {
        return wallBlocks;
    }
    
    /**
     * @return the onWallBlocks
     */
    public ArrayList<Polygon> getOnWallBlocks() {
        return onWallBlocks;
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

    /**
     * @return the endPoint
     */
    public Rectangle getEndPoint() {
        return endPoint;
    }
}
