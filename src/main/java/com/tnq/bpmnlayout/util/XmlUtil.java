package com.tnq.bpmnlayout.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class XmlUtil {

    private XmlUtil() {
    }

    public static Element createElement(
            Document document,
            String name) {

        return document.createElement(name);
    }

    public static Element createElementNS(
            Document document,
            String namespace,
            String name) {

        return document.createElementNS(namespace, name);
    }

    public static void setAttribute(
            Element element,
            String name,
            Object value) {

        element.setAttribute(name, String.valueOf(value));
    }

}