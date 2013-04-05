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
  model: function() {
    return ['red', 'yellow', 'blue'];
  }
});
