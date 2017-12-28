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
public class LogicPort extends ConfNN{
    public LogicPort()
    {
        this.NEURON_TYPE = Neuron.Type.SIGMOID_FUNCTION;
        this.EPOCHE = 80000;
        this.L_RATE = .5f;
        this.sizeLayer = new int[]{2,4,2};
        this.matrix=new double[][]
        {        
            {0,0},
            {0,1},
            {1,0},
            {1,1},
        };
        this.targetResults=new double[][]
        {
            {0,1},
            {1,0},
            {1,0},
            {0,1},
        };
    }
}
