var ws = new WebSocket("ws://node20.morado.com:10001/pubsub");

ws.onopen = function() {
    ws.send('{"type":"publish","topic":"CSGeoDimension","data":{"id”:1122,"type":"dataQuery","data":{"time":{"latestNumBuckets":10,"bucket":"1m"},"incompleteResultOK":true,"keys":{"region":"95","zipcode":"95054"},"fields":["time","zipcode","region","lon:FIRST","wait:AVG","lat:FIRST"]},"countdown":299,"incompleteResultOK":true}}');
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