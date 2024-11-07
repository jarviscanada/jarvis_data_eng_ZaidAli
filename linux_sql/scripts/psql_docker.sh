#!/bin/sh

# Capture CLI arguments
cmd=$1
db_username=$2
db_password=$3

#for arg in "$@";do
#  echo "$arg"
#done
# Start docker if it's not already running
sudo systemctl status docker || sudo systemctl start docker

# Check container status (try the following commands on terminal)
docker container inspect jrvs-psql
container_status=$?

# User switch case to handle create|stop|start options
case $cmd in
  create)
    # Check if the container already exists
    if [ $container_status -eq 0 ]; then
      echo 'Container already exists'
      exit 1
    fi

    # Check # of CLI arguments
    if [ $# -ne 3 ]; then
      echo 'Create requires username and password'
      exit 1
    fi

    # Create Docker volume if it doesn't exist
    docker volume create pgdata

    # Run the PostgreSQL container with the specified username and password
    docker run --name jrvs-psql -e POSTGRES_USER=$db_username -e POSTGRES_PASSWORD=$db_password \
      -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine

    # Check if the container was successfully created
    if [ $? -eq 0 ]; then
      echo "Container created and running"
    else
      echo "Failed to create container"
      exit 1
    fi
    exit 0
    ;;

  start|stop)
    # Ensure the container exists before attempting to start/stop
    if [ $container_status -ne 0 ]; then
      echo "Container 'jrvs-psql' does not exist"
      exit 1
    fi

    # Start or stop the container based on the command
    docker container $cmd jrvs-psql

    # Check if the start/stop operation was successful
    if [ $? -eq 0 ]; then
      echo "Container $cmd successfully"
    else
      echo "Failed to $cmd container"
      exit 1
    fi
    exit 0
    ;;

  *)
    echo 'Illegal command'
    echo 'Commands: start|stop|create'
    exit 1
    ;;
esac