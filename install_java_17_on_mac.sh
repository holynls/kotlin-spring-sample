#!/bin/bash

set -e

curl -s "https://get.sdkman.io" | bash

source "$HOME/.sdkman/bin/sdkman-init.sh"

sdk install java 17.0.9-zulu -y
