package descent.engine.entity;


import java.util.ArrayList;
import java.util.Iterator;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import descent.GameplayState;
import descent.Descent;
import descent.engine.component.Component;
import descent.engine.component.RenderComponent;

/**
 * Entity - A Game object that can interact with other objects and be
 * removed from the game dynamically
 *
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */
public class Entity {

    String id;

    Vector2f position;
    float scale;
    float rotation;
    float health;

    boolean isDead;
    boolean delete;
    
    RenderComponent renderComponent = null;

    ArrayList<Component> components = null;
    
    private final StateBasedGame stateBasedGame;
    GameplayState gameplayState;
    
    private Polygon collisionPoly;
    
    public Entity(StateBasedGame sbg)
    {
        
        this.stateBasedGame = sbg;
        gameplayState = (GameplayState) sbg.getState(Descent.GAMEPLAYSTATE);
        components = new ArrayList<Component>();

        position = new Vector2f(0,0);
        scale = 1;
        rotation = 0;
        
    }

    public void AddComponent(Component component)
    {
        if(RenderComponent.class.isInstance(component)) {
            renderComponent = (RenderComponent)component;
        }

        component.setOwnerEntity(this);
        components.add(component);
    }

    public void RemoveComponent(Component component) {
        components.remove(component);
    }

    public Component getComponent(String id)
    {
        for(Component comp : components)
        {
            if( comp.getId().equalsIgnoreCase(id) ) {
                return comp;
            }
        }

        return null;
    }

    /*
     * Returns the exact pixel position of the sprite (only use for rendering)
     */
    public Vector2f getPosition()
    {
        return new Vector2f(position.x, position.y);
    }

    /* Given tilesize and x,y position, return tile position
     *
     * @param tilesize Size of tiles in pixels
     */
    public Vector2f getTilePosition()
    {
        return new Vector2f((int) Math.floor((position.x / GameplayState.TILESIZE)),
                (int) Math.floor((position.y / GameplayState.TILESIZE)));
    }

    public float getScale()
    {
        return scale;
    }

    public float getRotation()
    {
        return rotation;
    }

    public float getHealth() {
        return health;
    }

    public void killEntity() {
        isDead=true;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setPosition(Vector2f position)
    {
        this.position = position;
    }

    public void setRotation(float rotate)
    {
        rotation = rotate;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }
    
    public void deleteEntity() {
        delete=true;
    }

    /**
     * @return the delete
     */
    public boolean isDelete() {
        return delete;
    }
    
    public void update(GameContainer gc, StateBasedGame sb, int delta)
    {
        for(Component component : components)
        {
            component.update(gc,sb,delta);
        }
        
    }

    public void render(GameContainer gc, StateBasedGame sb, Graphics gr)
    {
        if(renderComponent != null) {
            renderComponent.render(gc, sb, gr);
        }

//        gr.setColor(Color.yellow);
//        gr.draw(collisionPoly);
    }

    /**
     * @return the sbg
     */
    public StateBasedGame getStateBasedGame() {
        return stateBasedGame;
    }

    /**
     * @return the collisionPoly
     */
    public Polygon getCollisionPoly() {
        return collisionPoly;
    }

    /**
     * @param collisionPoly the collisionPoly to set
     */
    public void setCollisionPoly(Polygon collisionPoly) {
        this.collisionPoly = collisionPoly;
    }
    
    public boolean blocked() {
        
        for(Polygon collisionBlock:gameplayState.getCollisionBlocks()) {
            if(collisionPoly.intersects(collisionBlock)) {
                return true;
            }
        }
        
        return false;
    }
    
    public void checkHealthCollision(){
        for(int i=0;i<gameplayState.getHealthBlocks().size();i++){
            if(collisionPoly.intersects(gameplayState.getHealthBlocks().get(i))) {

                gameplayState.getHealthBlocks().remove(i);
            }
        }
    }

}
