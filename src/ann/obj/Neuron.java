/*
 */
package ann.obj;

import static ann.obj.ActivationFunction.*;
/**
 *double is represented in 64 bits, with 1 sign bit, 11 bits of exponent, and 52 bits of significand.
 * @author seve
 */
public class Neuron {
    
    public double trans_value = 1;
    public double prop_value = 1;
    public Synapsis[] synapsis;
    public double bias = .1;
    public Type type;
    public enum Type {
        LINEAR_FUNCTION, 
        SIGMOID_FUNCTION,
        TANH_FUNCTION
    };

    Neuron() {
        this.type = Type.LINEAR_FUNCTION;
    }

    public Neuron(Type type) throws Exception {
        switch(type){
            case LINEAR_FUNCTION:
                this.type=type;
                break;
                
            case SIGMOID_FUNCTION:
                this.type=type;
                break;
                
            case TANH_FUNCTION:
                this.type=type;
                break;
            default:
                throw new Exception("TRANSFER FUNCTION not found!");
        }
    }
    
    
    public double transVal()
    {
        this.trans_value = 0f;
        switch(this.type){
            case LINEAR_FUNCTION:
                this.trans_value = this.prop_value;
                break;
                
            case SIGMOID_FUNCTION:
                this.trans_value = sigmoid(this.prop_value);
                break;
                
            case TANH_FUNCTION:
                this.trans_value = tanh(this.prop_value);
                break;
        }
        return this.trans_value;
    }
    
    public double derivate()
    {
        double ret = 0f;
        switch(this.type){
            case LINEAR_FUNCTION:
                ret = 1d;
                break;
                
            case SIGMOID_FUNCTION:
                ret = dsigmoid(this.trans_value);
                break;
                
            case TANH_FUNCTION:
                ret = dtanh(this.trans_value);
                break;
        }
        return ret;
    }
}
