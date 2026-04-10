# S3
output "employee_photos_bucket_name" {
  description = "Name of the S3 bucket for employee photos"
  value       = aws_s3_bucket.employee_photos.id
}

output "employee_photos_bucket_arn" {
  description = "ARN of the S3 bucket for employee photos"
  value       = aws_s3_bucket.employee_photos.arn
}

output "employee_photos_access_policy_arn" {
  description = "ARN of the IAM policy for backend S3 access — attached via IRSA"
  value       = aws_iam_policy.employee_photos_access.arn
}

# ECR
output "backend_ecr_url" {
  description = "ECR repository URL for the backend image"
  value       = aws_ecr_repository.backend.repository_url
}

output "frontend_ecr_url" {
  description = "ECR repository URL for the frontend image"
  value       = aws_ecr_repository.frontend.repository_url
}

# EKS
output "eks_cluster_name" {
  description = "EKS cluster name — used in CI/CD to update kubeconfig"
  value       = module.eks.cluster_name
}

output "eks_cluster_endpoint" {
  description = "EKS API server endpoint"
  value       = module.eks.cluster_endpoint
}

# IAM
output "backend_irsa_role_arn" {
  description = "IAM role ARN for the backend service account — set as annotation on the Helm service account"
  value       = module.backend_irsa.iam_role_arn
}
