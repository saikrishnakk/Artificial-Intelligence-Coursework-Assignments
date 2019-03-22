/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_ids_15puzzle;

/**
 *
 * @author kksaikrishna
 */
public class NodeHandler {
    private int parentNodeIndex;
    private String node;
    private int depthLevel;
    
    public NodeHandler()
    {
    }
    
    public NodeHandler(String node, int parentNodeIndex, int depthLevel) {
        this.parentNodeIndex = parentNodeIndex;
        this.node = node;
        this.depthLevel = depthLevel;
    }
    
    public int getDepthLevel() {
        return depthLevel;
    }

    public void setDepthLevel(int depthLevel) {
        this.depthLevel = depthLevel;
    }

    public int getParentNodeIndex() {
        return parentNodeIndex;
    }

    public void setParentNodeIndex(int parentNodeIndex) {
        this.parentNodeIndex = parentNodeIndex;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }
}
