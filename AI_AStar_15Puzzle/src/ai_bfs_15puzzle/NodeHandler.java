/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_bfs_15puzzle;

/**
 *
 * @author kksaikrishna
 */
public class NodeHandler implements Comparable<NodeHandler> {
    private String node;
    private int parentNodeIndex;
    private int depthLevel;
    private int manhattanDistance;
    private int displacedTiles;
    private int hueristic;

    public NodeHandler(String node, int parentNodeIndex, int depthLevel, int manhattanDistance, int displacedTiles, int hueristic) {
        this.node = node;
        this.parentNodeIndex = parentNodeIndex;
        this.depthLevel = depthLevel;
        this.manhattanDistance = manhattanDistance;
        this.displacedTiles = displacedTiles;
        this.hueristic = hueristic;
    }

    public String getNode() {
        return node;
    }

    public int getParentNodeIndex() {
        return parentNodeIndex;
    }

    public int getDepthLevel() {
        return depthLevel;
    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }

    public int getDisplacedTiles() {
        return displacedTiles;
    }

    public int getHueristic() {
        return hueristic;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public void setParentNodeIndex(int parentNodeIndex) {
        this.parentNodeIndex = parentNodeIndex;
    }

    public void setDepthLevel(int depthLevel) {
        this.depthLevel = depthLevel;
    }

    public void setManhattanDistance(int manhattanDistance) {
        this.manhattanDistance = manhattanDistance;
    }

    public void setDisplacedTiles(int displacedTiles) {
        this.displacedTiles = displacedTiles;
    }

    public void setHueristic(int hueristic) {
        this.hueristic = hueristic;
    }

    public int compareTo(NodeHandler obj)
    {
        if(hueristic == 0)
        {
            //System.out.println("Manhattan");
            if((depthLevel+manhattanDistance) == (obj.depthLevel + obj.manhattanDistance))
            {
                return 0;
            }
            else if((depthLevel+manhattanDistance) > (obj.depthLevel + obj.manhattanDistance))
            {
                return 1;
            }
            else
            {
                return -1;
            }   
        }
        else if(hueristic == 1)
        {
            //System.out.println("Displaced Tiles");
            if((depthLevel+displacedTiles) == (obj.depthLevel + obj.displacedTiles))
            {
                return 0;
            }
            else if((depthLevel+displacedTiles) > (obj.depthLevel + obj.displacedTiles))
            {
                return 1;
            }
            else
            {
                return -1;
            }  
        }
        else
        {
            return -1;
        }
    }
}
