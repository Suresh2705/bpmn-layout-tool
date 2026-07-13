package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;

import java.util.*;

public class LaneAssigner {

    public void assignLanes(BpmnGraph graph) {

        reset(graph);

        Map<Integer, List<BpmnNode>> levels =
                new TreeMap<>();

        for (BpmnNode node : graph.getNodes()) {

            levels.computeIfAbsent(
                    node.getLevel(),
                    k -> new ArrayList<>())
                    .add(node);

        }

        int nextLane = 0;

        for (List<BpmnNode> nodes : levels.values()) {

            nodes.sort(
                    Comparator
                            .comparingInt(this::parentLane)
                            .thenComparing(BpmnNode::getId));

            for (BpmnNode node : nodes) {

                node.setLane(nextLane++);

            }

        }

    }

    private int parentLane(BpmnNode node) {

        if (node.getIncoming().isEmpty()) {
            return -1;
        }

        return node.getIncoming()
                .get(0)
                .getSource()
                .getLane();

    }

    private void reset(BpmnGraph graph) {

        for (BpmnNode node : graph.getNodes()) {

            node.setLane(-1);

        }

    }

}