# coding: utf-8
import os

from flask import Flask
from websocket import handle_websocket
from ClientList import ClientList

app = Flask(__name__)
app.secret_key = os.urandom(24)
app.debug = True
# app.clients = ClientList()
app.clients = {}

def my_app(environ, start_response):  
    # print environ
    # path = environ["PATH_INFO"]  
    # if path == "/":  
    #     return app(environ, start_response)  
    # elif "websocket" in path:
    #     print "DEEERP"
    #     handle_websocket(environ["wsgi.websocket"])   
    # else:  
    return app(environ, start_response)  

import views
