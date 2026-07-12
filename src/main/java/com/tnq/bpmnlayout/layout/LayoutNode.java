package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.grid.GridPosition;

public class LayoutNode {

    private final BpmnNode node;
    private GridPosition gridPosition;
    private int x;
    private int y;
    private int width;
    private int height;

    public LayoutNode(BpmnNode node) {

        this.node = node;

        this.width = node.getWidth();
        this.height = node.getHeight();

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
        return width;
    }

    public void setWidth(int width) {

        this.width = width;

    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {

        this.height = height;

    }

    /*
     * Connection Points
     */

    public int getCenterX() {
        return x + width / 2;
    }

    public int getCenterY() {
        return y + height / 2;
    }

    public int getLeftCenterX() {
        return x;
    }

    public int getLeftCenterY() {
        return y + height / 2;
    }

    public int getRightCenterX() {
        return x + width;
    }

    public int getRightCenterY() {
        return y + height / 2;
    }

    public int getTopCenterX() {
        return x + width / 2;
    }

    public int getTopCenterY() {
        return y;
    }

    public int getBottomCenterX() {
        return x + width / 2;
    }

    public int getBottomCenterY() {
        return y + height;
    }

}