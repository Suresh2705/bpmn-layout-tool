package com.tnq.bpmnlayout.analysis;

import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;

public class LoopInfo {

    private Edge backEdge;

    private BpmnNode from;

    private BpmnNode to;

    public Edge getBackEdge() {
        return backEdge;
    }

    public void setBackEdge(Edge backEdge) {
        this.backEdge = backEdge;
    }

    public BpmnNode getFrom() {
        return from;
    }

    public void setFrom(BpmnNode from) {
        this.from = from;
    }

    public BpmnNode getTo() {
        return to;
    }

    public void setTo(BpmnNode to) {
        this.to = to;
    }
}