package com.tnq.bpmnlayout.graph;

import java.util.*;

public class BpmnGraph {

    private final Map<String,BpmnNode> nodes =
            new LinkedHashMap<>();

    private final List<Edge> edges =
            new ArrayList<>();

    public void addNode(BpmnNode node){
        nodes.put(node.getId(),node);
    }

    public BpmnNode getNode(String id){
        return nodes.get(id);
    }

    public Collection<BpmnNode> getNodes(){
        return nodes.values();
    }

    public List<Edge> getEdges(){
        return edges;
    }

    public void addEdge(Edge edge) {

        edges.add(edge);
    
        if (edge.getSource() != null) {
            edge.getSource().addOutgoing(edge);
        }
    
        if (edge.getTarget() != null) {
            edge.getTarget().addIncoming(edge);
        }
    }
}