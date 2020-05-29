#!/bin/bash
CIERA_VERSION=2.1.0
CLASSPATH=$HOME/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar:$HOME/.m2/repository/org/json/json/20180813/json-20180813.jar:$HOME/.m2/repository/org/springframework/boot/spring-boot-starter-thymeleaf:$HOME/.m2/repository/io/ciera/EchoSystem/1.0.0-SNAPSHOT/EchoSystem-1.0.0-SNAPSHOT.jar
java -cp $CLASSPATH -jar $HOME/.m2/repository/io/ciera/EchoSystem/1.0.0-SNAPSHOT/EchoSystem-1.0.0-SNAPSHOT.jar