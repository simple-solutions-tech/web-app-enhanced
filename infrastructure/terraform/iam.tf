# IRSA (IAM Roles for Service Accounts) lets the backend pod assume an IAM role
# without static credentials — the EKS OIDC provider exchanges the pod's
# Kubernetes service account token for temporary AWS credentials.
module "backend_irsa" {
  source  = "terraform-aws-modules/iam/aws//modules/iam-role-for-service-accounts-eks"
  version = "~> 5.0"

  role_name = "${var.project}-${var.environment}-backend"

  role_policy_arns = {
    s3 = aws_iam_policy.employee_photos_access.arn
  }

  oidc_providers = {
    main = {
      provider_arn               = module.eks.oidc_provider_arn
      namespace_service_accounts = ["${var.project}-${var.environment}:backend"]
    }
  }
}
