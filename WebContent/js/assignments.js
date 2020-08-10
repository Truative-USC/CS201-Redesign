//local path
var path = "/"+window.location.pathname.split("/")[1];
//map from deliverable number to table row html
var deliverableHtml = {};
//map from assignment number to table row html
var assignmentHtml = {};
//number of deliverables and assignments
var numDeliverables;
var numAssignments;

//called once upon loading assignments.jsp in order to store the html of table rows
function populateMaps(numDels, numAs){
	numAssignments = numAs;
	numDeliverables = numDels;
	populateMap(numDels, deliverableHtml, 'd');
	populateMap(numAs, assignmentHtml, 'a');
}

//populate either the map of deliverable html or the assignment html
function populateMap(num, map, idPrefix){
	for (i = 0; i<num; i++){
		//get the ith deliverable/assignment table row
		var current = document.getElementById(idPrefix+i);
		//the value of the element title is the number of the deliverable/assignment
		map[current.title] = current.innerHTML;	
	}
}

//return the value of the selected radio button
function getSelected(radioName){
	var radioOptions = document.getElementsByName(radioName);
	for (i = 0; i<radioOptions.length; i++){
		if (radioOptions[i].checked){
			return radioOptions[i].value;
		}
	}
}

//modify the front end based on the sorting choices
function sort(type, sortBy, sortDirection){
	//get the values of the selected radio buttons
	var by = getSelected(sortBy);
	var dir = getSelected(sortDirection);

	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (xhttp.responseText.length > 0){
			//returns a list of GeneralAssignment objects
			var list = JSON.parse(xhttp.responseText);
			//change the front end
			changeElements(list, type.charAt(0));
		}
	};

	xhttp.open("GET", path+"/AssignmentsSortServlet?type="+type+"&sortBy="+by+"&direction="+dir, true);
	xhttp.send();
}

//change the front end
function changeElements(args, character){
	//are we changing the deliverables or the assignments
	var isDeliverables = character === 'd' ;
	//determine the appropriate map and num to use
	var num = isDeliverables ? numDeliverables : numAssignments;
	var map = isDeliverables ? deliverableHtml : assignmentHtml;
	//iterate over the elements in order and set the appropriate html  
	for (i = 0; i<num; i++){
		var current = document.getElementById(character+i);
		//get the appropriate html from the map using the number attribute of the GeneralAssignment
		current.innerHTML = map[args[i].number.toString()];
	}
}