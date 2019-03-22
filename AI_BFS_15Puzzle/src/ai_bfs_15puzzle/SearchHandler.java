/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_bfs_15puzzle;
import java.util.*;
import java.text.NumberFormat;
/**
 *
 * @author kksaikrishna
 */
public class SearchHandler {
    
    public static final String GOAL_STATE = "123456789ABCDEFS";
    
    ArrayList<String> visitedStates; //A structure that mainitains list of Visited States for convenience
    ArrayList<NodeHandler> stateTree; //A structure that maintains list of visited states with their parent's index and depth level
    Queue<NodeHandler> queue; //A queue to maintain the next states to be visited
 //An object to class that finds next states
    
    public SearchHandler()
    {
        visitedStates = new ArrayList<String>();
        stateTree = new ArrayList<NodeHandler>();
        queue = new LinkedList<>();
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
    
    private void markVisited(String state, int parentNodeIndex, int depthLevel)
    {
        if(!state.isEmpty())
        {
            NodeHandler objNodeHandler = new NodeHandler(state,parentNodeIndex,depthLevel);
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
    
    private void addToQueue(String state, int parentNodeIndex, int depthLevel)
    {
        NodeHandler obj = new NodeHandler(state,parentNodeIndex,depthLevel);
        queue.add(obj);
    }
    
    public ArrayList<NodeHandler> search(String initialState)
    {
        ArrayList<String> nextStates = new ArrayList<String>();
        StateHandler objStateHandler = new StateHandler();
        String parentState = initialState;
        int i = 0,parentNodeIndex=0,depthLevel=0;
        
        addToQueue(initialState,parentNodeIndex,depthLevel);
        markVisited(initialState,parentNodeIndex,depthLevel);
        
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
                    addToQueue(nextStates.get(i),parentNodeIndex,depthLevel);
                    if(isGoalState(nextStates.get(i)))
                    {
                        markVisited(nextStates.get(i),parentNodeIndex,depthLevel);
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
