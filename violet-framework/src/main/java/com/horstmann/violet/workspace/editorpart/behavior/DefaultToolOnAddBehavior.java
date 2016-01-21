package com.horstmann.violet.workspace.editorpart.behavior;

import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;
import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

/**
 *
 * Created by piter on 09.01.16.
 */
public class DefaultToolOnAddBehavior extends AbstractEditorPartBehavior {
    private final IGraphToolsBar graphToolsBar;

    public DefaultToolOnAddBehavior(IGraphToolsBar graphToolsBar) {
        this.graphToolsBar = graphToolsBar;
    }

    @Override
    public void onMouseClicked(MouseEvent event) {
        if(event.getClickCount() > 1){
            return;
        }

        if(event.getButton() != MouseEvent.BUTTON1){
            return;
        }

        boolean isCtrl = (event.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0;
        if(isCtrl){
            return;
        }

        setDefaultGraphTool();
    }

    private void setDefaultGraphTool(){
        GraphTool defaultGraphTool = GraphTool.SELECTION_TOOL;
        GraphTool currentGraphTool = graphToolsBar.getSelectedTool();

        if(currentGraphTool.equals(defaultGraphTool)){
            return;
        }
        graphToolsBar.setSelectedTool(defaultGraphTool);
    }


}
