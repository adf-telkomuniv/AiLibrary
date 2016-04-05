/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dee
 */
public class AStar {

    private double[] heuristic;
    private int[][] cityMap;
    private double[] cost;
    private final double[] fScore;
    private final List<Integer> openSet;
    private final List<Integer> closeSet;

    public AStar(double[] heuristic, int[][] cityMap, double[] cost) {
        this.heuristic = heuristic;
        setCityMap(cityMap);
        setCost(cost);
        fScore = new double[heuristic.length];
        openSet = new ArrayList();
        closeSet = new ArrayList();

    }

    public int[] optimize(int start, int goal) {
        openSet.add(start);
        int[] cameFrom = new int[heuristic.length];
        for (int i = 0; i < heuristic.length; i++) {
            cameFrom[i] = -1;
        }
        double[] gScore = new double[heuristic.length];
        double max = 0;
        for (int i = 0; i < heuristic.length; i++) {
            max += heuristic[i];
        }
        for (int i = 0; i < gScore.length; i++) {
            gScore[i] = max;
        }
        gScore[start] = 0;
        fScore[start] = heuristic[start];
        boolean found = false;
        boolean loop = true;
//        int loops = 0;
        int current;
        while (openSet.size() > 0) {
            List<Double> openScore = new ArrayList();
            openSet.forEach((open) -> openScore.add(fScore[open]));
            List<Double> closeScore = new ArrayList();
            closeSet.stream().forEach((close) -> closeScore.add(fScore[close]));
//            loops++;
            current = getMinimumOpen();
            if (current == goal) {
                found = true;
                loop = false;
                break;
            }
            openSet.remove(openSet.indexOf(current));
            closeSet.add(current);
            List<Integer> neighborId = new ArrayList();
            for (int i = 0; i < cityMap[0].length; i++) {
                if (cityMap[0][i] == current) {
                    neighborId.add(i);
                }
            }
            for (Integer nId : neighborId) {
                int neighbor = cityMap[1][nId];
                if (closeSet.contains(neighbor)) {
                    double tentativeG2 = gScore[current] + actualCost(current, neighbor);
                    if (tentativeG2 < gScore[neighbor]) {
                        cameFrom[neighbor] = current;
                        gScore[neighbor] = tentativeG2;
                        fScore[neighbor] = gScore[neighbor] + heuristic[neighbor];
                        int j = 0;
                        for (int i = 0; i < cameFrom.length; i++) {
                            if (i == neighbor) {
                                System.out.println(j + " " + neighbor);
                                gScore[j] = gScore[neighbor] + actualCost(neighbor, j);
                                fScore[j] = gScore[j] + heuristic[j];
                            }
                            j++;
                        }
                    }
                    continue;
                }
                double tentativeG = gScore[current] + actualCost(current, neighbor);
                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                } else if (tentativeG >= gScore[neighbor]) {
                    continue;
                }
                cameFrom[neighbor] = current;
                gScore[neighbor] = tentativeG;
                fScore[neighbor] = gScore[neighbor] + heuristic[neighbor];
            }
        }
        System.out.println("found = " + found);
        int[] path = generatePath(cameFrom, start, goal);
        return path;
    }

    public int[] generatePath(int[] cameFrom, int start, int goal) {
        List<Integer> path = new ArrayList();
        path.add(goal);
        while (cameFrom[goal] != -1) {
            goal = cameFrom[goal];
            path.add(goal);
        }
        int[] x = new int[path.size()];
        for (int i = 0; i < x.length; i++) {
            x[i] = path.get(i);
        }
        return x;
    }

    public double getTotalCost(int[] path) {
        double total = 0;
        for (int i = 0; i < path.length - 1; i++) {
            total += actualCost(path[i + 1], path[i]);
        }
        return total;
    }

    public double actualCost(int start, int destination) {
        for (int i = 0; i < cityMap[0].length; i++) {
            if ((cityMap[0][i] == start) && (cityMap[1][i] == destination)) {
                return cost[i];
            }
        }
        return 0;
    }

    public int getMinimumOpen() {
        int minId = openSet.get(0);
        for (Integer open : openSet) {
            if (fScore[minId] > fScore[open]) {
                minId = open;
            }
        }
        return minId;
    }

    public double[] getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(double[] heuristic) {
        this.heuristic = heuristic;
    }

    public int[][] getCityMap() {
        return cityMap;
    }

    public final void setCityMap(int[][] cityMap) {
        if (cityMap.length != 2) {
            throw new IllegalStateException("cityMap dimension must be [2xn]");
        }
        if (cityMap[0].length != cityMap[1].length) {
            throw new IllegalStateException("cityMap start and destination size must be the same");
        }
        this.cityMap = cityMap;
    }

    public double[] getCost() {
        return cost;
    }

    public final void setCost(double[] cost) {
        if (cost.length != cityMap[0].length) {
            throw new IllegalStateException("cost size and cityMap size must be the same");
        }
        this.cost = cost;
    }
}
