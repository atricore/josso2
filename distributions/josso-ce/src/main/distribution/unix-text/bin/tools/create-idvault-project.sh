#!/bin/sh

PROGNAME=`basename $0`

JOSSO2_VERSION=3.1.0-SNAPSHOT
JOSSO2_REPO=http://repository.josso.org/m2-release-repository,http://repository.josso.org/m2-snapshot-repository

warn() {
    echo "${PROGNAME}: $*"
}

die () {
    warn "$*"
    exit 1
}


printUsage () {
    die "Usage <group-id> <artifact-id>"
}

init() {

   if [ "x$GROUP_ID" = "x" ]; then
        GROUP_ID=$1
   fi

   if [ "x$ARTIFACT_ID" = "x" ]; then
        ARTIFACT_ID=$2
   fi

   if [ "x$GROUP_ID" = "x" ]; then
        printUsage
   fi

   if [ "x$ARTIFACT_ID" = "x" ]; then
        printUsage
   fi

   if [ "x$PACKAGE" = "x" ]; then
        PACKAGE=$GROUP_ID
   fi

}

run() {
    mvn -B  archetype:generate -Dmaven.repo.remote=${JOSSO2_REPO} -DarchetypeGroupId=org.atricore.josso.archetypes -DarchetypeArtifactId=josso2-idvault-archetype -DarchetypeVersion=${JOSSO2_VERSION} -DgroupId=${GROUP_ID} -DartifactId=${ARTIFACT_ID} -Dpackage=${PACKAGE}
}

main() {
    init "$@"
    run "$@"
}

main "$@"
