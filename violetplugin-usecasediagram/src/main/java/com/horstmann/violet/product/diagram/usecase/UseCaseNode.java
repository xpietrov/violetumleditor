/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.product.diagram.usecase;

import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.EllipticalNode;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * A use case node in a use case diagram.
 */
public class UseCaseNode extends EllipticalNode
{
    /**
     * Construct a use case node with a default size
     */
    public UseCaseNode()
    {
        title = new MultiLineString();
        title.setSize(MultiLineString.LARGE);
        title.setJustification(MultiLineString.CENTER);
        description = new MultiLineString();
        description.setJustification(MultiLineString.CENTER);
    }

    private Rectangle2D getTitleBounds()
    {
        Rectangle2D globalBounds = new Rectangle2D.Double(0, 0, 0, 0);
        Rectangle2D titleBounds = title.getBounds();
        globalBounds.add(titleBounds);
        boolean isTitleEmpty = (description.getText().length() == 0);
        double defaultHeight = DEFAULT_HEIGHT;
        if (!isTitleEmpty)
        {
            defaultHeight = DEFAULT_COMPARTMENT_HEIGHT;
        }
        globalBounds.add(new Rectangle2D.Double(0, 0, DEFAULT_WIDTH, defaultHeight));
        Point2D currentLocation = getLocation();
        double x = currentLocation.getX();
        double y = currentLocation.getY();
        double w = globalBounds.getWidth();
        double h = globalBounds.getHeight();
        globalBounds.setFrame(x, y, w, h);
        Rectangle2D snappedBounds = getGraph().getGridSticker().snap(globalBounds);
        return snappedBounds;
    }

    private Rectangle2D getDescirptionBounds()
    {
        Rectangle2D globalBounds = new Rectangle2D.Double(0, 0, 0, 0);
        Rectangle2D descriptionBounds = description.getBounds();
        globalBounds.add(descriptionBounds);
        if (descriptionBounds.getHeight() > 0)
        {
            globalBounds.add(new Rectangle2D.Double(0, 0, DEFAULT_WIDTH, DEFAULT_COMPARTMENT_HEIGHT));
        }
        Rectangle2D topBounds = getTitleBounds();
        double x = topBounds.getX();
        double y = topBounds.getMaxY();
        double w = globalBounds.getWidth();
        double h = globalBounds.getHeight();
        globalBounds.setFrame(x, y, w, h);
        Rectangle2D snappedBounds = getGraph().getGridSticker().snap(globalBounds);
        return snappedBounds;
    }

    @Override
    public Rectangle2D getBounds()
    {
        Rectangle2D top = getTitleBounds();
        Rectangle2D bot = getDescirptionBounds();
        top.add(bot);
        Rectangle2D snappedBounds = getGraph().getGridSticker().snap(top);
        return snappedBounds;
    }

    @Override
    public Point2D getConnectionPoint(IEdge e)
    {
        // if use case node is atatched to an actor node, we force connection point to cardianl points
        if (e.getStart().getClass().isAssignableFrom(ActorNode.class) || e.getEnd().getClass().isAssignableFrom(ActorNode.class))
        {

        }

        return super.getConnectionPoint(e);
    }

    @Override
    public Shape getShape()
    {
        Rectangle2D bounds = getBounds();
        return new RoundRectangle2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), 50, 50);
    }

    @Override
    public void draw(Graphics2D g2)
    {
        Color oldColor = g2.getColor();
        super.draw(g2);

        Rectangle2D currentBounds = getBounds();
        Rectangle2D nameBounds = getTitleBounds();
        Rectangle2D descriptionBounds = getDescirptionBounds();
        if (nameBounds.getWidth() < currentBounds.getWidth())
        {
            nameBounds.setRect(nameBounds.getX(), nameBounds.getY(), currentBounds.getWidth(), nameBounds.getHeight());
        }

        if (descriptionBounds.getWidth() < currentBounds.getWidth())
        {
            descriptionBounds.setRect(descriptionBounds.getX(), descriptionBounds.getY(), currentBounds.getWidth(), descriptionBounds.getHeight());
        }

        // Draw shape and text
        Shape shape = getShape();
        g2.setColor(getBackgroundColor());
        g2.fill(shape);
        g2.setColor(getBorderColor());
        g2.draw(shape);
        g2.setColor(getTextColor());
        title.draw(g2, nameBounds);
        description.draw(g2, descriptionBounds);

        // Restore first color
        g2.setColor(oldColor);
    }

    /**
     * Sets the title property value.
     * 
     * @param newValue the new use case title
     */
    public void setTitle(MultiLineString newValue)
    {
        title = newValue;
    }

    /**
     * Gets the title property value.
     */
    public MultiLineString getTitle()
    {
        return title;
    }

    /**
     * Gets the description property value.
     * @return MultiLineString
     */
    public MultiLineString getDescription()
    {
        return description;
    }

    /**
     * Sets the description property value
     * @param description the new use case description
     */
    public void setDescription(MultiLineString description)
    {
        this.description = description;
    }

    @Override
    public UseCaseNode clone()
    {
        UseCaseNode cloned = (UseCaseNode) super.clone();
        cloned.title = title.clone();
        cloned.description = description.clone();
        return cloned;
    }

    private MultiLineString title;
    private MultiLineString description;

    private static int DEFAULT_COMPARTMENT_HEIGHT = 10;
    private static int DEFAULT_WIDTH = 110;
    private static int DEFAULT_HEIGHT = 40;
}
