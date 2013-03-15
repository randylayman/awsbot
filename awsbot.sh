#!/bin/bash

AWS_BOT_FINDER_HOME=/usr/local/awsBot
JAVA_HOME=/usr/local/java

$JAVA_HOME/bin/java -classpath $AWS_BOT_FINDER/lib* com.vocalocity.awsbot.AWSBot $@
