package com.tnq.bpmnlayout.grid;

import java.util.HashMap;
import java.util.Map;

public class Grid {

    private final Map<String, GridCell> cells =
            new HashMap<>();

    public GridCell get(int row, int column) {

        String key = row + ":" + column;

        return cells.computeIfAbsent(
                key,
                k -> new GridCell(row, column));

    }

    public void put(int row, int column, GridCell cell) {

        cells.put(row + ":" + column, cell);

    }

}