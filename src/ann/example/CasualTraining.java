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
public class CasualTraining {

        
    // http://www.binaryhexconverter.com/binary-to-decimal-converter
    public static final double matrice[][]=
    {        // 7 numeri a 5 bit
            {32,0,0,5}, //65 A
            {5,32,0,0}, //66 B
            {0,5,32,0}, //67 C
            {0,0,5,32}, //68 D
            
//        {4,     10,17,   31, 17,17,  17}, //65 A
//        {30,    17,17,   30, 17,17,  30}, //66 B
//        {31,    16,16,   16, 16,16,  31}, //67 C
//        {30,    17,17,   17, 17,17,  30}, //68 D
//        {31,16,16,30,16,16,31}, //69 E
    };
    public static final double desiderati[][]=
    {
        {.2}, // 65
        {.3}, // 66
        {.1}, // 67
        {.4}, // 68
//        {0,1,0,0,0,1,0,1}, // 69
    };
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            int[] sizeLayer = {matrice[0].length,5,desiderati[0].length};
            NeuralNetwork nn = NeuralNetwork.create(
                    Neuron.Type.SIGMOID_FUNCTION,
                    50000,
                    .5f,
                    sizeLayer,
                    matrice,
                    desiderati
            );
//            nn.setPrintDebug(true).getOutput();
            nn.setPrintDebug(false).trainingMatrix();
            //nn.printNetwork();
            
            System.out.println("OUTPUT: \n");
            for(int p = 0; p < matrice.length; p++)
            {
                nn.setPrintDebug(false).loadInput(matrice[p]);
                double[] output = nn.setPrintDebug(false).getOutput();
                System.out.print("\t");
                for(int o = 0; o<output.length;o++)
                {
                    double out = output[o];
                    System.out.print(""
                            + "" + UtilityNN.round1(out, 3) + " "
                    );
                }
                System.out.println("\n");
                    
            }
//            nn.printNetwork();
            
        } catch (Exception ex) {
            Logger.getLogger(CasualTraining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
