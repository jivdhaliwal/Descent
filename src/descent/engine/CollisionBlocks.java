package descent.engine;

import descent.GameplayState;
import descent.engine.component.ImageRenderComponent;
import descent.engine.component.PlatformMovement;
import descent.engine.component.PlayerCheckPoint;
import descent.engine.component.PlayerMovement;
import descent.engine.entity.Entity;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
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
    
    private Entity player;
    
    private ArrayList<Polygon> wallBlocks;
    private ArrayList<Polygon> onWallBlocks;
    private ArrayList<Rectangle> spikeBlocks;
    private ArrayList<Rectangle> checkpoints;
    private ArrayList<Entity> platforms;
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
    
    /**
     * @param map the map to set
     */
    public void setMap(TiledMap map) throws SlickException {
        this.map = map;
        wallBlocks = new ArrayList<Polygon>();
        onWallBlocks = new ArrayList<Polygon>();
        spikeBlocks = new ArrayList<Rectangle>();
        checkpoints = new ArrayList<Rectangle>();
        platforms = new ArrayList<Entity>();
        generateStart_EndPoint();
        generatePlayer();
        generateCollisionBlocks();
        generateMovingPlatforms();
        
    }
    
    private void generatePlayer() throws SlickException {
        Image playerSprite = ResourceManager.getInstance().getPlayerSprite();
        player = new Entity();
        getPlayer().setPosition(getStartPoint());
        getPlayer().setCollisionBox(new Rectangle(getPlayer().getPosition().x, 
                getPlayer().getPosition().y, 11,14));
        getPlayer().AddComponent(new ImageRenderComponent("ImageRender", playerSprite));
        getPlayer().AddComponent(new PlayerMovement("PlayerMovement"));
        getPlayer().AddComponent(new PlayerCheckPoint("PlayerCheckPoint"));
    }

    private void generateCollisionBlocks() {

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int tileID = map.getTileId(x, y, map.getLayerIndex("map"));

                String value = map.getTileProperty(tileID, "Type", "false");
                if ("collision".equals(value)) {
                    int squareX = (x * TILESIZE)+mapOffset;
                    int squareY = (y * TILESIZE)+mapOffset;
                    wallBlocks.add(new Polygon(new float[]{squareX+1, squareY,
                                squareX + TILESIZE, squareY, 
                                squareX + TILESIZE, squareY-1 + TILESIZE, 
                                squareX+1, squareY-1 + TILESIZE}));
                    onWallBlocks.add(new Polygon(new float[]{squareX+2, squareY-4,
                                squareX-1 + TILESIZE, squareY-4, 
                                squareX-1 + TILESIZE, squareY, 
                                squareX+2, squareY}));
                } else if ("spike".equals(value)) {
                    int squareX = (x * TILESIZE)+mapOffset;
                    int squareY = (y * TILESIZE)+mapOffset;
                    spikeBlocks.add(new Rectangle(squareX+3, squareY+2,7, 7));
                } else if ("checkpoint".equals(value)) {
                    int squareX = (x * TILESIZE)+mapOffset;
                    int squareY = (y * TILESIZE)+mapOffset;
                    checkpoints.add(new Rectangle(squareX+1, squareY-6, 10, 15));
                }
            }
        }
    }
    
    private void generateMovingPlatforms() throws SlickException {
        Image platformSprite = ResourceManager.getInstance().getPlatformSprite();
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int tileID = map.getTileId(x, y, map.getLayerIndex("platforms"));

                String value = map.getTileProperty(tileID, "Type", "false");
                if ("platform".equals(value)) {
                    int squareX = (x * TILESIZE)+mapOffset;
                    int squareY = (y * TILESIZE)+mapOffset;
                    Entity platform = new Entity();
                    platform.setPosition(new Vector2f(squareX, squareY));
                    platform.setCollisionBox(new Rectangle(squareX, squareY, TILESIZE, TILESIZE));
                    platform.getCollisionBox().setLocation(platform.getPosition());
                    platform.AddComponent(new ImageRenderComponent("ImageRender", platformSprite));
                    platform.AddComponent(new PlatformMovement("PlatformMovement", PlatformMovement.HORI));
                    platforms.add(platform);
                }
            }
        }
    }
    
    private void generateStart_EndPoint() {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int tileID = map.getTileId(x, y, map.getLayerIndex("collision"));

                String value = map.getTileProperty(tileID, "Type", "false");
                if ("start".equals(value)) {
                    int squareX = (x * TILESIZE)+mapOffset;
                    int squareY = (y * TILESIZE)+mapOffset;
                    startPoint = new Vector2f(squareX,squareY);
                    break;
                } else if ("end".equals(value)) {
                    int squareX = (x * TILESIZE)+mapOffset;
                    int squareY = (y * TILESIZE)+mapOffset;
                    endPoint = new Rectangle(squareX-5, squareY, 20, 10);
                    break;
                }
            }
        }
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

    /**
     * @return the platforms
     */
    public ArrayList<Entity> getPlatforms() {
        return platforms;
    }

    /**
     * @return the player
     */
    public Entity getPlayer() {
        return player;
    }
}
