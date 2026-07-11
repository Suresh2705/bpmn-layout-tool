package com.tnq.bpmnlayout.analysis;

import com.tnq.bpmnlayout.graph.*;

public class NodeClassifier {

    public void classify(BpmnGraph graph) {

        for (BpmnNode node : graph.getNodes()) {

            switch (node.getType()) {

                case START_EVENT:
                    node.setRole(NodeRole.START);
                    break;

                case END_EVENT:
                    node.setRole(NodeRole.END);
                    break;

                case CALL_ACTIVITY:
                    node.setRole(NodeRole.CALL_ACTIVITY);
                    break;

                case SUB_PROCESS:
                    node.setRole(NodeRole.SUBPROCESS);
                    break;

                case EXCLUSIVE_GATEWAY:
                case PARALLEL_GATEWAY:
                case INCLUSIVE_GATEWAY:
                case EVENT_GATEWAY:

                    classifyGateway(node);

                    break;

                default:

                    node.setRole(NodeRole.TASK);

            }

        }

    }

    private void classifyGateway(BpmnNode node) {

        int in = node.getIncoming().size();
        int out = node.getOutgoing().size();

        if (out > 1 && in <= 1) {

            node.setRole(NodeRole.SPLIT_GATEWAY);

        }
        else if (in > 1 && out <= 1) {

            node.setRole(NodeRole.JOIN_GATEWAY);

        }
        else {

            node.setRole(NodeRole.MIXED_GATEWAY);

        }

    }

}