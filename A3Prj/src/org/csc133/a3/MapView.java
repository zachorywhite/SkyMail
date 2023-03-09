package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.plaf.Border;

/**
 * MapView that displays the current game state, including painting
 * the objects on screen with their correct positions
 */
public class MapView extends Component {
    private GameWorld world;
    private Point p;

    /**
     * Creates new MapView object
     * @param gw reference to the GameWorld
     */
    MapView(GameWorld gw){
        world = gw;
        this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
        this.getAllStyles().setBgTransparency(255);
    }

    /**
     * Draws each object onto the MapView
     * @param g component's graphics
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        p = new Point(getX(), getY());
        for (int i = 0; i<world.getCollection().size(); i++) {
            world.getCollection().get(i).draw(g, p);
        }
    }

    /**
     * Update and repaint the MapView when game state changes
     */
    public void update(){
        //world.map();
        repaint();
    }
}
