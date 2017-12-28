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
public class DbImage extends ConfNN{
    public DbImage()
    {
        this.NEURON_TYPE = Neuron.Type.LINEAR_FUNCTION;
        this.EPOCHE = 99000;
        this.L_RATE = .0003f;
        
        this.matrix=new double[][]
        {        
            {4,10,17,31,17,17,17}, //65 A
            {30,17,17,30,17,17,30}, //66 B
            {31,16,16,16,16,16,31}, //67 C
            {30,17,17,17,17,17,30}, //68 D
//            {31,16,16,30,16,16,31}, //69 E
        };
        this.targetResults=new double[][]
        {   
            {65},
            {66},
            {67},
            {68},
//            {69},
        };
        this.sizeLayer = new int[]{matrix[0].length,7,targetResults[0].length};
    }
}
