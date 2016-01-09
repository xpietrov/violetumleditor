package com.horstmann.violet.workspace.editorpart.behavior;

import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;
import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 *
 * Created by piter on 09.01.16.
 */
public class DefaultToolOnAddBehavior extends AbstractEditorPartBehavior {
    private final IGraphToolsBar graphToolsBar;
    private boolean isCtrlPressed = false;

    public DefaultToolOnAddBehavior(IGraphToolsBar graphToolsBar) {
        this.graphToolsBar = graphToolsBar;
    }

    private void setDefaultGraphTool(){
        GraphTool defaultGraphTool = GraphTool.SELECTION_TOOL;
        GraphTool currentGraphTool = graphToolsBar.getSelectedTool();

        if(currentGraphTool.equals(defaultGraphTool)){
            return;
        }
        graphToolsBar.setSelectedTool(defaultGraphTool);
    }

    @Override
    public void onMouseClicked(MouseEvent event) {
        isCtrlPressed = (event.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0;
    }

    @Override
    public void afterAddingEdgeAtPoints(IEdge edge, Point2D startPoint, Point2D endPoint) {
        if(edge != null && !isCtrlPressed){
            setDefaultGraphTool();
        }
    }

    @Override
    public void afterAddingNodeAtPoint(INode node, Point2D location) {
        if(node != null && !isCtrlPressed){
            setDefaultGraphTool();
        }
    }
}
