$(function() {
    var ws_URL ='';
    var latest = document.URL.split('/');
    var sessID = latest[latest.length-1];
    if ("WebSocket" in window) {
        ws_URL = "ws://" + document.domain + ":8000/websocket/"+sessID
        ws = new WebSocket(ws_URL);
        ws.onmessage = function (msg) {
            var message = JSON.parse(msg.data);
            console.log(message);
            $("p#log").append(message.output + '<hr />');
        };
    };

    $('#chat_form input[name=text]').focus();

    $("#chat_form").on('submit', function(e){
        e.preventDefault();

        var input = $('#chat_form input[name=text]');
        var message = $(input).val();
        $(input).val('');

        ws.send(JSON.stringify({'output': message}));
    });
    
    var currentURL = window.location.hostname + window.location.pathname;
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
});

