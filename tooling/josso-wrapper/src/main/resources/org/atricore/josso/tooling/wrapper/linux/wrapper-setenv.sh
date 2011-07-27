#!/bin/bash
# -----------------------------------------------------------------------------
# Set java exe and conf file for all scripts
#
# -----------------------------------------------------------------------------

# resolve links - $0 may be a softlink

current=${karaf.base}/bin
cd `dirname "$0"`
PRGDIR=${karaf.base}/bin
cd "$current"

yajsw_home=${karaf.base}/lib/wrapper
export yajsw_home

yajsw_jar="$yajsw_home"/wrapper.jar
export yajsw_jar

yajsw_java_options=-Xmx15m
export yajsw_java_options

java_exe=java
export java_exe

# show java version
"$java_exe" -version

conf_file="$yajsw_home"/../../etc/${name}-wrapper.conf
export conf_file

conf_default_file="$yajsw_home"/../../etc/${name}-wrapper.conf.default
export conf_default_file

KARAF_BASE=${karaf.base}
KARAF_DATA=${karaf.data}
KARAF_HOME=${karaf.home}
JAVA_HOME=${java.home}

export KARAF_BASE KARAF_DATA KARAF_HOME JAVA_HOME



