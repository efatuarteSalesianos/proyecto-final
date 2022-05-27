package com.salesianostriana.dam.finalapi.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter @Setter
public class AwsConfig {

    @Value("${aws.access.key.id}")
    private String accessKeyId;

    @Value("${aws.secret.access.key}")
    private String accessSecretKey;

    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public AmazonS3 getS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, accessSecretKey);

        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region)).
                withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .enableForceGlobalBucketAccess().build();
    }
}