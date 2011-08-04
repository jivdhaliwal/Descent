package descent.engine.component;

import descent.GameplayState;
import descent.engine.CollisionBlocks;
import descent.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
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
//    private static final int RIGHT = 1;
//    private static final int UP = -1;
//    private static final int DOWN = 1;
       
    // 256>>8 == 1 pixel per update
    private int velocity = 256;
    // ms before next movement update
    private int movementRate=30;
    
    private int movementCounter;
    
    private Rectangle onTopCollision;
    private Rectangle insideCollision;
    
    
    public PlatformMovement(String id, int movement){
        player = CollisionBlocks.getInstance().getPlayer();
        
        this.id = id;
        this.movement=movement;
        
        direction = LEFT;
        
        onTopCollision = new Rectangle(0, 0, GameplayState.TILESIZE-6, 3);
        insideCollision = new Rectangle(0,0,7, 7);
    }
    
    private boolean touchingPlatform() {
        if (entity.getCollisionBox().intersects(player.getCollisionBox()) || 
                onTopCollision.intersects(player.getCollisionBox())) {
            return true;
        }
        return false;
    }
    
     
    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        
        movementCounter-=delta;
        
        Vector2f position = entity.getPosition();
        
        if(movementCounter < 0) {
            if (movement == HORI) {
                position.x += (velocity * direction) >> 8;
                entity.getCollisionBox().setX(position.x);
                if (entity.touchingWall()) {
                    position.x -= (velocity * direction) >> 8;
                    entity.getCollisionBox().setX(position.x);
                    direction = direction * -1;
                }
            } else if (movement == VERT) {
                position.y += (velocity * direction) >> 8;
                entity.getCollisionBox().setY(position.y);
                if (entity.touchingWall()) {
                    position.y -= (velocity * direction) >> 8;
                    entity.getCollisionBox().setY(position.y);
                    direction = direction * -1;
                }
            }
            //Moves the player when on top of a platform
            if (touchingPlatform()) {
                player.setPosition(
                        new Vector2f(player.getPosition().x + ((velocity * direction) >> 8),
                        player.getPosition().y));
                player.getCollisionBox().setLocation(player.getPosition());
                if (player.touchingWall()) {
                    player.setPosition(
                            new Vector2f(player.getPosition().x - ((velocity * direction) >> 8),
                            player.getPosition().y));
                    player.getCollisionBox().setLocation(player.getPosition());
                }
            }
            movementCounter=movementRate;
        }
        
        onTopCollision.setLocation(position.x+3, position.y-3);
        insideCollision.setLocation(position.x+2,position.y+2);
        entity.setPosition(position);
        entity.getCollisionBox().setLocation(position);
    }

    /**
     * @return the onTopCollision
     */
    public Rectangle getOnTopCollision() {
        return onTopCollision;
    }

    /**
     * @return the insideCollision
     * This acts like a spike inside platforms to
     * kill a player if they end up inside a platform
     */
    public Rectangle getInsideCollision() {
        return insideCollision;
    }

}
