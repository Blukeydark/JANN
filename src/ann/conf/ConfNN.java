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
abstract public class ConfNN {
    protected Neuron.Type NEURON_TYPE;
    protected int EPOCHE;
    protected float L_RATE;
    protected int[] sizeLayer;
    protected double[][] matrix;
    protected double[][] targetResults;

    public Neuron.Type getNeuronType() {
        return NEURON_TYPE;
    }

    public int getEpoche() {
        return EPOCHE;
    }

    public float getLRate() {
        return L_RATE;
    }

    public int[] getSizeLayer() {
        return sizeLayer;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public double[][] getTargetResults() {
        return targetResults;
    }
    
}
