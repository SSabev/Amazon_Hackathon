# coding: utf-8
from flask import render_template, request, redirect, url_for
import json

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
    return redirect(url_for('listen', websocket = sess_id))


@app.route('/remove', methods=["GET", "POST"])
def remove_clients():
    app.clients.remove_client(request.remote_addr)
    counter = app.clients.list_clients()
    return redirect(url_for('index',num_clients=counter))


@app.route('/listen/<websocket>', methods=["GET", "POST"])
def listen(websocket):
    if request.method == 'GET':
        return render_template('listen.html', websocket=websocket)
    else:
        params = request.values['content']

        ws = app.client.get_sess_id(websocket)
        ws.send(json.dumps({'output': params}))
        print params
        print "yolo"

        return render_template('listen.html', websocket=websocket)
