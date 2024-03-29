# coding: utf-8
from flask import render_template, request, redirect, url_for
from flask.wrappers import Request, Response
import json
import random
import copy
import redis
import amazonscr
from getsong import getsong

from app import app

@app.route('/', methods=["GET", "POST"])
def index():
    # counter = app.clients.list_clients()
    return redirect(url_for('static', filename='index.html'))


# @app.route('/add', methods=["GET", "POST"])
# def add_clients():
#     # app.clients.add_client(request.remote_addr)
#     # counter = app.clients.list_clients()
#     # sess_id = app.clients.get_sess_id(request.remote_addr)
#     return redirect(url_for('listen', client_id = random.randint(1000, 9999)))


# @app.route('/remove', methods=["GET", "POST"])
# def remove_clients():
#     app.clients.remove_client(request.remote_addr)
#     counter = app.clients.list_clients()
#     return redirect(url_for('index',num_clients=counter))

# @app.route('/register/<client_id>')
# def register(client_id):
#     environ = request.environ
#     print "socket"
#     ws = request.environ.get('wsgi.websocket')
#     app.clients[client_id] = copy.deepcopy(ws)
#     return Response("")


@app.route('/listen/<client_id>', methods=["GET", "POST"])
def listen(client_id):
    if request.method == 'GET':
        return render_template('listen.html', websocket=client_id)
    else:
        query = request.values['content']
        
        data = { "query": query }

        amazon = amazonscr.Buymp3(query)
        song_id = getsong(query)

        if song_id>0:
            data["soundcloud"] = {
                "song_id": song_id
            }

        if not amazon.error:
            data["amazon"] = { 
                "link" : amazon.oneclickbuy,
                "price" : amazon.price,
                "title" : amazon.name,
                "artist" : amazon.artist,
                "album_link" : amazon.albumbuylink 
            }

        msg = {
          "channels": [client_id],
          "data": data
        }

        r = redis.Redis()
        r.publish("juggernaut", json.dumps(msg))

        return Response("")
