package com.tnq.bpmnlayout.graph;

import com.tnq.bpmnlayout.routing.Route;

public class Edge {

    private final String id;
    private final BpmnNode source;
    private final BpmnNode target;
    private Route route;

    public Edge(
            String id,
            BpmnNode source,
            BpmnNode target){

        this.id=id;
        this.source=source;
        this.target=target;
    }

    public String getId(){
        return id;
    }

    public BpmnNode getSource(){
        return source;
    }

    public BpmnNode getTarget(){
        return target;
    }

    public Route getRoute(){
        return route;
    }

    public void setRoute(Route route){
        this.route=route;
    }

}