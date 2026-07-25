package com.tnq.bpmnlayout.layout;

import com.tnq.bpmnlayout.analysis.Backbone;
import com.tnq.bpmnlayout.analysis.BackboneFinder;
import com.tnq.bpmnlayout.analysis.GraphAnalyzer;
import com.tnq.bpmnlayout.analysis.GraphStatistics;
import com.tnq.bpmnlayout.graph.BpmnGraph;
import com.tnq.bpmnlayout.optimizer.CrossingReducer;
import com.tnq.bpmnlayout.optimizer.JoinDetector;
import com.tnq.bpmnlayout.optimizer.LoopRouter;
import com.tnq.bpmnlayout.routing.OrthogonalRouter;

public class LayoutEngine {

        private final GraphAnalyzer analyzer = new GraphAnalyzer();

        private final BackboneFinder backboneFinder = new BackboneFinder();

        private final LevelAssigner levelAssigner = new LevelAssigner();

        private final LaneAssigner laneAssigner = new LaneAssigner();

        private final BranchBalancer branchBalancer = new BranchBalancer();

        private final LaneOptimizer laneOptimizer = new LaneOptimizer();

        private final CoordinateAssigner coordinateAssigner = new CoordinateAssigner();

        private final JoinDetector joinDetector = new JoinDetector();

        private final LoopRouter loopRouter = new LoopRouter();

        private final CrossingReducer crossingReducer = new CrossingReducer();

        private final OrthogonalRouter orthogonalRouter = new OrthogonalRouter();

        private final BranchExpander branchExpander = new BranchExpander();

        public LayoutContext layout(BpmnGraph graph) {

                LayoutContext context = new LayoutContext();

                context.setGraph(graph);

                System.out.println("1. Graph analysis");

                GraphStatistics stats = analyzer.analyze(graph);

                context.setStatistics(stats);

                System.out.println("2. Finding backbone");

                Backbone backbone = backboneFinder.find(graph);

                context.setBackbone(backbone);

                System.out.println("3. Level assign");

                // levelAssigner.assignLevels(graph);
                levelAssigner.assignLevels(context);

                branchExpander.expand(context);

                System.out.println("4. Lane assign");

                // laneAssigner.assignLanes(graph);
                laneAssigner.assignLanes(context);

                // LaneAssigner assigns complete branch paths.  The old immediate-child
                // balancing and linear-path optimiser could split a branch across rows.

                System.out.println("7. Grid");

                GridLayoutEngine grid = new GridLayoutEngine();

                grid.build(graph);

                context.setGrid(grid);

                System.out.println("8. Coordinate");

                coordinateAssigner.assign(graph);

                System.out.println("9. Routing");

                orthogonalRouter.route(context);

                System.out.println("10. Crossing");

                crossingReducer.reduce(context);

                System.out.println("DONE");

                return context;
        }

}
