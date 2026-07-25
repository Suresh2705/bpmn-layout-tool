package com.tnq.bpmnlayout.analysis;

import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;

import java.util.*;

public class Backbone {

    private final List<BpmnNode> nodes =
            new ArrayList<>();

    private final List<Edge> edges =
            new ArrayList<>();

    private final Set<String> nodeIds =
            new HashSet<>();

    private final Set<String> edgeIds =
            new HashSet<>();

    public void addNode(BpmnNode node) {

        if (node == null) {
            return;
        }

        if (nodeIds.add(node.getId())) {
            nodes.add(node);
        }

    }

    public void addEdge(Edge edge) {

        if (edge == null) {
            return;
        }

        if (edgeIds.add(edge.getId())) {
            edges.add(edge);
        }

    }

    public boolean contains(BpmnNode node) {

        if (node == null) {
            return false;
        }

        return nodeIds.contains(node.getId());
    }

    public boolean contains(Edge edge) {

        if (edge == null) {
            return false;
        }

        return edgeIds.contains(edge.getId());
    }

    public List<BpmnNode> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public List<Edge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public int size() {
        return nodes.size();
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    public BpmnNode getFirst() {

        if (nodes.isEmpty()) {
            return null;
        }

        return nodes.get(0);

    }

    public BpmnNode getLast() {

        if (nodes.isEmpty()) {
            return null;
        }

        return nodes.get(nodes.size() - 1);

    }

    public Edge getNextEdge(BpmnNode node) {

        for (Edge edge : edges) {
    
            if (edge.getSource() == node) {
                return edge;
            }
    
        }
    
        return null;
    }

}