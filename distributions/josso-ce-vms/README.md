# JOSSO CE VM/Containers distribution

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

### Virtual Machines

They are based on centos or amazon linux, they include an apache server already configured to proxy requests to JOSSO.

You can choose one of the avaiable builders: amazon, virtualbox.

**amazon-ebs:** Used to create AWS AMI definitions

**virtualbox-ovf:** Only for internal use, requires a pre-existing OVF VM

#### Example:

User **josso-os-packer.json** to create a base OS to work with any JOSSO version

packer build -only=amazon-ebs -var-file=./josso-vars.json ./josso-os-packer.json

Use **josso-ce-packer.json** to actually create a specific JOSSO VM
packer build -only=amazon-ebs -var-file=./josso-vars.json ./josso-ce-packer.json






