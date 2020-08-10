//hide/show due dates and lectures based on choice
function filter(chosenOption){
	
	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		
		if (xhttp.responseText.length > 0){
			//object var is a map from string to object
			var object = JSON.parse(xhttp.responseText);
			//check the current radio button
			document.getElementById(chosenOption).checked = true;
			//disable search buttons and input if necessary
			document.getElementById('clear_button').disabled = object['disableSearch'];
			document.getElementById('search_button').disabled = object['disableSearch'];
			document.getElementById('topic_search').disabled = object['disableSearch'];
			//array with the function parameter values for displayLectures
			var displayLecArgs = object['displayLectures'];
			//display or hide lectures
			displayLectures(displayLecArgs[0], displayLecArgs[1], displayLecArgs[2]);
			//display or hide due dates
			displayDueDates(object['displayDueDates']);
		}
	};

	xhttp.open("GET", path+"/LecturesFilterServlet?choice="+chosenOption, true);
	xhttp.send();
}

//hides or shows due dates
function displayDueDates(display){
	changeDisplay("due_date", display);
}

//hides or shows lectures
function displayLectures(display, weekCol, shouldSplit){
	//hide or show labs
	changeDisplay("lab", display);
	//hide or show lectures
	changeDisplay("lecture", display);
	//if we are hiding lectures, we want to set the column span on one of the column on due date elements to 0
	changeDisplay("topic_col", display);
	//if we are hiding lectures, we want the week column to have a span of 3
	changeColSpan("week", weekCol);
	//if we are hiding due dates, we want the rowspan for week number and labs to be equal to the number of lectures
	//if wer are showing all, we want the rowspand for week number and labs to be equal to the sum of the number of lectures and the number of due dates
	changeRowSpan("week", shouldSplit);
	changeRowSpan("lab", shouldSplit);

}

//changes the rowspan of a table row to either the number of lectures or the number of lectures plus the number of due dates
function changeElementRowSpan(elem, conditional){
	var nums = elem.title.split(',');
	if (conditional){
		//number of lectures
		elem.rowSpan = nums[0];
	}
	else{
		//sum of number of lectures and number of due dates
		elem.rowSpan = (parseInt(nums[0], 10) + parseInt(nums[1], 10)).toString(10);
	}
}

//changes the rowspan for every element with the class, className
function changeRowSpan(className, shouldSplit){
	iterate(className, changeElementRowSpan, shouldSplit);
}

