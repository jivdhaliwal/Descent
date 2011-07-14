package descent.engine.component;

import descent.engine.CollisionBlocks;
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
            entity.getCollisionPoly().setLocation(new Vector2f(
                    getCurrentCheckpoint().getX(),getCurrentCheckpoint().getY()));
//            entity.setPosition(new Vector2f(8f,16f));
//            entity.getCollisionPoly().setLocation(new Vector2f(8f,16f));
        }
        
    }
    
    public boolean checkSpikeCollision(){
        for(int i=0;i<CollisionBlocks.getInstance().getSpikeBlocks().size();i++){
            if(entity.getCollisionPoly().intersects(CollisionBlocks.getInstance().getSpikeBlocks().get(i))) {
                return true;
            }
        }
        return false;
    }
    
    public void checkCheckpointCollision(){
        for(int i=0;i<checkpoints.size();i++){
            if(entity.getCollisionPoly().intersects(checkpoints.get(i))) {
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
