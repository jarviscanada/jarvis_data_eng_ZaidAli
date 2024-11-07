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

vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

memory_free=$(echo "$vmstat_mb" | awk '{print $4}' | tail -n1 | xargs)
cpu_idle=$(echo "$vmstat_mb" | awk '{print $15}' | tail -n1 | xargs)
cpu_kernel=$(echo "$vmstat_mb" | awk '{print $14}' | tail -n1 | xargs)
disk_io=$(vmstat -d | tail -n1 | awk '{print $10}' | xargs)
disk_available=$(df -BM / | tail -n1 | awk '{print $4}' | sed 's/M//')
timestamp=$(date '+%Y-%m-%d %H:%M:%S')

export PGPASSWORD=$psql_password

host_id="(SELECT id FROM host_info WHERE hostname='$hostname')"

# PSQL command: Inserts server usage data into host_usage table
insert_stmt="INSERT INTO host_usage (timestamp, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available, host_id)
             VALUES ('$timestamp', $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available, $host_id);"

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