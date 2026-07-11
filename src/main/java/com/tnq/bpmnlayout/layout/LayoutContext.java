package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnGraph;

public class LayoutContext {

    /*
     * Grid spacing
     */
    public static final int HORIZONTAL_SPACING = 220;
    public static final int VERTICAL_SPACING = 120;

    /*
     * Initial offsets
     */
    public static final int LEFT_MARGIN = 80;
    public static final int TOP_MARGIN = 80;

    /*
     * BPMN node sizes
     */
    public static final int EVENT_SIZE = 36;
    public static final int GATEWAY_SIZE = 50;
    public static final int TASK_WIDTH = 120;
    public static final int TASK_HEIGHT = 80;
    public static final int CALL_ACTIVITY_WIDTH = 140;
    public static final int CALL_ACTIVITY_HEIGHT = 90;

    private BpmnGraph graph;

    private GridLayoutEngine grid;

    public LayoutContext() {
    }

    public BpmnGraph getGraph() {
        return graph;
    }

    public void setGraph(BpmnGraph graph) {
        this.graph = graph;
    }

    public GridLayoutEngine getGrid() {
        return grid;
    }

    public void setGrid(GridLayoutEngine grid) {
        this.grid = grid;
    }

}