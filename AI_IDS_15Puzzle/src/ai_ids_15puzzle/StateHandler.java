/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_ids_15puzzle;
import java.util.*;


public class StateHandler {
    public static final char SPACE_CHAR = 'S';
    public void printPuzzleState(String state)
    {
        if(!state.isEmpty())
        {
            for(int i=0; i<16; i++)
            {
                System.out.print(state.charAt(i) + " ");
                if(i%4==3)
                {
                    System.out.println();
                }
            }
        }
        System.out.println();
    }
    
    private boolean isStateVisited(String currentState, NodeHandler parentNode, ArrayList<NodeHandler> stateTree)
    {
        int parentIndex;
        for(parentIndex = parentNode.getParentNodeIndex(); parentIndex>=0;)
        {
            if(currentState.equals((stateTree.get(parentIndex).getNode())))
            {
                return true;
            }
            else
            {
                parentIndex = stateTree.get(parentIndex).getParentNodeIndex();
                if(parentIndex==0 /*&& depth == 0*/)
                {
                    if(currentState.equals((stateTree.get(parentIndex).getNode())))
                    {
                        return true;
                    }
                    else
                    {
                        break;
                    }
                }
            }
           }
        return false;
    }

    private ArrayList<String> getReturnableStates(String[] nextStates)
    {
        ArrayList<String> retStates = new ArrayList<String>();
        for(int i=0; i<4; i++)
        {
            if(!nextStates[i].isEmpty())
                retStates.add(nextStates[i]);
        }
        return retStates;
    }

    public ArrayList<String> computeNextStates(NodeHandler currentNode, ArrayList<NodeHandler> stateTree)
    {
        
        String[] nextStates = new String[4];
        nextStates[0] = moveLeft(currentNode,stateTree);
        nextStates[1] = moveRight(currentNode,stateTree);
        nextStates[2] = moveTop(currentNode,stateTree);
        nextStates[3] = moveBottom(currentNode,stateTree);
        return getReturnableStates(nextStates);
    }
    
    private String moveLeft(NodeHandler currentNode, ArrayList<NodeHandler> stateTree)
    {
        char strTemp[] = currentNode.getNode().toCharArray();
        int sPos = getSpacePosition(currentNode.getNode());
        String leftState = new String();
        
        if(isLeftMoveValid(currentNode.getNode()))
        {
           strTemp[sPos] = strTemp[sPos-1];
           strTemp[sPos-1] = SPACE_CHAR;
           leftState = String.valueOf(strTemp);
        }
        if(!leftState.isEmpty())
        if(isStateVisited(leftState,currentNode,stateTree))
        {
            leftState = "";
        }
        
        return leftState;
        
    }
    private String moveRight(NodeHandler currentNode, ArrayList<NodeHandler> stateTree)
    {
        char strTemp[] = currentNode.getNode().toCharArray();
        int sPos = getSpacePosition(currentNode.getNode());
        String rightState = new String();
        
        if(isRightMoveValid(currentNode.getNode()))
        {
           strTemp[sPos] = strTemp[sPos+1];
           strTemp[sPos+1] = SPACE_CHAR;
           rightState= String.valueOf(strTemp);
        }
        
        if(!rightState.isEmpty())
        if(isStateVisited(rightState,currentNode,stateTree))
        {
            rightState = "";
        }
        
        return rightState;
    }
    private String moveTop(NodeHandler currentNode, ArrayList<NodeHandler> stateTree)
    {
        char strTemp[] = currentNode.getNode().toCharArray();
        int sPos = getSpacePosition(currentNode.getNode());
        String topState = new String();
        
        if(isTopMoveValid(currentNode.getNode()))
        {
           strTemp[sPos] = strTemp[sPos-4];
           strTemp[sPos-4] = SPACE_CHAR;
           topState= String.valueOf(strTemp);
        }
        
        if(!topState.isEmpty())
        if(isStateVisited(topState,currentNode,stateTree))
        {
            topState = "";
        }
        
        return topState;
    }
    private String moveBottom(NodeHandler currentNode, ArrayList<NodeHandler> stateTree)
    {
        char strTemp[] = currentNode.getNode().toCharArray();
        int sPos = getSpacePosition(currentNode.getNode());
        String bottomState = new String();
        
        if(isBottomMoveValid(currentNode.getNode()))
        {
           strTemp[sPos] = strTemp[sPos+4];
           strTemp[sPos+4] = SPACE_CHAR;
           bottomState= String.valueOf(strTemp);
        }
        
        if(!bottomState.isEmpty())
        if(isStateVisited(bottomState,currentNode,stateTree))
        {
            bottomState = "";
        }
        
        return bottomState;
    }
    
    private Boolean isLeftMoveValid(String currentState)
    {
        Boolean ret;
        int sPos = getSpacePosition(currentState);
        
        if(sPos == 0 || sPos == 4 || sPos == 8 || sPos == 12)
        {
            ret = false;
        }
        else
        {
            ret = true;
        }
        
        return ret;
    }
    
    private Boolean isRightMoveValid(String currentState)
    {
        Boolean ret;
        int sPos = getSpacePosition(currentState);
        
        if(sPos == 3 || sPos == 7 || sPos == 11 || sPos == 15)
        {
            ret = false;
        }
        else
        {
            ret = true;
        }
        
        return ret;
    }
    
    private Boolean isTopMoveValid(String currentState)
    {
        Boolean ret;
        int sPos = getSpacePosition(currentState);
        
        if(sPos == 0 || sPos == 1 || sPos == 2 || sPos == 3)
        {
            ret = false;
        }
        else
        {
            ret = true;
        }
        
        return ret;
    }
    
    private Boolean isBottomMoveValid(String currentState)
    {
        Boolean ret;
        int sPos = getSpacePosition(currentState);
        
        if(sPos == 12 || sPos == 13 || sPos == 14 || sPos == 15)
        {
            ret = false;
        }
        else
        {
            ret = true;
        }
        
        return ret;
    }
    
    private int getSpacePosition(String currentState)
    {
        int sPos;
        sPos = currentState.indexOf("S");
        return sPos;
    }
}
