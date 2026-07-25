package com.tnq.bpmnlayout.layout;

import java.util.PriorityQueue;

public class LanePool {

    private final PriorityQueue<Integer> free =
            new PriorityQueue<>();

    private int nextLane = 1;

    public int acquire() {

        if (!free.isEmpty()) {
            return free.poll();
        }

        return nextLane++;

    }

    public void release(int lane) {

        if (lane > 0) {
            free.offer(lane);
        }

    }

}