package com.tnq.bpmnlayout.grid;

public class GridOccupancy {

    private final Grid grid = new Grid();

    public boolean occupied(int row, int column) {

        return !grid.get(row, column).isEmpty();

    }

    public void occupy(
            int row,
            int column,
            com.tnq.bpmnlayout.graph.BpmnNode node) {

        grid.get(row, column).setNode(node);

    }

    public Grid getGrid() {
        return grid;
    }

}