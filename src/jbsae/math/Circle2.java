package jbsae.math;

import jbsae.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;

public class Circle2{
    public float x, y, r;


    public Circle2(){
    }

    public Circle2(float x, float y, float r){
        set(x, y, r);
    }


    public Circle2 set(float x, float y, float r){
        this.x = x;
        this.y = y;
        this.r = r;
        return this;
    }

    public Circle2 nor(){
        return set(x, y, abs(r));
    }


    public boolean contains(Pos2 value){
        return dst(x, y, value.x(), value.y()) <= r;
    }

    public boolean overlaps(Circle2 o){
        return dst(x, y, o.x, o.y) <= r + o.r;
    }

    public Vec2 constrain(Vec2 pos){
        if(contains(pos)) return pos;
        return pos.setr(pos.sub(x, y).ang(), r);
    }


    public Circle2 cpy(){
        return new Circle2(x, y, r);
    }


    @Override
    public String toString(){
        return valToString(x, y, r);
    }
}
