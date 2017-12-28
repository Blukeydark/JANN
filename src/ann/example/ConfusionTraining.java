/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann.example;

import ann.conf.Confusion;
import ann.factory.NeuralNetwork;
import ann.factory.UtilityNN;
import ann.obj.ConfusionMatrix;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Una rete non serve per convertire un byte in dec!!!
 * Questo è solo un esempio sul training ridotto.
 * 
 * @author seve
 */
public class ConfusionTraining {

    public static Confusion confNN = new Confusion();
    public static NeuralNetwork nn;
    
    public static void initNet() throws Exception
    {
        String filePathByte = "Export_ConfusionTraining_Weights_epoch_" +confNN.getEpoche() + ".json";
        File f = new File(filePathByte);
        if(f.exists())
        {
            nn = NeuralNetwork.create(confNN);
            nn.loadTrainedWeights(filePathByte);
        }else{
            nn = NeuralNetwork.trainingAndCreate(confNN);
            nn.saveTrainedWeights(filePathByte);
        }        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            initNet();
            //nn.printNetwork();
            
            int round = 4;
            double[][] output = new double[confNN.getMatrix().length][confNN.getTargetResults()[0].length];
            for(int p = 0; p < confNN.getMatrix().length; p++)
            {
                nn.setPrintDebug(false).loadInput(confNN.getMatrix()[p]);
                output[p]=nn.setPrintDebug(false).getOutput();
                double out = nn.setPrintDebug(false).getOutput()[0];
                double out2 =0;//nn.setPrintDebug(false).getOutput()[1];
                System.out.println(""
                        + "OUT-" + (p+1)+ ": " + UtilityNN.round1(out, round) + " - " + UtilityNN.round1(out2, round) + "\n"
                );
            }
            
            float[] classMap = {0.2f,0.6f,0.9f};
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
            Logger.getLogger(ConfusionTraining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
