package descent;

import descent.engine.LevelLoader;
import descent.engine.ResourceManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */
public class MainMenuState extends BasicGameState implements ComponentListener{
    
    private int stateID = 0;
    
    private StateBasedGame game;
    
    private int worldY = 317;
    private int world1X = 205;
    private int world2X = 269;
    private int world3X = 331;
    private int world4X = 397;
    
    private Image background,world1,world2,world3,world4; 
    
    private MouseOverArea world1Area,world2Area,world3Area,world4Area;

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gc.setShowFPS(false);
        this.game = sbg;
        
        background = ResourceManager.getInstance().getMenuBackground();
        world1 = ResourceManager.getInstance().getWorld1();
        world2 = ResourceManager.getInstance().getWorld2();
        world3 = ResourceManager.getInstance().getWorld3();
        world4 = ResourceManager.getInstance().getWorld4();
        
        world1Area = new MouseOverArea(gc, world1, world1X, worldY,this);
        world2Area = new MouseOverArea(gc, world2, world2X, worldY,this);
        world3Area = new MouseOverArea(gc, world3, world3X, worldY,this);
        world4Area = new MouseOverArea(gc, world4, world4X, worldY,this);
        
        world1Area.setMouseOverColor(Color.lightGray);
//        world2Area.setMouseOverColor(Color.black);
//        world3Area.setMouseOverColor(Color.black);
//        world4Area.setMouseOverColor(Color.black);
        
        //Init LevelLoader early to reduce stutter when first map loads
        LevelLoader.getInstance();
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        background.draw();
        
        world1Area.render(gc, grphcs);
        world2Area.render(gc, grphcs);
        world3Area.render(gc, grphcs);
        world4Area.render(gc, grphcs);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    }

    @Override
    public void componentActivated(AbstractComponent source) {
        if(source == world1Area) {
            GameplayState gameplaystate = new GameplayState();
            gameplaystate.setLevel(0, 0);
            game.addState(gameplaystate);
            game.enterState(Descent.GAMEPLAYSTATE);
        }
        if(source == world2Area) {
            
        }
        if(source == world3Area) {
            
        }
        if(source == world4Area) {
            
        }
    }
    
}
