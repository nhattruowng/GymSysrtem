package com.server.gymServerApplication.service;

import com.server.gymServerApplication.entity.mysql.User;
import com.server.gymServerApplication.entity.postgresql.FaceID;
import com.server.gymServerApplication.iservice.IFacereco;
import com.server.gymServerApplication.repository.mysql.IUserrepository;
import com.server.gymServerApplication.repository.postgresql.IFaceIdRepository;
import com.server.gymServerApplication.utils.OtherFunctions;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaceReco implements IFacereco {

    private LBPHFaceRecognizer lbphFaceRecognizer;
    private CascadeClassifier cascadeClassifier;
    private final IUserrepository iUserrepository;
    private final IFaceIdRepository iFaceIdRepository;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Autowired
    public FaceReco(IUserrepository iUserrepository, IFaceIdRepository faceID) {
        this.iUserrepository = iUserrepository;
        this.iFaceIdRepository = faceID;
        lbphFaceRecognizer = LBPHFaceRecognizer.create();
        cascadeClassifier = new CascadeClassifier("haarcascade_frontalface_default.xml");
    }

    @Override
    public boolean compareFaces(String idUser, String base64Image) {
        if (base64Image == null) {
            throw new RuntimeException("KHONG CO GIA TRI NHAN DANG!");
        }
        Mat face = OtherFunctions.convertBase64ToMat(base64Image);

        User user = iUserrepository.findById(idUser)
                .filter(u -> !u.isDelete())
                .orElseThrow(() -> new UsernameNotFoundException("NGUOI DUNG CHUA DANG KY HOAC DA BI KHOA!"));

        FaceID faceobj = iFaceIdRepository.findByUser(idUser)
                .orElseThrow(() -> new UsernameNotFoundException("CHUA DANG KY KHUON MAT!"));

        Mat result = Imgcodecs.imdecode(new MatOfByte(faceobj.getFace()), Imgcodecs.IMREAD_GRAYSCALE);

        lbphFaceRecognizer.train(List.of(result), new MatOfInt(1));// Train với ảnh trong DB

        double[] confidence = new double[1];
        lbphFaceRecognizer.predict(face,
                new int[1],
                confidence);

        return confidence[0] < 50;
    }
}
