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
public class Sigmoid extends ConfNN{
    public Sigmoid()
    {
        this.NEURON_TYPE = Neuron.Type.SIGMOID_FUNCTION;
        this.EPOCHE = 99999;
        this.L_RATE = 0.5f;
        this.sizeLayer = new int[]{1,4,1};
        this.matrix=new double[][]
        {        
            {0.0000},
            {0.0625},
            {0.1250},
            {0.1875},
            {0.2500},
            {0.3125},
            {0.3750},
            {0.4375},
            {0.5000},
            {0.5625},
            {0.6250},
            {0.6875},
            {0.7500},
            {0.8125},
            {0.8750},
            {0.9375},
            {1.0000}
        };
        this.targetResults=new double[][]
        {   
            {0.5000},
            {0.6531},
            {0.7828},
            {0.8696},
            {0.9000},
            {0.8696},
            {0.7828},
            {0.6531},
            {0.5000},
            {0.3469},
            {0.2172},
            {0.1304},
            {0.1000},
            {0.1304},
            {0.2172},
            {0.3469},
            {0.5000}
        };
    }
}
