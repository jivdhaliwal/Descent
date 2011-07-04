/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package descent;

import descent.engine.CollisionBlocks;
import descent.engine.component.ImageRenderComponent;
import descent.engine.component.PlayerCheckPoint;
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
    private ArrayList<Rectangle> spikeBlocks;
    private ArrayList<Rectangle> checkpoints;
    
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
        map = new TiledMap("levels/des3.tmx");
        TILESIZE = map.getTileHeight();
        CollisionBlocks.getInstance().setMap(map);
        playerSprite = new Image("sprites/player.png");
        
        player = new Entity(sbg);
        player.AddComponent(new ImageRenderComponent("ImageRender", playerSprite));
        player.AddComponent(new PlayerMovement("PlayerMovement"));
//        player.setCollisionPoly(new Polygon(new float[]{0,0,10,0,10,14,0,14}));
        player.setCollisionPoly(new Polygon(new float[]{0,0,9,0,9,11,0,11}));
        player.setPosition(CollisionBlocks.getInstance().getStartPoint());
        player.getCollisionPoly().setLocation(player.getPosition());
        player.AddComponent(new PlayerCheckPoint("PlayerCheckPoint"));
        
        
        widthInTiles = gc.getWidth() / TILESIZE;
        heightInTiles = gc.getHeight() / TILESIZE;
        topOffsetInTiles = heightInTiles / 2;
        leftOffsetInTiles = widthInTiles / 2;
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
        
        map.render(0,0);
        player.render(gc, sbg, gr);
//        int playerTileX = (int) player.getTilePosition().x;
//        int playerTileY = (int) player.getTilePosition().y;
//
//        // caculate the offset of the player from the edge of the tile. As the player moves around this
//        // varies and this tells us how far to offset the tile based rendering to give the smooth
//        // motion of scrolling
//        int playerTileOffsetX = (int) ((playerTileX - (player.getPosition().x/TILESIZE)) * TILESIZE);
//        int playerTileOffsetY = (int) ((playerTileY - (player.getPosition().y/TILESIZE)) * TILESIZE);

//        for(Rectangle block:CollisionBlocks.getInstance().getSpikeBlocks()) {
//            gr.setColor(Color.green);
//            gr.draw(block);
//        }
//        for(Polygon block:CollisionBlocks.getInstance().getWallBlocks()) {
//            gr.setColor(Color.yellow);
//            gr.draw(block);
//        }
//        for(Rectangle block:CollisionBlocks.getInstance().getCheckpoints()) {
//            gr.setColor(Color.blue);
//            gr.draw(block);
//        }
        
        
        
        
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
    
    

}