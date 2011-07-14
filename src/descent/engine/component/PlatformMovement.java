package descent.engine.component;

import descent.GameplayState;
import descent.engine.CollisionBlocks;
import descent.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */
public class PlatformMovement extends Component{

    Entity player;
    
    // Movement types
    private final int movement;
    public static final int HORI = 0;
    public static final int VERT = 1;
    
    // Directions
    private int direction;
    private static final int LEFT = -1;
    private static final int RIGHT = 1;
    private static final int UP = -1;
    private static final int DOWN = 1;
       
    // 256>>8 == 1 pixel per update
    private int velocity = 256;
    // ms before next movement update
    private int movementRate=10;
    
    private int movementCounter;
    
    private Rectangle onTopCollision;
    
    
    public PlatformMovement(String id, int movement){
        player = CollisionBlocks.getInstance().getPlayer();
        
        this.id = id;
        this.movement=movement;
        
        direction = LEFT;
        
        onTopCollision = new Rectangle(0, 0, GameplayState.TILESIZE, 3);
    }
    
    private boolean playerOnTop(){
        if(onTopCollision.intersects(player.getCollisionBox())) {
            return true;
        }
        return false;
    }
    
     
    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        
        movementCounter-=delta;
        
        Vector2f position = entity.getPosition();
        Rectangle platformCollision = (Rectangle) entity.getCollisionBox();
        
        if(movementCounter < 0) {
            if (movement == HORI) {
                position.x += (velocity * direction) >> 8;
                entity.getCollisionBox().setX(position.x);
                if (entity.blocked()) {
                    position.x -= (velocity * direction) >> 8;
                    entity.getCollisionBox().setX(position.x);
                    direction = direction * -1;
                }
            } else if (movement == VERT) {
                position.y += (velocity * direction) >> 8;
                entity.getCollisionBox().setY(position.y);
                if (entity.blocked()) {
                    position.y -= (velocity * direction) >> 8;
                    entity.getCollisionBox().setY(position.y);
                    direction = direction * -1;
                }
            }
            //Moves the player when on top of a platform
            if(playerOnTop()){
                player.setPosition(
                        new Vector2f(player.getPosition().x+((velocity * direction)>>8),
                        player.getPosition().y));
            }
            movementCounter=movementRate;
        }
        
        onTopCollision.setLocation(position.x, position.y-3);
        entity.setPosition(position);
        entity.getCollisionBox().setLocation(position);
    }
    
}
