# coding: utf-8
import random


class ClientList(object):
    """
    Adding a simple client list of all the active connections.
    Not the smartest way, but until we max out the memory this is good enough.
    """
    def __init__(self):
        """
        Self explnatory
        """
        self.clients = {}


    def add_client(self, client):
        """
        Adding a new key to the dictionary. It will compute a random value and 
        return the hash which will serve as a session id.
        """
        if client not in self.clients.keys():
            self.clients[client] = random.randint(1000, 9999)# hex(random.getrandbits(128))[2:-1]


    def remove_client(self, client):
        """
        Removes a client from the list, if not there, KeyError happens.
        """
        if client in self.clients.keys():
            del self.clients[client]
        else:
            raise KeyError


    def list_clients(self):
        """
        Pretty prints the clients for pretty purposes.
        """
        counter = 0
        for client in self.clients.keys():
            counter +=1 
            print "User: %s, random value: %s"%(client, self.clients[client])

        return counter

    def get_sess_id(self, client):
        return self.clients.get(client)
