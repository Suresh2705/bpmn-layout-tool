package com.tnq.bpmnlayout.grid;

import com.tnq.bpmnlayout.graph.BpmnNode;

public class GridCell {

    private final int row;
    private final int column;

    private BpmnNode node;

    public GridCell(int row, int column) {

        this.row = row;
        this.column = column;

    }

    public boolean isEmpty() {
        return node == null;
    }

    public BpmnNode getNode() {
        return node;
    }

    public void setNode(BpmnNode node) {
        this.node = node;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}