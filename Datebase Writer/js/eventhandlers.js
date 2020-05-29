const fs = require('fs');

var div = 0;
var maxPage = 6;

document.getElementById('next').addEventListener('click', function () {
    console.log(div)
    if (div < maxPage) { 
        document.getElementById(div.toString()).setAttribute("style","display: none")
        document.getElementById((++div).toString()).removeAttribute("style");
        document.getElementById("step"+ div).className += " active"
    }
    else if (div == maxPage) {
        writeData();

        document.getElementById("next").innerText = "Next";
        document.getElementById(div.toString()).setAttribute("style","display: none")
        div = 0;
        document.getElementById((div).toString()).removeAttribute("style");
    
        for (var i = 1; i < document.getElementsByClassName("step").length; i++) {
            document.getElementById("step" + i).classList.remove("active");
        }
        document.getElementById('form').reset();
    }

    if (div == maxPage) document.getElementById("next").innerText = "Submit";
});

document.getElementById('back').addEventListener('click', function () {
    if (div > 0) { 
        document.getElementById("next").innerText = "Next";
        document.getElementById("step"+ div).classList.remove("active");
        document.getElementById(div.toString()).setAttribute("style","display: none")
        document.getElementById((--div).toString()).removeAttribute("style");
    } 
});

function writeData () {
    var data = "<game>";
    data += "<title>" + document.getElementById("name").value + "</title>";
    data += "<description>" + document.getElementById("description").value + "</description>";
    data += "<year>" + document.getElementById("year").value + "</year>";
    data += "<publisher>" + document.getElementById("publisher").value + "</publisher>";
    data += "<developer>" + document.getElementById("developer").value + "</developer>";
    data += "<genre>" + document.getElementById("genre").value + "</genre>";
    data += "<platforms>" + document.getElementById("platform").value + "</platforms>";
    data += "<region>" + document.getElementById("region").value + "</region>";
    data += "<music>" + document.getElementById("music").value + "</music>";
    data += "<visuals>" + document.getElementById("visuals").value + "</visuals>";
    data += "<pace>" + document.getElementById("pace").value + "</pace>";
    data += "<length>" + document.getElementById("length").value + "</length>";
    data += "<violence>" + document.getElementById("violence").value + "</violence>";
    data += "<protag>" + document.getElementById("protag").value + "</protag>";
    data += "<tone>" + document.getElementById("tone").value + "</tone>";
    data += "<dimension>" + document.getElementById("dimension").value + "</dimension>";
    data += "<camera>" + document.getElementById("camera").value + "</camera>";
    data += "<setting>" + document.getElementById("setting").value + "</setting>";
    data += "<standard>" + document.getElementById("standard").value + "</standard>";
    data += "<decade>" + document.getElementById("decade").value + "</decade>";
    data += "<players>" + document.getElementById("players").value + "</players>";
    data += "<goal>" + document.getElementById("goal").value + "</goal>";
    data += "<weapon>" + document.getElementById("weapon").value + "</weapon>";
    data += "<story>" + document.getElementById("story").value + "</story>";
    data += "<accessories>" + document.getElementById("accessories").value + "</accessories>";
    data += "<customizing>" + document.getElementById("customizing").value + "</customizing>";
    data += "<enemy>" + document.getElementById("enemy").value + "</enemy>";
    data += "<difficulty>" + document.getElementById("difficulty").value + "</difficulty>";
    data += "<curve>" + document.getElementById("curve").value + "</curve>";
    data += "<feel>" + document.getElementById("feel").value + "</feel>";
    data += "<communities>" + document.getElementById("communities").value + "</communities>";
    data += "<achievement>" + document.getElementById("achievement").value + "</achievement>";
    data += "</game>\r\n"
    fs.appendFile('./data/db.txt', data, (err) => { 
        if (err) throw err; 
    });
}