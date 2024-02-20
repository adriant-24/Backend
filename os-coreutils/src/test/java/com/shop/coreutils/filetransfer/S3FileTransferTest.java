package com.shop.coreutils.filetransfer;

import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.shop.coreutils.filetransfer.FileTransferFactory;
import com.shop.coreutils.filetransfer.FileTransferService;
import com.shop.coreutils.filetransfer.S3Service;
import com.shop.coreutils.filetransfer.TransferType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class S3FileTransferTest {

    @Autowired
    FileTransferFactory fileTransferFactory;

    String s3FilePath = "test/test1.txt";

    @BeforeAll
    static void setTest(){

    }

    @Test
    @Order(1)
    void whenUploadFile_thenNoErrorsRaised(){
        FileTransferService s3Service = fileTransferFactory.getFileTransferService(TransferType.S3);
        File newS3BucketFile = new File("C:\\temp\\s3Uploads\\test.txt");
        Assertions.assertDoesNotThrow(()-> {
            s3Service.upload(newS3BucketFile, "");
        });
    }

    @Test
    @Order(2)
    void whenUploadFileToGivenFolder_thenNoErrorsRaised(){
        FileTransferService s3Service = fileTransferFactory.getFileTransferService(TransferType.S3);
        File newS3BucketFile = new File("C:\\temp\\s3Uploads\\test1.txt");
        Assertions.assertDoesNotThrow(()-> {
            s3Service.upload(newS3BucketFile, "test/");
        });
    }

    @Test
    @Order(3)
    void whenFindS3Files_thenAllFilesAreReturned (){
        S3Service s3Service =
                (S3Service) fileTransferFactory.getFileTransferService(TransferType.S3);
        ListObjectsV2Result objectsV2Result =
                s3Service.getBucketFilesList(s3Service.getAwsClientConfig().getBucketName());
        String objectsList =  objectsV2Result.getObjectSummaries()
                .stream().map(S3ObjectSummary::getKey)
                .collect(Collectors.joining(","));
        log.info("Files in Bucket{}: {}",
                s3Service.getAwsClientConfig().getBucketName(),
                objectsList);
        assertEquals(2, objectsV2Result.getObjectSummaries().size());
    }

    @Test
    @Order(4)
    void whenDownloadFileFromS3_thenFileIsFoundLocally() throws IOException {
        S3Service s3Service =
                (S3Service) fileTransferFactory.getFileTransferService(TransferType.S3);
        String downloadFilePath = "C:\\temp\\s3Downloads\\" + FileUtils.getFileNameFromPath(s3FilePath);
        File downloadedFile = FileUtils.byteArrayToFile(s3Service.download(s3FilePath),downloadFilePath);
        assertTrue(downloadedFile.exists());
        Scanner scanner = new Scanner(downloadedFile);
        String firstLineInFile = null;
        if(scanner.hasNextLine()){
            firstLineInFile = scanner.nextLine();
        }
        scanner.close();
        assertNotNull(firstLineInFile);
        assertEquals(firstLineInFile,"First Test content for s3.");
    }



    @Test
    @Order(15)
    @Disabled
    void whenDeleteFileFromS3_thenFileIsNotFoundInList(){
        S3Service s3Service =
                (S3Service) fileTransferFactory.getFileTransferService(TransferType.S3);
        s3Service.deleteFile(s3FilePath);
        ListObjectsV2Result objectsV2Result =
                s3Service.getBucketFilesList(s3Service.getAwsClientConfig().getBucketName());
       List<String> keysList = objectsV2Result.getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .filter(key->key.equals(s3FilePath)).toList();
        assertTrue(keysList.isEmpty());
    }

    @Test
    @Order(16)
    @Disabled
    void whenDeleteAllFileFromBucket_thenListIsEmpty(){
        S3Service s3Service =
                (S3Service) fileTransferFactory.getFileTransferService(TransferType.S3);
        s3Service.deleteAllFilesInBucket();
        ListObjectsV2Result objectsV2Result =
                s3Service.getBucketFilesList(s3Service.getAwsClientConfig().getBucketName());
        assertTrue(objectsV2Result.getObjectSummaries().isEmpty());
    }

}
