/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tnq.bpmnlayout.graph;

import java.util.ArrayList;
import java.util.List;

import com.tnq.bpmnlayout.analysis.NodeRole;
import com.tnq.bpmnlayout.layout.LayoutNode;

/**
 *
 * @author suresh
 */
public class BpmnNode {

    private String id;
    private String name;
    private NodeType type;
    private NodeRole role = NodeRole.UNKNOWN;
    private LayoutNode layout;
    private final List<Edge> outgoing;
    private final List<Edge> incoming;
    private int level = -1;
    private int lane = -1;
    private int x;
    private int y;

    public BpmnNode(String id, String name, NodeType type) {
        this.incoming = new ArrayList<>();
        this.outgoing = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public NodeType getType() {
        return type;
    }

    public List<Edge> getOutgoing() {
        return outgoing;
    }

    public List<Edge> getIncoming() {
        return incoming;
    }

    public void addOutgoing(Edge edge) {
        outgoing.add(edge);
    }

    public void addIncoming(Edge edge) {
        incoming.add(edge);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLane() {
        return lane;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    public NodeRole getRole() {
        return role;
    }

    public void setRole(NodeRole role) {
        this.role = role;
    }

    public LayoutNode getLayout() {
        return layout;
    }

    public void setLayout(LayoutNode layout) {
        this.layout = layout;
    }

    // Need to remove later
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRightCenterX() {
        return x + getWidth();
    }

    public int getRightCenterY() {
        return y + getHeight() / 2;
    }

    public int getLeftCenterX() {
        return x;
    }

    public int getLeftCenterY() {
        return y + getHeight() / 2;
    }

    public int getWidth() {

        switch (type) {

            case START_EVENT:
            case END_EVENT:
                return 36;

            case EXCLUSIVE_GATEWAY:
            case PARALLEL_GATEWAY:
            case INCLUSIVE_GATEWAY:
            case EVENT_GATEWAY:
                return 50;

            case CALL_ACTIVITY:
                return 140;
            default:
                return 120;
        }
    }

    public int getHeight() {

        switch (type) {

            case START_EVENT:
            case END_EVENT:
                return 36;

            case EXCLUSIVE_GATEWAY:
            case PARALLEL_GATEWAY:
            case INCLUSIVE_GATEWAY:
            case EVENT_GATEWAY:
                return 50;

            case CALL_ACTIVITY:
                return 48;
            default:
                return 48;
        }
    }

}
