terraform {
  backend "s3" {
    bucket = "oulipo-state"
    region = "eu-west-1"
    key    = "oulipo/oulipo.tfstate"
  }
}