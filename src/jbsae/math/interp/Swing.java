package jbsae.math.interp;


public class Swing implements Interp{
    public float scale;

    public Swing(float scale){
        this.scale = scale * 2;
    }

    @Override
    public float get(float f){
        if(f <= 0.5f){
            f *= 2;
            return f * f * ((scale + 1) * f - scale) / 2;
        }
        f--;
        f *= 2;
        return f * f * ((scale + 1) * f + scale) / 2 + 1;
    }
}