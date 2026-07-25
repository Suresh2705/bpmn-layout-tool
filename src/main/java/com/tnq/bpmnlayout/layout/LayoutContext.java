package com.tnq.bpmnlayout.layout;

import java.util.ArrayList;
import java.util.List;

import com.tnq.bpmnlayout.analysis.Backbone;
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

    /*
     * Main process backbone
     */
    private Backbone backbone;
    private final List<Branch> branches =
        new ArrayList<>();

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

    public Backbone getBackbone() {
        return backbone;
    }

    public void setBackbone(Backbone backbone) {
        this.backbone = backbone;
    }

    public List<Branch> getBranches() {
        return branches;
    }

}