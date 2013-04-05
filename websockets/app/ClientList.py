# coding: utf-8
import random

class ClientList(object):


    def __init__(self):
        self.clients = {}

    def add_client(self, client):
        if client not in self.clients.keys():
            self.clients[client] = hex(random.getrandbits(128))[2:-1]

    def remove_client(self, client):
        if client in self.clients.keys():
            del self.clients[client]
        else:
            raise KeyError

    def list_clients(self):

        for client in self.clients.keys():
            print "User: %s, random value: %s"%(client, self.clients[client])
