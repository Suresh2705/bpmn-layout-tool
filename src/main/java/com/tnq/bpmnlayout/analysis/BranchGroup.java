package com.tnq.bpmnlayout.analysis;

import com.tnq.bpmnlayout.graph.BpmnNode;

import java.util.ArrayList;
import java.util.List;

public class BranchGroup {

    private final BpmnNode splitGateway;

    private BpmnNode joinGateway;

    private final List<BranchPath> paths =
            new ArrayList<>();

    public BranchGroup(BpmnNode splitGateway) {
        this.splitGateway = splitGateway;
    }

    public BpmnNode getSplitGateway() {
        return splitGateway;
    }

    public void setJoinGateway(BpmnNode joinGateway) {
        this.joinGateway = joinGateway;
    }

    public BpmnNode getJoinGateway() {
        return joinGateway;
    }

    public void addPath(BranchPath path) {
        paths.add(path);
    }

    public List<BranchPath> getPaths() {
        return paths;
    }

}