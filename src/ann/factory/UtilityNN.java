/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann.factory;

import ann.obj.Layer;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author seve
 */
public class UtilityNN {
    
    public static double computeHiddenDelta(Layer fromLayer, int indexNeuron)
    {
        double delta = 0;
        for(int s = 0; s < fromLayer.elements.size(); s++)
        {
            delta += fromLayer.elements.get(s).synapsis[indexNeuron].delta * 
                    fromLayer.elements.get(s).synapsis[indexNeuron].weight;
        }
        return delta;
    }
    
    public static double computeOutputDelta(double out, double des)
    {
        double delta;
        // Dj =  (yj  – dj )  f'( Pj )
        delta = (des - out);
        return delta;        
    }
    
    
    public static double normalizeDecimalLimit(double value)
    {
        double limit = 0.0001d;
        if(value > 0 && value < limit)
            value = limit;
        if(value < 0 && value > -limit)
            value = -limit;
        return value;
    }
        
    
    public static double round(double d) {
        int decimalPlace = 4;
        return (double) UtilityNN.round1(d, decimalPlace);
    }
    public static float PopularRound(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    
    public static double round1(double value, int scale) {
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }
    
    public static float round2(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
    }
    
    public static float round3(double value, int scale)
    {
        value = UtilityNN.round1((float)value, scale);
        DecimalFormat df = new DecimalFormat("#.##########");
        return Float.valueOf(df.format(value));
    }
    
    
}
