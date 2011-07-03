package descent.engine;

import descent.GameplayState;
import java.util.ArrayList;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
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
                    int squareX = x * TILESIZE;
                    int squareY = y * TILESIZE;
                    wallBlocks.add(new Polygon(new float[]{squareX, squareY,
                                squareX + TILESIZE, squareY, squareX + TILESIZE, squareY + TILESIZE, squareX, squareY + TILESIZE}));
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
                    int squareX = x * TILESIZE;
                    int squareY = y * TILESIZE;
                    spikeBlocks.add(new Rectangle(squareX, squareY + 6, 9, 2));
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
                    int squareX = x * TILESIZE;
                    int squareY = y * TILESIZE;
                    checkpoints.add(new Rectangle(squareX + 4, squareY-4, 2, 12));
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
}
