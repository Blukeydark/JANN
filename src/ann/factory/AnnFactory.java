/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann.factory;

import ann.conf.ConfNN;
import ann.obj.Neuron;

/**
 *
 * @author seve
 */
public abstract class AnnFactory {
    public static NeuralNetwork create(Neuron.Type type, 
            int max_epochs, 
            float l_rate, 
            int[] sizeLayer,
            double[][]matrix,
            double[][]result) 
             throws Exception
    {
        NeuralNetwork nn = new NeuralNetwork();
        nn.setType(type);
        nn.initNetwork(max_epochs, l_rate);
        nn.setNumLayers(sizeLayer.length, sizeLayer);
        nn.setTrainingResult(result);
        nn.setTrainingMatrix(matrix);
        return nn;
    }
    
    public static NeuralNetwork create(ConfNN conf) 
             throws Exception
    {        
        NeuralNetwork nn = new NeuralNetwork();
        nn.setType(conf.getNeuronType());
        nn.initNetwork(conf.getEpoche(), conf.getLRate());
        nn.setNumLayers(conf.getSizeLayer().length, conf.getSizeLayer());
        nn.setTrainingResult(conf.getTargetResults());
        nn.setTrainingMatrix(conf.getMatrix());
        return nn;
    }
    
    public static NeuralNetwork trainingAndCreate(Neuron.Type type, 
            int max_epochs, 
            float l_rate, 
            int[] sizeLayer,
            double[][]matrix,
            double[][]result) 
             throws Exception
    {
        NeuralNetwork nn = NeuralNetwork.create(
                type,max_epochs,l_rate,sizeLayer,matrix,result
        );
        nn.trainingMatrix();
        return nn;
    }
    
    
    public static NeuralNetwork trainingAndCreate(ConfNN conf) 
             throws Exception
    {
        NeuralNetwork nn = NeuralNetwork.create(conf);
        nn.trainingMatrix();
        return nn;
    }
}
