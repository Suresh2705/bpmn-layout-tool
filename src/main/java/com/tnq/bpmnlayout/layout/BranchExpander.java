package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.analysis.Backbone;
import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;

import java.util.LinkedList;
import java.util.Queue;

public class BranchExpander {

    public void expand(LayoutContext context) {

        Backbone backbone = context.getBackbone();

        for (BpmnNode node : backbone.getNodes()) {

            expandFrom(node, backbone);

        }

        // A valid BPMN file can contain disconnected artefacts (especially a
        // call activity).  They still need DI coordinates, but must not alter
        // the start-to-end backbone.
        int nextLevel = backbone.size();
        for (BpmnNode node : context.getGraph().getNodes()) {
            if (node.getLevel() == -1) {
                node.setLevel(nextLevel++);
            }
        }

    }

    private void expandFrom(
            BpmnNode splitNode,
            Backbone backbone) {

        Edge mainEdge =
                backbone.getNextEdge(splitNode);

        for (Edge edge : splitNode.getOutgoing()) {

            // Do not re-expand a loop or a merge into the backbone: changing a
            // backbone node here would destroy its fixed left-to-right level.
            if (edge == mainEdge || backbone.contains(edge.getTarget())) {
                continue;
            }

            expandBranch(
                    splitNode,
                    edge.getTarget(),
                    backbone);

        }

    }

    private void expandBranch(
            BpmnNode splitNode,
            BpmnNode startNode,
            Backbone backbone) {

        Queue<BpmnNode> queue =
                new LinkedList<>();

        startNode.setLevel(
                splitNode.getLevel() + 1);

        queue.add(startNode);

        while (!queue.isEmpty()) {

            BpmnNode current =
                    queue.poll();

            for (Edge edge : current.getOutgoing()) {

                BpmnNode target =
                        edge.getTarget();

                /*
                 * Branch rejoins backbone.
                 */
                if (backbone.contains(target)) {
                    continue;
                }

                int level =
                        current.getLevel() + 1;

                if (target.getLevel() == -1) {

                    target.setLevel(level);

                    queue.add(target);

                }

            }

        }

    }

}
