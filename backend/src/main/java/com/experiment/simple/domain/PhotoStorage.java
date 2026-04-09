package com.experiment.simple.domain;

import java.io.IOException;
import java.io.InputStream;

public interface PhotoStorage {
  String upload(Long employeeId, String contentType, InputStream data, long size) throws IOException;
}
