#!/bin/sh
echo "Starting maven build"
mvn clean install
cd target
echo "Extracting release archive"
unzip TuxBounce-0.6-release.zip
cd TuxBounce-0.6
echo "Run application"
./run.sh
