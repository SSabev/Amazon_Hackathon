App = Ember.Application.create({
  LOG_TRANSITIONS: true
});

App.Router.map(function() {
  App.Router.map(function() {
      this.route("play");
      this.route("about");
    });
});

App.musicController = Ember.Object.create({
    play: function() {
        console.log("asd");
    }
});

App.HomeView = Ember.View.extend({
    didInsertElement: function() {
        
    }
});

App.PlayerView = Ember.View.extend({
    didInsertElement: function() {
        var widgetIframe = document.getElementById('sc-widget'),
            widget       = SC.Widget(widgetIframe),
            newSoundUrl = 'http://api.soundcloud.com/tracks/13692671';

        widget.bind(SC.Widget.Events.READY, function() {
          widget.bind(SC.Widget.Events.FINISH, function() {
            widget.load(newSoundUrl, {
                auto_play: true,
            });
            
          });
        });
    },
});


$(document).ready(function() {
        var ws_URL ='';
        var sessID = Math.floor(Math.random()*9999).toString();
        console.log(sessID);
        var jug = new Juggernaut;
        jug.subscribe(sessID, function(data){ 
            console.log("Got data: " + data);
            // return App.musicController.play();
            $('#qrcode').empty();
            var s_id = data["soundcloud"]["song_id"];
            var sc = '<div class="player"><iframe id="sc-widget" width="960px" height="166" scrolling="no" frameborder="no" src="https://w.soundcloud.com/player/?url=http%3A%2F%2Fapi.soundcloud.com%2Ftracks%2F' + s_id + '&show_artwork=true&auto_play=true"></iframe></div>'
            $('#qrcode').append(sc);
            var widgetIframe = document.getElementById('sc-widget'),
                widget       = SC.Widget(widgetIframe),
                newSoundUrl = 'http://api.soundcloud.com/tracks/'+ data["soundcloud"]["song_id"];
            widget.bind(SC.Widget.Events.READY, function() {
              widget.bind(SC.Widget.Events.FINISH, function() {
                widget.load(newSoundUrl, {
                    auto_play: true,
                });
                
              });
            });

        });

        var currentURL = window.location.hostname + ':8000:/listen/' + sessID;
        var src="http://chart.googleapis.com/chart?cht=qr&chl=http://" + currentURL + "&chs=250x250";
        var qrImg=document.createElement("img");
        qrImg.setAttribute('src', src);
        qrImg.setAttribute('alt', 'QR Code');
        qrImg.setAttribute('height', '250px');
        qrImg.setAttribute('width', '250px');

       $('#qrcode').prepend(qrImg);

   }); 








