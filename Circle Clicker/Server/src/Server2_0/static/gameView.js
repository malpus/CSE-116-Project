var socket = io.connect({transports: ['websocket']});
socket.on('gameState', parseGameState);

var canvas = document.getElementById("canvas");
var context = canvas.getContext("2d");
context.globalCompositeOperation = 'source-over';

function parseGameState(event) {
    const gameState = JSON.parse(event);
    drawGameBoard();
    console.log(gameState);
    for (var i=0, size = gameState.length; i < size; i++) {
        var player = JSON.parse(gameState[i]);
        var id = player['pid'];
        var x = parseFloat(player['posx']);
        var y = parseFloat(player['posy']);
        var point = parseFloat(player['score']);

        drawCircle(x,y,point, id === socket.id ? 'red' : 'blue');
    }
}

function drawCircle(x,y, point, color){
    var r = 10;

    if(point>=1) {                         /* dialate circle method in javascript, adds 5 to radius per click */
        r = r + 5*point
    }
    context.beginPath();
    context.arc(x,y,r,0,2*Math.PI);
    context.fillStyle = color;                            /* use ID to change color of player in if statement */
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