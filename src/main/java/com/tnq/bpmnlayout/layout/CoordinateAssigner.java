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

            // Align centres, not top edges.  Events, gateways and tasks have
            // different heights; centre alignment keeps the backbone truly
            // horizontal and avoids pointless bends on its sequence flows.
            int y = Constants.TOP_MARGIN
                    + node.getLane() * Constants.VERTICAL_SPACING
                    + (Constants.CALL_ACTIVITY_HEIGHT - node.getHeight()) / 2;

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
