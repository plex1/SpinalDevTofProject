#!/bin/bash

source "./spinaldev.conf"
cd $SPINALDEV_DIR_HDL
pwd
sbt test
