import soundcloud
from requests.exceptions import HTTPError

# Our client ID
client = soundcloud.Client(client_id='1271764b213f4a47d125e9e24c898475')


def getsong(query):
    '''A function that returns a Song ID to be used in the HTML5 widget
    provided. If the return value is -1, there was a 503 error from the server.
    503 is sometimes returned on malformed query OR when too many requests were
    made OR whenever they feel like it. If the return value is 0, then we
    didn't find any songs that matched the search.'''
    try:
        tracks = client.get('/tracks', q=query)
    except HTTPError, e:
        print("The server returned the following error:")
        print(e)
        return -1

    # Variables to hold the temporary found best songs
    maxviewers = 0
    maxviewersid = 0

    # In case we only found short versions of the song, return them in stead
    maxviewersshort = 0
    maxviewershortid = 0

    for track in tracks:
        try:
            if track.duration < 95000:  # Try to find non 1:30 samples
                if "remix" in track.title.lower():  # Avoid remixes
                    continue
                if track.playback_count > maxviewersshort:
                    maxviewersshort = track.playback_count
                    maxviewershortid = track.id
            else:
                if "remix" in track.title.lower():  # Avoid remixes
                    continue
                if track.playback_count > maxviewers:
                    maxviewers = track.playback_count
                    maxviewersid = track.id
        except AttributeError, e:
            print("One of the attributes that we need is inaccessible," +
                  " continue to the next element")
            continue

    # If we have only found short samples, at least return the ID of the sample
    if maxviewersid == 0:
        return maxviewershortid
    else:
        return maxviewersid
