package com.shop.coreutils.filetransfer;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class FileUtils {

    static public File byteArrayToFile(byte[] data, String fileName) {
        File file = new File(fileName);
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    static public File MultipartFileToFile(MultipartFile multipartFile){
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
           Files.copy(multipartFile.getInputStream(),
                   file.toPath(),
                   StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
           throw new RuntimeException(e);
        }
        return file;
    }

    static public String getFileNameFromPath(String filePath){

        int index = filePath.lastIndexOf("/");
        if (index == -1)
            return filePath;
        else
            return filePath.substring(index + 1);

    }
}
