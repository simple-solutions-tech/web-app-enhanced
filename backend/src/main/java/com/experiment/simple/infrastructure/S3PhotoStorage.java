package com.experiment.simple.infrastructure;

import com.experiment.simple.domain.PhotoStorage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.UUID;

@Component
public class S3PhotoStorage implements PhotoStorage {

  private final S3Client s3Client;

  @Value("${aws.s3.bucket}")
  private String bucket;

  @Value("${aws.s3.endpoint:#{null}}")
  private String endpoint;

  @Value("${aws.s3.region}")
  private String region;

  public S3PhotoStorage(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  @PostConstruct
  void ensureBucketExists() {
    try {
      s3Client.headBucket(HeadBucketRequest.builder().bucket(bucket).build());
    } catch (NoSuchBucketException e) {
      s3Client.createBucket(CreateBucketRequest.builder().bucket(bucket).build());
    }
  }

  @Override
  public String upload(Long employeeId, String contentType, InputStream data, long size) {
    String key = "employee-photos/" + employeeId + "/" + UUID.randomUUID();

    s3Client.putObject(
        PutObjectRequest.builder().bucket(bucket).key(key).contentType(contentType).build(),
        RequestBody.fromInputStream(data, size));

    return buildUrl(key);
  }

  private String buildUrl(String key) {
    if (endpoint != null && !endpoint.isBlank()) {
      // LocalStack path-style: http://localhost:4566/bucket/key
      return endpoint + "/" + bucket + "/" + key;
    }
    // Real S3 virtual-hosted style
    return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
  }
}
