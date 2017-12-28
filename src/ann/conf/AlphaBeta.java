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
public class AlphaBeta extends ConfNN{
    public AlphaBeta()
    {
        this.NEURON_TYPE = Neuron.Type.LINEAR_FUNCTION;
        this.EPOCHE = 10000;
        this.L_RATE = .42f;
        this.sizeLayer = new int[]{8,8,4,1};
        this.matrix=new double[][]
        {        
            // http://www.binaryhexconverter.com/binary-to-decimal-converter
            { // A
                0,0,1,0,0, // 4
                0,1,0,1,0, // 10
                1,0,0,0,1, // 17
                1,1,1,1,1, // 31
                1,0,0,0,1, // 17
                1,0,0,0,1, // 17
                1,0,0,0,1, // 17
            },          
            { // B
                1,1,1,1,0, // 30
                1,0,0,0,1, // 17
                1,0,0,0,1, // 17
                1,1,1,1,0, // 30
                1,0,0,0,1, // 17
                1,0,0,0,1, // 17
                1,1,1,1,0, // 30
            },      
            { // C
                1,1,1,1,1, // 31
                1,0,0,0,0, // 16
                1,0,0,0,0, // 16
                1,0,0,0,0, // 16
                1,0,0,0,0, // 16
                1,0,0,0,0, // 16
                1,1,1,1,1, // 31
            },      
            { // D
                1,1,1,1,0, // 30
                1,0,0,0,1, // 17
                1,0,0,0,1, // 17
                1,0,0,0,1, // 17
                1,0,0,0,1, // 17
                1,0,0,0,1, // 17
                1,1,1,1,0, // 30
            },          
//            { // E
//                1,1,1,1,1, // 31
//                1,0,0,0,0, // 16
//                1,0,0,0,0, // 16
//                1,1,1,1,0, // 30
//                1,0,0,0,0, // 16
//                1,0,0,0,0, // 16
//                1,1,1,1,1, // 31
//            },
        };
        this.targetResults=new double[][]
        {    // 7 numeri a 5 bit
            // ordinate sarebbero E C B D A
            {4,10,17,31,17,17,17}, //65 A
            {30,17,17,30,17,17,30}, //66 B
            {31,16,16,16,16,16,31}, //67 C
            {30,17,17,17,17,17,30}, //68 D
//            {31,16,16,30,16,16,31}, //69 E
        };
    }
}
