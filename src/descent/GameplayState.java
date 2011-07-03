/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package descent;

import descent.engine.component.ImageRenderComponent;
import java.util.ArrayList;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import descent.engine.component.PlayerMovement;
import descent.engine.entity.Entity;

public class GameplayState extends BasicGameState {

    int stateID = 1;
    
    TiledMap map;
    public static int TILESIZE;
    
    private Entity player;
    private Image playerSprite;
    
    private ArrayList<Polygon> collisionBlocks;
    private ArrayList<Rectangle> healthBlocks;
    
    private int widthInTiles;
    private int heightInTiles;
    private int topOffsetInTiles;
    private int leftOffsetInTiles;
    

    GameplayState() {
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gc.setShowFPS(false);
        map = new TiledMap("levels/des2.tmx");
        collisionBlocks = new ArrayList<Polygon>();
        setHealthBlocks(new ArrayList<Rectangle>());
        TILESIZE = map.getTileHeight();
        generateCollisionBlocks(map);
        generateSpikeBlocks(map);
        playerSprite = new Image("sprites/player.png");
        
        player = new Entity(sbg);
        player.AddComponent(new ImageRenderComponent("ImageRender", playerSprite));
        player.AddComponent(new PlayerMovement("PlayerMovement"));
//        player.setCollisionPoly(new Polygon(new float[]{0,0,10,0,10,14,0,14}));
        player.setCollisionPoly(new Polygon(new float[]{0,0,9,0,9,11,0,11}));
        player.setPosition(new Vector2f(3*8,2*8));
        player.getCollisionPoly().setLocation(player.getPosition());
        
        
        widthInTiles = gc.getWidth() / TILESIZE;
        heightInTiles = gc.getHeight() / TILESIZE;
        topOffsetInTiles = heightInTiles / 2;
        leftOffsetInTiles = widthInTiles / 2;
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
        
        map.render(0,0);
        
//        // draw the appropriate section of the tilemap based on the centre (hence the -(TANK_SIZE/2)) of
//        // the player
//        int playerTileX = (int) player.getTilePosition().x;
//        int playerTileY = (int) player.getTilePosition().y;
//
//        // caculate the offset of the player from the edge of the tile. As the player moves around this
//        // varies and this tells us how far to offset the tile based rendering to give the smooth
//        // motion of scrolling
//        int playerTileOffsetX = (int) ((playerTileX - (player.getPosition().x/TILESIZE)) * TILESIZE);
//        int playerTileOffsetY = (int) ((playerTileY - (player.getPosition().y/TILESIZE)) * TILESIZE);

        // render the section of the map that should be visible. Notice the -1 and +3 which renders
        // a little extra map around the edge of the screen to cope with tiles scrolling on and off
        // the screen
//        map.render(playerTileOffsetX,playerTileOffsetY,
//                playerTileX - leftOffsetInTiles,
//                playerTileY - topOffsetInTiles,
//                widthInTiles, heightInTiles);
        
//        map.render(0,playerTileOffsetY,0,playerTileY - topOffsetInTiles,widthInTiles,heightInTiles+2);
//        
//        
//        gr.translate(0, (gc.getWidth()/2) -(int)(player.getPosition().y));

        // draw other entities here if there were any
        player.render(gc, sbg, gr);
//        for(Rectangle block:getSpikeBlocks()) {
//            gr.setColor(Color.green);
//            gr.draw(block);
//        }
//        for(Polygon block:collisionBlocks) {
//            gr.setColor(Color.yellow);
//            gr.draw(block);
//        }
        
//        gr.resetTransform();
        
        if(player.isDead()) {
            gr.setColor(Color.white);
            gr.fillRect(18,0, 180, 40);
            gr.setColor(Color.red);
            gr.drawString("GAMEOVER",18,0);
            gr.drawString("Press R to restart",18,20);
        }
        
        
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input i = gc.getInput();
        
        if(i.isKeyPressed(Input.KEY_R)) {
            gc.reinit();
        }
        if(!player.isDead()) {
            player.update(gc, sbg, delta);
        }
    }
    
    
    private void generateCollisionBlocks(TiledMap map) {
        
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int tileID = map.getTileId(x, y, 1);

                String value = map.getTileProperty(tileID, "Type", "false");
                if ("collision".equals(value)) {
                    int squareX = x*TILESIZE;
                    int squareY = y*TILESIZE;
                    collisionBlocks.add(new Polygon(new float[]{squareX,squareY,
                        squareX+TILESIZE,squareY,squareX+TILESIZE,squareY+TILESIZE,squareX,squareY+TILESIZE}));
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
                    int squareX = x*TILESIZE;
                    int squareY = y*TILESIZE;
                    getSpikeBlocks().add(new Rectangle(squareX, squareY+6, 9, 2));
                }
            }
        }
    }

    /**
     * @return the collisionBlocks
     */
    public ArrayList<Polygon> getCollisionBlocks() {
        return collisionBlocks;
    }

    /**
     * @return the healthBlocks
     */
    public ArrayList<Rectangle> getSpikeBlocks() {
        return healthBlocks;
    }

    /**
     * @param healthBlocks the healthBlocks to set
     */
    public void setHealthBlocks(ArrayList<Rectangle> healthBlocks) {
        this.healthBlocks = healthBlocks;
    }
    
    

}