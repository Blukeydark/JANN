/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann.factory;

import ann.obj.Layer;
import ann.obj.Neuron;
import ann.obj.Synapsis;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author seve
 */
public class NeuralNetwork extends AnnFactory implements Ann {

    
    public int max_epochs; // numero massimo di cicli
    public float l_rate; // learning rate
    // i 3 layer IXO!
    public Layer[] layers; // input
    public double RMSerror;
    
    public double[][] training_result;
    public double[][] matrix;
    
    
    public boolean print_debug = true;
    
    private Neuron.Type type;
    
    
    
    public double[] getOutput(double[] row)  throws Exception{
        this.loadInput(row);
        return this.getOutput();
    }
    
    public Neuron.Type getType() {
        return type;
    }

    public NeuralNetwork setType(Neuron.Type type) {
        this.type = type;
        return this;
    }
    
    public NeuralNetwork setPrintDebug(boolean p)
    {
        this.print_debug = p;
        return this;
    }
        
    @Override
    public void trainingMatrix()  throws Exception
    {
        double totalRMSError = 0;
        double maxRMSError = 0;
        double minRMSError = 0;
        // TRAINING
        // Ciclo per max_epochs volte
        for(int j=0; j < this.max_epochs; j++) {
            
            // LEGGO RIGA N
            for(int r = 0; r < this.matrix.length; r++){ 
                // CARICO INPUT riga
                this.setPrintDebug(false).loadInput(this.matrix[r]);
                this.setPrintDebug(false).backPropagation(this.training_result[r]);
                double localRmsError = this.setPrintDebug(false).getRMSerror();
                totalRMSError += localRmsError * localRmsError;
                if(localRmsError > maxRMSError)
                    maxRMSError = localRmsError;
                if(localRmsError < minRMSError)
                    minRMSError = localRmsError;
            } // fine riga
        } // fine epoca
        totalRMSError = totalRMSError/this.max_epochs;
        totalRMSError= (double)java.lang.Math.sqrt(totalRMSError);
//        if(this.print_debug)
            System.out.print( ""
                    +"--------------------------\n"
                    + "RMS ERROR: \n"
                    + "" + totalRMSError + "\n"
                    + "\tMAX ERROR: \n"
                    + "\t\t" + maxRMSError + "\n"
                    + "\tMIN ERROR: \n"
                    + "\t\t" + minRMSError + "\n"
                    +"--------------------------\n"
            );
    }
        
    @Override
    public boolean initNetwork(int max_epochs, float l_rate) {
        this.max_epochs = max_epochs;
        this.l_rate = l_rate;
        return true;
    }

    @Override
    public void setNumLayers(int numLayer, int[] size) throws Exception{
        
        if(size.length != numLayer)
            throw new Exception("Size must have length == numLayer");
        
//        if(numLayer < 3)
//            throw new Exception("min numLayer is 3 ( input, hidden & output )");
        
        this.layers = new Layer[numLayer];
        String printNumLayer = "" + this.layers.length;
        String printStruct = "";
        for(int l = 0; l < this.layers.length; l++)
        {
            printStruct += "\tLayer-"+l+" elements: "+size[l]+ "\n";
            this.layers[l] = new Layer();
            this.layers[l].elements = new ArrayList(size[l]);
            
            int synapsis = l == 0?1:size[l-1];
            for(int n = 0; n < size[l]; n++)
            {
                double defaultWeight = (float)(Math.random() *0.5);
                printStruct += "\t\tNeuron-"+n+" synapsis: "+synapsis+ " defaultWeight:" + defaultWeight + "\n";
                Neuron neuron = new Neuron(this.getType());
                neuron.prop_value =5;   
                neuron.trans_value = neuron.transVal();  
                neuron.synapsis = new Synapsis[synapsis];
                for(int s = 0; s < synapsis; s++){
                    neuron.synapsis[s] = new Synapsis();
                    neuron.synapsis[s].weight =  defaultWeight;
                    neuron.synapsis[s].delta =  defaultWeight;
                }
                neuron.bias =  defaultWeight;
                this.layers[l].elements.add(neuron);
            }
        }
        if(this.print_debug)
            System.out.print("Creata rete: \n"
                    + "NumLayer:" + printNumLayer + "\n"
                    + "" + printStruct + "\n"
            );
    }
    
    @Override
    public void loadInput(double[] row)  throws Exception{
        
        if(this.layers[0].elements.size() != row.length)
        {
            throw new Exception("Load fail: matrix length < of input layer.");
        }
        
        String printStruct = "";
        for(int l = 0; l < this.layers.length; l++)
        {
            printStruct += "\tLayer-"+l+" elements: "+this.layers[l].elements.size()+ "\n";
            boolean is_input_layer = l==0;
            for(int n = 0; n < this.layers[l].elements.size(); n++)
            {
                double x = 0;
                double potetial= 0;
                if(is_input_layer)
                {
                    x=row[n];
                    potetial = x;
                    printStruct += "\t\t INPUT LAYER";
                }
                else
                {
                    int sizeLastLayer = this.layers[l-1].elements.size();
                    printStruct += "\t\t INPUT SYNAPSIS " + sizeLastLayer;
                    for(int inputLayer = 0; inputLayer < sizeLastLayer; inputLayer++)
                    {
                        x=this.layers[l-1].elements.get(inputLayer).trans_value;
                        potetial += x * this.layers[l].elements.get(n).synapsis[inputLayer].weight;
                    }
                    potetial += this.layers[l].elements.get(n).bias;
                    
                }      
                this.layers[l].elements.get(n).prop_value = potetial;
                this.layers[l].elements.get(n).transVal();
                printStruct += " -- Neuron-"+n+ " x: "+x+  " potetial: " +potetial+  " output: "+this.layers[l].elements.get(n).trans_value+ "\n";
            }
        }
        
        if(this.print_debug)
            System.out.print("LoadInput: \n"
                    + "" + printStruct + "\n"
            );
    }

    @Override
    public double[] getOutput() {
        String printStruct = "";
        int tot_layers= this.layers.length;
        Layer outputLayer = this.layers[tot_layers-1];
        int output_size = outputLayer.elements.size();
        double[] output = new double[output_size];
        for(int n = 0; n < output_size; n++){
            output[n]= UtilityNN.round(outputLayer.elements.get(n).trans_value);
            printStruct += "\t" + output[n] + "\n";
        }
        
        if(this.print_debug)
            System.out.print( ""
                    +"--------------------------\n"
                    + "OUTPUT size("+output_size+"): \n"
                    + "" + printStruct + "\n"
                    +"--------------------------\n"
            );
        return output;
    }
    
    @Override
    public void backPropagation(double[] target) {
        this.computeDelta(target);
        this.updateWeight();
    }
    
    public void computeDelta(double[] target) {
        this.RMSerror=0;
        // al contrario per i layers
        for(int l = this.layers.length-1; l > 0; l--)
        {
            boolean is_output_layer = l==this.layers.length-1;
            for(int n = 0; n < this.layers[l].elements.size(); n++)
            {                
                double tran = this.layers[l].elements.get(n).trans_value;
                double derivate = this.layers[l].elements.get(n).derivate();
                double delta; // Dj
                if(is_output_layer)
                    // ∆o =  f'( Po )(T-O)  
                    delta = derivate * UtilityNN.computeOutputDelta(tran,target[n]) ;
                else
                    // ∆h =  f'( Ph )(∑(Wio * ∆o))  
                    delta = derivate * UtilityNN.computeHiddenDelta(this.layers[l+1], n);
                
                if(is_output_layer)
                    this.RMSerror+=0.5 * delta * delta;
                
                double lRate = this.l_rate;
                for(int s = 0; s < this.layers[l].elements.get(n).synapsis.length; s++)
                {
                    // valore neurone ingresso
                    double x= this.layers[l-1].elements.get(s).trans_value;
                    // ∆wij = η Dj x
                    this.layers[l].elements.get(n).synapsis[s].delta = lRate * delta * x;
                }
                this.layers[l].elements.get(n).bias += lRate * delta;
            }
        }
    }
    
    public void updateWeight() {
        // al contrario per i layers
        for(int l = this.layers.length-1; l > 0; l--)
        {
            for(int n = 0; n < this.layers[l].elements.size(); n++)
            {                  
                for(int s = 0; s < this.layers[l].elements.get(n).synapsis.length; s++)
                {
                    double peso = this.layers[l].elements.get(n).synapsis[s].delta;
                    this.layers[l].elements.get(n).synapsis[s].weight += peso;
                }
            }
        }
    }
       
    @Override
    public boolean imReady() {
        boolean ret= true;
        
        if(this.matrix.length < 1)
                ret= false;
        
        if(this.training_result.length < 1)
                ret= false;
        
        if(this.layers.length < 1)
                ret= false;
        
        if(this.max_epochs < 1)
                ret= false;
                
        return ret;
    }

    @Override
    public void setTrainingMatrix(double[][] matrix) {        
        this.matrix=matrix;
    }
    
    public JSONArray exportWeight()
    {
        JSONArray layers = new JSONArray();
        for(int l = 0; l < this.layers.length; l++)
        {
            JSONArray neurons = new JSONArray();
            for(int n = 0; n < this.layers[l].elements.size(); n++)
            {                
                JSONObject neuron = new JSONObject();
                JSONArray synapsis = new JSONArray();
                for(int s = 0; s < this.layers[l].elements.get(n).synapsis.length; s++)
                {
                    JSONObject synWeight = new JSONObject();
                    synWeight.put("weight", this.layers[l].elements.get(n).synapsis[s].weight);
                    synWeight.put("delta", this.layers[l].elements.get(n).synapsis[s].delta);
                    synapsis.put(synWeight);
                }
                
                neuron.put("synapsis", synapsis);
                neuron.put("bias", this.layers[l].elements.get(n).bias);
                neurons.put(neuron);
            }
            layers.put( neurons);
        }
        return layers;
    }
    
    
    public boolean loadWeight(JSONArray layers)
    {
        for(int l = 0; l < this.layers.length; l++)
        {
            JSONArray neurons = layers.getJSONArray(l);
            for(int n = 0; n < this.layers[l].elements.size(); n++)
            {                
                JSONObject neuron = neurons.getJSONObject(n);
                JSONArray synapsis = neuron.getJSONArray("synapsis");
                for(int s = 0; s < this.layers[l].elements.get(n).synapsis.length; s++)
                {
                    JSONObject synWeight = synapsis.getJSONObject(s);
                    this.layers[l].elements.get(n).synapsis[s].weight = synWeight.getDouble("weight");
                    this.layers[l].elements.get(n).synapsis[s].delta = synWeight.getDouble("delta");
                }
                this.layers[l].elements.get(n).bias = neuron.getDouble("bias");
            }
        }
        return true;
    }

    @Override
    public void saveTrainedWeights(String pathFile) {        
        JSONArray weightsByte = this.exportWeight();
        String contentByte = weightsByte.toString();
        FileManager.write(contentByte.getBytes(), pathFile);        
    }

    @Override
    public void loadTrainedWeights(String pathFile) {        
        String content = FileManager.readFile(pathFile);
        JSONArray fileArray = new JSONArray(content);
        this.loadWeight(fileArray);
    }

    @Override
    public void setTrainingResult(double[][] result) {
        this.training_result=result;
    }

    @Override
    public void saveConf(String pathFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadConf(String pathFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public double getRMSerror() {        
//        double mediaError = this.RMSerror/this.matrix.length;
//        this.RMSerror = (double)java.lang.Math.sqrt(mediaError);
        if(this.print_debug)
            System.out.print( ""
                    +"--------------------------\n"
                    + "RMS ERROR: \n"
                    + "" + this.RMSerror + "\n"
                    +"--------------------------\n"
            );
        return this.RMSerror;
    }

    @Override
    public void printNetwork() {
        String printStruct = "";
        for(int l = 0; l < this.layers.length; l++)
        {
            printStruct += "\tLayer-"+l+" elements: "+this.layers[l].elements.size()+ "\n";
            boolean is_input_layer = l==0;
            for(int n = 0; n < this.layers[l].elements.size(); n++)
            {                
                printStruct += "\t\tNeuron-"+n+ 
                        " potetial: " +this.layers[l].elements.get(n).prop_value+  
                        " output: "+this.layers[l].elements.get(n).trans_value+ "\n";
                for(int s = 0; s < this.layers[l].elements.get(n).synapsis.length; s++)
                {
                    printStruct += "\t\t\tWeight: " + this.layers[l].elements.get(n).synapsis[s].weight+ "\n";
                    printStruct += "\t\t\tDelta: " + this.layers[l].elements.get(n).synapsis[s].delta+ "\n";
                }
                printStruct += "\t\t\tBias: " + this.layers[l].elements.get(n).bias+ "\n";
            }
        }
        
        System.out.print( ""
                +"--------------------------\n"
                + "AN_NETWORK: \n"
                + "" + printStruct + "\n"
                +"--------------------------\n"
        );
    }

}
