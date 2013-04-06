#!/bin/sh
nohup juggernaut & nohup redis-server & python ./websockets/runserver.py
