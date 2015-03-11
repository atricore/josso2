#!/bin/sh

PROGNAME=`basename $0`

JOSSO2_VERSION=2.4.2-SNAPSHOT
JOSSO2_REPO=http://repository.josso.org/m2-release-repository,http://repository.josso.org/m2-snapshot-repository

warn() {
    echo "${PROGNAME}: $*"
}

die () {
    warn "$*"
    exit 1
}


printUsage () {
    die "Usage <group-id> <ui-id>"
}

init() {

   if [ "x$GROUP_ID" = "x" ]; then
        GROUP_ID=$1
   fi

   if [ "x$UI_ID" = "x" ]; then
        UI_ID=$2
   fi

   if [ "x$GROUP_ID" = "x" ]; then
        printUsage
   fi

   if [ "x$UI_ID" = "x" ]; then
        printUsage
   fi


   if [ "x$VARIANT" = "x" ]; then
        VARIANT=$UI_ID
   fi

   if [ "x$PACKAGE" = "x" ]; then
        PACKAGE=$GROUP_ID
   fi

   if [ "x$ARTIFACT_ID" = "x" ]; then
        ARTIFACT_ID=$UI_ID
   fi

   VARIANT_SUFFIX=_$VARIANT

}

run() {
    mvn -B  archetype:generate -Dmaven.repo.remote=${JOSSO2_REPO} -DarchetypeGroupId=org.atricore.josso.archetypes -DarchetypeArtifactId=josso2-ui-archetype -DarchetypeVersion=${JOSSO2_VERSION} -DgroupId=${GROUP_ID} -DartifactId=${ARTIFACT_ID} -Dpackage=${PACAKGE} -DuiId=${UI_ID} -Dvariatn=${VARIANT} -DvariantSuffix=${VARIANT_SUFFIX}
}

main() {
    init "$@"
    run "$@"
}

main "$@"
