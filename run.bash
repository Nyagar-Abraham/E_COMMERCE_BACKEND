#!/bin/bash

# =========================================
# SET VARIABLES
# =========================================
MODULES=$( ls -d */ | sed 's:/$::' | grep -v docs)
LOG_FILE=/var/log/e_commerce_backend.log


# =========================================
# start all services
# =========================================
function start_services_func(){
    echo "postgres_db up...."
    for module in $MODULES
      do
  #     Change directory to the module dir
        echo "Changing directory to $module"
        cd "$module" || exit 1
  #      install mvn packages
        echo "installing dependencies"
        mvn clean install > $LOG_FILE

        if [ "$module" == "protos" ]
          then
            echo "Running $module"
            mvn exec:java > "$LOG_FILE" 2>&1 &
        else
            echo "Running $module"
            mvn spring-boot:run > "$LOG_FILE" 2>&1 &
        fi

        # Go back up before next loop iteration
        cd ..
      done
}


# =========================================
# GET
# =========================================
echo "Checking db status..."
docker ps | grep 'postgres-db'
#=============================================
# Check if db is up else start it
#=============================================
if [ $?  -eq 0 ]
then
 start_services_func
else
#  Change directory to working directory
  echo "Changing directory back to working directory"
#  start db service
  echo "Starting postgres_db database service"
  docker compose up -d postgres-db
  start_services_func
fi



