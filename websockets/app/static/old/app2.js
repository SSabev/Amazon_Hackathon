$(function() {
    var ws_URL ='';
    var latest = document.URL.split('/');
    var sessID = latest[latest.length-1];
    var jug = new Juggernaut;
        jug.subscribe(sessID, function(data){
        console.log("Got data: " + data);
    });
    // if ("WebSocket" in window) {
    //     ws_URL = "ws://" + document.domain + ":8000/register/"+sessID;
    //     ws = new WebSocket(ws_URL);
    //     ws.onmessage = function (msg) {
    //         var message = JSON.parse(msg.data);
    //         console.log(message);
    //         $("p#log").append(message.output + '<hr />');
    //     };
    // };

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

