#!/bin/bash
# * Alias
alias awslocal='aws --endpoint-url=http://localhost:4566'

# * Variables
POSTGRESQL_SECRET_FILE_PATH=/mnt/d/localstack/secrets/cinema-postgres.json

printf "
# *
# * Autor: Luis Guillermo Gómez Galeano
# *
# * Crea los recursos en LocalStack para el proyecto de Cinema
# *
"

printf "\n\nSecretos:\n"

# Crear secreto para la base de datos de alertas
awslocal secretsmanager create-secret --name cinema-local-secretrds-CNX --description "Cinema database secret" --secret-string file://$POSTGRESQL_SECRET_FILE_PATH
