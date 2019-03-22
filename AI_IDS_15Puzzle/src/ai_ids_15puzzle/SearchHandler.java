/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_ids_15puzzle;
import java.util.*;
import java.text.NumberFormat;
/**
 *
 * @author kksaikrishna
 */
public class SearchHandler {
    
    public static final String GOAL_STATE = "123456789ABCDEFS";
    ArrayList<NodeHandler> stateTree; 
    Stack<NodeHandler> stack;
    StateHandler objStateHandler;
    
    public SearchHandler()
    {
        stateTree = new ArrayList<NodeHandler>();
        stack = new Stack<NodeHandler>();
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
    
    private void markVisited(String state, int parentNodeIndex, int depthLevel)
    {
        if(!state.isEmpty())
        {
            NodeHandler objNodeHandler = new NodeHandler(state,parentNodeIndex,depthLevel);
            stateTree.add(objNodeHandler);
            stack.pop();
        }       
    }
    private void markVisited(NodeHandler state)
    {
        if(state!=null)
        {
            stateTree.add(state);
            stack.pop();
        }       
    }
    
    private void addToStack(String state, int parentNodeIndex, int depthLevel)
    {
        NodeHandler obj = new NodeHandler(state,parentNodeIndex,depthLevel);
        stack.push(obj);
    }
    
    private void printStack()
    {
        System.out.println("Stack");
        for(int i=0;i<stack.size();i++)
        {
            System.out.println("Depth: "+stack.get(i).getDepthLevel());
            objStateHandler.printPuzzleState(stack.get(i).getNode());
        }
        
        System.out.println("----------------");
    }
    
    public ArrayList<NodeHandler> search(String initialState)
    {
        ArrayList<String> nextStates = new ArrayList<String>();
        NodeHandler parentStateNode = new NodeHandler();
        String parentState;
        int i, dl, parentNodeIndex,depthLevel;
        boolean isGoalReached;
        
         if(isGoalState(initialState))
         {
            addToStack(initialState,0,0);
            markVisited(initialState,0,0);
            return stateTree;
         }

        for(dl = 0, isGoalReached=false; isGoalReached == false ;dl++)
        {
            parentNodeIndex=0;
            depthLevel=0;
            stack.clear();
            stateTree.clear();
            parentState = initialState;
            addToStack(parentState,parentNodeIndex,depthLevel);
            parentStateNode = stack.peek();

            while(!stack.empty())
            {
                markVisited(stack.peek());
                parentNodeIndex = getParentNodeIndexByNode(parentState);
                depthLevel = getDepthByNode(parentState)+1;
                if(depthLevel <= dl)
                {
                    nextStates = objStateHandler.computeNextStates(parentStateNode, stateTree);
                    for(i=0;i<nextStates.size();i++)
                    {
                        addToStack(nextStates.get(i),parentNodeIndex,depthLevel);
                        if(isGoalState(nextStates.get(i)))
                        {
                            markVisited(nextStates.get(i),parentNodeIndex,depthLevel);
                            isGoalReached = true;
                            break;
                        }
                    }
                }
                if(isGoalReached == false)
                {
                    if(!stack.empty())
                    {
                        parentState = stack.peek().getNode();
                        parentStateNode = stack.peek();
                    }
                }
                else
                {
                    break;
                }
            }
        }        
        if(isGoalReached)
            return stateTree;
        else
            return null;
    }
}
