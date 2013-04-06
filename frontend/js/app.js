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







