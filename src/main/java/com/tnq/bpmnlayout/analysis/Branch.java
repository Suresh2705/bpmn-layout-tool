package com.tnq.bpmnlayout.analysis;

import com.tnq.bpmnlayout.graph.BpmnNode;

public class Branch {

    private final BpmnNode splitNode;
    private final BpmnNode firstNode;

    public Branch(
            BpmnNode splitNode,
            BpmnNode firstNode) {

        this.splitNode = splitNode;
        this.firstNode = firstNode;
    }

    public BpmnNode getSplitNode() {
        return splitNode;
    }

    public BpmnNode getFirstNode() {
        return firstNode;
    }

}