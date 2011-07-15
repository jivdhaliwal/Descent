package descent.engine.entity;

import descent.GameplayState;
import descent.engine.CollisionBlocks;
import descent.engine.component.Component;
import descent.engine.component.RenderComponent;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */
public class Entity {

    ArrayList<Component> components = null;
    private boolean delete;
    private boolean isDead;
    private Vector2f position;
    RenderComponent renderComponent = null;
    private Shape collisionBox;

    public Entity()
    {
        components = new ArrayList<Component>();

        position = new Vector2f(0,0);
    }
    
    public void AddComponent(Component component) {
        if (RenderComponent.class.isInstance(component)) {
            renderComponent = (RenderComponent) component;
        }
        component.setOwnerEntity(this);
        components.add(component);
    }

    public void RemoveComponent(Component component) {
        components.remove(component);
    }

    public Component getComponent(String id) {
        for (Component comp : components) {
            if (comp.getId().equalsIgnoreCase(id)) {
                return comp;
            }
        }
        return null;
    }
    
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
        if (renderComponent != null) {
            renderComponent.render(gc, sb, gr);
        }
//        gr.draw(collisionBox);
    }


    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        for (Component component : components) {
            component.update(gc, sb, delta);
        }
    }
    
    public boolean touchingWall() {
        for (Polygon collisionBlock : CollisionBlocks.getInstance().getWallBlocks()) {
            if (getCollisionBox().intersects(collisionBlock)) {
                return true;
            }
        }
        return false;
    }
    
    /* Given tilesize and x,y position, return tile position
     *
     * @param tilesize Size of tiles in pixels
     */
    public Vector2f getTilePosition() {
        return new Vector2f((int) Math.floor(getPosition().x / GameplayState.TILESIZE), (int) Math.floor(getPosition().y / GameplayState.TILESIZE));
    }

    /**
     * @return the delete
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     * @return the isDead
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * @return the position
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     * @param delete the delete to set
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * @param isDead the isDead to set
     */
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Vector2f position) {
        this.position = new Vector2f(position);
    }

    /**
     * @return the collisionPoly
     */
    public Shape getCollisionBox() {
        return collisionBox;
    }

    /**
     * @param collisionBox the collisionPoly to set
     */
    public void setCollisionBox(Shape collisionBox) {
        this.collisionBox = collisionBox;
    }

    
}
