#!/usr/bin/env python3

import os
import os.path
import subprocess

for subdir in os.listdir("."):
    if os.path.isdir(subdir):
        dockerCmd = subprocess.run(['docker', 'build', '-t', "nedbank/bpmxjee-" + subdir + ":latest", '-f', 'Dockerfile', subdir])

