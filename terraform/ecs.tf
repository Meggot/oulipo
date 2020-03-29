locals {
  cpu    = 256
  memory = 512
}

resource "aws_ecs_cluster" "oulipo_ecs_cluster" {
  name = "oulipo_ecs_cluster"
}

resource "aws_cloudwatch_log_group" "oulipo_log_group" {
  name              = "oulipo_log_group"
  retention_in_days = 3
}

resource "aws_iam_role" "ecsTaskExecutionRole" {
  name               = "oulipo_task_execution_role"
  assume_role_policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
      {
        "Sid": "",
        "Effect": "Allow",
        "Principal": {
          "Service": "ecs-tasks.amazonaws.com"
        },
        "Action": "sts:AssumeRole"
      }
    ]
  }
EOF
}

resource "aws_iam_role_policy_attachment" "task_execution_policy_attachment" {
  role       = aws_iam_role.ecsTaskExecutionRole.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_security_group" "oulipo_ecs_service_sg" {
  name        = "oulipo-ecs-service-sg"
  description = "Oulipo ECS Service SG"
  vpc_id      = module.vpc.vpc_id

  ingress {
    security_groups = [aws_security_group.oulipo_alb_sg.id]
    # TLS (change to whatever ports you need)
    from_port = 0
    to_port   = 13000
    protocol  = "tcp"
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_ecs_task_definition" "gateway_task_def" {
  family                   = "gateway_task_def"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = local.cpu
  memory                   = local.memory
  execution_role_arn       = aws_iam_role.ecsTaskExecutionRole.arn
  container_definitions    = <<EOF
 [
   {
     "name": "gateway",
     "image": "${data.aws_caller_identity.current.account_id}.dkr.ecr.eu-west-1.amazonaws.com/meggot/oulipo/gateway:LATEST",
     "cpu": ${local.cpu},
     "memory": ${local.memory},
     "environment": [
       {
         "name": "SPRING_PROFILES_ACTIVE",
         "value": "staging"
       }
     ],
     "portMappings": [
       {
         "containerPort": 13000,
         "hostPort": 13000
       }
     ],
     "logConfiguration": {
       "logDriver": "awslogs",
       "options": {
         "awslogs-region": "eu-west-1",
         "awslogs-group": "${aws_cloudwatch_log_group.oulipo_log_group.name}",
         "awslogs-stream-prefix": "ecs"
       }
     }
   }
 ]
 EOF
}

resource "aws_ecs_service" "gateway_ecs_service" {
  name                              = "gateway"
  launch_type                       = "FARGATE"
  cluster                           = aws_ecs_cluster.oulipo_ecs_cluster.id
  task_definition                   = aws_ecs_task_definition.gateway_task_def.arn
  desired_count                     = 1
  health_check_grace_period_seconds = 30

  network_configuration {
    subnets         = module.vpc.private_subnets
    security_groups = [module.vpc.default_security_group_id, aws_security_group.oulipo_ecs_service_sg.id]
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.oulipo_alb_tg.arn
    container_name   = "gateway"
    container_port   = 13000
  }
}