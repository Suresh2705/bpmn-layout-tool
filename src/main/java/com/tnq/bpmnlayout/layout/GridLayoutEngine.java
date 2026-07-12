package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.grid.GridOccupancy;
import com.tnq.bpmnlayout.grid.GridPosition;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Builds a logical grid from the calculated level/lane values.
 *
 * Row    -> Lane
 * Column -> Level
 */
public class GridLayoutEngine {

    private final GridOccupancy occupancy = new GridOccupancy();

    private final Map<BpmnNode, LayoutNode> layoutNodes =
            new LinkedHashMap<>();

    public void build(BpmnGraph graph) {

        occupancy.clear();
        layoutNodes.clear();

        for (BpmnNode node : graph.getNodes()) {

            LayoutNode layoutNode = new LayoutNode(node);

            int row = calculateRow(node);
            int column = calculateColumn(node);

            GridPosition position =
                    new GridPosition(row, column);

            layoutNode.setGridPosition(position);

            occupancy.occupy(row, column, node);

            layoutNodes.put(node, layoutNode);

            node.setLayout(layoutNode);
        }

    }

    private int calculateRow(BpmnNode node) {

        /*
         * Leave one empty row between lanes.
         * This gives routing space.
         */
        return node.getLane() * 2;

    }

    private int calculateColumn(BpmnNode node) {

        /*
         * Leave two empty columns between levels.
         * This gives space for gateways and routing.
         */
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