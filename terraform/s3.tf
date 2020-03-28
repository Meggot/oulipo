data "aws_caller_identity" "current" {}

resource "aws_s3_bucket" "oulipo_logs" {
  bucket = "oulipo-logs"
  acl    = "private"

  versioning {
    enabled = true
  }

  policy = <<-EOF
    {
      "Id": "Policy",
      "Version": "2012-10-17",
      "Statement": [
        {
          "Action": [
            "*"
          ],
          "Effect": "Allow",
          "Resource": "arn:aws:s3:::oulipo-logs/*",
          "Principal": {
            "AWS": [
              "${data.aws_caller_identity.current.account_id}"
            ]
          }
        }
      ]
    }
    EOF
}

# # UPLOAD TARGET
# resource "aws_s3_bucket_object" "auth_lambda_code" {
#   bucket = aws_s3_bucket.oulipo_bucket.bucket
#   key = "oul/releasy_auth_lambda_${var.app_version}.zip"
#   source = "${var.target_dir}/releasy_auth_lambda_${var.app_version}.zip"
# }
