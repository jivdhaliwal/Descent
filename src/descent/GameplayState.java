package descent;

import descent.engine.CollisionBlocks;
import descent.engine.LevelLoader;
import descent.engine.component.*;
import descent.engine.entity.Entity;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
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
        background = new Image("levels/backgrounds/background1.png");
        TILESIZE = map.getTileHeight();
        CollisionBlocks.getInstance().setMap(map);
        playerSprite = new Image("sprites/player.png");

        player = new Entity();
        player.setCollisionPoly(new Polygon(new float[]{0, 0, 10, 0, 10, 13, 0, 13}));
        player.setPosition(CollisionBlocks.getInstance().getStartPoint());
        player.getCollisionPoly().setLocation(player.getPosition());
        player.AddComponent(new ImageRenderComponent("ImageRender", playerSprite));
        player.AddComponent(new PlayerMovement("PlayerMovement"));
        player.AddComponent(new PlayerCheckPoint("PlayerCheckPoint"));

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
        background.draw();
        map.render(0,0,2,2,64*10,48*10,1,true);
        player.render(gc, sbg, gr);
        
//
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
        
        
        if(player.getCollisionPoly().intersects(CollisionBlocks.getInstance().getEndPoint())) {
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