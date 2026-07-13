package com.tnq.bpmnlayout.writer;

import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.graph.BpmnNode;
import com.tnq.bpmnlayout.graph.Edge;
import com.tnq.bpmnlayout.layout.LayoutNode;
import com.tnq.bpmnlayout.layout.LayoutContext;
import com.tnq.bpmnlayout.model.Waypoint;
import com.tnq.bpmnlayout.routing.Route;
import com.tnq.bpmnlayout.util.Constants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;

public class BpmnDiWriter {

    public void write(
        File inputFile,
        File outputFile,
        LayoutContext context) throws Exception {

    Document document =
            loadDocument(inputFile);

    removeExistingDiagram(document);

    Element diagram =
            createDiagram(document);

    String processId =
            findProcessId(document);

    Element plane =
            createPlane(
                    document,
                    processId);

    diagram.appendChild(plane);

    document.getDocumentElement()
            .appendChild(diagram);

    writeShapes(
            document,
            plane,
            context);

    writeEdges(
            document,
            plane,
            context);

    saveDocument(
            document,
            outputFile);
    }

    private Document loadDocument(File file) throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
    
        factory.setNamespaceAware(true);
    
        DocumentBuilder builder =
                factory.newDocumentBuilder();
    
        Document document =
                builder.parse(file);
    
        document.getDocumentElement().normalize();
    
        return document;
    }
    
    private void saveDocument(
        Document document,
        File file) throws Exception {

    Transformer transformer =
            TransformerFactory
                    .newInstance()
                    .newTransformer();

    transformer.setOutputProperty(
            OutputKeys.INDENT,
            "yes");

    transformer.setOutputProperty(
            "{http://xml.apache.org/xslt}indent-amount",
            "2");

    transformer.transform(
            new DOMSource(document),
            new StreamResult(file));
    }

private void removeExistingDiagram(Document document) {

    NodeList diagrams =
            document.getElementsByTagNameNS(
                    Constants.BPMNDI_NS,
                    "BPMNDiagram");

    while (diagrams.getLength() > 0) {

        diagrams.item(0)
                .getParentNode()
                .removeChild(diagrams.item(0));

    }
}

private Element createDiagram(Document document) {

    Element diagram =
            document.createElementNS(
                    Constants.BPMNDI_NS,
                    "bpmndi:BPMNDiagram");

    diagram.setAttribute("id", "BPMNDiagram_1");
    diagram.setAttribute("name", "Generated Diagram");

    return diagram;
}

private Element createPlane(
    Document document,
    String processId) {

Element plane =
        document.createElementNS(
                Constants.BPMNDI_NS,
                "bpmndi:BPMNPlane");

plane.setAttribute(
        "id",
        "BPMNPlane_1");

plane.setAttribute(
        "bpmnElement",
        processId);

return plane;
}

private void writeShapes(
    Document document,
    Element plane,
    LayoutContext context) {

BpmnGraph graph = context.getGraph();

for (BpmnNode node : graph.getNodes()) {

    writeShape(
            document,
            plane,
            node);

}
}

private void writeShape(
    Document document,
    Element plane,
    BpmnNode node) {

LayoutNode layout = node.getLayout();

if (layout == null) {
    return;
}

Element shape =
        createElement(
                document,
                "bpmndi:BPMNShape");

shape.setAttribute(
        "id",
        node.getId() + "_di");

shape.setAttribute(
        "bpmnElement",
        node.getId());

shape.appendChild(
        createBounds(
                document,
                node));

plane.appendChild(shape);
}

private Element createBounds(
    Document document,
    BpmnNode node) {

LayoutNode layout = node.getLayout();

Element bounds =
        createElement(
                document,
                "dc:Bounds");

bounds.setAttribute(
        "x",
        Integer.toString(layout.getX()));

bounds.setAttribute(
        "y",
        Integer.toString(layout.getY()));

bounds.setAttribute(
        "width",
        Integer.toString(layout.getWidth()));

bounds.setAttribute(
        "height",
        Integer.toString(layout.getHeight()));

return bounds;
}

private void writeEdges(
    Document document,
    Element plane,
    LayoutContext context) {

BpmnGraph graph = context.getGraph();

for (Edge edge : graph.getEdges()) {

    writeEdge(
            document,
            plane,
            edge);

}
}

private void writeEdge(
    Document document,
    Element plane,
    Edge edge) {

Route route = edge.getRoute();

if (route == null || route.isEmpty()) {
    return;
}

Element edgeElement =
        createElement(
                document,
                "bpmndi:BPMNEdge");

edgeElement.setAttribute(
        "id",
        edge.getId() + "_di");

edgeElement.setAttribute(
        "bpmnElement",
        edge.getId());

writeWaypoints(
        document,
        edgeElement,
        edge);

plane.appendChild(edgeElement);
}

private void writeWaypoints(
    Document document,
    Element edgeElement,
    Edge edge) {

Route route = edge.getRoute();

for (Waypoint point : route.getPoints()) {

    Element waypoint =
            createElement(
                    document,
                    "di:waypoint");

    waypoint.setAttribute(
            "x",
            Integer.toString(point.getX()));

    waypoint.setAttribute(
            "y",
            Integer.toString(point.getY()));

    edgeElement.appendChild(waypoint);
}
}

    private Element createElement(
        Document document,
        String name) {

    if (name.startsWith("bpmndi:")) {

        return document.createElementNS(
                Constants.BPMNDI_NS,
                name);

    }

    if (name.startsWith("dc:")) {

        return document.createElementNS(
                Constants.DC_NS,
                name);

    }

    if (name.startsWith("di:")) {

        return document.createElementNS(
                Constants.DI_NS,
                name);

    }

    return document.createElement(name);
}

    private String findProcessId(Document document) {

        NodeList processes =
                document.getElementsByTagNameNS(
                        Constants.BPMN_NS,
                        "process");
    
        if (processes.getLength() == 0) {
            throw new IllegalStateException(
                    "No BPMN process found.");
        }
    
        Element process =
                (Element) processes.item(0);
    
        return process.getAttribute("id");
    }
}