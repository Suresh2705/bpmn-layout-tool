package com.tnq.bpmnlayout.analysis;

public enum NodeRole {

    START,

    END,

    TASK,

    SPLIT_GATEWAY,

    JOIN_GATEWAY,

    MIXED_GATEWAY,

    CALL_ACTIVITY,

    SUBPROCESS,

    EVENT,

    UNKNOWN

}