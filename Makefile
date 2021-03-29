SHELL=/bin/bash

HOME := $(shell pwd)
LIB_HOME := $(HOME)/build

build/aggregator-service:
	mvn clean install -pl :aggregator-service -am

run/aggregator-service:
	pushd ${LIB_HOME} > /dev/null && \
	java -jar lib/aggregator-service-1.0.jar

run/app: build/aggregator-service run/aggregator-service
