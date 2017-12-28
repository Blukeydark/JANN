/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann.example;

import ann.factory.NeuralNetwork;
import ann.factory.UtilityNN;
import ann.obj.Neuron;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seve
 */
public class ImageByteTraining {

    
    public static final double matrice[][]=
    {      
//        { // a sub4
//            // 0,0,1,0,0,0,1,0,     
//            // 1,0,1,0,0,0,1,1,
//            // 1,1,1,1,1,0,0,0,
//            // 1,1,0,0,0,1,1,0,
//            // 0,0,1,
//            0,0,1,0,0,
//            0,1,0,1,0,
//            1,0,0,0,1,
//            1,1,1,1,1,
//            1,0,0,0,1,
//            1,0,0,0,1,
//            1,0,0,0,1,
//        },
        { // a sub1
            0,0,1,0,0,0,1,0, // 34
        },      
        { // a sub2
            1,0,1,0,0,0,1,1, // 163
        },      
        { // a sub3
            1,1,1,1,1,0,0,0, // 248
        },       
        { // a sub4
            1,1,0,0,0,1,1,0, // 198
        },         
        { // tail 3 + 5 zeri
            0,0,1,  0,0,0,0,0, // 32
        },
         
    };
    public static final double desiderati[][]=
    {
        {34}, //65 = A
        {163}, //66 = B
        {248}, //67 = C
        {198}, //68 = D  
        {32}, //68 = D  
    };
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            int[] sizeLayer = {8,8,1};
            NeuralNetwork nn = NeuralNetwork.create(
                    Neuron.Type.LINEAR_FUNCTION,
                    10000,
                    .42f,
                    sizeLayer,
                    matrice,
                    desiderati
            );
            nn.setPrintDebug(true).getOutput();
            nn.setPrintDebug(false).trainingMatrix();
            nn.printNetwork();
            
            double[] outputRete = new double[5];
            nn.setPrintDebug(false).loadInput(matrice[0]);
            outputRete[0] = UtilityNN.round3(nn.setPrintDebug(true).getOutput()[0], 0);
            nn.setPrintDebug(false).loadInput(matrice[1]);
            outputRete[1] = UtilityNN.round3(nn.setPrintDebug(true).getOutput()[0], 0);
            nn.setPrintDebug(false).loadInput(matrice[2]);
            outputRete[2] = UtilityNN.round3(nn.setPrintDebug(true).getOutput()[0], 0);
            nn.setPrintDebug(false).loadInput(matrice[3]);
            outputRete[3] = UtilityNN.round3(nn.setPrintDebug(true).getOutput()[0], 0);
            nn.setPrintDebug(false).loadInput(matrice[4]);
            outputRete[4] = UtilityNN.round3(nn.setPrintDebug(true).getOutput()[0], 0);
            
            
            double[][] matrix2 = {
                {34,163,248,198,32}, //65 A
            };
            double[][] target2 = {{65}};
            int[] sizeLayer2 = {5,5,1};
            NeuralNetwork nn2 = NeuralNetwork.create(
                    Neuron.Type.LINEAR_FUNCTION,
                    10000,
                    .012f,
                    sizeLayer2,
                    matrix2,
                    target2
            );
            nn2.setPrintDebug(false).trainingMatrix();
            nn2.setPrintDebug(true).getOutput();
            
            nn2.setPrintDebug(true).loadInput(outputRete);
            nn2.setPrintDebug(true).getOutput();
            //nn2.printNetwork();
        } catch (Exception ex) {
            Logger.getLogger(ImageByteTraining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
