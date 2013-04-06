App = Ember.Application.create({
  LOG_TRANSITIONS: true
});

App.Router.map(function() {
  App.Router.map(function() {
      this.route("play");
      this.route("about");
    });
});

App.IndexRoute = Ember.Route.extend({

});

App.Song = DS.Model.extend({
    song_id: DS.attr('integer'),

    link: DS.attr('string'),
    price: DS.attr('string'),
    artist: DS.attr('string'),
    song_title: DS.attr('string'),
    album: DS.attr('string')
});

App.HomeView = Ember.View.extend({
    didInsertElement: function() {
        var ws_URL ='';
        var sessID = Math.floor(Math.random()*9999);
        var jug = new Juggernaut;
            jug.subscribe(sessID, function(data){
            console.log("Got data: " + data);
        });

        var currentURL = window.location.hostname + ':8000:/listen/' + sessID;
        var src="http://chart.googleapis.com/chart?cht=qr&chl=http://" + currentURL + "&chs=250x250";
        var qrImg=document.createElement("img");
        qrImg.setAttribute('src', src);
        qrImg.setAttribute('alt', 'QR Code');
        qrImg.setAttribute('height', '250px');
        qrImg.setAttribute('width', '250px');

       $('#qrcode').prepend(qrImg);

        window.onbeforeunload = function() {
            ws.onclose = function () {};
            ws.close()
        };
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







