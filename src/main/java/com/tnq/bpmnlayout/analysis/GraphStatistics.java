package com.tnq.bpmnlayout.analysis;

import java.util.ArrayList;
import java.util.List;

public class GraphStatistics {

    private int maxLevel;

    private int maxLane;

    private final List<BranchGroup> branches =
            new ArrayList<>();

    private final List<LoopInfo> loops =
            new ArrayList<>();

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getMaxLane() {
        return maxLane;
    }

    public void setMaxLane(int maxLane) {
        this.maxLane = maxLane;
    }

    public List<BranchGroup> getBranches() {
        return branches;
    }

    public List<LoopInfo> getLoops() {
        return loops;
    }

}