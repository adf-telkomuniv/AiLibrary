/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;

/**
 *
 * @author dee
 */
public class TestAStar {

    public static void main(String[] args) {
        double[] heur = {0, 0.5, 5, 2, 4.5, 4, 5, 6, 7, 8, 10, 10.5};
        int[][] cityMap = {{11, 11, 11, 10, 10, 9, 9, 7, 8, 8, 6, 6, 5, 5, 2, 3, 3, 4, 1},
        {10, 8, 4, 9, 8, 7, 2, 6, 7, 5, 5, 3, 3, 4, 0, 0, 4, 1, 0}};
        double[] cost = {5, 3, 15, 3, 3, 2, 8, 2, 2, 3, 1, 3, 2, 6, 6, 3, 2, 5, 1};
        AStar x = new AStar(heur, cityMap, cost);
        int[] path = x.optimize(11, 0);
        System.out.print("path = ");
        for (int i = 0; i < path.length; i++) {
            System.out.print(path[i]+" ");
        }
        System.out.println("");
        double total = x.getTotalCost(path);
        System.out.println("total = "+total);
    }
}
