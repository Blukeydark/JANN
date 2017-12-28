/*
 * CONFUSION MATRIX
 */
package ann.obj;

import ann.factory.UtilityNN;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seve
 */
public class ConfusionMatrix {
    
    protected double[][] confusionMatrix;
    
    protected double[][] target;
    protected double[][] output;
    
    protected double accuracy;
    protected double[] precision;
    protected double[] recall;
    
    protected int pattern_class; // min 2 = booleana!
    
    protected float[] classMap;
    protected int floatRound;
    protected float errorAccepted;
    

    /**
     * 
     * @param target
     * @param output
     * @param classMap
     * @param floatRound
     * @param errorAccepted
     * @throws Exception 
     */
    public ConfusionMatrix(
            double[][] target, 
            double[][] output, 
            float[] classMap, 
            int floatRound,
            float errorAccepted
    ) throws Exception 
    {   
        int patterns = classMap.length;
        if(patterns < 2)
            throw new Exception("Min 2 classes for ConfusionMatrix.patterns");
        this.confusionMatrix = new double[patterns][patterns];
        this.accuracy = 0.;
        this.precision = new double[patterns];
        this.recall = new double[patterns];  
        this.pattern_class = patterns; 
        
        this.target = target; 
        this.output = output; 
        this.classMap = classMap; 
        this.floatRound = floatRound; 
        this.errorAccepted = errorAccepted; 
        
        this.populateMatrix();
    }
    
    /**
     * 
     */
    public void evalMatrix()
    {        
        int patterns = confusionMatrix.length;
        double totalSum = 0;
        double[] classPV = new double[patterns];
        double[] classPrecision = new double[patterns];
        double[] classRecall = new double[patterns];
        for (int y = 0; y < patterns; y++) {
            double col_ = 0.;
            double row_ = 0.;
            for(int x = 0; x < patterns; x++)
            {
                if(x == y)
                {
                    classPV[y]= confusionMatrix[y][x];
                    accuracy += confusionMatrix[y][x];
                }
                totalSum += confusionMatrix[y][x];
                row_ += confusionMatrix[y][x];
                col_ += confusionMatrix[x][y];
            }
            if(classPV[y] > 0)
            {
                classPrecision[y] =classPV[y] /  row_;
                classRecall[y] = classPV[y] / col_;
            }else{
                classPrecision[y] =0;
                classRecall[y] = 0;                
            }
            
        }

        accuracy /= totalSum;        
        precision = classPrecision;
        recall = classRecall;
    }
    
    /**
     * 
     */
    public void printEvalutation()
    {        
        System.out.println("--------------------");
        System.out.println("ANN model evaluation");
        System.out.println("--------------------");
        
        System.out.println("CONFUSION MATRIX");
        for (int y = 0; y < pattern_class; y++) {            
            System.out.print("\t   " + y);
        }
        System.out.print("\n");
        for (int y = 0; y < pattern_class; y++) {
            System.out.print(""+y+ "");
            for(int x = 0; x < pattern_class; x++)
            {                
                System.out.print("\t|"+confusionMatrix[y][x] +"| ");
            }
            System.out.print("\n");
        }
        System.out.println("--------------------");
        System.out.printf("Accuracy: %.1f %%\n", accuracy * 100);
        System.out.println("Precision:");
        for (int i = 0; i < pattern_class; i++) {
            System.out.printf(" class %d: %.1f %%\n", i+1, precision[i] * 100);
        }
        System.out.println("Recall:");
        for (int i = 0; i < pattern_class; i++) {
            System.out.printf(" class %d: %.1f %%\n", i+1, recall[i] * 100);
        }
    }
        
    private void populateMatrix() throws Exception
    {
        for (int y = 0; y < output.length; y++) {
            for(int x = 0; x < output[y].length; x++)
            {
                float actual_ = (float) UtilityNN.round1(target[y][x], floatRound);
                float predicted_ = (float)  UtilityNN.round1(output[y][x], floatRound);
                
                int col = -1;
                int row = -1;
                for(int m = 0; m < classMap.length; m++)
                {
                    if(Math.abs(actual_ - classMap[m]) <= errorAccepted)
                        col = m;
                    if(Math.abs(predicted_ - classMap[m]) <= errorAccepted)
                        row = m;
                }
                
                if(col == -1)
                    throw new Exception("actual_: " + actual_ + " not found in classMap!");
                
                if(row == -1)
                {
//                    throw new Exception("predicted_: " + predicted_ + " !");
                    /*
                    Non ho corrispondene. La rete ha risposto con qualcosa di non comprensibile.
                    Assegno l'errore casualmente 
                    (sposto alla classe a destra o alla prima classe)
                    */
                    int tempX = col+1 > output[y].length?0:col+1;
                    confusionMatrix[col][tempX] += 1;
                }                    
                else
                    confusionMatrix[col][row] += 1;
            }
        }
    }
    
}
