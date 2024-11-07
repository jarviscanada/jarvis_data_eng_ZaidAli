#!/bin/bash

# Setup and validate arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Check the number of arguments
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi
lscpu_out=`lscpu`
hostname=$(hostname -f)
cpu_architecture=$(echo "$lscpu_out" | grep "Architecture" | awk '{print $2}')
cpu_model=$(echo "$lscpu_out" | grep "Model name" | awk -F ': ' '{print $2}' | xargs)
cpu_mhz=$(echo "$lscpu_out" | grep "Model name" | sed -E 's/.*@ ([0-9.]+)GHz/\1/' | awk '{print $1 * 1000}')
cpu_number=$(echo "$lscpu_out" | grep "^CPU(s):" | awk '{print $2}' | xargs)
l2_cache=$(echo "$lscpu_out" | grep "L2 cache" | awk '{print $3}' | sed 's/K//')
total_mem=$(vmstat --unit M | awk 'NR==3{print $4}')
timestamp=$(date '+%Y-%m-%d %H:%M:%S')

export PGPASSWORD=$psql_password

insert_stmt="INSERT INTO host_info (hostname, cpu_architecture, cpu_model, cpu_mhz, cpu_number, l2_cache, total_mem, timestamp) VALUES ('$hostname', '$cpu_architecture', '$cpu_model', $cpu_mhz, $cpu_number, $l2_cache, $total_mem, '$timestamp');"

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

exit_status=$?

# Print success message if command is successful
if [ $exit_status -eq 0 ]; then
    echo "Success"
else
    echo "Failed to insert data"
fi

# Exit with the same status as the psql command
exit $exit_status