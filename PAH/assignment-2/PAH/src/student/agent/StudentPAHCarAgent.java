package student.agent;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.jme3.math.Vector3f;

import cz.agents.alite.pahtactical.agent.PAHCarAgent;
import cz.agents.alite.pahtactical.vis.PlanLayer;
import cz.agents.alite.pahtactical.vis.PlanLayer.PlanProvider;
import cz.agents.alite.tactical.universe.entity.embodiment.Car.VehicleType;
import cz.agents.alite.tactical.universe.environment.TacticalEnvironment.TacticalEnvironmentHandler;
import cz.agents.alite.tactical.universe.world.map.Building;
import cz.agents.alite.tactical.universe.world.map.UrbanMap;
import cz.agents.alite.tactical.universe.world.physics.Terrain;
import cz.agents.alite.tactical.util.Point;
import cz.agents.alite.tactical.util.Polygon2d;
import cz.agents.alite.tactical.util.Visibility;
import cz.agents.alite.vis.VisManager;
import cz.agents.alite.vis.element.Line;
import cz.agents.alite.vis.element.StyledPoint;
import cz.agents.alite.vis.element.aggregation.StyledPointElements;
import cz.agents.alite.vis.element.implemetation.StyledPointImpl;
import cz.agents.alite.vis.layer.terminal.StyledPointLayer;
import cz.agents.alite.vis.layer.terminal.LineLayer;
import cz.agents.alite.vis.element.aggregation.LineElements;
import cz.agents.alite.vis.element.implemetation.LineImpl;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.vecmath.Point2d;
/**
 * Example PAH car agent for the first assignment of PAH course illustrating
 * the use of API.
 *
 * Takes implicit order of obstacles and tries to reach them by
 * straight line while ignoring obstacles.
 */
public class StudentPAHCarAgent extends PAHCarAgent {

    final static float CAR_SPEED = 30.0f;
    final static float BASE_INFLATE = 3.0f;
    final static float BASE_INFLATE_EPSILON = 2.99f;

    List<Point> checkpointsToGo;
    List<Point> buildingCorners;
    private Visibility inflatedVisibility;
    private Graph g;
    private ArrayList<Node> plannedPath;
    private double DISTANCE_SEGMENTATION_THRESHOLD = 20.0;

    public StudentPAHCarAgent(String name, UrbanMap map, Terrain terrain, TacticalEnvironmentHandler handler, Set<Point> checkpoints) {
        super(name, map, terrain, handler, checkpoints);
    }

    @Override
    protected void ready() {
        // This method is called when the simulation has been properly initialized.

        System.out.println("Ready to go... ");

        // Current position can be obtained like this
        Point currentPosition = getCurrentPosition();
        System.out.println("My current position is " + currentPosition);

        // Current heading of the car can be obtained like this
        Vector3f currentHeading = getCurrentHeading();
        System.out.println("My current heading is " + currentHeading);

        // Current speed of the car can be obtained like this
        float currentSpeed = getCurrentSpeed();
        System.out.println("My current speed is " + currentSpeed);

        // The type of vehicle your agent currently controls
        VehicleType vehicleType = getVehicleType();
        System.out.println("The vehicle is: " + vehicleType);

        // The set of checkpoints can be obtained like this:
        Set<Point> checkpoints = getCheckpoints();
        System.out.println("Checkpoints: " + checkpoints);

        // The buildings (obstacles) can be obtained like this:
        List<Building> buildings = getMap().getBuildings();
        // The points defining the base of the building can be obtained like this:
        buildings.get(0).getPoints();
        // The polygon representing the base of the building can be obtained using:
//        Polygon2d base = buildings.get(0).getBaseAsPolygon2d();
        // The base can be inflated using inflate method. The polygon is inflated by by 5m, where the arcs at corners are approximated by 2 points.
//        Polygon2d inflatedBase = base.inflate(5.0, 1);

        // get the list of points that will serve as input for the trajectory planning
        buildingCorners = new LinkedList<Point>();
        int id = 0;
        ArrayList<Polygon2d> inflatedBases = new ArrayList<Polygon2d>();
        for(Building b : buildings) {
            Polygon2d base = b.getBaseAsPolygon2d();
            Polygon2d inflatedBase = base.inflate(BASE_INFLATE, 1);
            Polygon2d smallerInflatedBase = base.inflate(BASE_INFLATE_EPSILON, 1);

            for(Point2d p : inflatedBase.getPoints()) {
                Point pp = new Point(p.x, p.y, getAltitude(p.x, p.y));
                buildingCorners.add(pp);
            }

            inflatedBases.add(smallerInflatedBase);
        }

        g = new Graph();
        for (Point p : checkpoints) {
            g.add(new Node(p, id));
            id++;
        }
        for (Point p :buildingCorners) {
            // check that the point is not on the inside of other bases
            boolean addPoint = true;
            for(Polygon2d otherInflatedBase : inflatedBases) {
                if(otherInflatedBase.isInside(new Point2d(p.x,p.y))) {
                    addPoint = false;
                }
            }
            if(addPoint) {
                g.add(new Node(p, id));
                id++;
            }
        }
        g.add(new Node(getCurrentPosition(), id));
        int startingPosition = id;

        inflatedVisibility = new Visibility(inflatedBases);

        // create visibility graph
        g.initDistances();
        System.out.println("Creating visibility graph...");
        for (int i = 0; i < g.nodes.size(); i++) {
            Node n1 = g.nodes.get(i);
            for (int j = 0; j < g.nodes.size(); j++) {
                Node n2 = g.nodes.get(j);
                if(inflatedVisibility.isVisible(n1.p, n2.p, Double.MAX_VALUE)) {
                    g.addEdge(n1,n2);
                }
            }
        }

        // get dijkstra plan
        int start = startingPosition;
        plannedPath = new ArrayList<Node>();
        ArrayList<Integer> visitedCheckpoints = new ArrayList<Integer>();
        while(visitedCheckpoints.size() != checkpoints.size()) {
            g.dijkstra(start);

            double shortestPathToCheckpoint = Double.MAX_VALUE;
            int selectedCheckpoint = -1;
            for(int i = 0; i < checkpoints.size(); i++) {
                if(visitedCheckpoints.contains(i)) {
                    continue;
                }
                if(g.distances[i] < shortestPathToCheckpoint) {
                    shortestPathToCheckpoint = g.distances[i];
                    selectedCheckpoint = i;
                }
            }
            // get the path to the selected checkpoint
            visitedCheckpoints.add(selectedCheckpoint);
            int current = selectedCheckpoint;
            ArrayList<Node> partialPlannedPath = new ArrayList<Node>();
            partialPlannedPath.add(g.nodes.get(current));
            while(current != start) {
                partialPlannedPath.add(g.nodes.get(g.predecessors[current]));
                current = g.nodes.get(g.predecessors[current]).id;
            }

            // add the partial in reverse
            ListIterator li = partialPlannedPath.listIterator(partialPlannedPath.size());
            while(li.hasPrevious()) {
                plannedPath.add((Node) li.previous());
            }

            start = selectedCheckpoint;
        }





        // You can check if the points are straight-line visible (w.r.t. the buildings) like this:
        // E.g. the point 425, 979 is not visible from initial position
//        boolean visible1 = getMap().getVisibility().isVisible(currentPosition, new Point(425,979, getAltitude(425, 979) + 1.0), Double.MAX_VALUE);
        // E.g. the point 650, 850 is visible from initial position
//        boolean visible2 = getMap().getVisibility().isVisible(currentPosition, new Point(650, 850, getAltitude(650, 850) + 1.0), Double.MAX_VALUE);

        // Visibility with respect to customized polygons can be obtained by creating your own Visibility object:
//        Visibility inflatedVisibility = new Visibility(Collections.singleton(inflatedBases));
//        inflatedVisibility.isVisible(currentPosition, new Point(425,979, getAltitude(425, 979) + 1.0));


        // To get the z-coordinate of the terrain use:
//        getAltitude(425, 979);

        // Order the checkpoints implicitly
        checkpointsToGo = new LinkedList<Point>();
        Node prev = null;
        for(Node n : plannedPath) {
            // adjust the path, if there are long segments, create more checkpoints
            if(prev == null) {
                prev = n;
            } else {
                double dist = new Point2d(n.p.x, n.p.y).distance(new Point2d(prev.p.x, prev.p.y));
                if(dist > DISTANCE_SEGMENTATION_THRESHOLD) {
                    double numOfAddedPoints = Math.round(dist/DISTANCE_SEGMENTATION_THRESHOLD);
                    for (double i = 1.0; i < numOfAddedPoints; i++) {
                        double t = i/numOfAddedPoints;
                        double x = (n.p.x-prev.p.x)*t+prev.p.x;
                        double y = (n.p.y-prev.p.y)*t+prev.p.y;
                        System.out.println("added some points: "+new Point(x,y,0));
                        checkpointsToGo.add(new Point(x,y,0));
                    }
                }
                prev = n;
            }
            checkpointsToGo.add(n.p);
        }

        // Start moving towards the first checkpoint
        // The arguments are:
        // 1. Target waypoint,
        // 2. Target seed (km/h),
        // 3. Direction: true=forward, false=reverse,
        // 4. Waypoint reached tolerance -- the target waypoint is considered reached when the vehicle is at most this far
        goToWaypoint(checkpointsToGo.get(0), CAR_SPEED, true, 3.5f);

        // Create a 2D-Vis layer to visualize your planned path
        VisManager.registerLayer(PlanLayer.create(new PlanProvider() {

            @Override
            public List<Point> getPlan() {
                return getPlanForVis();
            }
        }, Color.BLUE, 2));

        // Create a 2D-Vis layer to visualize your checkpoints
        VisManager.registerLayer(StyledPointLayer.create( new StyledPointElements() {

			@Override
			public Iterable<? extends StyledPoint> getPoints() {
				LinkedList<StyledPoint> points = new LinkedList<StyledPoint>();

				for (Point checkPoint : checkpointsToGo) {
					points.add(new StyledPointImpl(checkPoint, Color.RED, 8));
				};
				return points;
			}
		}));

        VisManager.registerLayer(StyledPointLayer.create( new StyledPointElements() {

			@Override
			public Iterable<? extends StyledPoint> getPoints() {
				LinkedList<StyledPoint> points = new LinkedList<StyledPoint>();

				for (Point checkPoint : getCheckpoints()) {
					points.add(new StyledPointImpl(checkPoint, Color.PINK, 8));
				};
				return points;
			}
		}));

        // expanded buildings
        VisManager.registerLayer(LineLayer.create( new LineElements() {
			@Override
			public Iterable<? extends Line> getLines() {
				LinkedList<Line> lines = new LinkedList<Line>();
                                List<Building> buildings = getMap().getBuildings();
                                for(Building b : buildings) {
                                    Polygon2d base = b.getBaseAsPolygon2d();
                                    Polygon2d inflatedBase = base.inflate(BASE_INFLATE, 1);


                                    Point2d p[] = inflatedBase.getPoints();
                                    for (int i = 1; i < p.length; i++) {
                                        lines.add(new LineImpl(
                                            new Point(p[i-1].x, p[i-1].y, 0),
                                            new Point(p[i].x, p[i].y, 0)
                                        ));
                                    }
                                    lines.add(new LineImpl(
                                        new Point(p[0].x, p[0].y, 0),
                                        new Point(p[p.length-1].x, p[p.length-1].y, 0)
                                    ));
                                }
				return lines;
			}

                          @Override
                        public Color getColor() {
                            return Color.yellow;
                        }

                        @Override
                        public int getStrokeWidth() {
                            return 2;
                        }
		}));

        // corners visible from the moving truck
        /*VisManager.registerLayer(LineLayer.create(  new LineElements() {

			@Override
			public Iterable<? extends Line> getLines() {
				LinkedList<Line> lines = new LinkedList<Line>();
                                for (Point corner : buildingCorners) {
                                    if(getMap().getVisibility().isVisible(getCurrentPosition(), corner, Double.MAX_VALUE)) {
                                        lines.add(new LineImpl(getCurrentPosition(), corner));
                                    }
                                }
				return lines;
			}
                        @Override
                        public Color getColor() {
                            return Color.darkGray;
                        }

                        @Override
                        public int getStrokeWidth() {
                            return 1;
                        }
		}));*/

        // the visibility graph
        /*VisManager.registerLayer(LineLayer.create(  new LineElements() {

			@Override
			public Iterable<? extends Line> getLines() {
				LinkedList<Line> lines = new LinkedList<Line>();
                                for (Node n1 : g.nodes) {
                                    for(Node n2 : n1.neighbours)  {
                                        lines.add(new LineImpl(n1.p, n2.p));
                                    }
                                }
				return lines;
			}
                        @Override
                        public Color getColor() {
                            return Color.red;
                        }

                        @Override
                        public int getStrokeWidth() {
                            return 1;
                        }
		}));*/

        // the planned path
        VisManager.registerLayer(LineLayer.create(  new LineElements() {

			@Override
			public Iterable<? extends Line> getLines() {
				LinkedList<Line> lines = new LinkedList<Line>();
                                for (int i = 1; i < plannedPath.size(); i++) {
                                    lines.add(new LineImpl(plannedPath.get(i-1).p, plannedPath.get(i).p));
                                }
				return lines;
			}
                        @Override
                        public Color getColor() {
                            return Color.cyan;
                        }

                        @Override
                        public int getStrokeWidth() {
                            return 2;
                        }
		}));


    }

    @Override
    protected void waypointReached(Point waypoint) {
    	 // Called when the waypoint set using the goToWaypoint method has been reached.

         System.out.println("Hurray, the waypoint " + waypoint + " has been reached!");
         if (waypoint != null && waypoint.epsilonEquals(checkpointsToGo.get(0), 1.0)) {

             halt();
             checkpointsToGo.remove(0);

             // go to next checkpoint
             if (!checkpointsToGo.isEmpty()) {
                 goToWaypoint(checkpointsToGo.get(0), CAR_SPEED, true, 3.5f);
             } else {
                 System.out.println("Hurray Hurray, done completely!!!");
             }
         }
    }

    @Override
    protected void positionChanged(Point newPosition) {
       // Called every time the position of the car changes.

       // System.out.println("Position changed to " + newPosition);
       // System.out.println("My current position is " + getCurrentPosition());
       // System.out.println("My current heading is " + getCurrentHeading());
       // System.out.println("My current speed is " + getCurrentSpeed());
    }

    @Override
    protected void tick(long simulationTime) {
       // Called with a given period of the simulation time. Roughly 1000 ms.
       // System.out.println("Tick. Distance driven so far: " + getDistance());
    }

    private List<Point> getPlanForVis() {
    	// The returned sequence of points will be shown in 2D Vis.

    	List<Point> plan = new LinkedList<Point>();

    	if (getCurrentPosition() != null) {
    		plan.add(getCurrentPosition());
    	}

    	plan.addAll(checkpointsToGo);
        return plan;
    }


}
