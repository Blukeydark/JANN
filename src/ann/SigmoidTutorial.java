/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

import ann.conf.Sigmoid;
import ann.factory.UtilityNN;

/**
 *
 * @author seve
 */
public class SigmoidTutorial {
    
    /*
    STRUTTURA DATI
    */
    private static final int NX = 1; 
    private static final int NH = 4;
    private static final int NY = 1;
    private static double A;
    private static int i, k, j;
    private static double[] X;
    private static double[] H;
    private static double[] Y;
    private static double[][] wh;
    private static double[][] wy;
    
    /*
    STRUTTURA DATI AGGIUNTIVA PER APPRENDIMENTO
    */
    private static final double Eps = 0.5d;
    private static double Err;
    private static double[] D;
    private static double[] DeltaY;
    private static double[] DeltaH;

    protected static Sigmoid NNSigmoid = new Sigmoid();
    
    private static void init()
    {
        DeltaY = new double[NY];
        DeltaH = new double[NH];
        D  = new double[NY];
        X = new double[NX];
        H = new double[NH];
        Y = new double[NY];
        wh = new double[NH][NX+1];
        wy = new double[NY][NH+1];
        for(int y = 0 ; y < wh.length; y++)
        {
            for(int x = 0 ; x < wh[y].length; x++)
            {
                wh[y][x]=0.5d;
            }
        }
        for(int y = 0 ; y < wy.length; y++)
        {
            for(int x = 0 ; x < wy[y].length; x++)
            {
                wy[y][x]=0.5d;
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        init();
        // TRAINING
        // Ciclo per max_epochs volte
        for(int j=0; j < NNSigmoid.getEpoche(); j++) {            
            // LEGGO RIGA N
            for(int r = 0; r < NNSigmoid.getMatrix().length; r++){ 
                X = NNSigmoid.getMatrix()[r];
                D = NNSigmoid.getTargetResults()[r];
                // Esecuzione
                loadInput();
                // backPropagation
                backPropagation();
            }
        }
        int round = 8;
        for(int p = 0; p < NNSigmoid.getMatrix().length; p++)
        {
            X = NNSigmoid.getMatrix()[p];
            // Esecuzione
            loadInput();
            for(j=0; j < NY; j++)
            {
                double out = Y[j];
                System.out.println(""
                        + "OUT-" + (p+1)+ ": " + UtilityNN.round1(out, round) + "\n"
                );
            }
        }
        
    }
    
    
    private static void loadInput()
    {
        /*
        ESECUZIONE
        */
        
        //Calcola strato H
        for(k=0; k < NH; k++)
        {
            A =0;
            for(i=0; i < NX-1; i++)
                A = A + (wh[k][i] * X[i]);
            A = A + wh[k][NX-1]; // bias
            H[k] = 1.0 / (1.0 +  Math.exp(-A));
        }
        
        //Calcola strato Y
        for(j=0; j < NY; j++)
        {
            A =0;
            for(k=0; k < NH-1; k++)
                A = A + (wy[j][k] * H[k]);
            A = A + wy[j][NH-1]; // bias
            Y[j] = 1.0 / (1.0 + Math.exp(-A));
        }
    }
    
    private static void backPropagation()
    {
        Err = 0.0;
        //Calcolo errore strato Y
        for(j=0; j < NY; j++)
        {
            Err = D[j] - Y[j];
            DeltaY[j] = Err * Y[j] * (1 - Y[j]);
        }
        //Calcolo errore strato H
        for(k=0; k < NH-1; k++)
        {
            Err = 0.0;
            for(j=0; j < NY; j++)
                Err = Err + (DeltaY[j] * wy[j][k]);
            DeltaH[k] = Err * H[k] * (1 - H[k]);
        }
        //Modifica pesi strato Y
        for(j=0; j < NY; j++)
        {
            for(k=0; k < NH-1; k++)
                wy[j][k] = wy[j][k] + (Eps * DeltaY[j] * H[k]);
            wy[j][NH-1] += (Eps * DeltaY[j]); // bias
        }
        //Modifica pesi strato H
        for(k=0; k < NH; k++)
        {
            for(i=0; i < NX-1; i++)
                wh[k][i] = wh[k][i] + (Eps * DeltaH[k] * X[i]);
            wh[k][NX-1] += (Eps * DeltaH[k]); // bias
        }
    }
    
}
