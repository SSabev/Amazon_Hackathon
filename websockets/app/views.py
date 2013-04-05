# coding: utf-8
from flask import render_template, request, redirect, url_for

from app import app

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/add')
def add_clients():
    app.clients.add_client(request.remote_addr)
    app.clients.list_clients()
    return redirect(url_for('index'))

@app.route('/remove')
def add_clients():
    app.clients.remove_client(request.remote_addr)
    app.clients.list_clients()
    return redirect(url_for('index'))


