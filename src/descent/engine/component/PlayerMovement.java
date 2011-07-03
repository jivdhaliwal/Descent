/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package descent.engine.component;

/**
 * TODO change from floats to ints
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import descent.GameplayState;

public class PlayerMovement extends Component {

    GameplayState gameplayState;
    
    float direction;
    float speed;
    float velocityY;
    
    private int gravityCounter;
    private int inputCounter;
    private int jumpCounter;
    
    private float jumpSpeed,gravity;
    private boolean isJumping;
    private final float maxGravity;
    private final int maxJumpTime;

    public PlayerMovement( String id )
    {
        this.id = id;
        gravity=-0.0015f;
        maxGravity=-0.2f;
        jumpSpeed=0.18f;
        velocityY=gravity;
        maxJumpTime=200;
        isJumping=true;
    }

    public float getSpeed()
    {
        return speed;
    }

    public float getDireciton()
    {
        return direction;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {

        float rotation = entity.getRotation();
        float scale = entity.getScale();

        Vector2f position = entity.getPosition();
        
        inputCounter-=delta;
        gravityCounter-=delta;

        Input input = gc.getInput();

//        if(input.isKeyDown(Input.KEY_DOWN))
//        {
//            position.y += 0.1f*delta;
//            entity.getCollisionPoly().setY(position.y);
//            if(entity.blocked()) {
//                position.y -= 0.1f*delta;
//                entity.getCollisionPoly().setY(position.y);
//            }
//        }
        if(inputCounter<0) {
            
        
            if (( input.isKeyDown(Input.KEY_UP)|| input.isKeyDown(Input.KEY_SPACE) ) && !isJumping) {
                jumpCounter=maxJumpTime;
                isJumping = true;
            }
            if (( input.isKeyDown(Input.KEY_UP)|| input.isKeyDown(Input.KEY_SPACE) )) {
                jumpCounter-=delta;
            } else {
                jumpCounter=-1;
            }
            if (input.isKeyDown(Input.KEY_LEFT)) {
                position.x -= 0.1f * delta;
                entity.getCollisionPoly().setX(position.x);
                if (entity.blocked()) {
                    position.x += 0.1f * delta;
                    entity.getCollisionPoly().setX(position.x);
                }
                inputCounter=13;
            }
            if (input.isKeyDown(Input.KEY_RIGHT)) {
                position.x += 0.1f * delta;
                entity.getCollisionPoly().setX(position.x);
                if (entity.blocked()) {
                    position.x -= 0.1f * delta;
                    entity.getCollisionPoly().setX(position.x);
                }
                inputCounter=13;
            }

            if (input.isKeyDown(Input.KEY_U)) {
                scale += 0.001f * delta;
                inputCounter=10;
            }

            if (input.isKeyDown(Input.KEY_I)) {
                scale -= 0.001f * delta;
                inputCounter=10;
            }

            if (input.isKeyDown(Input.KEY_J)) {
                rotation += 3;
                inputCounter=10;
            }

            if (input.isKeyDown(Input.KEY_K)) {
                rotation -= 3;
                inputCounter=10;
            }

        }

        if(gravityCounter<0) {
            
            if(jumpCounter>0) {
                velocityY = jumpSpeed;
            }
            
            if (isJumping && (velocityY > maxGravity)) {
                velocityY += gravity*delta;
            } else {
                if(isJumping){
                    velocityY = maxGravity;
                }
            }
            position.y -= velocityY*delta;
            entity.getCollisionPoly().setY(position.y);

            if (entity.blocked()) {
                if (velocityY == maxGravity) {
                    isJumping = false;
                }
                position.y += velocityY*delta;
                entity.getCollisionPoly().setY(position.y);
            } else {
                isJumping = true;
            }
            
            if(isJumping) {
                position.y += 0.1f * delta;
                entity.getCollisionPoly().setY(position.y);
                if (entity.blocked()) {
                    isJumping=false;
                    velocityY=0;
                } 
                position.y -= 0.1f * delta;
                entity.getCollisionPoly().setY(position.y);
            }
            
            if(!isJumping) {
                position.y += 0.05f * delta;
                entity.getCollisionPoly().setY(position.y);
                if (entity.blocked()) {
                    position.y -= 0.05f * delta;
                    entity.getCollisionPoly().setY(position.y);
                }
            }
            gravityCounter=10;
        }
        
        
        System.out.println(jumpCounter);
        entity.setPosition(position);
        entity.getCollisionPoly().setLocation(position);
        
        entity.setRotation(rotation);

        entity.setScale(scale);

    }

    private boolean onGround(Vector2f position, int delta) {
        position.y += 0.001f * delta;
        entity.getCollisionPoly().setY(position.y);
        if (entity.blocked()) {
            position.y -= 0.001f * delta;
            entity.getCollisionPoly().setY(position.y);
            return true;
        } else {
            position.y -= 0.001f * delta;
            entity.getCollisionPoly().setY(position.y);
            return false;
        }
        
    }

}

