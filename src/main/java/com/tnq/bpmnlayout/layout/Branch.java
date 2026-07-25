package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Branch {

    private final BpmnNode splitNode;

    private BpmnNode mergeNode;

    private int lane = -1;

    private final List<BpmnNode> nodes =
            new ArrayList<>();

    public Branch(BpmnNode splitNode) {
        this.splitNode = splitNode;
    }

    public BpmnNode getSplitNode() {
        return splitNode;
    }

    public BpmnNode getMergeNode() {
        return mergeNode;
    }

    public void setMergeNode(BpmnNode mergeNode) {
        this.mergeNode = mergeNode;
    }

    public int getLane() {
        return lane;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    public void addNode(BpmnNode node) {

        if (!nodes.contains(node)) {
            nodes.add(node);
        }

    }

    public List<BpmnNode> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

}