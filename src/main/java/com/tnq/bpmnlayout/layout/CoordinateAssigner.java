package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.util.Constants;

public class CoordinateAssigner {

    public void assign(BpmnGraph graph) {

        for (BpmnNode node : graph.getNodes()) {

            int x =
                    Constants.LEFT_MARGIN +
                    node.getLevel() *
                    Constants.HORIZONTAL_SPACING;

            int y =
                    Constants.TOP_MARGIN +
                    node.getLane() *
                    Constants.VERTICAL_SPACING;

            LayoutNode layoutNode = node.getLayout();

            if (layoutNode == null) {
                layoutNode = new LayoutNode(node);
                node.setLayout(layoutNode);
            }

            layoutNode.setX(x);
            layoutNode.setY(y);
        }
    }
}