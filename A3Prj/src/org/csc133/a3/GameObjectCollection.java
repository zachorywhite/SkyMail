package org.csc133.a3;

import java.util.ArrayList;

/**
 * Holds a collection of Game Objects
 */
public class GameObjectCollection extends ArrayList<GameObject>{
    /**
     * Creates new GameObjectCollection
     */
    public GameObjectCollection(){ }

    /**
     * Gets every Non-player helicopter in the collection
     * @return NPHelicopters
     */
    public ArrayList getNPHelicopter(){
        ArrayList<NPHelicopter> h = new ArrayList<>();
        for(GameObject object : this){
            if(object instanceof NPHelicopter)
                h.add((NPHelicopter) object);
        }
        return h;
    }

    /**
     * Returns every bird in the collection
     * @return Birds
     */
    public ArrayList getBirds(){
        ArrayList<Bird> b = new ArrayList<>();
        for(GameObject object : this){
            if(object instanceof Bird)
                b.add((Bird) object);
        }
        return b;
    }

    /**
     * Returns every RefuelingBlimp in the collection
     * @return RefuelingBlimps
     */
    public ArrayList getRefuelingBlimps(){
        ArrayList<RefuelingBlimp> blimp = new ArrayList<>();
        for(GameObject object : this){
            if(object instanceof RefuelingBlimp)
                blimp.add((RefuelingBlimp) object);
        }
        return blimp;
    }

    /**
     * Returns every SkyScraper in the collection
     * @return SkyScrapers
     */
    public ArrayList getSkyScrapers(){
        ArrayList<SkyScraper> skyScrapers = new ArrayList<>();
        for(GameObject object : this){
            if(object instanceof SkyScraper)
                skyScrapers.add((SkyScraper) object);
        }
        return skyScrapers;
    }
}
