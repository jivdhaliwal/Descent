package descent;

import descent.engine.CollisionBlocks;
import descent.engine.LevelLoader;
import descent.engine.ResourceManager;
import descent.engine.entity.Entity;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class GameplayState extends BasicGameState {

    int stateID = 1;
    
    Entity player;
    
    private TiledMap map;
    int currentWorld, currentLevel;
    public static int TILESIZE;
    private Image playerSprite;
    
    private Image background;
    
    private Image alphamap;
    private Image textureMap;
    

    GameplayState() {
        
    }

    @Override
    public int getID() {
        return 1;
    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        init(gc,sbg);
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gc.setShowFPS(false);
        map = LevelLoader.getInstance().getLevelMap(currentWorld,currentLevel);
        background = ResourceManager.getInstance().getGameBackground();
        TILESIZE = map.getTileHeight();
        CollisionBlocks.getInstance().setMap(map);
        player = CollisionBlocks.getInstance().getPlayer();
//        alphamap = new Image("testdata/alphamap.png");
        alphamap = ResourceManager.getInstance().getAlphamap();
        
        gc.getGraphics().setBackground(Color.black);
        gc.setMinimumLogicUpdateInterval(5);
        gc.setMaximumLogicUpdateInterval(20);
        }

    /**
     * @param map the map to set
     */
    public void setLevel(int worldNum, int levelNum) {
        this.currentWorld = worldNum;
        this.currentLevel = levelNum;
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
        
        gr.clearAlphaMap();
        // write only alpha
//        gr.setDrawMode(Graphics.MODE_ALPHA_MAP);
//        alphamap.drawCentered(player.getPosition().x,player.getPosition().y);
//        gr.setDrawMode(Graphics.MODE_ALPHA_BLEND);
        
        background.draw();
        map.render(0,0,2,2,64*10,48*10,map.getLayerIndex("map"),true);
        player.render(gc, sbg, gr);
        
        for(Entity platform:CollisionBlocks.getInstance().getPlatforms()) {
            platform.render(gc, sbg, gr);
        }
        
        gr.setDrawMode(Graphics.MODE_NORMAL);
//        // Draw collision boxes for debuggins
//        for(Rectangle block:CollisionBlocks.getInstance().getSpikeBlocks()) {
//            gr.setColor(Color.green);
//            gr.draw(block);
//        }
//        for(Polygon block:CollisionBlocks.getInstance().getWallBlocks()) {
//            gr.setColor(Color.yellow);
//            gr.draw(block);
//        }
//        for(Polygon block:CollisionBlocks.getInstance().getOnWallBlocks()) {
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

        if (i.isKeyPressed(Input.KEY_R)) {
            gc.reinit();
        }

        player.update(gc, sbg, delta);
        
        for(Entity platform:CollisionBlocks.getInstance().getPlatforms()) {
            platform.update(gc, sbg, delta);
        }
        
        if(player.getCollisionBox().intersects(CollisionBlocks.getInstance().getEndPoint())) {
            if((currentLevel+1)<LevelLoader.getInstance().getMaxLevels(currentWorld)) {
                GameplayState gameplaystate = new GameplayState();
                gameplaystate.setLevel(currentWorld, currentLevel+1);
                sbg.addState(gameplaystate);
                sbg.enterState(Descent.GAMEPLAYSTATE);
                
            } else {
                sbg.enterState(Descent.MAINMENUSTATE);
            }
        }

    }

    
}