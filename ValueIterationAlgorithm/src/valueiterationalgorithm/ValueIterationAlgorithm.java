/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valueiterationalgorithm;
import java.util.*;
import java.io.*;
import java.nio.*;

/**
 *
 * @author kksaikrishna
 */
public class ValueIterationAlgorithm {

    Vector<Double> u1;
    Vector<Double> u2;
    Vector<Integer> pi;
    Vector<Double> r;
    Vector<Vector<HashMap<Integer,Double>>> tp;
    double gamma;
    double epsilon;
    int numberOfStates;
    int numberOfActions;
    Vector<Integer> terminalStates;
    
    ValueIterationAlgorithm()
    {
        u1 = new Vector<Double>();
        u2 = new Vector<Double>();
        pi = new Vector<Integer>();
        tp = new Vector<Vector<HashMap<Integer,Double>>>();
        r = new Vector<Double>();
        terminalStates = new Vector<Integer>();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        ValueIterationAlgorithm obj = new ValueIterationAlgorithm();
        try
        {
            //Parses Input.txt File
            obj.LoadInput();
            //Computes Utilities
            obj.ComputeUtilities();
            
            //Outputs Utilities in the Form of State --- Utilities --- Actions
            System.out.println("State --- Converged Utilities --- Action Policy");
            for(int i =0; i<obj.numberOfStates;i++)
            {
                System.out.println((i+1) + " --- " + obj.u2.get(i) + " --- " + (obj.pi.get(i)+1));
                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    //Reads Input from the input.txt file
    void LoadInput() throws IOException
    {
        String fileName;
       // fileName = System.getProperty("user.dir") + "//input.txt";
        fileName = "input.txt";
        File file = new File(fileName);
        BufferedReader reader = null;
        String text = null;
        String [] tpValues;
        Vector<HashMap<Integer,Double>> ap = new Vector<HashMap<Integer,Double>>();
        int lasttp0 = 0;
        int i;

        try
        {
            reader = new BufferedReader(new FileReader(file));
            
            //Get Number of States
            text = reader.readLine();
            numberOfStates = Integer.parseInt(text);
            
            //Get Number of Actions
            text = reader.readLine();
            numberOfActions = Integer.parseInt(text);
            
            //Get Transition Table with Probabilites
            do
            {
                text = reader.readLine();
                tpValues = text.split(" ");
                
                if(tpValues.length == 1)
                {
                    break;
                }
                
                if(lasttp0 != Integer.parseInt(tpValues[0])-1)
                {
                    ap = new Vector<HashMap<Integer,Double>>();
                    lasttp0 = Integer.parseInt(tpValues[0])-1;
                }
                
                HashMap<Integer,Double> prob = new HashMap<Integer,Double>();
                for(i = 2; i < tpValues.length; i+=2)
                {
                    prob.put(Integer.parseInt(tpValues[i])-1, Double.parseDouble(tpValues[i+1]));
                }
            
                ap.add(Integer.parseInt(tpValues[1])-1, prob);
                
                tp.add(Integer.parseInt(tpValues[0])-1,ap);

            }while(true);
            
            //Gamma
            gamma = Double.parseDouble(tpValues[0]);
            
            //Epsilon
            text = reader.readLine();
            tpValues = text.split(" ");
            epsilon = Double.parseDouble(tpValues[0]);
            
            //Terminal State
            text = reader.readLine();
            tpValues = text.split(" ");
            for(i = 0; i < tpValues.length; i++)
            {
                terminalStates.add(Integer.parseInt(tpValues[i]));
            }
            
            //reward
            text = reader.readLine();
            tpValues = text.split(" ");
            for(i = 0; i < tpValues.length; i++)
            {
                r.add(Double.parseDouble(tpValues[i]));
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
    
    //Computes Utilities Based on Value Iteration Algorithm
    void ComputeUtilities()
    {
        double delta;
        double temp1=0,temp2=0,temp;
        int i,j,k;
        double maxElement;
        int flag = 0;
        double change;
        int count = 0;
        int maxPi;
        Vector<Double> actionWiseUtility = new Vector<Double>();
        
        //Termination Condition
        change = epsilon * (1 - gamma) / gamma;
        
        //All Utilities are initially zero
        for(i=0;i<numberOfStates;i++)
        {
            u2.add(i, 0.0);
        }

        do
        {
            //Initialization
            delta = 0;
            
            //Copy U from U'
            u1.clear();
            u1.addAll(0, u2);
            
            count++;
            
            //For Each State
            for (i=0; i<numberOfStates; i++) 
            {
                
                //Neglect Utility Computation for Terminal States except for the first time
                if(count!=1)
                {
                    for(k = 0; k<terminalStates.size();k++)
                    {
                        if(i==terminalStates.get(k)-1)
                        {    
                            flag = 1;
                            break;
                        }
                    
                    }
                    if(flag == 1)
                    {
                        flag = 0;
                        continue;
                    }
                }
                
                //For each Action in the state
                for(j=0;j<numberOfActions;j++)
                {
                    //Compute Summation
                    Iterator it = tp.get(i).get(j).entrySet().iterator();
                    while(it.hasNext())
                    {
                        Map.Entry pair = (Map.Entry)it.next();
                        temp = (double)pair.getValue() * u1.get((int)pair.getKey());
                        temp1 += temp;
                    }
                    actionWiseUtility.add(j, temp1);
                    temp1 = 0;
                }
                
                //Compute Maximum Element from all the summations
                maxElement = actionWiseUtility.get(0);
                maxPi = 0;
                for(int l = 1; l< numberOfActions; l++)
                {
                    if(actionWiseUtility.get(l)>maxElement)
                    {
                        maxElement = actionWiseUtility.get(l);
                        maxPi = l;
                    }
                }
                
                //Compute Bellman Equation
                temp2 = r.get(i) + gamma * maxElement;
                
                //Modify U' Function
                u2.remove(i);
                u2.add(i, temp2);
                
                //Modify Pi Function
                pi.add(i,maxPi);
                
                //Update Delta Value
                if((u2.get(i) - u1.get(i)) > delta)
                {
                    delta = u2.get(i) - u1.get(i); 
                }
            }
        }while(delta > change); //if change is lesser than Epsilon
        
        // Modify Policy to -1 for Terminal States
        for(k = 0; k<terminalStates.size();k++)
        {
            pi.remove(terminalStates.get(k)-1);
            pi.add(terminalStates.get(k)-1, -1);
        }
        
  
    }
}
