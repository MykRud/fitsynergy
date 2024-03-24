var tim;
var sec = 0;
var startDate = new Date();

function startRecording() {
    sec++;
    tim = setTimeout(startRecording, 1000);

    var min = Math.floor(sec / 60);
    var secDisplay = sec % 60;

    document.getElementById("timer").innerHTML = min + " : " + secDisplay;
}

function stopRecording() {
    clearTimeout(tim);
}

function initTimer() {
    document.getElementById("start-div").style.display = "none";
    document.getElementById("start-div").style.visibility = "hidden";

    document.getElementById("timer-div").style.display = "block";
    document.getElementById("timer-div").style.visibility = "visible";

    document.getElementById("timer").innerHTML = "0 : 0";
}