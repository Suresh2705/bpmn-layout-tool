package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.analysis.Backbone;
import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;

public class LevelAssigner {

    public void assignLevels(LayoutContext context) {

        BpmnGraph graph = context.getGraph();
        Backbone backbone = context.getBackbone();

        reset(graph);

        int level = 0;

        for (BpmnNode node : backbone.getNodes()) {

            node.setLevel(level++);

        }

    }

    private void reset(BpmnGraph graph) {

        for (BpmnNode node : graph.getNodes()) {

            node.setLevel(-1);

        }

    }

}