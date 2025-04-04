package jbsae.audio;

import jbsae.struct.*;
import org.lwjgl.stb.*;
import org.lwjgl.system.*;

import javax.sound.sampled.*;
import java.nio.*;

import static org.lwjgl.openal.AL10.*;

public class Sound{
    public static Seq<Sound> all = new Seq<>();

    public int id;

    public ByteBuffer data;

    public Sound(ByteBuffer data, int openALFormat, AudioFormat format){
        all.add(this);

        id = alGenBuffers();
        this.data = data;
        alBufferData(id, openALFormat, data, (int)format.getSampleRate());
    }

    public void dispose(){
        all.remove(this);
        alDeleteBuffers(id);
    }
}