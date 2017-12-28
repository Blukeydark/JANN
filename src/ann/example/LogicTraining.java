/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann.example;

import ann.conf.LogicPort;
import ann.factory.NeuralNetwork;
import ann.factory.UtilityNN;
import ann.obj.ConfusionMatrix;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Una rete non serve per convertire un byte in dec!!!
 * Questo è solo un esempio sul training ridotto.
 * 
 * @author seve
 */
public class LogicTraining {

    public static LogicPort confNN = new LogicPort();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            NeuralNetwork nn = NeuralNetwork.trainingAndCreate(confNN);
            //nn.printNetwork();
            
            int round = 0;
            double[][] output = new double[confNN.getMatrix().length][confNN.getTargetResults()[0].length];
            for(int p = 0; p < confNN.getMatrix().length; p++)
            {
                double[] outputNet = nn.setPrintDebug(false).getOutput(confNN.getMatrix()[p]);
                double out = outputNet[0];
                double out2 =outputNet[1];
                output[p]=outputNet;
                System.out.println(""
                        + "OUT-" + (p+1)+ ": " + UtilityNN.round1(out, round) + " - " + UtilityNN.round1(out2, round) + "\n"
                );
            }
            
            float[] classMap = {0f,1f};
            float errorAccepted = 0.09f;
            ConfusionMatrix cm = new ConfusionMatrix(
                    confNN.getTargetResults(), 
                    output, 
                    classMap, 
                    round, 
                    errorAccepted
            );
            cm.evalMatrix();
            cm.printEvalutation();
            //nn.printNetwork();
        } catch (Exception ex) {
            Logger.getLogger(LogicTraining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
