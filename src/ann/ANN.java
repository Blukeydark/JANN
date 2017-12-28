/*
 */
package ann;

import ann.conf.AlphaBeta;
import ann.conf.Byte;
import ann.conf.DbImage;
import ann.factory.FileManager;
import ann.factory.NeuralNetwork;
import ann.factory.UtilityNN;
import ann.obj.ConfusionMatrix;
import ann.utility.ImageProcessing;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.json.JSONArray;

/**
 *
 * @author seve
 */
public class ANN {
    
    protected static Byte NNTypeByte = new Byte();
    protected static AlphaBeta NNAlphaBeta = new AlphaBeta();
    protected static DbImage NNDbImage = new DbImage();
    protected static NeuralNetwork byteNeuralNetwork;
    protected static NeuralNetwork imageNeuralNetwork;
    
    private static void initNetwork() throws Exception
    {
        String filePathByte = "export_weights_Byte.json";
        File f = new File(filePathByte);
        String filePathDbImage = "export_weights_DbImage.json";
        File f2 = new File(filePathDbImage);
        
        if(f.exists())
        {
            byteNeuralNetwork = NeuralNetwork.create(NNTypeByte);         
            byteNeuralNetwork.loadTrainedWeights(filePathByte);  
        }else
        {
            byteNeuralNetwork = NeuralNetwork.trainingAndCreate(NNTypeByte);
            byteNeuralNetwork.saveTrainedWeights(filePathByte);
        } 
        if(f2.exists())
        {
            imageNeuralNetwork = NeuralNetwork.create(NNDbImage);         
            imageNeuralNetwork.loadTrainedWeights(filePathDbImage);
        }else
        {
            imageNeuralNetwork = NeuralNetwork.trainingAndCreate(NNDbImage);
            imageNeuralNetwork.saveTrainedWeights(filePathDbImage);
        }
          
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            initNetwork();
                        
            double[][] outputCOnfusion = new double[4][1];
            int rigaImage = 0;
            for(double[] row : NNAlphaBeta.getMatrix())
            {
                /*
                Scompatto la matrice a blocchi da 5 bit
                */
                int numBit = 5;
                double riga[][] = new double[row.length/numBit][numBit];
                for(int i = 0;i < row.length; i++)
                {
                    riga[i%row.length/numBit][i%numBit]=(int)row[i];
                }
                generateImage(riga,"file_" +rigaImage + ".png");
                
                System.out.print("\n|---BIT--- MATRIX("+ riga.length + "x" + riga[0].length + ")|\n");
                
                // preparo output per seconda rete
                double[] outputByte = new double[7];
                for(int i = 0;i < riga.length; i++)
                {
                    System.out.print("\n");
                    
                    double[] byteInput = {
                        0,
                        0,
                        0,
                        riga[i][0],
                        riga[i][1],
                        riga[i][2],
                        riga[i][3],
                        riga[i][4],
                    };
                    
                    double out = byteNeuralNetwork
                                    .setPrintDebug(false)
                                    .getOutput(byteInput)[0];
                    
                    for(int x = 0;x < riga[i].length; x++)
                    {
                        System.out.print(" " + (int)riga[i][x] + "");
                    }
                    outputByte[i] = UtilityNN.round1(out, 0);
//                    System.out.print(" \n" + outputByte[i] + "\n");
                }
                
                double out = imageNeuralNetwork
                                .setPrintDebug(false)
                                .getOutput(outputByte)[0];
                System.out.print(" \n" + (char) UtilityNN.round1(out, 0) + "\n");
                System.out.print("\n|---------|\n");
                outputCOnfusion[rigaImage] = new double[]{out};
                rigaImage++;
            }
            
            
            
            float[] classMap =new float[]{65,66,67,68};
            float errorAccepted = 0.5f;
            ConfusionMatrix cm = new ConfusionMatrix(
                    NNDbImage.getTargetResults(), 
                    outputCOnfusion, 
                    classMap, 
                    0, 
                    errorAccepted
            );
            cm.evalMatrix();
            cm.printEvalutation();
            
            
        } catch (Exception ex) {
            System.out.print("\n|---ERROR---|\n");
            System.out.print("\n" + ex.getMessage() + "\n");;
            System.out.print("\n|---------|\n");
            ex.printStackTrace();
        }
        
    }
    
    
    private static boolean generateImage(double[][] matrix, String name) throws IOException
    {        
        BufferedImage imageAlpha = ImageProcessing.createFromMatrix(matrix);
        File output = new File(name);
        ImageIcon ii = new ImageIcon(imageAlpha);
        JOptionPane.showMessageDialog(null, ii);
        boolean write = ImageIO.write(imageAlpha, "png", output);
        return write;
    }
    
}
