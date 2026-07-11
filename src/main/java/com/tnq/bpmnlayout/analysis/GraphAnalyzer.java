package com.tnq.bpmnlayout.analysis;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;

import java.util.*;

public class GraphAnalyzer {

    public GraphStatistics analyze(BpmnGraph graph) {

        GraphStatistics stats = new GraphStatistics();

        for (BpmnNode node : graph.getNodes()) {

            // Track maximum level
            if (node.getLevel() > stats.getMaxLevel()) {
                stats.setMaxLevel(node.getLevel());
            }

            // Track maximum lane
            if (node.getLane() > stats.getMaxLane()) {
                stats.setMaxLane(node.getLane());
            }

            // Detect branch gateways
            if (node.getOutgoing().size() > 1) {

                BranchGroup group = analyzeBranch(node);

                stats.getBranches().add(group);
            }
        }

        return stats;
    }

    private BranchGroup analyzeBranch(BpmnNode split) {

        BranchGroup group = new BranchGroup(split);

        for (Edge edge : split.getOutgoing()) {

            BranchPath path = new BranchPath();

            walk(edge.getTarget(), path, new HashSet<>());

            group.addPath(path);
        }

        group.setJoinGateway(findJoin(group));

        return group;
    }

    private void walk(
            BpmnNode node,
            BranchPath path,
            Set<BpmnNode> visited) {

        if (!visited.add(node))
            return;

        path.add(node);

        if (node.getOutgoing().size() == 1) {

            walk(
                    node.getOutgoing().get(0).getTarget(),
                    path,
                    visited);

        }
    }

    private BpmnNode findJoin(BranchGroup group) {

        Map<BpmnNode,Integer> count =
                new HashMap<>();

        for (BranchPath path : group.getPaths()) {

            for (BpmnNode node : path.getNodes()) {

                count.merge(node,1,Integer::sum);

            }

        }

        int required = group.getPaths().size();

        for (Map.Entry<BpmnNode,Integer> e : count.entrySet()) {

            if (e.getValue() == required) {
                return e.getKey();
            }

        }

        return null;
    }

}