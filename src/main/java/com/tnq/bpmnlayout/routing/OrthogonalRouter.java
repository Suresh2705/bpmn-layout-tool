package com.tnq.bpmnlayout.routing;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.Edge;
import com.tnq.bpmnlayout.layout.LayoutContext;

public class OrthogonalRouter {

    public void route(LayoutContext context) {

        BpmnGraph graph = context.getGraph();

        for (Edge edge : graph.getEdges()) {

            // Routing implementation
            // will be added in next phase.

        }

    }

}