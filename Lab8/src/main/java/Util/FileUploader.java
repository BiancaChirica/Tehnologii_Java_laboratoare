package Util;

import org.primefaces.model.file.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUploader {

    public static String basePath = "C:\\Users\\bianc\\Desktop\\Tehnologii_Java_laboratoare\\lab8\\src\\main\\java\\uploadedFiles\\";

    public static void writeFile(UploadedFile file, String fileName) {
        FileOutputStream stream;
        String path = basePath + fileName;

        try {
            stream = new FileOutputStream(path);
            stream.write(file.getContent());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
