package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.grid.GridPosition;

public class LayoutNode {

    private final BpmnNode node;

    private int x;
    private int y;
    private int width;
    private int height;
    private int centerX;
    private int centerY;
    private int leftCenterX;
    private int rightCenterX;
    private int topCenterY;
    private int bottomCenterY;

    private GridPosition gridPosition;

    public LayoutNode(BpmnNode node) {
        this.node = node;
    }

    public BpmnNode getNode() {
        return node;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLeftCenterX() {
        return x;
    }

    public int getRightCenterX() {
        return x + width;
    }

    public int getCenterY() {
        return y + (height / 2);
    }

    public int getCenterX() {
        return x + (width / 2);
    }

    public int getTopCenterY() {
        return y;
    }

    public int getBottomCenterY() {
        return y + height;
    }

    public GridPosition getGridPosition() {
        return gridPosition;
    }

    public void setWidth(int width){
        this.width=width;
    }

    public void setHeight(int height){
        this.height=height;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setGridPosition(GridPosition gridPosition) {
        this.gridPosition = gridPosition;
    }

}