const fs = require("fs");
const path = require("path");

const regex = /public String toString/;
const content = fs.readFileSync(process.argv[2], "utf8").replace(/\r/g, "");
const lines = content.split("\n").filter(e => e.length > 0);
const buffer = [];
for (let i = 0; i < lines.length; i++) {
    if (!regex.test(lines[i])) continue;
    buffer.push("@Override");
    const className = path.basename(process.argv[2], ".java");
    buffer.push("public Object visita" + className + "(Visitabile visitabile" + className + ") {"); 
    buffer.push(className + " v = (" + className + ") visitabile" + className + ";");
    let j = 0;
    while (lines[i][j] === " " || lines[i][j] === "\t") j++;
    let spaces = j;
    i++;
    for (; i < lines.length; i++) {
        j = 0;
        while (lines[i][j] === " " || lines[i][j] === "\t") j++;
        if (j === spaces) {
            buffer.push(lines[i++]);
            break;
        }
        buffer.push(lines[i]);
    }
}

console.log(buffer.join("\n"));
