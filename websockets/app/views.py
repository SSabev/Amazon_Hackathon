# coding: utf-8
from flask import render_template, request, redirect, url_for

from app import app

@app.route('/')
def index():
    counter = app.clients.list_clients()
    return render_template('index.html', num_clients = counter)


@app.route('/add')
def add_clients():
    app.clients.add_client(request.remote_addr)
    counter = app.clients.list_clients()
    return redirect(url_for('index', num_clients=counter))


@app.route('/remove')
def remove_clients():
    app.clients.remove_client(request.remote_addr)
    counter = app.clients.list_clients()
    return redirect(url_for('index',num_clients=counter))

@app.route('/websocket/<socket_id>')
def handle_socket():

    pass
    
