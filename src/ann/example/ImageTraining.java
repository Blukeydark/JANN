/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann.example;

import ann.conf.Sigmoid;
import ann.factory.NeuralNetwork;
import ann.factory.UtilityNN;
import ann.obj.ConfusionMatrix;
import ann.obj.Neuron;

/**
 *
 * @author seve
 */
public class ImageTraining {

    protected static Sigmoid NNSigmoid = new Sigmoid();
    protected static NeuralNetwork imageNeuralNetwork;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            initNetwork();
            int round = 4;
            double[][] output = new double[NNSigmoid.getMatrix().length][NNSigmoid.getTargetResults()[0].length];
            for(int p = 0; p < NNSigmoid.getMatrix().length; p++)
            {
                imageNeuralNetwork.setPrintDebug(false).loadInput(NNSigmoid.getMatrix()[p]);
                double out = imageNeuralNetwork.setPrintDebug(false).getOutput()[0];
                output[p] = imageNeuralNetwork.setPrintDebug(false).getOutput();
                System.out.println(""
                        + "OUT-" + (p+1)+ ": " + UtilityNN.round1(out, round) + "\n"
                );
            }
            double[][] target = NNSigmoid.getTargetResults();
            double errorTot = 0;
            for(int y = 0; y < output.length; y++)
            {
                for(int x = 0; x < output[y].length; x++)
                {
                    double perc = target[y][x]/100;
                    double error = Math.abs(target[y][x] - output[y][x]);
                    if(error > target[y][x] )
                        error = target[y][x];
                    errorTot += Math.abs(error/perc);
                    System.out.print("Error " +
                           (error/perc) +"% target:" + target[y][x] + " - output:" +  output[y][x] + " error: " +  error + " perc: " +  perc + "  " +"\n"
                    );
                }
            }
            System.out.print("TOTAL Error " +
                   (( (errorTot/output.length)/output[0].length )) +"%" + "\n"
            );
//            ConfusionMatrix.evalTest(target, output, round);
            
            
        } catch (Exception ex) {
            System.out.print("\n|---ERROR---|\n");
            System.out.print("\n" + ex.getMessage() + "\n");;
            System.out.print("\n|---------|\n");
            ex.printStackTrace();
        }
    }
    private static void initNetwork() throws Exception
    {
        System.out.print("\n|---Init Neural-Network---|\n");
        imageNeuralNetwork = NeuralNetwork.create(NNSigmoid);
//        imageNeuralNetwork.printNetwork();
//        System.out.print("\n|---Training START---|\n");
        imageNeuralNetwork.trainingMatrix();
        
        
    }
}
