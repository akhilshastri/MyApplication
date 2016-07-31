/**
 * Created by Akhil on 31-07-2016.
 */

var btn = document.getElementById('btnHello');
var msg = document.getElementById('msg');

btn.onclick = function (e) {
    alert('Hello from cache');
};


msg.innerHTML = "Hello from cache-xx";

alert('cache-js loaded');