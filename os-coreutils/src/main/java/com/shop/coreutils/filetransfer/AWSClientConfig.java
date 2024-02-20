package com.shop.coreutils.filetransfer;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;

@Configuration
@Getter
public class AWSClientConfig {

    @Value("${aws.accessKey:AKIA4MTWHGBFYXD76JUY}")
    String accessKey;

    @Value("${aws.secretAccessKey:fdx3s83HtDkN6IFhx8QwWqUaD9ZJKc5MgPjfYy1l}")
    String secretAccessKey;

    @Value("${aws.bucketName:online-shop-storage-bucket}")
    String bucketName;

    @Value("${aws.region:eu-north-1}")
    String awsRegionName;


    Region region;
    @Bean
    public AmazonS3 amazonS3(){
        region = Region.of(awsRegionName);
        BasicAWSCredentials basicAWSCredentials =
                new BasicAWSCredentials(accessKey, secretAccessKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(region.toString())
                .build();
    }


}
