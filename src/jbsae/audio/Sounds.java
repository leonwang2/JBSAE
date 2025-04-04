package jbsae.audio;

import org.lwjgl.openal.*;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.AL11.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Sounds{
    public long device, context;

    public Listener listener;

//    public Mat4 cameraMatrix;

    public Sounds(){
    }

    public void init(){
        this.device = alcOpenDevice(alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER));
        this.context = alcCreateContext(device, new int[]{0});

        alcMakeContextCurrent(context);
        AL.createCapabilities(ALC.createCapabilities(device));
        alDistanceModel(AL_EXPONENT_DISTANCE);

        listener = new Listener();
    }

    public void dispose(){
        if(context != NULL) alcDestroyContext(context);
        if(device != NULL) alcCloseDevice(device);
    }
}

