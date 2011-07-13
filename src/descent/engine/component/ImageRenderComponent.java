package descent.engine.component;


import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import descent.GameplayState;

/**
 *
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */
public class ImageRenderComponent extends RenderComponent {

    Image image;

    public ImageRenderComponent(String id, Image image)
    {
        super(id);
        this.image = image;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sb, Graphics gr) 
    {
        Vector2f pos = entity.getPosition();
//        float scale = entity.getScale();
//        try {
//            image.setFilter(Image.FILTER_NEAREST);
//        } catch (SlickException ex) {
//            Logger.getLogger(ImageRenderComponent.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //Temp fix to allign with collision box
        //TODO Find out why collision box doesn't line up with sprite
        image.draw(pos.x-1, pos.y);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) 
    {
//        image.setCenterOfRotation(entity.getPosition().x+(GameplayState.TILESIZE/2), 
//                entity.getPosition().y+(GameplayState.TILESIZE/2));
//        image.rotate(entity.getRotation() - image.getRotation());
    }

}
