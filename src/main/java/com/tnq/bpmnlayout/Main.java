package com.tnq.bpmnlayout;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.layout.LayoutEngine;
import com.tnq.bpmnlayout.layout.LayoutNode;
import com.tnq.bpmnlayout.parser.BpmnParser;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("java -jar bpmn-layout-tool.jar input.bpmn");
            return;
        }

        File input = new File(args[0]);

        BpmnParser parser = new BpmnParser();

        BpmnGraph graph = parser.parse(input);

        System.out.println("Nodes : " + graph.getNodes().size());

        LayoutEngine engine = new LayoutEngine();

        engine.layout(graph);

        System.out.println("\n===== Layout Result =====\n");

        for (BpmnNode node : graph.getNodes()) {

            LayoutNode layout = node.getLayout();

            if (layout == null) {

                System.out.printf(
                        "%-30s  Level=%2d  Lane=%2d%n",
                        node.getId(),
                        node.getLevel(),
                        node.getLane());

            } else {

                System.out.printf(
                        "%-30s  Level=%2d  Lane=%2d  X=%4d  Y=%4d%n",
                        node.getId(),
                        node.getLevel(),
                        node.getLane(),
                        layout.getX(),
                        layout.getY());

            }

        }

    }

}