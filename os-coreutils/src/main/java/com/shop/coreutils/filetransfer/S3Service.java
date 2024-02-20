package com.shop.coreutils.filetransfer;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

//@RequiredArgsConstructor
@Getter
@Slf4j
@Component
public class S3Service implements FileTransferService{

    AmazonS3 amazonS3;
    AWSClientConfig awsClientConfig;


    @Autowired
    public S3Service(AmazonS3 amazonS3,
                     AWSClientConfig awsClientConfig){
        this.amazonS3 = amazonS3;
        this.awsClientConfig = awsClientConfig;
    }

    @Override
    public void upload(File file, String folderPath) {
        String key = folderPath + file.getName();
        amazonS3.putObject(
                new PutObjectRequest(
                        awsClientConfig.getBucketName(),
                        key,
                        file)
        );

    }

    @Override
    public byte[] download(String path) throws IOException {
       S3Object s3Object = amazonS3.getObject(
               new GetObjectRequest(
                       awsClientConfig.getBucketName(),
                       path)
       );
       return s3Object.getObjectContent().readAllBytes();
    }

    @Override
    public void deleteFile(String filePath){

        DeleteObjectRequest deleteObjectRequest =
                new DeleteObjectRequest(awsClientConfig.getBucketName(),filePath);
        amazonS3.deleteObject(awsClientConfig.getBucketName(),filePath);
    }

    @Override
    public TransferType getType() {
        return TransferType.S3;
    }

    public void deleteAllFilesInBucket(){
        ListObjectsV2Result objectsV2Result =
                getBucketFilesList(awsClientConfig.getBucketName());
        DeleteObjectsRequest deleteObjectsRequest =
                new DeleteObjectsRequest(awsClientConfig.bucketName)
                        .withKeys(objectsV2Result
                                .getObjectSummaries()
                                .stream()
                                .map(obj-> new DeleteObjectsRequest.KeyVersion(obj.getKey()))
                                .toList()
                        );
        amazonS3.deleteObjects(deleteObjectsRequest);
    }

    public boolean createBucket(String bucketName){
        if(amazonS3.doesBucketExistV2(bucketName)) {
            log.warn("Bucket {} already exists. Please set an unique name for the bucket.",
                    bucketName);
            return false;
        }
        CreateBucketRequest createBucketRequest =
                new CreateBucketRequest(bucketName, awsClientConfig.awsRegionName);

       amazonS3.createBucket(createBucketRequest);

       return true;
    }

    public ListObjectsV2Result getBucketFilesList(String bucketName){
        return amazonS3.listObjectsV2(bucketName);
    }
}
