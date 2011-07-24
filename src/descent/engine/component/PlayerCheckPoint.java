package descent.engine.component;

import descent.engine.CollisionBlocks;
import descent.engine.ResourceManager;
import descent.engine.entity.Entity;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Handles checkpoints
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */
public class PlayerCheckPoint extends Component{

    
    private ArrayList<Rectangle> checkpoints;
    private Rectangle currentCheckpoint;
    
    private boolean firstCheckpoint = true;
    
    public PlayerCheckPoint(String id) {
        
        this.id = id;
        this.checkpoints = CollisionBlocks.getInstance().getCheckpoints();
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        checkCheckpointCollision();
        try {
            if(checkSpikeCollision()) {
                PlayerMovement movement = (PlayerMovement) entity.getComponent("PlayerMovement");
                movement.resetMovement();
                ResourceManager.getInstance().getDie().play();
                entity.setPosition(new Vector2f(
                        getCurrentCheckpoint().getX(),getCurrentCheckpoint().getY()));
                entity.getCollisionBox().setLocation(new Vector2f(
                        getCurrentCheckpoint().getX(),getCurrentCheckpoint().getY()));
    //            entity.getCollisionBox().setLocation(new Vector2f(8f,16f));
    //            entity.getCollisionBox().setLocation(new Vector2f(8f,16f));
            }
        } catch (SlickException ex) {
            Logger.getLogger(PlayerCheckPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public boolean checkSpikeCollision() throws SlickException{
        for(int i=0;i<CollisionBlocks.getInstance().getSpikeBlocks().size();i++){
            if(entity.getCollisionBox().intersects(CollisionBlocks.getInstance().getSpikeBlocks().get(i))) {
                return true;
            }
        }
        /*
         * If the player ends up inside a platform (due to player getting stuck 
         * between a wall and moving platform), kill the player
         */
        for(Entity platform:CollisionBlocks.getInstance().getPlatforms()) {
            PlatformMovement platMov = (PlatformMovement)platform.getComponent("PlatformMovement");
            if(entity.getCollisionBox().intersects(platMov.getInsideCollision())) {
                return true;
            }
            
        }
        return false;
    }
    
    public void checkCheckpointCollision(){
        for(int i=0;i<checkpoints.size();i++){
            if(entity.getCollisionBox().intersects(checkpoints.get(i))) {
                if(currentCheckpoint!=checkpoints.get(i)) {
                    currentCheckpoint = checkpoints.get(i);
                    // Only play checkpoint sound if player touches any checkpoint
                    // other than the starting one
                    if(!firstCheckpoint) {
                        try {
                            ResourceManager.getInstance().getCheckpoint().play();
                        } catch (SlickException ex) {
                            Logger.getLogger(PlayerCheckPoint.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    firstCheckpoint = false;
                }
            }
        }
    }

    /**
     * @return the currentCheckpoint
     */
    public Rectangle getCurrentCheckpoint() {
        return currentCheckpoint;
    }
    
}
