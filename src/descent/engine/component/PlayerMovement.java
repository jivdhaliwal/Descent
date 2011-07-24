package descent.engine.component;

/**
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import descent.GameplayState;
import descent.engine.CollisionBlocks;
import descent.engine.ResourceManager;
import descent.engine.entity.Entity;
import org.newdawn.slick.geom.Polygon;

public class PlayerMovement extends Component {

    GameplayState gameplayState;
    
    
    private int inputCounter;
    private int jumpCounter;
    private int gravityCounter;
    private int walkSoundCounter;
    
    private boolean isJumping;
    
    private static final int gravity = 64;
    private static final int jumpspeed = 640;
    private static final int maxVelocity = 896;
    private static final int hori_velocity = 512;
    private static final int maxJumpTime = 300;
    private int velocity_y = 0;
    
    private boolean leftWalk;
    
    

    public PlayerMovement( String id )
    {
        this.id = id;
    }
    
    public boolean onTopOfWall() {
        for(Polygon collisionBlock:CollisionBlocks.getInstance().getOnWallBlocks()) {
            if(entity.getCollisionBox().intersects(collisionBlock)) {
                return true;
            }
        }
        for(Entity platform:CollisionBlocks.getInstance().getPlatforms()) {
            PlatformMovement plat2 = (PlatformMovement) platform.getComponent("PlatformMovement");
            if(entity.getCollisionBox().intersects(plat2.getOnTopCollision())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean blocked() {
        for (Polygon collisionBlock : CollisionBlocks.getInstance().getWallBlocks()) {
            if (entity.getCollisionBox().intersects(collisionBlock)) {
                return true;
            }
        }
        for(Entity platform:CollisionBlocks.getInstance().getPlatforms()) {
            if(entity.getCollisionBox().intersects(platform.getCollisionBox())) {
                return true;
            }
        }
        return false;
    }
    
    // Call to reset player's velocity and jumping state
    // Stops player jumping out if they died while jumping
    public void resetMovement() {
        velocity_y = 0;
        isJumping = true;
        jumpCounter=-1;
    }
    
    private void playWalkingSound() throws SlickException {
        if(onTopOfWall() && (walkSoundCounter)<0) {
            
            if(leftWalk) {
                ResourceManager.getInstance().getWalk().play(0.9f, 0.5f);
                leftWalk=!leftWalk;
            } else {
                ResourceManager.getInstance().getWalk().play(0.8f, 0.5f);
                leftWalk=!leftWalk;
            }
            
            walkSoundCounter=175;
        }
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {

        if(onTopOfWall()){
            isJumping=false;
        }

        Vector2f position = entity.getPosition();
        
        inputCounter-=delta;
        jumpCounter-=delta;
        gravityCounter-=delta;
        walkSoundCounter-=delta;

        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_SPACE)) {
            jumpCounter -= delta;
        } else {
            jumpCounter = -1;
        }

        if(inputCounter<0) {

            if(input.isKeyPressed(Input.KEY_SPACE) && !isJumping) {
                jumpCounter=maxJumpTime;
                isJumping=true;
                try {
                    ResourceManager.getInstance().getJump().play(0.6f, 1.0f);
                } catch (SlickException ex) {
                    Logger.getLogger(PlayerMovement.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(input.isKeyDown(Input.KEY_LEFT)) {
                position.x-= hori_velocity>>8;
                entity.getCollisionBox().setX(position.x);
                if (blocked()) {
                    position.x += hori_velocity >> 8;
                    entity.getCollisionBox().setX(position.x);
                } else {
                    try {
                        playWalkingSound();
                    } catch (SlickException ex) {
                        Logger.getLogger(PlayerMovement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                inputCounter=10;
            }
            
            if(input.isKeyDown(Input.KEY_RIGHT)) {
                position.x+= hori_velocity>>8;
                entity.getCollisionBox().setX(position.x);
                if (blocked()) {
                    position.x -= hori_velocity >> 8;
                    entity.getCollisionBox().setX(position.x);
                } else {
                    try {
                        playWalkingSound();
                    } catch (SlickException ex) {
                        Logger.getLogger(PlayerMovement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                inputCounter=10;
            }
            
            
        }
        
        if(gravityCounter<0) {
            
            if(jumpCounter>0) {
                velocity_y= (-jumpspeed);
            } else {
                velocity_y+=gravity;
                if(velocity_y>maxVelocity) {
                    velocity_y=maxVelocity;
                }
            }
            position.y+= velocity_y>>8;
            entity.getCollisionBox().setY(position.y);
            if(blocked()) {
                jumpCounter= -1;
                position.y-=velocity_y>>8;
                entity.getCollisionBox().setY(position.y);
                velocity_y=0;
            } else {
                isJumping=true;
            }
            gravityCounter=10;
        }
        
        
        entity.setPosition(position);
        entity.getCollisionBox().setLocation(position);
        

    }

}

