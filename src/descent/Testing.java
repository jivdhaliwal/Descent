package descent;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;

/**
 * Testing stuff
 */
public class Testing extends BasicGame {
    
        float maxHealth = 100f;
        float currentHealth = 100f;
        int healthCounter;

	/**
	 * Create the test
	 */
	public Testing() {
		super("Polygon Test");
	}

	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		
	}

	/**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) throws SlickException {
                healthCounter-=delta;
                
                if(healthCounter<0) {
                    currentHealth-=0.025;
                    healthCounter=10;
                }
	}

	/**
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) throws SlickException {
            
        g.setColor(Color.green);
        g.fillRect(100.0f, 130.0f, 10.0f, (currentHealth/maxHealth)*-30f);
        g.setColor(Color.red);
        g.drawRect(100.0f, 100.0f, 10.0f, 30.0f);

//            g.setColor(Color.white);
//            g.draw(poly);
	}

	/**
	 * Entry point into our test
	 * 
	 * @param argv The arguments passed on the command line
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new Testing(), 640, 480, false);
			container.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
