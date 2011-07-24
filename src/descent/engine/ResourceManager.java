package descent.engine;

import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * Singleton for loading game resources
 * 
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */
public class ResourceManager {
    
    private static ResourceManager resourceLoader = null;
    
    
    private Image world1;
    private Image world2;
    private Image world3;
    private Image world4;
    private Image menuBackground;
    private Image gameBackground;
    
    private Image playerSprite;
    private Image platformSprite;
    private Image alphamap;
    
    private Sound jump;
    private Sound walk;
    private Sound bump;
    private Sound checkpoint;
    private Sound die;
    private Sound walk_l;
    private Sound walk_r;
    
    private Music track1;
    
    private ResourceManager() throws SlickException {
        loadResources();
    }
    
    public static ResourceManager getInstance() throws SlickException {
        if(resourceLoader==null) {
            resourceLoader = new ResourceManager();
        }
        return resourceLoader;
    }
    
    private void loadResources() throws SlickException {
        
        // Load Menu Res
        menuBackground = new Image("levels/backgrounds/menu/mainmenu.png");
        world1 = new Image("levels/backgrounds/menu/world1.png");
        world2 = new Image("levels/backgrounds/menu/world2Disabled.png");
        world3 = new Image("levels/backgrounds/menu/world3Disabled.png");
        world4 = new Image("levels/backgrounds/menu/world4Disabled.png");
        
        // Load Game Res
        gameBackground = new Image("levels/backgrounds/background1.png");
        playerSprite = new Image("sprites/player.png",false,Image.FILTER_NEAREST);
        platformSprite = new Image("sprites/platform.png");
        alphamap = new Image("sprites/alphamap.png");
        
        // Load Sound
        jump = new Sound("audio/effects/jump.wav");
        walk = new Sound("audio/effects/walk.wav");
        bump = new Sound("audio/effects/bump.wav");
        die = new Sound("audio/effects/die.wav");
        checkpoint = new Sound("audio/effects/checkpoint.wav");
        
        // Load Music
//        track1 = new Music("audio/music/track1.aiff");
        
        
    }

    /**
     * @return the world1
     */
    public Image getWorld1() {
        return world1;
    }

    /**
     * @return the world2
     */
    public Image getWorld2() {
        return world2;
    }

    /**
     * @return the world3
     */
    public Image getWorld3() {
        return world3;
    }

    /**
     * @return the world4
     */
    public Image getWorld4() {
        return world4;
    }

    /**
     * @return the menuBackground
     */
    public Image getMenuBackground() {
        return menuBackground;
    }

    /**
     * @return the gameBackground
     */
    public Image getGameBackground() {
        return gameBackground;
    }

    /**
     * @return the playerSprite
     */
    public Image getPlayerSprite() {
        return playerSprite;
    }

    /**
     * @return the platformSprite
     */
    public Image getPlatformSprite() {
        return platformSprite;
    }

    /**
     * @return the jump
     */
    public Sound getJump() {
        return jump;
    }

    /**
     * @return the walk
     */
    public Sound getWalk() {
        return walk;
    }

    /**
     * @return the bump
     */
    public Sound getBump() {
        return bump;
    }

    /**
     * @return the die
     */
    public Sound getDie() {
        return die;
    }

    /**
     * @return the alphamap
     */
    public Image getAlphamap() {
        return alphamap;
    }

    /**
     * @return the track1
     */
    public Music getTrack1() {
        return track1;
    }

    /**
     * @return the walk_l
     */
    public Sound getWalk_l() {
        return walk_l;
    }

    /**
     * @return the walk_r
     */
    public Sound getWalk_r() {
        return walk_r;
    }

    /**
     * @return the checkpoint
     */
    public Sound getCheckpoint() {
        return checkpoint;
    }
    
}
