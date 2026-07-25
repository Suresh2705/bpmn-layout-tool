package com.tnq.bpmnlayout.routing;

import com.tnq.bpmnlayout.analysis.Backbone;
import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;
import com.tnq.bpmnlayout.layout.LayoutContext;
import com.tnq.bpmnlayout.layout.LayoutNode;

import java.util.ArrayList;
import java.util.List;

/** Routes sequence flows with separate boundary ports and bend channels. */
public class OrthogonalRouter {
    private static final int CHANNEL_GAP = 22;
    private static final int LOOP_CLEARANCE = 55;
    private static final int OBSTACLE_CLEARANCE = 28;
    private final List<DetourTrack> detourTracks = new ArrayList<DetourTrack>();

    public void route(LayoutContext context) {
        BpmnGraph graph = context.getGraph();
        Backbone backbone = context.getBackbone();
        int bottom = bottomOf(graph) + LOOP_CLEARANCE;
        detourTracks.clear();

        for (Edge edge : graph.getEdges()) {
            LayoutNode source = edge.getSource().getLayout();
            LayoutNode target = edge.getTarget().getLayout();
            if (source != null && target != null) {
                edge.setRoute(buildRoute(edge, source, target, backbone, graph, bottom));
            }
        }
    }

    private Route buildRoute(Edge edge, LayoutNode source, LayoutNode target,
                             Backbone backbone, BpmnGraph graph, int bottom) {
        int sourceIndex = edge.getSource().getOutgoing().indexOf(edge);
        int targetIndex = edge.getTarget().getIncoming().indexOf(edge);
        int sx = source.getRightCenterX();
        boolean mainFlow = backbone.contains(edge);
        int sy = mainFlow ? source.getRightCenterY()
                : portY(source, sourceIndex, edge.getSource().getOutgoing().size());
        int tx = target.getLeftCenterX();
        int ty = mainFlow ? target.getLeftCenterY()
                : portY(target, targetIndex, edge.getTarget().getIncoming().size());
        Route route = new Route();
        route.addPoint(sx, sy);

        boolean obstacleBetween = sy == ty && hasObstacleBetween(graph, edge, sx, tx, sy);
        boolean simpleContinuation = edge.getSource().getOutgoing().size() == 1
                && edge.getTarget().getIncoming().size() == 1
                && edge.getSource().getLane() == edge.getTarget().getLane()
                && sy == ty;
        if (((mainFlow && sy == ty) || simpleContinuation) && !obstacleBetween) {
            route.addPoint(tx, ty);
        } else if (target.getX() > source.getX() && obstacleBetween) {
            routeAroundObstacles(route, graph, edge, sx, sy, tx, ty);
        } else if (target.getX() > source.getX()) {
            // Each source gets unique nearby channels, so sibling flows do not
            // overlap as they leave the source element.
            int channelX = sx + LOOP_CLEARANCE + sourceIndex * CHANNEL_GAP;
            route.addPoint(channelX, sy);
            route.addPoint(channelX, ty);
            route.addPoint(tx, ty);
        } else {
            // A back edge is taken below the diagram instead of cutting through it.
            route.addPoint(sx + LOOP_CLEARANCE + sourceIndex * CHANNEL_GAP, sy);
            route.addPoint(sx + LOOP_CLEARANCE + sourceIndex * CHANNEL_GAP, bottom);
            route.addPoint(tx - LOOP_CLEARANCE - targetIndex * CHANNEL_GAP, bottom);
            route.addPoint(tx - LOOP_CLEARANCE - targetIndex * CHANNEL_GAP, ty);
            route.addPoint(tx, ty);
        }
        return route;
    }

    /** Returns true when a same-row flow would pass through another shape. */
    private boolean hasObstacleBetween(
            BpmnGraph graph, Edge edge, int sx, int tx, int y) {

        if (tx <= sx) {
            return false;
        }
        for (BpmnNode node : graph.getNodes()) {
            if (node == edge.getSource() || node == edge.getTarget()
                    || node.getLayout() == null) {
                continue;
            }
            LayoutNode bounds = node.getLayout();
            boolean overlapsHorizontally = bounds.getX() < tx
                    && bounds.getX() + bounds.getWidth() > sx;
            boolean crossesFlowRow = bounds.getY() < y
                    && bounds.getY() + bounds.getHeight() > y;
            if (overlapsHorizontally && crossesFlowRow) {
                return true;
            }
        }
        return false;
    }

    private void routeAroundObstacles(
            Route route, BpmnGraph graph, Edge edge,
            int sx, int sy, int tx, int ty) {

        int above = Math.min(sy, ty) - OBSTACLE_CLEARANCE;
        int below = Math.max(sy, ty) + OBSTACLE_CLEARANCE;
        int detourY = firstClearChannel(graph, edge, sx, tx, above, -OBSTACLE_CLEARANCE);
        if (detourY == Integer.MIN_VALUE) {
            detourY = firstClearChannel(graph, edge, sx, tx, below, OBSTACLE_CLEARANCE);
        }

        detourTracks.add(new DetourTrack(sx, tx, detourY, edge.getTarget().getId()));
        if (detourY == Integer.MIN_VALUE) {
            // This is extremely unlikely, but still produces a valid route.
            detourY = above;
        }

        route.addPoint(sx + CHANNEL_GAP, sy);
        route.addPoint(sx + CHANNEL_GAP, detourY);
        route.addPoint(tx - CHANNEL_GAP, detourY);
        route.addPoint(tx - CHANNEL_GAP, ty);
        route.addPoint(tx, ty);
    }

    private int firstClearChannel(
            BpmnGraph graph, Edge edge, int sx, int tx, int candidate, int step) {

        for (int attempt = 0; attempt < 20; attempt++) {
            if (isHorizontalChannelClear(graph, edge, sx, tx, candidate)
                    && isDetourTrackFree(edge, sx, tx, candidate)) {
                return candidate;
            }
            candidate += step;
        }
        return Integer.MIN_VALUE;
    }

    private boolean isHorizontalChannelClear(
            BpmnGraph graph, Edge edge, int sx, int tx, int y) {

        for (BpmnNode node : graph.getNodes()) {
            if (node == edge.getSource() || node == edge.getTarget()
                    || node.getLayout() == null) {
                continue;
            }
            LayoutNode bounds = node.getLayout();
            if (bounds.getX() < tx && bounds.getX() + bounds.getWidth() > sx
                    && bounds.getY() <= y && bounds.getY() + bounds.getHeight() >= y) {
                return false;
            }
        }
        return true;
    }

    /**
     * Different destinations receive separate horizontal detour tracks.
     * Incoming flows to the same target may intentionally share a track.
     */
    private boolean isDetourTrackFree(Edge edge, int sx, int tx, int y) {
        for (DetourTrack track : detourTracks) {
            if (track.y != y || edge.getTarget().getId().equals(track.targetId)) {
                continue;
            }
            if (track.startX < tx && sx < track.endX) {
                return false;
            }
        }
        return true;
    }

    private static final class DetourTrack {
        private final int startX;
        private final int endX;
        private final int y;
        private final String targetId;

        private DetourTrack(int startX, int endX, int y, String targetId) {
            this.startX = startX;
            this.endX = endX;
            this.y = y;
            this.targetId = targetId;
        }
    }

    private int portY(LayoutNode node, int index, int count) {
        if (count <= 1) {
            return node.getRightCenterY();
        }
        // Keep a small margin, preserving visibly distinct ports even for gateways.
        int usableHeight = Math.max(8, node.getHeight() - 12);
        int port = node.getY() + 6 + (usableHeight * (index + 1)) / (count + 1);
        if (port == node.getRightCenterY()) {
            port += Math.max(4, usableHeight / (count + 1));
        }
        return port;
    }

    private int bottomOf(BpmnGraph graph) {
        int bottom = 0;
        for (BpmnNode node : graph.getNodes()) {
            if (node.getLayout() != null) {
                bottom = Math.max(bottom, node.getLayout().getY() + node.getLayout().getHeight());
            }
        }
        return bottom;
    }
}
