#!/bin/bash

export MONGODB_PASS=ct9drvuqreb10oc6gc3risge5q

if [ $1 = b ]; then
    mvn clean install
fi

heroku local web
