/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann.example;

import ann.conf.Byte;
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
public class ByteTraining {

    protected static Byte NNTypeByte = new Byte();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            NeuralNetwork nn = NeuralNetwork.trainingAndCreate(NNTypeByte);
            
            //nn.printNetwork();
            
            
            int round = 0;
            double[][] output = new double[4][1];
            double[][] target = new double[4][1];
            int indexConfusion = 0;
            for(int p = 0; p < NNTypeByte.getMatrix().length; p++)
            {
                nn.setPrintDebug(false).loadInput(NNTypeByte.getMatrix()[p]);
                double out =UtilityNN.round1( nn.setPrintDebug(false).getOutput()[0] ,round);
                
                System.out.println(""
                        + "OUT-" + (p+1)+ ": " + UtilityNN.round1(out,round) + "\n"
                );
            }
            double[] test = {1,1,1,1,1,1,1,0};
            nn.setPrintDebug(false).loadInput(test);
            double out = nn.setPrintDebug(false).getOutput()[0];
            System.out.println(""
                    + "OUT-TEST: " + UtilityNN.round1(out, 0) + "\n"
            );
            
            output[indexConfusion] =  new double[]{127.5};
            target[indexConfusion]= new double[]{128f};
            indexConfusion++;
            output[indexConfusion] =  new double[]{64f};
            target[indexConfusion]= new double[]{64f};
            indexConfusion++;
            output[indexConfusion] =  new double[]{254d};
            target[indexConfusion]= new double[]{254d};
            indexConfusion++;
            output[indexConfusion] =  new double[]{16};
            target[indexConfusion]= new double[]{16};
            float[] classMap = {128f,64f,254f,16f};
            float errorAccepted = 0.5f;
            ConfusionMatrix cm = new ConfusionMatrix(
                    target, 
                    output, 
                    classMap, 
                    round, 
                    errorAccepted
            );
            cm.evalMatrix();
            cm.printEvalutation();
            System.out.print("\n\tABS: "+Math.abs(127f - 128f)+"\n");
        } catch (Exception ex) {
            Logger.getLogger(ByteTraining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
