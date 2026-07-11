
package com.tnq.bpmnlayout.parser;

import com.tnq.bpmnlayout.graph.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 *
 * @author suresh
 */
public class BpmnParser {

    public BpmnGraph parse(File file) throws Exception {

        Document doc =
                DocumentBuilderFactory
                        .newInstance()
                        .newDocumentBuilder()
                        .parse(file);

        doc.getDocumentElement().normalize();

        BpmnGraph graph = new BpmnGraph();

        parseNodes(doc, graph);

        parseEdges(doc, graph);

        return graph;
    }

    private void parseNodes(Document doc, BpmnGraph graph) {

        NodeList all = doc.getElementsByTagName("*");

        for (int i = 0; i < all.getLength(); i++) {

            Element e = (Element) all.item(i);

            if (!e.hasAttribute("id"))
                continue;

            String tag = e.getLocalName();

            if (tag == null)
                tag = e.getTagName();

            NodeType type = map(tag);

            if (type == NodeType.UNKNOWN)
                continue;

            String id = e.getAttribute("id");
            String name = e.getAttribute("name");

            graph.addNode(new BpmnNode(id, name, type));

        }

    }

    private void parseEdges(Document doc, BpmnGraph graph) {

        NodeList list = doc.getElementsByTagName("*");

        for (int i = 0; i < list.getLength(); i++) {

            Element e = (Element) list.item(i);

            String tag = e.getLocalName();

            if (tag == null)
                tag = e.getTagName();

            if (!tag.endsWith("sequenceFlow"))
                continue;

            String edgeId = e.getAttribute("id");
            String sourceId = e.getAttribute("sourceRef");
            String targetId = e.getAttribute("targetRef");

            BpmnNode source = graph.getNode(sourceId);
            BpmnNode target = graph.getNode(targetId);

            if (source == null || target == null) {

                System.out.println(
                        "Warning: sequenceFlow "
                                + edgeId
                                + " references missing node.");

                continue;
            }

            Edge edge = new Edge(edgeId, source, target);

            graph.addEdge(edge);
        }
    }

    private NodeType map(String tag) {

        switch (tag) {

            case "startEvent":
                return NodeType.START_EVENT;

            case "endEvent":
                return NodeType.END_EVENT;

            case "userTask":
                return NodeType.USER_TASK;

            case "scriptTask":
                return NodeType.SCRIPT_TASK;

            case "serviceTask":
                return NodeType.SERVICE_TASK;

            case "businessRuleTask":
                return NodeType.BUSINESS_RULE_TASK;

            case "manualTask":
                return NodeType.MANUAL_TASK;

            case "sendTask":
                return NodeType.SEND_TASK;

            case "receiveTask":
                return NodeType.RECEIVE_TASK;

            case "callActivity":
                return NodeType.CALL_ACTIVITY;

            case "subProcess":
                return NodeType.SUB_PROCESS;

            case "exclusiveGateway":
                return NodeType.EXCLUSIVE_GATEWAY;

            case "parallelGateway":
                return NodeType.PARALLEL_GATEWAY;

            case "inclusiveGateway":
                return NodeType.INCLUSIVE_GATEWAY;

            case "eventBasedGateway":
                return NodeType.EVENT_GATEWAY;

            case "intermediateThrowEvent":
                return NodeType.INTERMEDIATE_THROW_EVENT;

            case "intermediateCatchEvent":
                return NodeType.INTERMEDIATE_CATCH_EVENT;

            default:
                return NodeType.UNKNOWN;
        }

    }

}