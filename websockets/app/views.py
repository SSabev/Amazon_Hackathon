# coding: utf-8
from flask import render_template, request, redirect, url_for
import json
import random

from app import app

@app.route('/', methods=["GET", "POST"])
def index():
    counter = app.clients.list_clients()
    return render_template('index.html', num_clients = counter)


@app.route('/add', methods=["GET", "POST"])
def add_clients():
    app.clients.add_client(request.remote_addr)
    counter = app.clients.list_clients()
    sess_id = app.clients.get_sess_id(request.remote_addr)
    return redirect(url_for('listen', websocket = random.randint(1000, 9999)))


@app.route('/remove', methods=["GET", "POST"])
def remove_clients():
    app.clients.remove_client(request.remote_addr)
    counter = app.clients.list_clients()
    return redirect(url_for('index',num_clients=counter))


@app.route('/listen/<client_id>', methods=["GET", "POST"])
def listen(client_id):
    if request.method == 'GET':
        return render_template('listen.html', websocket=client_id)
    else:
        params = request.values['content']

        ws = environ["wsgi.websocket"]
        ws.send(json.dumps({'output': params}))
        print params
        print "yolo"

        return render_template('listen.html', websocket=client_id)
