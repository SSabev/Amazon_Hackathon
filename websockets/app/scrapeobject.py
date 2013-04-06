#!/usr/bin/python

__author__ = 'Stefan'

import json
import requests
from bs4 import BeautifulSoup


class ScrapeObject(object):
    """
    Fetches a page given a url and with the parse_html function return a 
    beautiful soup object which can be easily queried almost like xpath
    to find all the info you want.
    Example on how to use:

    >>> from scrapeobject import ScrapeObject
    >>> google = ScrapeObject('http://google.com')
    >>> google.fetch()
    >>> soup = google.parse_html()
    """
    def __init__(self, url):
        self.get = False
        self.post = False
        self.url = url
    

    def parse_html(self):
        """
        This will basically create a BS object that is quite easy to query.
        More info -> http://www.crummy.com/software/BeautifulSoup/bs4/doc/
        """
        return BeautifulSoup(self.html)


    def set_get(self, params):
        """
        Pass in a dictionary with get parametres
        """
        self.get = True
        self.params = params
    
    
    def set_post(self, data):
        """
        Pass in a nicey dictionary, which is then transformed in a json 
        and then passed on to the requests module
        """
        self.post = True
        self.data = json.loads(data)

    
    def fetch(self):
        """
        Finally fetch the page and then get all the content you need. 
        """
        if self.post:
            self.request = requests.post(self.url, params=self.params if self.get else {}, data=self.data)
        elif self.get:
            self.request = requests.get(self.url, params=self.params)
        else:
            self.request = requests.get(self.url)
        
        self.html = self.request.content

