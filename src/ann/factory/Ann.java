/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann.factory;

/**
 *
 * @author seve
 */
public interface Ann {
    
    public void trainingMatrix()  throws Exception;  // OK
    
    public boolean initNetwork(int max_epochs, float l_rate);  // OK
    
    public void setNumLayers(int numLayer, int[] size) throws Exception ;  // OK
    
    public void loadInput(double[] row)  throws Exception;  // OK
    
    public double[] getOutput()  throws Exception;  // OK
    
    public void backPropagation(double[] target);  // OK
    
    public boolean imReady();  // OK
    
    public void setTrainingMatrix(double[][] matrix); // OK
    public void setTrainingResult(double[][] result); // OK
    
    
    public void saveTrainedWeights(String pathFile); // OK
    public void loadTrainedWeights(String pathFile); // OK
    
    public void saveConf(String pathFile);
    public void loadConf(String pathFile);
    
    public double getRMSerror(); // OK
    
    public void printNetwork(); // OK
    
}
