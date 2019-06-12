#!/bin/bash

source "./spinaldev.conf"
cd $SPINALDEV_DIR_HDL
pwd
sbt -Dsbt.scala.version=2.10.7 testOnly
