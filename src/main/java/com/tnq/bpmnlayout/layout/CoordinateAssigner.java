package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;

public class CoordinateAssigner {

    public void assign(LayoutContext context) {

        BpmnGraph graph = context.getGraph();

        GridLayoutEngine grid = context.getGrid();

        for (BpmnNode node : graph.getNodes()) {

            LayoutNode layoutNode =
                    grid.getLayoutNode(node);

            int x =
                    LayoutContext.LEFT_MARGIN
                            + layoutNode.getGridPosition().getColumn()
                            * LayoutContext.HORIZONTAL_SPACING;

            int y =
                    LayoutContext.TOP_MARGIN
                            + layoutNode.getGridPosition().getRow()
                            * LayoutContext.VERTICAL_SPACING;

            layoutNode.setX(x);
            layoutNode.setY(y);

            node.setLayout(layoutNode);
        }

    }

}