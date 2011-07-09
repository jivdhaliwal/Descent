/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package descent;

import descent.engine.CollisionBlocks;
import descent.engine.LevelLoader;
import descent.engine.component.ImageRenderComponent;
import descent.engine.component.PlayerCheckPoint;
import java.util.ArrayList;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import descent.engine.component.PlayerMovement;
import descent.engine.entity.Entity;
import java.util.logging.Level;

public class GameplayState extends BasicGameState {

    int stateID = 1;
    private TiledMap map;
    int currentWorld, currentLevel;
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
    
    //Particle System vars
//    private ParticleSystem particleSys;
//    private ConfigurableEmitter smokeEmitter;
//    PlayerCheckPoint checkpointComp;
    
    int emitterPosCounter;
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

        player = new Entity(sbg);
        player.AddComponent(new ImageRenderComponent("ImageRender", playerSprite));
        player.AddComponent(new PlayerMovement("PlayerMovement"));
//        player.setCollisionPoly(new Polygon(new float[]{0,0,10,0,10,14,0,14}));
        player.setCollisionPoly(new Polygon(new float[]{0, 0, 11, 0, 11, 14, 0, 14}));
        player.setPosition(CollisionBlocks.getInstance().getStartPoint());
        player.getCollisionPoly().setLocation(player.getPosition());
        player.AddComponent(new PlayerCheckPoint("PlayerCheckPoint"));


        widthInTiles = gc.getWidth() / TILESIZE;
        heightInTiles = gc.getHeight() / TILESIZE;
        topOffsetInTiles = heightInTiles / 2;
        leftOffsetInTiles = widthInTiles / 2;

//        particleSys = new ParticleSystem(image);
//        checkpointComp = (PlayerCheckPoint) player.getComponent("PlayerCheckPoint");
        
//        try {
//            File xmlFile = new File("res/emitters/smoke.xml");
//            smokeEmitter = ParticleIO.loadEmitter(xmlFile);
//        } catch (Exception e) {
//            System.out.println("Exception: " + e.getMessage());
//            e.printStackTrace();
//            System.exit(0);
//        }
//
//        particleSys.addEmitter(smokeEmitter);

        gc.setMinimumLogicUpdateInterval(15);
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
//        for(Rectangle block:CollisionBlocks.getInstance().getCheckpoints()) {
//            gr.setColor(Color.blue);
//            gr.draw(block);
//        }
//        
//        gr.setColor(Color.red);
//        gr.draw(CollisionBlocks.getInstance().getEndPoint());

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        emitterPosCounter-=delta;
        
//        particleSys.update(delta);
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
//        if(emitterPosCounter<0){
//            if (checkpointComp.getCurrentCheckpoint() != null) {
//                smokeEmitter.setPosition(
//                        checkpointComp.getCurrentCheckpoint().getX() - 2,
//                        checkpointComp.getCurrentCheckpoint().getY() - 4);
//            }
//            emitterPosCounter=100;
//        }

    }

    
}