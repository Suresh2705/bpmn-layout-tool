package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.grid.GridOccupancy;
import com.tnq.bpmnlayout.grid.GridPosition;

import java.util.HashMap;
import java.util.Map;

public class GridLayoutEngine {

    private final GridOccupancy occupancy =
            new GridOccupancy();

    private final Map<BpmnNode, LayoutNode> layoutNodes =
            new HashMap<>();

    public void build(BpmnGraph graph) {

        layoutNodes.clear();

        for (BpmnNode node : graph.getNodes()) {

            LayoutNode layoutNode = new LayoutNode(node);

            int row = calculateRow(node);
            int col = calculateColumn(node);

            GridPosition position =
                    new GridPosition(row, col);

            layoutNode.setGridPosition(position);

            occupancy.occupy(row, col, node);

            layoutNodes.put(node, layoutNode);

        }

    }

    private int calculateRow(BpmnNode node) {

        return node.getLane() * 2;

    }

    private int calculateColumn(BpmnNode node) {

        return node.getLevel() * 3;

    }

    public LayoutNode getLayoutNode(BpmnNode node) {

        return layoutNodes.get(node);

    }

    public Map<BpmnNode, LayoutNode> getLayoutNodes() {

        return layoutNodes;

    }

    public GridOccupancy getOccupancy() {

        return occupancy;

    }

}