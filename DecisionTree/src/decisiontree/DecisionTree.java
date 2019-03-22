/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author kksaikrishna
 */

public class DecisionTree {

    /**
     * @param args the command line arguments
     */
    int n_e; //Number of Examples
    int na; //Number of Attribues
    int[][] examples; //Example Table
    int pe; //Number of Positive Goal Examples
    int ne; //Number of Negative Goal Examples   
    int[] at_value_size;  //Number of Values in the domain for each Attribute
    
    DecisionTree(int ln_e,int lna)
    {
        n_e = ln_e;
        na = lna;
        examples = new int[n_e][na+1];
        at_value_size = new int[na];
        pe=0;
        ne=0;
    }
    
    class Node 
    {
        List<Integer> e; //Available Example at Each Node
        List<Integer> a; //Available Attributes at Each Node
        boolean isFinal; //Is LeafNode
        int d;  //Decision
        int na; //Next Attribute
        List<Node> child; //Child Nodes in the Tree

        public Node() {
            e = new ArrayList<Integer>();
            child = new ArrayList<Node>();
            a = new ArrayList<Integer>();
        }

        public List<Integer> getE() {
            return e;
        }

        public List<Integer> getA() {
            return a;
        }
        
        public int getD() {
            return d;
        }

        public int getNa() {
            return na;
        }

        public List<Node> getChild() {
            return child;
        }
        
        public boolean isIsFinal() {
            return isFinal;
        }

        public void setA(List<Integer> la) {
            a = la;
        }

        public void setE(List<Integer> e) {
            this.e = e;
        }

        public void setIsFinal(boolean isFinal) {
            this.isFinal = isFinal;
        }

        public void setD(int d) {
            this.d = d;
        }

        public void setNa(int na) {
            this.na = na;
        }

        public void setChild(List<Node> child) {
            this.child = child;
        }

        //Main Decision Tree Algorithm returns Tree
        Node Decision_Tree_Learning(List<Integer> e, List<Integer> a, List<Integer> pe) throws InterruptedException
        {
            Node n = new Node();
            n.setA(a); //Set Current Set of Examples as the same as I/P Parameter
            n.setE(e); //Set Current Set of Attributes as the same as I/P Parameter
            int ca; //Most Important Attribute
            int i;
            List<Integer> new_e; //New Example Calculated for Sub trees

            //IF There are No examples
            if(e.size()==0)
            {
                n.setIsFinal(true);
                n.setD(pluralityValue(pe)); //Set Plurality Value of the Parent SEt of Examples
                return n;
            }
            //if there are no attributes left
            else if(a.size() == 0)
            {
                n.setIsFinal(true);
                n.setD(pluralityValue(e)); ////Set Plurality Value of the Current Set of Examples
                return n;
            }
            //If the examples are classified as positive or negative
            else if(classification(e)!=-1)
            {
                n.setIsFinal(true);
                n.setD(classification(e));
                return n;
            }
            else
            {
                ca = importance(a, e); //Find Important Attribute
                n.setNa(ca);

                //Create Temporary Attribute Copy
                List<Integer> a1 = new ArrayList<Integer>();
                for(i=0;i<a.size();i++)
                {
                    a1.add(a.get(i));
                } 
                
                //Remove the Important Attribute from Attribute List
                for(i=0;i<a1.size();i++)
                {
                    if(a1.get(i) == ca)
                    {
                        a1.remove(i);
                    }
                }
                
                //For Each Possible Value of the Important Attribute, Create a SubTree
                for(i = 0; i<at_value_size[ca];i++)
                {
                    //Calculate the applicable Subset of Examples for the Subtree
                    new_e = getExamplesByAttributeValue(ca,i,e);
   
                    //Create a Subtree for the Current Value
                    n.child.add(Decision_Tree_Learning(new_e, a1, e)); 
                }
                return n;
            }
        }
        
        //Prints the Tree
        void printNode(Node n)
        {
            if(n==null)
                return;
            else
            {
                if(n.isFinal)
                    System.out.println("Examples:"+n.e.toString()+" Decision: "+n.d );
                else
                    System.out.println("Examples:"+n.e.toString()+" Next Attribute: "+n.na );
                
                for(int i =0;i<n.child.size();i++)
                {
                    System.out.println("\nChild of:"+n.e.toString());  
                    printNode(n.child.get(i));
                }
            }
        }
        
        //Does Chi^2 Pruning
        void calculateDelta(Node n,Node p,int index)
        {
            int i,j;
            double delta = 0.0;
            
            //Hardcoded Chi Table for 0.05 Significance
            double chi[] = {3.84,5.99,7.82,9.49,11.07,12.59,14.07,15.51,16.92,18.31};
            
            if(n == null)
            {
                return;
            }
            else
            {
                //For Each Child in the Current Node, if all the subtrees are just leaves, find the Delta.
                for(i=0;i<n.child.size();i++)
                {
                    if(!n.child.get(i).child.isEmpty())
                        break;
                }
                if(i<n.child.size())
                {
                    for(i = 0; i<n.child.size();i++)
                    {
                        calculateDelta(n.child.get(i),n,i);
                    }
                }
                else if(!n.isFinal) 
                {
                    int[] pk;
                    int[] nk;
                    pk = pkCountPerValue(n.na,n.e); //Observed pk
                    nk = nkCountPerValue(n.na,n.e); //Observed nk
                    double expPk[] = new double[pk.length]; //Expected pk
                    double expNk[] = new double[pk.length]; //Expected nk
                    for(j = 0;j<pk.length;j++)
                    {
                        double temp1;
                        double temp2;
                        
                        //Computig expected pk
                        temp1 = (double)pk[j]+nk[j];
                        temp1 /= (double)pe + ne;
                        temp1 *= (double)pe;
                        expPk[j] = temp1;

                        //Computing Expected nk
                        temp2 = (double)pk[j]+nk[j];
                        temp2 /= (double)pe+ne;
                        temp2 *= (double)ne;
                        expNk[j] = temp2;
                        
                        //Computig Delta
                        temp1 = pk[j]-expPk[j];
                        temp1 *= temp1;
                        temp1 /= expPk[j];
                            
                        temp2 = nk[j]-expNk[j];
                        temp2 *= temp2;
                        temp1 /= expNk[j];
                            
                        delta += temp1 + temp2;
                    }
                    //if Delta value is rejected by the Chi Table
                    if(delta>=chi[n.a.size()-1])
                    {
                        System.out.println("Delta=" +delta +" is Rejected");
                        p.child.remove(index); //Prune the Tree
                    }
                    else //If Accepted
                    {
                        System.out.println("Delta=" +delta+" is Accepted");
                    }
                }
            }
        }
    }
    //Runs the Decision Tree Algorithm
    Node runDecisionTreeAlgorithm() throws InterruptedException
    {
        Node n = new Node();
        List<Integer> e = new ArrayList<Integer>();
        List<Integer> a = new ArrayList<Integer>();
        int i;
        
        for(i=0;i<n_e;i++)
            e.add(i);
        for(i=0;i<na;i++)
            a.add(i);
        
        Node res = n.Decision_Tree_Learning(e,a,null);
        System.out.println("Tree");
        n.printNode(res);
        System.out.println("\n\nPruning");
        n.calculateDelta(res,res,0);
        
        
        return res;
    }
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        String fileName;
        fileName = "input.txt";
        File file = new File(fileName);
        BufferedReader reader = null;
        String text = null;
        String [] buffer;
        int i,j;
        
        try
        {
            reader = new BufferedReader(new FileReader(file));
            
            //Get Number of Examples
            text = reader.readLine();
            int ln_e = Integer.parseInt(text);
            
            //Get Number of Attributes
            text = reader.readLine();
            int lna = Integer.parseInt(text);
            reader.close();
            
            DecisionTree d = new DecisionTree(ln_e,lna);
            
            //Parses Input.txt File
            d.loadInput();
            d.runDecisionTreeAlgorithm();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    void loadInput() throws IOException
    {
        String fileName;
        fileName = "input.txt";
        File file = new File(fileName);
        BufferedReader reader = null;
        String text = null;
        String [] buffer;
        int i,j;
        
        try
        {
            reader = new BufferedReader(new FileReader(file));
            
            //Ignore First Two Lines
            text = reader.readLine();
            text = reader.readLine();
            
            //Get Attribute Domain Lengths
            text = reader.readLine();

            buffer = text.split(" ");
            for(i = 0;i<buffer.length;i++)
            {
                at_value_size[i] = Integer.parseInt(buffer[i]);

            }
            
            //Get Example Set
            for(i=0;i<n_e;i++)
            {
                text = reader.readLine();
                buffer = text.split(" ");
                for(j = 0;j<buffer.length;j++)
                {
                    examples[i][j] = Integer.parseInt(buffer[j]);
                }
                
                //Calculating number of Positive and Negative Goals
                if(examples[i][j-1]==1)
                    pe++;
                else
                    ne++;
            }
            
        }
        finally
        {
            if (reader != null) 
            {
                reader.close();
            }
        }
    }
    
    //Finds Remaining Examples for Given Value of the Given Attribute
    List<Integer> getExamplesByAttributeValue(int a, int value, List<Integer> e)
    {
        int i;
        List<Integer> res = new ArrayList<Integer>();
        for(i=0;i<e.size();i++)
        {
            if(examples[e.get(i)][a] == value)
            {
                res.add(e.get(i));
            }
        }
        return res;
    }

    
    int classification(List<Integer> e)
    {
        int i;
        int temp;
        int pc = 0; //Yes Count
        int nc = 0; //No Count

        for(i = 0; i<e.size(); i++)
        {
            temp = examples[e.get(i)][na];
            if(temp == 1)
            {
                pc++;
            }
            else
            {
                 nc++;
            }
        }

        if(pc==0)
            return 0;
        else if(nc==0)
            return 1;
        else
            return -1;
    }
    
    int pluralityValue(List<Integer> e)
    {
        int i;
        int temp;
        int pc = 0; //Yes Count
        int nc = 0; //No Count
        
        if(e==null)
        {
            return 0;
        }
        else if(e.isEmpty())
        {
            return 0;
        }
            
        for(i = 0; i<e.size(); i++)
        {
            temp = examples[e.get(i)][na];
            if(temp == 1)
            {
                pc++;
            }
            else
            {
                 nc++;
            }
        }

        if(pc>=nc)
            return 1;
        else
            return 0;
    }
    
    int importance(List<Integer> a,List<Integer> e)
    {
        double[] gain = calculateGain(a,e);
        double max = gain[0];
        int max_el = 0;
        int i;
        for(i = 1; i<gain.length;i++)
        {
            if(gain[i]>max)
            {
                max = gain[i];
                max_el = i;
            }
        }
        return max_el;
    }
    
    double[] calculateGain(List<Integer> a,List<Integer> e)
    {
        double[] gain = new double[na];
        int i,j;
        double goalGain = B(pe,ne);
        int[] pk;
        int[] nk;
        double temp;
        
        for(i=0;i<na;i++)
        {
           gain[i] = -1.0; 
        }
        
        for(i=0;i<a.size();i++)
        {
            
            pk = pkCountPerValue(a.get(i),e);
            nk = nkCountPerValue(a.get(i),e);
            for(j=0,gain[a.get(i)] = goalGain;j<pk.length;j++)
            {
                temp = B(pk[j],nk[j]);
                gain[a.get(i)] -= ((double)(pk[j]+nk[j])/(pe+ne)) * temp;
            }
        }
        
        return gain;
    }
    
    int[] pkCountPerValue(int attribute, List<Integer> e)
    {
        int i;
        int[] pk = new int[at_value_size[attribute]]; 
        for(i = 0;i<pk.length;i++)
        {
            pk[i] = 0;
        }
        
        for(i=0;i<e.size();i++)
        {
            if(examples[e.get(i)][na] == 1)
            {
                pk[examples[e.get(i)][attribute]]++;
            }
        }
        return pk;
    }
    
    int[] nkCountPerValue(int attribute, List<Integer> e)
    {
        int i;
        int[] nk = new int[at_value_size[attribute]]; 
        for(i = 0;i<nk.length;i++)
        {
            nk[i] = 0;
        }
        
        for(i=0;i<e.size();i++)
        {
            if(examples[e.get(i)][na] == 0)
            {
                nk[examples[e.get(i)][attribute]]++;
            }
        }
        
        return nk;
    }
    
    double B(int p, int n)
    {
        if(p+n == 0)
            return 0.0;
        
        double q = (double)p/(p+n);
        double temp1 = 1.0 - q;
        double temp2 = q;
        double temp3;
        temp1 *= Logb(1.0-q,2.0);
        temp2 *= Logb(q,2.0);
        temp3 = temp1 + temp2;
        temp3 *= -1;
        return temp3;
    }
    
    double Logb(double val,double base)
    {
        if(val==0)
            return 0.0;
        return (double)Math.log(val)/(double)Math.log(base);
    }
}
