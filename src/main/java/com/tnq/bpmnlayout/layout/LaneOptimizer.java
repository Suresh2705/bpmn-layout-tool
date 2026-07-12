package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;

import java.util.HashMap;
import java.util.Map;

public class LaneOptimizer {

    public void optimize(BpmnGraph graph) {

        Map<Integer, Map<Integer, BpmnNode>> occupied =
                new HashMap<>();

        for (BpmnNode node : graph.getNodes()) {

            occupied
                    .computeIfAbsent(node.getLevel(),
                            k -> new HashMap<>())
                    .put(node.getLane(), node);
        }

        boolean changed;

        do {

            changed = false;

            for (BpmnNode node : graph.getNodes()) {

                if (node.getIncoming().isEmpty()) {
                    continue;
                }

                int desiredLane = Integer.MAX_VALUE;

                for (Edge edge : node.getIncoming()) {

                    desiredLane = Math.min(
                            desiredLane,
                            edge.getSource().getLane());
                }

                if (desiredLane == Integer.MAX_VALUE) {
                    continue;
                }

                if (desiredLane >= node.getLane()) {
                    continue;
                }

                Map<Integer, BpmnNode> level =
                        occupied.get(node.getLevel());

                if (level.containsKey(desiredLane)) {
                    continue;
                }

                level.remove(node.getLane());

                node.setLane(desiredLane);

                level.put(desiredLane, node);

                changed = true;
            }

        } while (changed);

    }

}