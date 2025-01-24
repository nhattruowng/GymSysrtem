package com.server.gymServerApplication.utils;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class Transformation {

    public static byte[] matToByteArray(Mat mat){
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", mat,matOfByte);
        return matOfByte.toArray();
    }



}
