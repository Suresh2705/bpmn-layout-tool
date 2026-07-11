package com.tnq.bpmnlayout.layout;

import java.util.*;
import com.tnq.bpmnlayout.graph.*;

public class LaneAssigner {

    public void assignLanes(BpmnGraph graph) {

        Map<Integer, List<BpmnNode>> levels = new TreeMap<>();

        for (BpmnNode node : graph.getNodes()) {

            levels.computeIfAbsent(
                            node.getLevel(),
                            k -> new ArrayList<>())
                    .add(node);

        }

        int lane = 0;

        for (List<BpmnNode> levelNodes : levels.values()) {

            levelNodes.sort(
                    Comparator.comparing(BpmnNode::getId));

            for (BpmnNode node : levelNodes) {

                if (node.getLane() == -1) {

                    node.setLane(lane++);

                }

                for (Edge edge : node.getOutgoing()) {

                    BpmnNode child = edge.getTarget();

                    if (child.getLane() == -1) {

                        child.setLane(node.getLane());

                    }

                }

            }

        }

    }

}