package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.grid.GridPosition;
import com.tnq.bpmnlayout.model.Bounds;

public class LayoutNode {

    private int x;
    private int y;

    private final BpmnNode node;

    private GridPosition gridPosition;

    public LayoutNode(BpmnNode node) {
        this.node = node;
    }

    public BpmnNode getNode() {
        return node;
    }

    public GridPosition getGridPosition() {
        return gridPosition;
    }

    public void setGridPosition(GridPosition gridPosition) {
        this.gridPosition = gridPosition;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        node.setX(x);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        node.setY(y);
    }

    public int getWidth() {
        return node.getWidth();
    }

    public int getHeight() {
        return node.getHeight();
    }

    public Bounds getBounds() {
        return new Bounds(
                x,
                y,
                getWidth(),
                getHeight());
    }

    public int getCenterX() {
        return x + getWidth() / 2;
    }

    public int getCenterY() {
        return y + getHeight() / 2;
    }

    public int getLeftCenterX() {
        return x;
    }

    public int getLeftCenterY() {
        return getCenterY();
    }

    public int getRightCenterX() {
        return x + getWidth();
    }

    public int getRightCenterY() {
        return getCenterY();
    }

    public int getTopCenterX() {
        return getCenterX();
    }

    public int getTopCenterY() {
        return y;
    }

    public int getBottomCenterX() {
        return getCenterX();
    }

    public int getBottomCenterY() {
        return y + getHeight();
    }

}