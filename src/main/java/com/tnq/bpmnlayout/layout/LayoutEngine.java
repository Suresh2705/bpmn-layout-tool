package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.analysis.GraphAnalyzer;
import com.tnq.bpmnlayout.analysis.GraphStatistics;
import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.optimizer.CrossingReducer;
import com.tnq.bpmnlayout.optimizer.JoinDetector;
import com.tnq.bpmnlayout.optimizer.LoopRouter;
import com.tnq.bpmnlayout.routing.OrthogonalRouter;

public class LayoutEngine {

    private final GraphAnalyzer analyzer = new GraphAnalyzer();

    private final LevelAssigner levelAssigner = new LevelAssigner();

    private final LaneAssigner laneAssigner = new LaneAssigner();

    private final CoordinateAssigner coordinateAssigner =
            new CoordinateAssigner();

    private final JoinDetector joinDetector =
            new JoinDetector();

    private final LoopRouter loopRouter =
            new LoopRouter();

    private final CrossingReducer crossingReducer =
            new CrossingReducer();

    private final OrthogonalRouter orthogonalRouter =
            new OrthogonalRouter();

    public LayoutContext layout(BpmnGraph graph) {

        GraphStatistics stats =
                analyzer.analyze(graph);

        levelAssigner.assignLevels(graph);

        laneAssigner.assignLanes(graph);

        GridLayoutEngine grid =
                new GridLayoutEngine();

        grid.build(graph);

        LayoutContext context =
                new LayoutContext();

        context.setGraph(graph);

        context.setGrid(grid);

        coordinateAssigner.assign(context);

        orthogonalRouter.route(context);

        crossingReducer.reduce(context);

        return context;

    }
}