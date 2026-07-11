package com.tnq.bpmnlayout.optimizer;

import com.tnq.bpmnlayout.graph.*;

public class JoinDetector {

    public void detect(BpmnGraph graph) {

        for (BpmnNode node : graph.getNodes()) {

            if (node.getIncoming().size() > 1) {

                System.out.println(
                        "Join Gateway : "
                                + node.getId());

            }

        }

    }

}