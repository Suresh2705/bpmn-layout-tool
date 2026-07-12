package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;
// import com.tnq.bpmnlayout.graph.NodeType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BranchBalancer {

    /**
     * Balances the immediate branches of every split gateway.
     *
     * This is intentionally simple. We only reposition the first node of each
     * outgoing branch. The remaining nodes keep their relative positions.
     *
     * Later phases will extend this to balance complete branch paths.
     */
    public void balance(BpmnGraph graph) {

        for (BpmnNode gateway : graph.getNodes()) {

            if (!isSplitGateway(gateway)) {
                continue;
            }

            List<BpmnNode> children = new ArrayList<>();

            for (Edge edge : gateway.getOutgoing()) {

                BpmnNode child = edge.getTarget();

                if (child != null) {
                    children.add(child);
                }
            }

            if (children.size() < 2) {
                continue;
            }

            balanceChildren(children);
        }
    }

    private boolean isSplitGateway(BpmnNode node) {

        if (node.getOutgoing().size() < 2) {
            return false;
        }

        switch (node.getType()) {

            case EXCLUSIVE_GATEWAY:
            case PARALLEL_GATEWAY:
            case INCLUSIVE_GATEWAY:
            case EVENT_GATEWAY:
                return true;

            default:
                return false;
        }
    }

    /**
     * Reassigns lanes so the branches become consecutive.
     *
     * Example:
     *
     * 2,6,9  -> 2,3,4
     * 5,8    -> 5,6
     */
    private void balanceChildren(List<BpmnNode> children) {

        children.sort(
                Comparator.comparingInt(BpmnNode::getLane));

        int firstLane = children.get(0).getLane();

        for (int i = 0; i < children.size(); i++) {

            children.get(i).setLane(firstLane + i);
        }
    }

}