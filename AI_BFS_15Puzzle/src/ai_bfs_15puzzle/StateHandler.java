/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_bfs_15puzzle;
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
    
    private boolean isStateVisited(String currentState, ArrayList<String> visitedStates)
    {
        if(!currentState.isEmpty())
        {
            return visitedStates.contains(currentState);
        }
        else
        {
            return false;
        }
        
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
    public ArrayList<String> computeNextStates(String currentState, ArrayList<String> visitedStates)
    {
        String[] nextStates = new String[4];
        nextStates[0] = moveLeft(currentState,visitedStates);
        nextStates[1] = moveRight(currentState,visitedStates);
        nextStates[2] = moveTop(currentState,visitedStates);
        nextStates[3] = moveBottom(currentState,visitedStates);
        return getReturnableStates(nextStates);
    }
    
    private String moveLeft(String currentState, ArrayList<String> visitedStates)
    {
        char strTemp[] = currentState.toCharArray();
        int sPos = getSpacePosition(currentState);
        String leftState = new String();
        
        if(isLeftMoveValid(currentState))
        {
           strTemp[sPos] = strTemp[sPos-1];
           strTemp[sPos-1] = SPACE_CHAR;
           leftState = String.valueOf(strTemp);
        }
        
        if(isStateVisited(leftState,visitedStates))
        {
            leftState = "";
        }
        
        return leftState;
    }
    
    private String moveRight(String currentState, ArrayList<String> visitedStates)
    {
        char strTemp[] = currentState.toCharArray();
        int sPos = getSpacePosition(currentState);
        String rightState = new String();
        
        if(isRightMoveValid(currentState))
        {
           strTemp[sPos] = strTemp[sPos+1];
           strTemp[sPos+1] = SPACE_CHAR;
           rightState= String.valueOf(strTemp);
        }
        
        if(isStateVisited(rightState,visitedStates))
        {
            rightState = "";
        }
        return rightState;
    }
    
    private String moveTop(String currentState, ArrayList<String> visitedStates)
    {
        char strTemp[] = currentState.toCharArray();
        int sPos = getSpacePosition(currentState);
        String topState = new String();
        
        if(isTopMoveValid(currentState))
        {
           strTemp[sPos] = strTemp[sPos-4];
           strTemp[sPos-4] = SPACE_CHAR;
           topState= String.valueOf(strTemp);
        }
        
        if(isStateVisited(topState,visitedStates))
        {
            topState = "";
        }
        
        return topState;
    }
    
    private String moveBottom(String currentState, ArrayList<String> visitedStates)
    {
        char strTemp[] = currentState.toCharArray();
        int sPos = getSpacePosition(currentState);
        String bottomState = new String();
        
        if(isBottomMoveValid(currentState))
        {
           strTemp[sPos] = strTemp[sPos+4];
           strTemp[sPos+4] = SPACE_CHAR;
           bottomState= String.valueOf(strTemp);
        }
        
        if(isStateVisited(bottomState,visitedStates))
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
