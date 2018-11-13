function paintSquare(document,square,hasImage,pathImage)  {

    var element:HTMLElement = document.createElement("div");
    element.setAttribute("id", square);
    
    if (hasImage) {
        var myloc:HTMLImageElement = new Image();
        myloc.useMap = pathImage;
        var img:HTMLElement = document.createElement('img')
        img.setAttribute('src', myloc.useMap);
        element.appendChild(img);
    }
    document.body.appendChild(element);
}

document.title = "VulnChess";
let vertical:string[] = ["1","2","3","4","5","6","7","8"];
let horizontal:string[] = ["a","b","c","d","e","f","g","h"];

let i:string;
let j:string;
for (i of horizontal) {
    for (j of vertical) {
        let square:string = i+j;
        let color = "w";
        let pathImage = "";

        //Choose color
        if (square.charAt(1) === '8' || square.charAt(1)  === '7') {
            color = "b";
        }
        else {
            color = "w";
        }

        //paint squares
        if (square==="a8" || square==="h8" || square==="a1" || square==="h1") {
          pathImage = "../images/tower"+color+".png";
          paintSquare(document,square,true,pathImage);
        }
        else if (square==="b8" || square==="g8" || square==="b1" || square==="g1") {
          pathImage = "../images/knight"+color+".png";
          paintSquare(document,square,true,pathImage);
        }
        else if (square==="c8" || square==="f8" || square==="c1" || square==="f1") {
          pathImage = "../images/bishop"+color+".png";
          paintSquare(document,square,true,pathImage);
        }        
        else if (square==="d8" || square==="d1") {
          pathImage = "../images/queen"+color+".png";
          paintSquare(document,square,true,pathImage);
        }
        else if (square==="e8" || square==="e1") {
          pathImage = "../images/king"+color+".png";
          paintSquare(document,square,true,pathImage);
        }     
        else if (square.charAt(1) === "7" || square.charAt(1) === "2") {
          pathImage = "../images/pawn"+color+".png";
          paintSquare(document,square,true,pathImage);
        }        
     
        else {
          paintSquare(document,square,false,null);
        }
    }
}


