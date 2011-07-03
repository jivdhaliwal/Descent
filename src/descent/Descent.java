/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package descent;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Descent extends StateBasedGame
{
     static int height = 30*8;
     static int width = 30*8;

     static boolean fullscreen = false;

     static boolean showFPS = false;

     static String title = "Descent";

     static int fpslimit = 60;

     public static final int GAMEPLAYSTATE = 1;

     

     public Descent() throws SlickException
     {
          super(title);

          this.addState(new GameplayState());
          this.enterState(GAMEPLAYSTATE);
          
          
     }

     public static void main(String[] args) throws SlickException
     {
          AppGameContainer app = new AppGameContainer(new ScalableGame(new Descent(),width,height));

          app.setDisplayMode((int)(width), (int)(height), fullscreen);
          app.setSmoothDeltas(true);
          app.setTargetFrameRate(fpslimit);
          app.setShowFPS(showFPS);
          app.start();
     }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        
    }

    
    
     
}