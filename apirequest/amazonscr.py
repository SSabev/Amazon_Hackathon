from scrapeobject import ScrapeObject
import re


class Buymp3(object):
    '''
    An object that will contain the cheapest mp3 option for buying
    based on the query, taken from amazon.co.uk
    '''
    def __init__(self, query):
        baseurl = "http://www.amazon.co.uk/s/ref=nb_sb_noss_1?url=search-alias%3Ddigital-music&field-keywords="
        queryurl = baseurl + query.replace(" ", '+')
        a = ScrapeObject(queryurl)
        a.fetch()
        self.bsoupobj = a.parse_html()

    def getHTML(self):
        return self.bsoupobj

    def getSongTables(self):
        temp = self.bsoupobj.find_all('tr')
        self.songtables = []
        for item in temp:
            if len(item.findAll(attrs={'class': re.compile(r"titleColOdd")})) == 4:
                self.songtables.append(item)

    def getInfo(self):
        # Get only the first result, don't bother with the rest
        tds = self.songtables[0].findAll(attrs={'class': re.compile(r"titleColOdd")})
        self.oneclickbuy = self.songtables[0].find_all("a")[-1].get("href")
        self.name = tds[1].text[3:]
        self.artist = tds[2].text
        self.album = tds[3].text

    def process(self):
        self.getSongTables()
        self.getInfo()
