output "employee_photos_bucket_name" {
  description = "Name of the S3 bucket for employee photos"
  value       = aws_s3_bucket.employee_photos.id
}

output "employee_photos_bucket_arn" {
  description = "ARN of the S3 bucket for employee photos"
  value       = aws_s3_bucket.employee_photos.arn
}

output "employee_photos_access_policy_arn" {
  description = "ARN of the IAM policy for backend S3 access — attach to your backend's role"
  value       = aws_iam_policy.employee_photos_access.arn
}
