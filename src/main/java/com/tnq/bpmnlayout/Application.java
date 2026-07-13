package com.tnq.bpmnlayout;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.layout.LayoutContext;
import com.tnq.bpmnlayout.layout.LayoutEngine;
import com.tnq.bpmnlayout.parser.BpmnParser;
import com.tnq.bpmnlayout.writer.BpmnDiWriter;

import java.io.File;

public class Application {

    public static void main(String[] args) {

        try {

            if (args.length != 2) {

                System.out.println("Usage:");
                System.out.println(
                        "java -jar bpmn-layout-tool.jar input.bpmn output.bpmn");
                return;
            }

            File inputFile = new File(args[0]);
            File outputFile = new File(args[1]);

            if (!inputFile.exists()) {

                System.out.println(
                        "Input file not found:");
                System.out.println(
                        inputFile.getAbsolutePath());
                return;
            }

            System.out.println("Reading BPMN...");

            BpmnParser parser = new BpmnParser();

            BpmnGraph graph = parser.parse(inputFile);

            System.out.println(
                    "Nodes : " + graph.getNodes().size());

            System.out.println("Generating layout...");

            LayoutEngine engine = new LayoutEngine();

            LayoutContext context =
                    engine.layout(graph);

            System.out.println("Writing BPMN DI...");

            BpmnDiWriter writer =
                    new BpmnDiWriter();

            writer.write(
                    inputFile,
                    outputFile,
                    context);

            System.out.println();
            System.out.println("Layout completed successfully.");
            System.out.println(
                    "Output file:");
            System.out.println(
                    outputFile.getAbsolutePath());

        } catch (Exception e) {

            System.err.println(
                    "Layout failed.");

            e.printStackTrace();
        }
    }
}