//map that keeps track of the current elements on the home page and lecture page that are highlighted
var currentlyHighlighted = {'home': [], 'lecture' : []};
//local path
var path = "/"+window.location.pathname.split("/")[1];

//highlight elements based on the results from the search query
function highlight(input_element, page){
	//get the search query
	var searchQuery = document.getElementById(input_element).value;
	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (xhttp.responseText.length > 0){
			var list = JSON.parse(xhttp.responseText);
			//clear any current highlights
			clearHighlight(page);
			//add the appropriate highlights
			addHighlight(list, page);
		}
	};

	xhttp.open("GET", path+"/SearchServlet?query="+searchQuery+"&type="+page, true);
	xhttp.send();
}

//clear the highlight
function clearHighlight(page){
	//pass in the list of currently highlighted items based on which page we are on
	changeBackgroundColor(currentlyHighlighted[page], 'white');
	currentlyHighlighted[page] = [];
}

//add highlight
function addHighlight(arr, page){
	changeBackgroundColor(arr, 'yellow');
	//set the list of currently highlighted items for the page we are on
	currentlyHighlighted[page] = arr;
}

//iterate through the provided array and treat each element as a class name
//get all elements with that class name and change the background color
function changeBackgroundColor(args, color){
	
	for (j=0; j<args.length; j++){
		changeBackground(args[j].toString(), color);
	}
}

//iterate over all elements with class=className, and call func() on each element
function iterate(className, func, value){
	
	var elems = document.getElementsByClassName(className);
	for (i = 0; i<elems.length; i++){
		func(elems[i], value);
	}
}

//FUNCTIONS THAT MODIFY A LIST OF ELEMENTS BY CALLING ITERATE
//call iterate and pass in a function that changes the background color of the element
function changeBackground(className, color){
	iterate(className, changeElementColor, color);
}

//call iterate and pass in a function that changes the column span of the element
function changeColSpan(className, value){
	iterate(className, changeElementColSpan, value);
}

//call iterate and pass in a function that changes the display value of the element
function changeDisplay(className, display){
	iterate(className, changeElementDisplay, display);
}

//FUNCTIONS THAT MODIFY INDIVIDUAL ELEMENTS
//changes the display of the element
function changeElementDisplay(elem, display){
	elem.style.display = display;
}

//changes the column span of the element
function changeElementColSpan(elem, value){
	elem.colSpan = value;
}

//changes the background color of the element
function changeElementColor(elem, color){
	elem.style.backgroundColor = color;
}