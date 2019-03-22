/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_bfs_15puzzle;
import java.text.*;
import java.util.*;

/**
 *
 * @author kksaikrishna
 */
public class AI_BFS_15Puzzle {

    private static void printMemoryUsage()
    {
        Runtime runtime = Runtime.getRuntime();
        NumberFormat format = NumberFormat.getInstance();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemoryinB = (totalMemory-freeMemory);
        long usedMemoryinKB = usedMemoryinB/1024;
        long usedMemoryinMB = usedMemoryinKB/1024;
        
        System.out.println("Memory Used -  " + format.format(usedMemoryinB) + "B or "
                + format.format(usedMemoryinKB) + "KB or "
                + format.format(usedMemoryinMB) + "MB");
    }
    private static void printTimeUsage(long startTime, long endTime)
    {
        NumberFormat format = NumberFormat.getInstance();
        long timeUsage = endTime - startTime;
        long timeUsageInSeconds = timeUsage/1000;
        System.out.println("Running Time -  " + format.format(timeUsage)
                            + "ms or " + format.format(timeUsageInSeconds) +"s");
    }
    private static void printSolutionTree(ArrayList<NodeHandler> stateTree)
    {
        System.out.println("Goal State:");
        for(int i=stateTree.size()-1; i>=0;)
            {
                StateHandler objStateHandler = new StateHandler();
                
                objStateHandler.printPuzzleState(stateTree.get(i).getNode());
                System.out.println("||\n||\n||\n");
                
                i=stateTree.get(i).getParentNodeIndex();
                if(i==0)
                {
                   System.out.println("Initial State:");
                   objStateHandler.printPuzzleState(stateTree.get(i).getNode());
                   break;
                }
            }
    }
    public static void main(String[] args) 
    {
        long startTime = System.currentTimeMillis();
        long endTime;
        SearchHandler objSearchHandler = new SearchHandler();
        ArrayList<NodeHandler> stateTree = new ArrayList<NodeHandler>();
        stateTree = objSearchHandler.search(args[0]);
        
        
        if(stateTree == null)
        {
            System.out.println("No Solution");
        }
        else
        {
            System.out.println("Solution Exists!");
            printSolutionTree(stateTree);
        }
        
        printMemoryUsage();
        endTime = System.currentTimeMillis();
        printTimeUsage(startTime,endTime);
    }
}

