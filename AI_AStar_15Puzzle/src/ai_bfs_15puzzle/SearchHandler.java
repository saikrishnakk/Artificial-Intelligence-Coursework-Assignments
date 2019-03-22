/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_bfs_15puzzle;
import java.util.*;
import java.text.NumberFormat;
import static java.lang.Math.*;
/**
 *
 * @author kksaikrishna
 */
public class SearchHandler {
    
    public static final String GOAL_STATE = "123456789ABCDEFS";
    
    ArrayList<String> visitedStates; //A structure that mainitains list of Visited States for convenience
    ArrayList<NodeHandler> stateTree; //A structure that maintains list of visited states with their parent's index and depth level
    Queue<NodeHandler> queue; //A queue to maintain the next states to be visited
    StateHandler objStateHandler;
 //An object to class that finds next states
    
    public SearchHandler()
    {
        visitedStates = new ArrayList<String>();
        stateTree = new ArrayList<NodeHandler>();
        queue = new PriorityQueue<NodeHandler>();
        objStateHandler = new StateHandler();
    }
    
    private int getDepthByNode(String node)
    {
        for(int i=0;i<stateTree.size();i++)
        {
            if(stateTree.get(i).getNode().equals(node))
            {
                return stateTree.get(i).getDepthLevel();
            }
        }
        
        return -1;
    }
    
    private int getParentNodeIndexByNode(String node)
    {
        for(int i=0;i<stateTree.size();i++)
        {
            if(stateTree.get(i).getNode().equals(node))
            {
                return i;
            }
        }
        
        return -1;
    }
    
    private boolean isGoalState(String state)
    {
        if(state.compareTo(GOAL_STATE)==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private void markVisited(String state, int parentNodeIndex, int depthLevel,int numberofDisplacedNodes, int manhattanDistance, int heuristic)
    {
        if(!state.isEmpty())
        {
            NodeHandler objNodeHandler = new NodeHandler(state,parentNodeIndex,depthLevel,numberofDisplacedNodes,manhattanDistance, heuristic);
            stateTree.add(objNodeHandler);
            visitedStates.add(state);
            queue.remove();
        }       
    }
    private void markVisited(NodeHandler state)
    {
        if(state!=null)
        {
            stateTree.add(state);
            visitedStates.add(state.getNode());
            queue.remove();
        }       
    }
    
    private void addToQueue(String state, int parentNodeIndex, int depthLevel,int numberofDisplacedNodes, int manhattanDistance, int heuristic)
    {
        NodeHandler obj = new NodeHandler(state,parentNodeIndex,depthLevel,numberofDisplacedNodes,manhattanDistance,heuristic);
        queue.add(obj);
    }
    
    private int numberOfDisplacedNodes(String state)
    {
        int i,j,k,temp;
        int[][] mat;
        mat = new int[4][4];
        
        for(i=0,k=0;i<4;i++)
        {
            for(j=0;j<4;j++,k++)
            {
                if(state.charAt(k) == 'A')
                {
                    temp = 10;
                }
                else if(state.charAt(k) == 'B')
                {
                    temp = 11;
                }
                else if(state.charAt(k) == 'C')
                {
                    temp = 12;
                }
                else if(state.charAt(k) == 'D')
                {
                    temp = 13;
                }
                else if(state.charAt(k) == 'E')
                {
                    temp = 14;
                }
                else if(state.charAt(k) == 'F')
                {
                    temp = 15;
                }
                else if(state.charAt(k) == 'S')
                {
                    temp = 0;
                }
                else
                {
                    temp = Character.getNumericValue(state.charAt(k));
                }
                
                mat[i][j]=temp;
            }
        }
        
//        System.out.println("State:"+state);
//        for(i=0;i<4;i++)
//        {
//            for(j=0;j<4;j++)
//            {
//                System.out.print(mat[i][j]+" ");
//            }
//            System.out.println();
//        }
        
        for(i=0,k=0;i<4;i++)
        {
            for(j=0;j<4;j++)
            {
                switch (mat[i][j])
                {
                    case 0:
                        break;
                    case 1:
                        if(i==0 && j==0)
                        {
                            k++;
                        }
                        break;
                    case 2:
                        if(i==0 && j==1)
                        {
                            k++;
                        }
                        break;
                    case 3:
                        if(i==0 && j==2)
                        {
                            k++;
                        }
                        break;
                    case 4:
                        if(i==0 && j==3)
                        {
                            k++;
                        }
                        break;
                    case 5:
                        if(i==1 && j==0)
                        {
                            k++;
                        }
                        break;
                    case 6:
                        if(i==1 && j==1)
                        {
                            k++;
                        }
                        break;
                    case 7:
                        if(i==1 && j==2)
                        {
                            k++;
                        }
                        break;
                    case 8:
                        if(i==1 && j==3)
                        {
                            k++;
                        }
                        break;
                    case 9:
                        if(i==2 && j==0)
                        {
                            k++;
                        }
                        break;
                    case 10:
                        if(i==2 && j==1)
                        {
                            k++;
                        }
                        break;
                    case 11:
                        if(i==2 && j==2)
                        {
                            k++;
                        }
                        break;
                    case 12:
                        if(i==2 && j==3)
                        {
                            k++;
                        }
                        break;
                    case 13:
                        if(i==3 && j==0)
                        {
                            k++;
                        }
                        break;
                    case 14:
                        if(i==3 && j==1)
                        {
                            k++;
                        }
                        break;
                    case 15:
                        if(i==3 && j==2)
                        {
                            k++;
                        }
                        break;
                }
            }
        }
//        int t = 15-k;
//        System.out.println("Number of Displaced Tiles: " + t);
        return 15-k;
    }    
    
    private int computeManhattanDistance(String state)
    {
        int i,j,k,temp;
        int[][] mat;
        mat = new int[4][4];
        
        for(i=0,k=0;i<4;i++)
        {
            for(j=0;j<4;j++,k++)
            {
                if(state.charAt(k) == 'A')
                {
                    temp = 10;
                }
                else if(state.charAt(k) == 'B')
                {
                    temp = 11;
                }
                else if(state.charAt(k) == 'C')
                {
                    temp = 12;
                }
                else if(state.charAt(k) == 'D')
                {
                    temp = 13;
                }
                else if(state.charAt(k) == 'E')
                {
                    temp = 14;
                }
                else if(state.charAt(k) == 'F')
                {
                    temp = 15;
                }
                else if(state.charAt(k) == 'S')
                {
                    temp = 0;
                }
                else
                {
                    temp = Character.getNumericValue(state.charAt(k));
                }
                
                mat[i][j]=temp;
            }
        }
        
        for(i=0,k=0;i<4;i++)
        {
            for(j=0;j<4;j++)
            {
                switch (mat[i][j])
                {
                    case 0:
                        break;
                    case 1:
                        k += abs(0-i) + abs(0-j);
                        break;
                    case 2:
                        k += abs(0-i) + abs(1-j);
                        break;
                    case 3:
                        k += abs(0-i) + abs(2-j);
                        break;
                    case 4:
                        k += abs(0-i) + abs(3-j);
                        break;
                    case 5:
                        k += abs(1-i) + abs(0-j);
                        break;
                    case 6:
                        k += abs(1-i) + abs(1-j);
                        break;
                    case 7:
                        k += abs(1-i) + abs(2-j);
                        break;
                    case 8:
                        k += abs(1-i) + abs(3-j);
                        break;
                    case 9:
                        k += abs(2-i) + abs(0-j);
                        break;
                    case 10:
                        k += abs(2-i) + abs(1-j);
                        break;
                    case 11:
                        k += abs(2-i) + abs(2-j);
                        break;
                    case 12:
                        k += abs(2-i) + abs(3-j);
                        break;
                    case 13:
                        k += abs(3-i) + abs(0-j);
                        break;
                    case 14:
                        k += abs(3-i) + abs(1-j);
                        break;
                    case 15:
                        k += abs(3-i) + abs(2-j);
                        break;
                }
            }
        }
        
        return k;
    }
    private void printQueue()
    {
        System.out.println("Stack");
        
        for (NodeHandler obj: queue)
        {
            System.out.println("Depth: "+obj.getDepthLevel()+" Number Of Displaced Tiles:" + obj.getDisplacedTiles()
                   + "Manhattan Distance:" + obj.getManhattanDistance());
            objStateHandler.printPuzzleState(obj.getNode());
            
        }
        
        System.out.println("----------------");
    }
    
    public ArrayList<NodeHandler> search(String initialState, int hueristic)
    {
        ArrayList<String> nextStates = new ArrayList<String>();
        StateHandler objStateHandler = new StateHandler();
        String parentState = initialState;
        int i = 0,parentNodeIndex=0,depthLevel=0,md,ndn;
        md = computeManhattanDistance(initialState);
        ndn = numberOfDisplacedNodes(initialState);
        addToQueue(initialState,parentNodeIndex,depthLevel,md,ndn,hueristic);
        markVisited(initialState,parentNodeIndex,depthLevel,md,ndn,hueristic);
        if(isGoalState(initialState))
        {
            return stateTree;
        }
        else
        {
            do
            {
                parentNodeIndex = getParentNodeIndexByNode(parentState);
                depthLevel = getDepthByNode(parentState)+1;
                nextStates = objStateHandler.computeNextStates(parentState, visitedStates);
                for(i=0;i<nextStates.size();i++)
                {
                    md = computeManhattanDistance(nextStates.get(i));
                    ndn = numberOfDisplacedNodes(nextStates.get(i));
                    addToQueue(nextStates.get(i),parentNodeIndex,depthLevel,md,ndn,hueristic);
                    if(isGoalState(nextStates.get(i)))
                    {
                        markVisited(nextStates.get(i),parentNodeIndex,depthLevel,md,ndn,hueristic);
                        return stateTree;
                    }
                }
                parentState = queue.peek().getNode();
                markVisited(queue.peek());
            }while(!queue.isEmpty());
         }
        return null;
    }
}
