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

resource "aws_service_discovery_private_dns_namespace" "local_discovery_namespace" {
  name        = "local"
  description = "The local namespace used by all oulipo services. Deployed by Terraform."
  vpc         = module.vpc.vpc_id
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

  service_registries {
    registry_arn = aws_service_discovery_service.gateway_discovery_service.arn
  }
}

resource "aws_service_discovery_service" "gateway_discovery_service" {
  name = "gateway"

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.local_discovery_namespace.id

    dns_records {
      ttl  = 10
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {
    failure_threshold = 1
  }
}


resource "aws_ecs_task_definition" "user_task_def" {
  family                   = "user_task_def"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = local.cpu
  memory                   = local.memory
  execution_role_arn       = aws_iam_role.ecsTaskExecutionRole.arn
  container_definitions    = <<EOF
 [
   {
     "name": "user",
     "image": "${data.aws_caller_identity.current.account_id}.dkr.ecr.eu-west-1.amazonaws.com/meggot/oulipo/user:LATEST",
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
         "containerPort": 13001,
         "hostPort": 13001
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

resource "aws_ecs_service" "user_ecs_service" {
  name            = "user"
  launch_type     = "FARGATE"
  cluster         = aws_ecs_cluster.oulipo_ecs_cluster.id
  task_definition = aws_ecs_task_definition.user_task_def.arn
  desired_count   = 1

  network_configuration {
    subnets         = module.vpc.private_subnets
    security_groups = [module.vpc.default_security_group_id, aws_security_group.oulipo_ecs_service_sg.id]
  }

  service_registries {
    registry_arn = aws_service_discovery_service.user_discovery_service.arn
  }
}

resource "aws_service_discovery_service" "user_discovery_service" {
  name = "user"

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.local_discovery_namespace.id

    dns_records {
      ttl  = 10
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {
    failure_threshold = 1
  }
}

resource "aws_ecs_task_definition" "project_task_def" {
  family                   = "project_task_def"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = local.cpu
  memory                   = local.memory
  execution_role_arn       = aws_iam_role.ecsTaskExecutionRole.arn
  container_definitions    = <<EOF
 [
   {
     "name": "project",
     "image": "${data.aws_caller_identity.current.account_id}.dkr.ecr.eu-west-1.amazonaws.com/meggot/oulipo/project:LATEST",
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
         "containerPort": 13002,
         "hostPort": 13002
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

resource "aws_ecs_service" "project_ecs_service" {
  name            = "project"
  launch_type     = "FARGATE"
  cluster         = aws_ecs_cluster.oulipo_ecs_cluster.id
  task_definition = aws_ecs_task_definition.project_task_def.arn
  desired_count   = 1

  network_configuration {
    subnets         = module.vpc.private_subnets
    security_groups = [module.vpc.default_security_group_id, aws_security_group.oulipo_ecs_service_sg.id]
  }

  service_registries {
    registry_arn = aws_service_discovery_service.project_discovery_service.arn
  }
}

resource "aws_service_discovery_service" "project_discovery_service" {
  name = "project"

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.local_discovery_namespace.id

    dns_records {
      ttl  = 10
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {
    failure_threshold = 1
  }
}

resource "aws_ecs_task_definition" "metadata_task_def" {
  family                   = "metadata_task_def"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = local.cpu
  memory                   = local.memory
  execution_role_arn       = aws_iam_role.ecsTaskExecutionRole.arn
  container_definitions    = <<EOF
 [
   {
     "name": "metadata",
     "image": "${data.aws_caller_identity.current.account_id}.dkr.ecr.eu-west-1.amazonaws.com/meggot/oulipo/metadata:LATEST",
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
         "containerPort": 13003,
         "hostPort": 13003
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

resource "aws_ecs_service" "metadata_ecs_service" {
  name            = "metadata"
  launch_type     = "FARGATE"
  cluster         = aws_ecs_cluster.oulipo_ecs_cluster.id
  task_definition = aws_ecs_task_definition.metadata_task_def.arn
  desired_count   = 1

  network_configuration {
    subnets = module.vpc.private_subnets
    security_groups = [
      module.vpc.default_security_group_id,
    aws_security_group.oulipo_ecs_service_sg.id]
  }

  service_registries {
    registry_arn = aws_service_discovery_service.metadata_discovery_service.arn
  }
}

resource "aws_service_discovery_service" "metadata_discovery_service" {
  name = "metadata"

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.local_discovery_namespace.id

    dns_records {
      ttl  = 10
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {
    failure_threshold = 1
  }
}

resource "aws_ecs_task_definition" "notify_task_def" {
  family                   = "notify_task_def"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = local.cpu
  memory                   = local.memory
  execution_role_arn       = aws_iam_role.ecsTaskExecutionRole.arn
  container_definitions    = <<EOF
 [
   {
     "name": "notify",
     "image": "${data.aws_caller_identity.current.account_id}.dkr.ecr.eu-west-1.amazonaws.com/meggot/oulipo/notify:LATEST",
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
         "containerPort": 13005,
         "hostPort": 13005
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

resource "aws_ecs_service" "notify_ecs_service" {
  name            = "notify"
  launch_type     = "FARGATE"
  cluster         = aws_ecs_cluster.oulipo_ecs_cluster.id
  task_definition = aws_ecs_task_definition.notify_task_def.arn
  desired_count   = 1

  network_configuration {
    subnets = module.vpc.private_subnets
    security_groups = [
      module.vpc.default_security_group_id,
    aws_security_group.oulipo_ecs_service_sg.id]
  }

  service_registries {
    registry_arn = aws_service_discovery_service.notify_discovery_service.arn
  }
}

resource "aws_service_discovery_service" "notify_discovery_service" {
  name = "notify"

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.local_discovery_namespace.id

    dns_records {
      ttl  = 10
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {
    failure_threshold = 1
  }
}

resource "aws_ecs_task_definition" "zookeeper_task_def" {
  family                   = "zookeeper_task_def"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = local.cpu
  memory                   = local.memory
  execution_role_arn       = aws_iam_role.ecsTaskExecutionRole.arn
  container_definitions    = <<EOF
 [
   {
     "name": "zookeeper",
     "image": "${data.aws_caller_identity.current.account_id}.dkr.ecr.eu-west-1.amazonaws.com/wurstmeister/zookeeper:latest",
     "cpu": ${local.cpu},
     "memory": ${local.memory},
     "portMappings": [
       {
         "containerPort": 2181,
         "hostPort": 2181
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

resource "aws_ecs_service" "zookeeper_ecs_service" {
  name            = "zookeeper"
  launch_type     = "FARGATE"
  cluster         = aws_ecs_cluster.oulipo_ecs_cluster.id
  task_definition = aws_ecs_task_definition.zookeeper_task_def.arn
  desired_count   = 1

  network_configuration {
    subnets = module.vpc.private_subnets
    security_groups = [
      module.vpc.default_security_group_id,
    aws_security_group.oulipo_ecs_service_sg.id]
  }

  service_registries {
    registry_arn   = aws_service_discovery_service.zookeeper_discovery_service.arn
    container_name = "zookeeper"
  }
}

resource "aws_service_discovery_service" "zookeeper_discovery_service" {
  name = "zookeeper"

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.local_discovery_namespace.id

    dns_records {
      ttl  = 10
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {
    failure_threshold = 1
  }
}

resource "aws_ecs_task_definition" "kafka_task_def" {
  family                   = "kafka_task_def"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = local.cpu
  memory                   = local.memory
  execution_role_arn       = aws_iam_role.ecsTaskExecutionRole.arn
  container_definitions    = <<EOF
 [
   {
     "name": "kafka",
     "image": "${data.aws_caller_identity.current.account_id}.dkr.ecr.eu-west-1.amazonaws.com/wurstmeister/kafka:latest",
     "cpu": ${local.cpu},
     "memory": ${local.memory},
     "environment": [
       {
         "name": "KAFKA_ADVERTISED_LISTENERS",
         "value": "PLAINTEXT://kafka.local:9092"
       },
       {
         "name": "KAFKA_ADVERTISED_HOST_NAME",
         "value": "kafka.local"
       },
       {
         "name": "KAFKA_ZOOKEEPER_CONNECT",
         "value": "zookeeper.local:2181"
       },
       {
         "name": "KAFKA_BROKER_ID",
         "value": "1"
       }
     ],
     "portMappings": [
       {
         "containerPort": 9092,
         "hostPort": 9092
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

resource "aws_ecs_service" "kafka_ecs_service" {
  name            = "kafka"
  launch_type     = "FARGATE"
  cluster         = aws_ecs_cluster.oulipo_ecs_cluster.id
  task_definition = aws_ecs_task_definition.kafka_task_def.arn
  desired_count   = 1

  network_configuration {
    subnets = module.vpc.private_subnets
    security_groups = [
      module.vpc.default_security_group_id,
    aws_security_group.oulipo_ecs_service_sg.id]
  }

  service_registries {
    registry_arn = aws_service_discovery_service.kafka_discovery_service.arn
  }
}

resource "aws_service_discovery_service" "kafka_discovery_service" {
  name = "kafka"

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.local_discovery_namespace.id

    dns_records {
      ttl  = 10
      type = "A"
    }

    routing_policy = "MULTIVALUE"
  }

  health_check_custom_config {
    failure_threshold = 1
  }
}