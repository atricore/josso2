# JOSSO EE VM/Containers distribution

## Introduction

* Packaging VM/Containers is done use packer.  This allows us to reuse instructions for docker, AWS, etc.

* Make sure to update verify that the  **josso-vars.json** file has the correct values.

## Containers and VMs

You can create a *Docker* container or a *VM* (Amazon, VirtualBox, etc)

### Docker

A simple container based on openjdk:8, just starts JOSSO (no apache proxy, etc)

Use **josso-cn-packer.json** descriptor to create a base docker image for anyJOSSO.

packer build -only=docker -var-file=./josso-vars.json ./josso-cn-packer.json

Use **josso-ce-packer.json** descriptor to actually create the specific image

packer build -only=docker -var-file=./josso-vars.json ./josso-ce-packer.json

