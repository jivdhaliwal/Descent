package descent.engine;


import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.xml.XMLElement;
import org.newdawn.slick.util.xml.XMLElementList;
import org.newdawn.slick.util.xml.XMLParser;

/**
 * Singleton that loads all the worlds and levels 
 * @author Jiv Dhaliwal <jivdhaliwal@gmail.com>
 */
public class LevelLoader {

    private static LevelLoader levelLoader = null;
    
    private int maxWorlds;
    
    private ArrayList<ArrayList<String>> worldList;
    
    private static XMLElement root;
    private static XMLElementList worlds;
    
    private String filepath = "levels/worlds.xml";

    public LevelLoader() throws SlickException {
        XMLParser parser = new XMLParser();

        root = parser.parse(filepath);
        worlds = root.getChildrenByName("World");
        worldList = new ArrayList<ArrayList<String>>();
        
        loadLevels();
    }
    
    public static LevelLoader getInstance() throws SlickException {
        if (levelLoader == null) {
            levelLoader = new LevelLoader();
        }
        return levelLoader;
    }

    
    private void loadLevels() {
        
        for(int i=0;i<worlds.size();i++) {
            XMLElementList currentWorld = worlds.get(i).getChildrenByName("Level");
            ArrayList<String> levelList = new ArrayList<String>();
            for(int j=0;j<currentWorld.size();j++) {
                XMLElement currentLevel = currentWorld.get(j);
                levelList.add(currentLevel.getAttribute("path"));
            }
            worldList.add(levelList);
        }
        
    }
    
    public TiledMap getLevelMap(int world, int level) throws SlickException {
        return new TiledMap(worldList.get(world).get(level));
    }
    

    public int getMaxLevels(int worldNum) {
        return worldList.get(worldNum).size();
    }

}
