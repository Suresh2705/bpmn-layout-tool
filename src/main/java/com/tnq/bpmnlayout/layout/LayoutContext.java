package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.analysis.GraphStatistics;
import com.tnq.bpmnlayout.graph.BpmnGraph;

/**
 * Holds all objects produced during layout.
 */
public class LayoutContext {

    /*
     * Runtime objects
     */

    private BpmnGraph graph;

    private GraphStatistics statistics;

    private GridLayoutEngine grid;

    public LayoutContext() {
    }

    public BpmnGraph getGraph() {
        return graph;
    }

    public void setGraph(BpmnGraph graph) {
        this.graph = graph;
    }

    public GraphStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(GraphStatistics statistics) {
        this.statistics = statistics;
    }

    public GridLayoutEngine getGrid() {
        return grid;
    }

    public void setGrid(GridLayoutEngine grid) {
        this.grid = grid;
    }

}