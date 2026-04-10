resource "aws_s3_bucket" "employee_photos" {
  bucket = "${var.project}-${var.environment}-employee-photos"
}

resource "aws_s3_bucket_versioning" "employee_photos" {
  bucket = aws_s3_bucket.employee_photos.id
  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_s3_bucket_server_side_encryption_configuration" "employee_photos" {
  bucket = aws_s3_bucket.employee_photos.id
  rule {
    apply_server_side_encryption_by_default {
      sse_algorithm = "AES256"
    }
  }
}

resource "aws_s3_bucket_public_access_block" "employee_photos" {
  bucket                  = aws_s3_bucket.employee_photos.id
  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}

resource "aws_s3_bucket_lifecycle_configuration" "employee_photos" {
  bucket = aws_s3_bucket.employee_photos.id

  rule {
    id     = "transition-old-versions"
    status = "Enabled"

    noncurrent_version_transition {
      noncurrent_days = 30
      storage_class   = "STANDARD_IA"
    }

    noncurrent_version_expiration {
      noncurrent_days = 90
    }
  }
}

# IAM policy for backend access to the bucket
resource "aws_iam_policy" "employee_photos_access" {
  name        = "${var.project}-${var.environment}-employee-photos-access"
  description = "Allows backend to read/write employee photos in S3"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "s3:PutObject",
          "s3:GetObject",
          "s3:DeleteObject",
          "s3:ListBucket",
        ]
        Resource = [
          aws_s3_bucket.employee_photos.arn,
          "${aws_s3_bucket.employee_photos.arn}/*",
        ]
      }
    ]
  })
}
