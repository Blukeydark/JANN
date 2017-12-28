/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann.conf;

import ann.obj.Neuron;

/**
 *
 * @author seve
 */
public class Confusion extends ConfNN{
    public Confusion()
    {
        this.NEURON_TYPE = Neuron.Type.SIGMOID_FUNCTION;
        this.EPOCHE = 500000;
        this.L_RATE = .5f;
        this.sizeLayer = new int[]{3,3,1};
        this.matrix=new double[][]
        {
            
            // 0 = GATTO
            {60,0,0},
            {60,0,0},
            {60,0,0},
            {60,0,0},
            // 1 = CANE
            {0,60,0},
            {0,60,0},
            {0,60,0},
            {0,60,0},
            // 2 = CONIGLIO
            {0,0,60},
            {0,0,60},
            {0,0,60},
            {0,0,60},
            
            // 0 = GATTO
            {130,0,0},
            {140,0,0},
            {140,0,0},
            {190,0,0},
            // 1 = CANE
            {0,130,0},
            {0,140,0},
            {0,140,0},
            {0,190,0},
            // 2 = CONIGLIO
            {0,0,130},
            {0,0,140},
            {0,0,140},
            {0,0,190},
            
             
            // 0 = GATTO
            {180,5,5},
            {180,5,5},
            {180,5,5},
            {180,5,5},
            // 1 = CANE
            {5,180,5},
            {5,180,5},
            {5,180,5},
            {5,180,5},
            // 2 = CONIGLIO
            {5,5,180},
            {5,5,180},
            {5,5,180},
            {5,5,180},
             
            // 0 = GATTO
            {255,5,5},
            {255,5,5},
            {255,5,5},
            {255,5,5},
            // 1 = CANE
            {5,255,5},
            {5,255,5},
            {5,255,5},
            {5,255,5},
            // 2 = CONIGLIO
            {5,5,255},
            {5,5,255},
            {5,5,255},
            {5,5,255},
            
        };
        this.targetResults=new double[][]
        {
            // 0 = GATTO
            {0.2},
            {0.2},
            {0.2},
            {0.2},
            // 1 = CANE
            {0.6},
            {0.6},
            {0.6},
            {0.6},
            // 2 = CONIGLIO
            {0.9},
            {0.9},
            {0.9},
            {0.9},
            
            // 0 = GATTO
            {0.2},
            {0.2},
            {0.2},
            {0.2},
            // 1 = CANE
            {0.6},
            {0.6},
            {0.6},
            {0.6},
            // 2 = CONIGLIO
            {0.9},
            {0.9},
            {0.9},
            {0.9},
            
            // 0 = GATTO
            {0.2},
            {0.2},
            {0.2},
            {0.2},
            // 1 = CANE
            {0.6},
            {0.6},
            {0.6},
            {0.6},
            // 2 = CONIGLIO
            {0.9},
            {0.9},
            {0.9},
            {0.9},
            
            // 0 = GATTO
            {0.2},
            {0.2},
            {0.2},
            {0.2},
            // 1 = CANE
            {0.6},
            {0.6},
            {0.6},
            {0.6},
            // 2 = CONIGLIO
            {0.9},
            {0.9},
            {0.9},
            {0.9},
        };
    }
}
