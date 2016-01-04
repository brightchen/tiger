var ws = new WebSocket("ws://node20.morado.com:10001/pubsub");

ws.onopen = function() {
    ws.send('{"type":"subscribe","topic":”CSGeoQueryResult.1122”}');
};

ws.onmessage = function (evt) {
    alert("Message: " + evt.data);
};

ws.onclose = function() {
    alert("Closed!");
};

ws.onerror = function(err) {
    alert("Error: " + err);
};