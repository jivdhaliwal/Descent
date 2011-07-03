/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package descent.engine.component;

/**
 *
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
    
    private float jumpSpeed,gravity;
    private boolean isJumping;
    private final float maxGravity;

    public PlayerMovement( String id )
    {
        this.id = id;
        gravity=-0.35f;
        maxGravity=-2.0f;
        jumpSpeed=0.35f;
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
            
        
            if (input.isKeyPressed(Input.KEY_UP) && !isJumping) {
                velocityY = 5.5f;
                isJumping = true;
                inputCounter=10;
//            position.y -= 0.1f*delta;
//            entity.getCollisionPoly().setY(position.y);
//            if(entity.blocked()) {
//                position.y += 0.1f*delta;
//                entity.getCollisionPoly().setY(position.y);
//            }
            }
            if (input.isKeyDown(Input.KEY_LEFT)) {
                position.x -= 0.1f * delta;
                entity.getCollisionPoly().setX(position.x);
                if (entity.blocked()) {
                    position.x += 0.1f * delta;
                    entity.getCollisionPoly().setX(position.x);
                }
                inputCounter=15;
            }
            if (input.isKeyDown(Input.KEY_RIGHT)) {
                position.x += 0.1f * delta;
                entity.getCollisionPoly().setX(position.x);
                if (entity.blocked()) {
                    position.x -= 0.1f * delta;
                    entity.getCollisionPoly().setX(position.x);
                }
                inputCounter=15;
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

            if (input.isKeyPressed(Input.KEY_SPACE) && !isJumping) {
                velocityY = 5.5f;
                isJumping = true;
                inputCounter=10;
            }
        }

        if(gravityCounter<0) {
            if (velocityY > maxGravity) {
                velocityY += gravity;
            } else {
                velocityY = maxGravity;
            }
            position.y -= velocityY;
            entity.getCollisionPoly().setY(position.y);

            if (entity.blocked()) {
                if (velocityY == maxGravity) {
                    isJumping = false;
                }
                position.y += velocityY;
                entity.getCollisionPoly().setY(position.y);
            } else if (velocityY < 0) {
                isJumping = true;
            }
            gravityCounter=10;
        }
        
        // TODO Fix the wall jumping
        
        // Handles wall jumping
//        entity.getCollisionPoly().setX((position.x-3f));
//        if(entity.blocked()) {
//            isJumping=false;
//        } else {
//            entity.getCollisionPoly().setX(position.x+3f);
//            if(entity.blocked()){
//                isJumping=false;
//            }
//        }
//        entity.getCollisionPoly().setX(position.x);
        
        
        
        entity.setPosition(position);
        entity.getCollisionPoly().setLocation(position);
        
        entity.setRotation(rotation);

        entity.setScale(scale);

    }

}

