variable "app_name" {
  description = "App Name"
  default     = "oulipo"
}

variable "app_version" {
  description = "App version"
  default     = "1"
}

variable "vpc_name" {
  description = "VPC Name"
  default     = "Oulipo"
}

variable "aws_region" {
  description = "AWS Region"
  default     = "eu-west-1"
}

variable "ecr_repository_name" {
  description = "ECR Repository to store service images"
  default = "oulipo-backend-STAGING"
}

variable "aws_availability_zones" {
  description = "AWS availability zones"
  type        = list
  default     = ["eu-west-1a", "eu-west-1b"]
}

variable "aws_vpc_cidr" {
  description = "CIDR for this vpc"
  default     = "172.16.6.0/24"
}

variable "aws_private_subnets" {
  description = "Private subnets for region"
  type        = list
  default     = ["172.16.6.0/26", "172.16.6.64/26"]
}

variable "aws_public_subnets" {
  description = "Public subnets for region"
  type        = list
  default     = ["172.16.6.128/26", "172.16.6.192/26"]
}