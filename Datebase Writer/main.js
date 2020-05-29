const { app, BrowserWindow } = require('electron');

function createWindow () {
  // Create the browser window.
  win = new BrowserWindow({ width: 500, 
                            height: 710, 
                            title: "Scrambled Eggs: XML Writer",
                            resizable: "false" })

  // and load the index.html of the app.
  win.loadFile('index.html')
}

app.on('ready', createWindow);