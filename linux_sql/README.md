# Linux SQL Monitoring Agent
# Introduction
This project is a server monitoring tool designed to collect and store hardware specifications and usage data for Linux-based systems. It provides insights into system performance over time by storing data in a PostgreSQL database.
# Tech Stack Used
### Languages and Tools
- **Bash**: For scripting and automating system tasks.
- **PostgreSQL**: Relational database used to store hardware and usage data.
- **Docker**: To manage PostgreSQL instance in a containerized environment.
- **Crontab**: For scheduling periodic execution of the `host_usage.sh` script.
- **Git**: Version control system for tracking changes and collaborating.

### Technologies and Utilities
- **Linux Commands**: To gather system information (e.g., `vmstat`, `lscpu`, `df`).
- **psql**: PostgreSQL client for executing SQL queries and managing the database.
- **sed, awk, grep**: Text-processing tools used for extracting and formatting system data.
- **draw.io**: For creating architecture diagrams.
# Quick Start
Use markdown code block for your quick-start commands
- Start a psql instance using psql_docker.sh
- Create tables using ddl.sql
- Insert hardware specs data into the DB using host_info.sh
- Insert hardware usage data into the DB using host_usage.sh
- Crontab setup

## Scripts
Shell scripts
- psql_docker.sh  (Manages the lifecycle of a PostgreSQL Docker container.)
- host_info.sh  (Collects and stores static hardware specifications (CPU, memory, etc.).)
- host_usage.sh  (Collects and stores dynamic hardware usage data (CPU, memory, disk usage)

## Database Modeling

### `host_info` Table

| Column Name      | Data Type | Description                             |
|------------------|-----------|-----------------------------------------|
| id               | SERIAL    | Unique identifier for each host         |
| hostname         | VARCHAR   | Hostname of the server                  |
| cpu_number       | INT       | Number of CPUs                          |
| cpu_architecture | VARCHAR   | CPU architecture                        |
| cpu_model        | VARCHAR   | CPU model                               |
| cpu_mhz          | FLOAT     | CPU clock speed in MHz                  |
| l2_cache         | INT       | L2 cache size in KB                     |
| total_mem        | INT       | Total memory in MB                      |
| timestamp        | TIMESTAMP | Time of data collection                 |

### `host_usage` Table

| Column Name      | Data Type | Description                             |
|------------------|-----------|-----------------------------------------|
| id               | SERIAL    | Unique identifier for each record       |
| timestamp        | TIMESTAMP | Time of data collection                 |
| memory_free      | INT       | Free memory in MB                       |
| cpu_idle         | FLOAT     | CPU idle percentage                     |
| cpu_kernel       | FLOAT     | CPU kernel usage percentage             |
| disk_io          | INT       | Number of disk I/O operations           |
| disk_available   | INT       | Available disk space in MB              |
| host_id          | INT       | Foreign key referencing `host_info.id`  |
# Test
The bash scripts and SQL queries were tested with real server data. The `host_info.sh` script correctly inserted hardware specifications, and `host_usage.sh` successfully logged dynamic usage data every minute. Both scripts were validated using PostgreSQL queries.

# Deployment
The solution was deployed using a combination of Docker for the PostgreSQL database, crontab for periodic data logging, and GitHub for version control. The crontab job ensures that host_usage.sh runs every minute, providing continuous monitoring.