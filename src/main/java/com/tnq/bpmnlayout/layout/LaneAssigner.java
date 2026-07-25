package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.analysis.Backbone;
import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Packs off-backbone nodes into the smallest practical set of horizontal rows.
 * A row is reused as soon as its earlier branch has moved to a later level.
 */
public class LaneAssigner {

    public void assignLanes(LayoutContext context) {
        BpmnGraph graph = context.getGraph();
        Backbone backbone = context.getBackbone();
        Map<Integer, Set<Integer>> occupiedLevels = new HashMap<Integer, Set<Integer>>();

        for (BpmnNode node : graph.getNodes()) {
            node.setLane(-1);
        }
        for (BpmnNode node : backbone.getNodes()) {
            node.setLane(0);
        }

        List<BpmnNode> nodes = new ArrayList<BpmnNode>();
        for (BpmnNode node : graph.getNodes()) {
            if (!backbone.contains(node)) {
                nodes.add(node);
            }
        }
        Collections.sort(nodes, new Comparator<BpmnNode>() {
            @Override
            public int compare(BpmnNode left, BpmnNode right) {
                int byLevel = Integer.compare(left.getLevel(), right.getLevel());
                return byLevel != 0 ? byLevel : left.getId().compareTo(right.getId());
            }
        });

        for (BpmnNode node : nodes) {
            int lane = findPreferredLane(node, occupiedLevels);
            node.setLane(lane);
            occupy(occupiedLevels, lane, node.getLevel());
        }
    }

    private int findPreferredLane(
            BpmnNode node,
            Map<Integer, Set<Integer>> occupiedLevels) {

        // Keep a linear branch on its parent's row whenever that row is free.
        for (Edge incoming : node.getIncoming()) {
            BpmnNode parent = incoming.getSource();
            if (parent.getLane() > 0 && parent.getLevel() < node.getLevel()
                    && isFree(occupiedLevels, parent.getLane(), node.getLevel())) {
                return parent.getLane();
            }
        }

        // Otherwise reuse the first row that has no node in this column.
        for (int lane = 1; ; lane++) {
            if (isFree(occupiedLevels, lane, node.getLevel())) {
                return lane;
            }
        }
    }

    private boolean isFree(Map<Integer, Set<Integer>> occupiedLevels, int lane, int level) {
        Set<Integer> levels = occupiedLevels.get(lane);
        return levels == null || !levels.contains(level);
    }

    private void occupy(Map<Integer, Set<Integer>> occupiedLevels, int lane, int level) {
        Set<Integer> levels = occupiedLevels.get(lane);
        if (levels == null) {
            levels = new HashSet<Integer>();
            occupiedLevels.put(lane, levels);
        }
        levels.add(level);
    }
}
