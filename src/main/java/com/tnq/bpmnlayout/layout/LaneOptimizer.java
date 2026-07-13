package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;

public class LaneOptimizer {

    public void optimize(BpmnGraph graph) {

        boolean changed;

        do {

            changed = false;

            for (BpmnNode node : graph.getNodes()) {

                if (canAlign(node)) {

                    int lane =
                            node.getIncoming()
                                    .get(0)
                                    .getSource()
                                    .getLane();

                    if (lane != node.getLane()) {

                        node.setLane(lane);

                        changed = true;

                    }

                }

            }

        } while (changed);

    }

    /**
     * Align only simple linear paths.
     *
     * A -> B -> C
     *
     * but never gateways or merges.
     */
    private boolean canAlign(BpmnNode node) {

        if (node.getIncoming().size() != 1) {
            return false;
        }

        if (node.getOutgoing().size() != 1) {
            return false;
        }

        BpmnNode parent =
                node.getIncoming()
                        .get(0)
                        .getSource();

        if (parent.getOutgoing().size() > 1) {
            return false;
        }

        BpmnNode child =
                node.getOutgoing()
                        .get(0)
                        .getTarget();

        if (child.getIncoming().size() > 1) {
            return false;
        }

        return true;

    }

}