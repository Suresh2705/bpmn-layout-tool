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

    private final BranchBalancer branchBalancer =
        new BranchBalancer();

    private final LaneOptimizer laneOptimizer =
        new LaneOptimizer();

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

        System.out.println("1. Graph analysis");
        GraphStatistics stats = analyzer.analyze(graph);

        System.out.println("2. Level assign");
        levelAssigner.assignLevels(graph);

        System.out.println("3. Lane assign");
        laneAssigner.assignLanes(graph);

        System.out.println("4. Lane optimize");
        laneOptimizer.optimize(graph);

        System.out.println("5. Branch balance");
        branchBalancer.balance(graph);

        System.out.println("6. Grid");
        GridLayoutEngine grid = new GridLayoutEngine();
        grid.build(graph);

        LayoutContext context = new LayoutContext();
        context.setGraph(graph);
        context.setStatistics(stats);
        context.setGrid(grid);

        // coordinateAssigner.assign(context);
        System.out.println("7. Coordinate");
        coordinateAssigner.assign(graph);

        System.out.println("8. Routing");
        orthogonalRouter.route(context);

        System.out.println("9. Crossing");
        crossingReducer.reduce(context);

        System.out.println("DONE");
        return context;

    }
}