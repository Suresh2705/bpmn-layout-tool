package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;

import java.util.*;

public class LevelAssigner {

    public void assignLevels(BpmnGraph graph) {

        reset(graph);

        BpmnNode start = findStart(graph);

        if (start == null) {
            throw new IllegalStateException("StartEvent not found");
        }

        bfs(start);
    }

    private void bfs(BpmnNode start) {

        Queue<BpmnNode> queue = new LinkedList<>();

        start.setLevel(0);

        queue.add(start);

        while (!queue.isEmpty()) {

            BpmnNode current = queue.poll();

            int nextLevel = current.getLevel() + 1;

            for (Edge edge : current.getOutgoing()) {

                BpmnNode target = edge.getTarget();

                if (target.getLevel() < nextLevel) {

                    target.setLevel(nextLevel);

                    queue.add(target);
                }
            }
        }
    }

    private BpmnNode findStart(BpmnGraph graph) {

        for (BpmnNode node : graph.getNodes()) {

            if (node.getIncoming().isEmpty()) {
                return node;
            }
        }

        return null;
    }

    private void reset(BpmnGraph graph) {

        for (BpmnNode node : graph.getNodes()) {

            node.setLevel(-1);
        }

    }

}
