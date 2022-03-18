#!/bin/bash

if [ -z "$1" ] || [ -z "$2" ]
   then
    die "Usage: $0 current-number new-number [build-number]"
fi


for i in $(find . -name buildNumber.properties); do sed -i "s/\=$1/\=$2/g" $i; done
