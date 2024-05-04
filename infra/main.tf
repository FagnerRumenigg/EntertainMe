provider "aws" {
  region = "us-east-1"
}

resource "aws_security_group" "securityGroup" {
  name        = "securityGroup"
  description = "Permitir acesso HTPP e acesso a internet"

  ingress{
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress{
    from_port = 0
    to_port = 65535
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "servidor" {
  ami           = "ami-04e5276ebb8451442"
  instance_type = "t2.nano"
  user_data = file("user_data.sh")
  vpc_security_group_ids = [aws_security_group.securityGroup.id]
}