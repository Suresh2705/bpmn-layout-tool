package com.tnq.bpmnlayout.optimizer;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;

import java.util.HashSet;
import java.util.Set;

public class LoopRouter {

    private final Set<Edge> loopEdges = new HashSet<>();

    public void routeLoops(BpmnGraph graph) {

        loopEdges.clear();

        Set<BpmnNode> visited = new HashSet<>();
        Set<BpmnNode> stack = new HashSet<>();

        for (BpmnNode node : graph.getNodes()) {
            if (!visited.contains(node)) {
                dfs(node, visited, stack);
            }
        }
    }

    private void dfs(BpmnNode node,
                     Set<BpmnNode> visited,
                     Set<BpmnNode> stack) {

        visited.add(node);
        stack.add(node);

        for (Edge edge : node.getOutgoing()) {

            BpmnNode target = edge.getTarget();

            if (!visited.contains(target)) {

                dfs(target, visited, stack);

            } else if (stack.contains(target)) {

                // Back edge -> loop detected
                loopEdges.add(edge);
            }
        }

        stack.remove(node);
    }

    public boolean isLoopEdge(Edge edge) {
        return loopEdges.contains(edge);
    }

    public Set<Edge> getLoopEdges() {
        return loopEdges;
    }
}