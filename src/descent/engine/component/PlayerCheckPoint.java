package descent.engine.component;

import descent.engine.CollisionBlocks;
import descent.engine.entity.Entity;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
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
    
    public PlayerCheckPoint(String id) {
        
        this.id = id;
        this.checkpoints = CollisionBlocks.getInstance().getCheckpoints();
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        checkCheckpointCollision();
        if(checkSpikeCollision()) {
            entity.setPosition(new Vector2f(
                    getCurrentCheckpoint().getX(),getCurrentCheckpoint().getY()));
            entity.getCollisionBox().setLocation(new Vector2f(
                    getCurrentCheckpoint().getX(),getCurrentCheckpoint().getY()));
//            entity.setPosition(new Vector2f(8f,16f));
//            entity.getCollisionBox().setLocation(new Vector2f(8f,16f));
        }
        
    }
    
    public boolean checkSpikeCollision(){
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
                currentCheckpoint = checkpoints.get(i);
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
