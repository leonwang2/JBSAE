package jbsae.struct.prim;

import jbsae.*;
import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

//TODO: This is broken
public class FloatMap<V>{
    public V zero;
    public float[] keys;
    public V[] values;
    public int size = 0;


    public FloatMap(){
        this(16);
    }

    public FloatMap(int size){
        keys = new float[16];
        values = (V[])new Object[16];
    }


    public float[] keys(){
        int i = 0;
        float[] values = new float[size];
        for(int j = 0;j < keys.length;j++) if(keys[j] != 0) values[i++] = keys[j];
        if(zero != null) values[size - 1] = 0;
        return values;
    }

    public Object[] values(){
        float[] keys = keys();
        Object[] values = create(size);
        for(int i = 0;i < keys.length;i++) values[i] = get(keys[i]);
        return values;
    }


    public FloatMap<V> add(float key, V value){
        if(value == null) return this;
        if(zero(key)) return setZero(value);
        int steps = (trailZeros(keys.length) << 1) + 1;
        for(int step = 0;step < steps;step++){
            int[] checks =  hash3(intBits(key), keys.length, Tmp.i3);
            for(int i = 0;i < checks.length;i++) if(eqlf(keys[checks[i]], key)) return set(checks[i], key, value);
            for(int i = 0;i < checks.length;i++) if(keys[checks[i]] == 0) return set(checks[i], key, value);
            int index = checks[randInt(0, checks.length - 1)];
            float displacedKey = keys[index];
            V displacedValue = values[index];
            keys[index] = key;
            values[index] = value;
            key = displacedKey;
            value = displacedValue;
        }
        return resize(keys.length << 1).add(key, value);
    }

    private FloatMap<V> set(int i, float key, V value){
        if(keys[i] == 0) size++;
        keys[i] = key;
        values[i] = value;
        return this;
    }

    private FloatMap<V> setZero(V value){
        if(zero == null) size++;
        zero = value;
        return this;
    }

    public FloatMap<V> remove(float key){
        if(zero(key)){
            if(zero != null){
                zero = null;
                size--;
            }
            return this;
        }
        float[] keys = this.keys;
        V[] values = this.values;
        int[] checks = hash3(intBits(key), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(eqlf(keys[checks[i]], key)){
                keys[checks[i]] = 0;
                values[checks[i]] = null;
                size--;
                return this;
            }
        }
        return this;
    }

    public FloatMap<V> removeAll(float... keys){
        for(int i = 0;i < keys.length;i++) remove(keys[i]);
        return this;
    }


    public V get(float key){
        if(zero(key)) return zero;
        int[] checks = hash3(intBits(key), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(keys[checks[i]], key)) return values[checks[i]];
        return null;
    }


    public boolean contains(float key){
        if(zero(key)) return zero != null;
        int[] checks = hash3(intBits(key), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(keys[checks[i]], key)) return true;
        return false;
    }

    public FloatMap<V> eachKey(Floatc cons){
        if(zero != null) cons.get(0);
        for(int j = 0;j < keys.length;j++) if(keys[j] != 0) cons.get(keys[j]);
        return this;
    }

    public FloatMap<V> clear(){
        fill(keys, 0);
        fill(values, null);
        size = 0;
        return this;
    }

    public FloatMap<V> resize(int newSize){
        float[] keys = keys();
        V[] values = (V[])values();
        size = 0;
        zero = null;
        this.keys = new float[newSize];
        this.values = create(newSize, values);
        for(int j = 0;j < keys.length;j++) add(keys[j], values[j]);
        return this;
    }
}
