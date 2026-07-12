package com.tnq.bpmnlayout.routing;

import com.tnq.bpmnlayout.model.Waypoint;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private final List<Waypoint> points =
            new ArrayList<>();

    public void addPoint(int x, int y) {
        points.add(new Waypoint(x, y));
    }
    
    public void addPoint(Waypoint point) {
        points.add(point);
    }

    public List<Waypoint> getPoints() {
        return points;
    }

    public void clear() {
        points.clear();
    }

}