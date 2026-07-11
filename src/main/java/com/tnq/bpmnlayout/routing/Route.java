package com.tnq.bpmnlayout.routing;

import com.tnq.bpmnlayout.model.Point;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private final List<Point> points =
            new ArrayList<>();

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    public List<Point> getPoints() {
        return points;
    }

}