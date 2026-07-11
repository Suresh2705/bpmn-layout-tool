package com.tnq.bpmnlayout.analysis;

import com.tnq.bpmnlayout.graph.BpmnNode;

import java.util.ArrayList;
import java.util.List;

public class BranchPath {

    private final List<BpmnNode> nodes = new ArrayList<>();

    public void add(BpmnNode node) {
        nodes.add(node);
    }

    public List<BpmnNode> getNodes() {
        return nodes;
    }

}