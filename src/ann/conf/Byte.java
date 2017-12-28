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
public class Byte extends ConfNN{
    public Byte()
    {
        this.NEURON_TYPE = Neuron.Type.LINEAR_FUNCTION;
        this.EPOCHE = 9024;
        this.L_RATE = .005f;
        this.sizeLayer = new int[]{8,8,1};
        this.matrix=new double[][]
        {        
            {0,0,0,0,0,0,0,1},
            {0,0,0,0,0,0,1,0},
            {0,0,0,0,0,1,0,0},
            {0,0,0,0,1,0,0,0},
            {0,0,0,1,0,0,0,0},
            {0,0,1,0,0,0,0,0},
            {0,1,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,1},
        };
        this.targetResults=new double[][]
        {
            {1},
            {2},
            {4},
            {8},
            {16},
            {32},
            {64},
            {128},
            {129},
        };
    }
}
