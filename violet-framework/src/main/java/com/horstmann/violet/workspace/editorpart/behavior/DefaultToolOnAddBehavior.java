package com.horstmann.violet.workspace.editorpart.behavior;

import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;
import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

/**
 * Adding behavior, if Ctrl is pressed then user can add multiple Nodes and Edge
 * Created by piter on 09.01.16.
 */
public class DefaultToolOnAddBehavior extends AbstractEditorPartBehavior
{
    /**
     *  Default constructor of class
     * @param graphToolsBar current graph tool bar
     */
    public DefaultToolOnAddBehavior(final IGraphToolsBar graphToolsBar)
    {
        this.graphToolsBar = graphToolsBar;
        initKeyListener();
    }

    @Override
    public void afterAddingNodeAtPoint(final INode node, final Point2D location)
    {
        if(node != null)
        {
            setDefaultGraphTool();
        }
    }

    @Override
    public void afterAddingEdgeAtPoints(final IEdge edge, final Point2D startPoint, final Point2D endPoint)
    {
        if(edge != null)
        {
            setDefaultGraphTool();
        }
    }

    private void setDefaultGraphTool()
    {
        final GraphTool defaultGraphTool = GraphTool.SELECTION_TOOL;
        final GraphTool currentGraphTool = graphToolsBar.getSelectedTool();

        if(currentGraphTool.equals(defaultGraphTool))
        {
            return;
        }

        if(isKeyPressed)
        {
            graphToolsBar.setSelectedTool(defaultGraphTool);
        }
    }

    private void initKeyListener()
    {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new CtrlKeyListener());
    }

    private class CtrlKeyListener implements KeyEventDispatcher
    {
        @Override
        public boolean dispatchKeyEvent(KeyEvent event)
        {
            switch (event.getID())
            {
                case KeyEvent.KEY_PRESSED:
                    if (isCtrlKeyPressed(event))
                    {
                        isKeyPressed = false;
                    }
                    break;

                case KeyEvent.KEY_RELEASED:
                    if (!isCtrlKeyPressed(event))
                    {
                        isKeyPressed = true;
                    }
                    break;
            }
            return false;
        }

        private boolean isCtrlKeyPressed(final KeyEvent event)
        {
            boolean isPressed = event.isControlDown();
            return isPressed;
        }
    }

    private final IGraphToolsBar graphToolsBar;
    private boolean isKeyPressed = false;
}