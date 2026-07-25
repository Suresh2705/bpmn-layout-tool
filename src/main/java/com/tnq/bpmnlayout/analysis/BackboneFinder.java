package com.tnq.bpmnlayout.analysis;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;
import com.tnq.bpmnlayout.graph.NodeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Finds the longest forward start-to-end path in linear time.
 *
 * <p>The exact longest <em>simple</em> path in a BPMN graph with loops is an
 * NP-hard problem.  The previous recursive implementation tried every simple
 * path, so a process with many gateways could appear to hang.  We first remove
 * DFS back-edges (the loop flows), leaving a DAG, then apply dynamic
 * programming to that DAG.  The removed flows remain in the BPMN and are
 * routed separately; only their placement is excluded from the backbone.</p>
 */
public class BackboneFinder {

    public Backbone find(BpmnGraph graph) {
        Set<Edge> loopEdges = findLoopEdges(graph);
        List<BpmnNode> order = topologicalOrder(graph, loopEdges);
        Map<BpmnNode, Integer> distance = new HashMap<BpmnNode, Integer>();
        Map<BpmnNode, Edge> previous = new HashMap<BpmnNode, Edge>();

        List<BpmnNode> starts = startNodes(graph);
        for (BpmnNode start : starts) {
            distance.put(start, 1);
        }

        for (BpmnNode node : order) {
            Integer nodeDistance = distance.get(node);
            if (nodeDistance == null) {
                continue;
            }
            for (Edge edge : node.getOutgoing()) {
                if (loopEdges.contains(edge)) {
                    continue;
                }
                BpmnNode target = edge.getTarget();
                int candidate = nodeDistance + 1;
                Integer current = distance.get(target);
                if (current == null || candidate > current) {
                    distance.put(target, candidate);
                    previous.put(target, edge);
                }
            }
        }

        BpmnNode end = bestEnd(graph, distance);
        return buildBackbone(end, previous);
    }

    private List<BpmnNode> startNodes(BpmnGraph graph) {
        List<BpmnNode> starts = new ArrayList<BpmnNode>();
        for (BpmnNode node : graph.getNodes()) {
            if (node.getType() == NodeType.START_EVENT) {
                starts.add(node);
            }
        }
        if (starts.isEmpty()) {
            for (BpmnNode node : graph.getNodes()) {
                if (node.getIncoming().isEmpty()) {
                    starts.add(node);
                }
            }
        }
        return starts;
    }

    private BpmnNode bestEnd(BpmnGraph graph, Map<BpmnNode, Integer> distance) {
        BpmnNode best = null;
        for (BpmnNode node : graph.getNodes()) {
            if (!distance.containsKey(node)) {
                continue;
            }
            if (best == null || (node.getType() == NodeType.END_EVENT
                    && best.getType() != NodeType.END_EVENT)
                    || (node.getType() == best.getType()
                    && distance.get(node) > distance.get(best))) {
                best = node;
            }
        }
        return best;
    }

    private Backbone buildBackbone(BpmnNode end, Map<BpmnNode, Edge> previous) {
        Backbone backbone = new Backbone();
        if (end == null) {
            return backbone;
        }
        List<BpmnNode> nodes = new ArrayList<BpmnNode>();
        List<Edge> edges = new ArrayList<Edge>();
        BpmnNode current = end;
        nodes.add(current);
        while (previous.containsKey(current)) {
            Edge edge = previous.get(current);
            edges.add(edge);
            current = edge.getSource();
            nodes.add(current);
        }
        Collections.reverse(nodes);
        Collections.reverse(edges);
        for (BpmnNode node : nodes) {
            backbone.addNode(node);
        }
        for (Edge edge : edges) {
            backbone.addEdge(edge);
        }
        return backbone;
    }

    private Set<Edge> findLoopEdges(BpmnGraph graph) {
        Set<Edge> loops = new HashSet<Edge>();
        Map<BpmnNode, Integer> colour = new HashMap<BpmnNode, Integer>();
        for (BpmnNode node : graph.getNodes()) {
            if (!colour.containsKey(node)) {
                visit(node, colour, loops);
            }
        }
        return loops;
    }

    private void visit(BpmnNode node, Map<BpmnNode, Integer> colour, Set<Edge> loops) {
        colour.put(node, 1);
        for (Edge edge : node.getOutgoing()) {
            Integer targetColour = colour.get(edge.getTarget());
            if (targetColour == null) {
                visit(edge.getTarget(), colour, loops);
            } else if (targetColour == 1) {
                loops.add(edge);
            }
        }
        colour.put(node, 2);
    }

    private List<BpmnNode> topologicalOrder(BpmnGraph graph, Set<Edge> loopEdges) {
        Map<BpmnNode, Integer> indegree = new HashMap<BpmnNode, Integer>();
        for (BpmnNode node : graph.getNodes()) {
            indegree.put(node, 0);
        }
        for (Edge edge : graph.getEdges()) {
            if (!loopEdges.contains(edge)) {
                indegree.put(edge.getTarget(), indegree.get(edge.getTarget()) + 1);
            }
        }
        List<BpmnNode> ready = new ArrayList<BpmnNode>();
        for (BpmnNode node : graph.getNodes()) {
            if (indegree.get(node) == 0) {
                ready.add(node);
            }
        }
        List<BpmnNode> ordered = new ArrayList<BpmnNode>();
        for (int index = 0; index < ready.size(); index++) {
            BpmnNode node = ready.get(index);
            ordered.add(node);
            for (Edge edge : node.getOutgoing()) {
                if (!loopEdges.contains(edge)) {
                    BpmnNode target = edge.getTarget();
                    int next = indegree.get(target) - 1;
                    indegree.put(target, next);
                    if (next == 0) {
                        ready.add(target);
                    }
                }
            }
        }
        return ordered;
    }
}
