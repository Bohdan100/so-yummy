#!/bin/bash

sleep 5

for file in /docker-entrypoint-initdb.d/*.json; do

    filename=$(basename "$file" .json)

    collection=${filename#*.}

    echo "Importing $collection from $file..."

    mongoimport --host localhost \
                --db so-yummy \
                --collection "$collection" \
                --type json \
                --file "$file" \
                --jsonArray \
                --authenticationDatabase admin \
                -u "$MONGO_INITDB_ROOT_USERNAME" \
                -p "$MONGO_INITDB_ROOT_PASSWORD"
done