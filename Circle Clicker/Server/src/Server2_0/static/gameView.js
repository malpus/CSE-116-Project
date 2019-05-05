var socket = io.connect({transports: ['websocket']});
socket.on('gameState', parseGameState);

const tile = 30;

var canvas = document.getElementById("canvas");
var context = canvas.getContext("2d");
context.globalCompositeOperation = 'source-over';

function parseGameState(event) {
    console.log(event);
    const gameState = JSON.parse(event);
    drawGameBoard();
    console.log(gameState);
    for (var i=0, size = gameState.length; i < size; i++) {
        var player = JSON.parse(gameState[i]);
        console.log(player);
        var id = player['pid'];
        console.log(id);
        var x = parseFloat(player['posx']);
        console.log(x);
        var y = parseFloat(player['posy']);
        console.log(y);
        var point = parseFloat(player['score']);

        drawCircle(x,y,point);

        /* dialator can be 2.0*points or a variation of that */

        /*placeCircle(x,y,id === socket.id ? '#ffff00' : '#56bcff', 2) */
        /*placeCircle(player['posx'], player['posy'], player['id'] === socket.id ? '#ffff00' : '#56bcff', 2.0); */
    }
}

function drawCircle(x,y, point){
    var r = 10;

    if(point>=1) {                         /* dialate circle method in javascript, adds 5 to radius per click */
        r = r + 5*point
    }
    context.beginPath();
    context.arc(x,y,r,0,2*Math.PI);
    context.fillStyle = "blue";                            /* use ID to change color of player in if statement */
    context.fill();
    context.stroke();
}

function drawGameBoard() {
    var gameWidth = 1000;
    var gameHeight = 500;

    context.clearRect(0, 0, gameWidth, gameHeight);

    canvas.setAttribute("width", gameWidth);
    canvas.setAttribute("height", gameHeight);
}