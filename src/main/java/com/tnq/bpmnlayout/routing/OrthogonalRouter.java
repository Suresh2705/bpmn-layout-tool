package com.tnq.bpmnlayout.routing;

import com.tnq.bpmnlayout.graph.BpmnGraph;
// import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;
import com.tnq.bpmnlayout.layout.LayoutContext;
import com.tnq.bpmnlayout.layout.LayoutNode;

public class OrthogonalRouter {

    public void route(LayoutContext context) {

        BpmnGraph graph = context.getGraph();

        for (Edge edge : graph.getEdges()) {

            LayoutNode source = edge.getSource().getLayout();
            LayoutNode target = edge.getTarget().getLayout();

            if (source == null || target == null) {
                continue;
            }

            Route route = buildRoute(source, target);

            edge.setRoute(route);
        }
    }

    private Route buildRoute(
            LayoutNode source,
            LayoutNode target) {

        Route route = new Route();

        int sx = source.getRightCenterX();
        int sy = source.getRightCenterY();

        int tx = target.getLeftCenterX();
        int ty = target.getLeftCenterY();

        /*
         * Same row
         */
        if (sy == ty) {

            route.addPoint(sx, sy);
            route.addPoint(tx, ty);

            return route;
        }

        /*
         * Same column
         */
        if (sx == tx) {

            route.addPoint(sx, sy);
            route.addPoint(tx, ty);

            return route;
        }

        /*
         * Manhattan routing
         */

        int midX = (sx + tx) / 2;

        route.addPoint(sx, sy);
        route.addPoint(midX, sy);
        route.addPoint(midX, ty);
        route.addPoint(tx, ty);

        return route;
    }

}