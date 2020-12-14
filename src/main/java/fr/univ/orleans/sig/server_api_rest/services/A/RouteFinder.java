package fr.univ.orleans.sig.server_api_rest.services.A;

import java.util.*;

public class RouteFinder<T extends GraphNode> {

    private final Graph<T> graph;
    private final Scorer<T> nextNodeScorer;
    private final Scorer<T> targetScorer;

    public RouteFinder(Graph<T> graph, Scorer<T> nextNodeScorer, Scorer<T> targetScorer) {
        this.graph = graph;
        this.nextNodeScorer = nextNodeScorer;
        this.targetScorer = targetScorer;
    }

    public List<T> findRoute(T from, T to) {
        Queue<RouteNode<T>> openSet = new PriorityQueue<>();
        Map<T, RouteNode<T>> allNodes = new HashMap<>();

        RouteNode<T> start = new RouteNode<>(from, null, 0d, targetScorer.computeCost(from, to));
        openSet.add(start);
        allNodes.put(from, start);

        RouteNode<T> next = null;
        while (!openSet.isEmpty()) {
            next = openSet.poll();
            if (next.getCurrent().getId().equals(to.getId())) {
                List<T> route = new ArrayList<>();
                RouteNode<T> current = next;
                do {
                    route.add(0, current.getCurrent());
                    current = allNodes.get(current.getPrevious());
                } while (current != null);
                return route;
            }

            RouteNode<T> finalNext = next;
            graph.getConnections(next.getCurrent()).forEach(connection -> {
                RouteNode<T> nextNode = allNodes.getOrDefault(connection, new RouteNode<>(connection));
                allNodes.put(connection, nextNode);

                double newScore = finalNext.getRouteScore() + nextNodeScorer.computeCost(finalNext.getCurrent(), connection);
                if (newScore < nextNode.getRouteScore()) {
                    nextNode.setPrevious(finalNext.getCurrent());
                    nextNode.setRouteScore(newScore);
                    nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection, to));
                    openSet.add(nextNode);
                }
            });
        }
//        List<T> route = new ArrayList<>();
//        RouteNode<T> current = next;
//        do {
//            route.add(0, current.getCurrent());
//            current = allNodes.get(current.getPrevious());
//        } while (current != null);
//        return route;
        throw new IllegalStateException("No route found");
    }

//    public List<T> findRoute(T from, T to) {
//        Queue<RouteNode<T>> openSet = new PriorityQueue<>();
//        Map<T, RouteNode<T>> allNodes = new HashMap<>();
//
//        RouteNode<T> start = new RouteNode<>(from, null, 0d, targetScorer.computeCost(from, to));
//        openSet.add(start);
//        allNodes.put(from, start);
//
//        while (!openSet.isEmpty()) {
//            RouteNode<T> next = openSet.poll();
//            if (next.getCurrent().getId().equals(to.getId())) {
////            if (next.getCurrent().equals(to)) {
//                List<T> route = new ArrayList<>();
//                RouteNode<T> current = next;
//                do {
//                    route.add(0, current.getCurrent());
//                    current = allNodes.get(current.getPrevious());
//                } while (current != null);
//                return route;
//            }
//
//            graph.getConnections(next.getCurrent()).forEach(connection -> {
//                RouteNode<T> nextNode = allNodes.getOrDefault(connection, new RouteNode<>(connection));
//                allNodes.put(connection, nextNode);
//
//                double newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), connection);
//                if (newScore < nextNode.getRouteScore()) {
//                    nextNode.setPrevious(next.getCurrent());
//                    nextNode.setRouteScore(newScore);
//                    nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection, to));
//                    openSet.add(nextNode);
//                }
//            });
//        }
////        return
////        List<T> route = new ArrayList<>();
////        RouteNode<T> current = next;
////        do {
////            route.add(0, current.getCurrent());
////            current = allNodes.get(current.getPrevious());
////        } while (current != null);
////        return route;
//        throw new IllegalStateException("No route found");
//    }

}
