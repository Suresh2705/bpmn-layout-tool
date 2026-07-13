package com.tnq.bpmnlayout.util;

public final class Constants {

    private Constants() {
    }

    /*
     * Layout
     */

    public static final int LEFT_MARGIN = 80;
    public static final int TOP_MARGIN = 80;

    public static final int HORIZONTAL_SPACING = 220;
    public static final int VERTICAL_SPACING = 120;

    /*
     * Node sizes
     */

    public static final int EVENT_SIZE = 36;

    public static final int GATEWAY_SIZE = 50;

    public static final int TASK_WIDTH = 120;
    public static final int TASK_HEIGHT = 80;

    public static final int CALL_ACTIVITY_WIDTH = 140;
    public static final int CALL_ACTIVITY_HEIGHT = 90;

    /*
     * BPMN Namespaces
     */

    public static final String BPMN_NS =
        "http://www.omg.org/spec/BPMN/20100524/MODEL";

    public static final String BPMNDI_NS =
            "http://www.omg.org/spec/BPMN/20100524/DI";

    public static final String DI_NS =
            "http://www.omg.org/spec/DD/20100524/DI";

    public static final String DC_NS =
            "http://www.omg.org/spec/DD/20100524/DC";

}