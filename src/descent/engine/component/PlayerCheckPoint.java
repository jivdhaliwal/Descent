package descent.engine.component;

import descent.GameplayState;
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
                    currentCheckpoint.getX(),currentCheckpoint.getY()));
            entity.getCollisionPoly().setLocation(new Vector2f(
                    currentCheckpoint.getX(),currentCheckpoint.getY()));
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
        for(int i=0;i<CollisionBlocks.getInstance().getCheckpoints().size();i++){
            if(entity.getCollisionPoly().intersects(CollisionBlocks.getInstance().getCheckpoints().get(i))) {
                currentCheckpoint = CollisionBlocks.getInstance().getCheckpoints().get(i);
            }
        }
        
    }
    
}
