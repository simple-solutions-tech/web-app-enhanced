package com.experiment.simple.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import java.net.URI;

@Configuration
public class S3Config {

  @Value("${aws.s3.region}")
  private String region;

  @Value("${aws.s3.endpoint:#{null}}")
  private String endpoint;

  @Bean
  public S3Client s3Client() {
    S3ClientBuilder builder = S3Client.builder().region(Region.of(region));

    if (endpoint != null && !endpoint.isBlank()) {
      // LocalStack / custom endpoint — static dummy credentials + path-style addressing
      builder
          .endpointOverride(URI.create(endpoint))
          .credentialsProvider(
              StaticCredentialsProvider.create(AwsBasicCredentials.create("test", "test")))
          .forcePathStyle(true);
    } else {
      // Production — standard chain: env vars, instance profile, ECS task role, etc.
      builder.credentialsProvider(DefaultCredentialsProvider.create());
    }

    return builder.build();
  }
}
