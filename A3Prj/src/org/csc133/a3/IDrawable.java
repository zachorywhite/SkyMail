package org.csc133.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

/**
 * Provides methods that make objects drawable
 */
public interface IDrawable {
    /**
     * Draws objects on screen
     * @param g component's graphics
     * @param containerOrigin Origin of the container the object is in
     */
    void draw(Graphics g, Point containerOrigin);
}
