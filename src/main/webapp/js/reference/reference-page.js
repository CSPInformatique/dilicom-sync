var referencePageView;

$(document).ready(function(){
	router = initRouter();
	
	$(".nav li.references").addClass("active");
	
	// Initializing the router.
	Backbone.history.start();
		
	if(location.hash == ""){
		router.navigate(
			"0/50/publicationDate/ASC", 
			{trigger: true}
		);
	}else{
		Backbone.history.loadUrl(Backbone.history.fragment);
	}
	
	$(".searchBar button").click(function(){
		var query = $(".searchBar input[type='text']").val();
		
		var fields = [];
		$(".searchBar input[type='checkbox']:checked").each(function(index, element){
			fields.push($(element).val());
		});
		
		router.navigate(
			referencePageView.generateUrl(query, fields, 0, 50, referencePageView.model.sortBy, referencePageView.model.direction), 
			{trigger: true}
		);	
	});
});

var initRouter = function(){
	var router = new Backbone.Router({
		routes: {
			":page/:results/:sortBy/:direction" : "loadPage",  // #1/1/50/startDate/ASC/
			":page/:results/:sortBy/:direction/:query" : "searchWithQuery",  // #1/1/50/startDate/ASC/action/titre
			":page/:results/:sortBy/:direction/:query/:fields" : "searchWithQueryAndFields"  // #1/1/50/startDate/ASC/action/titre
		}
	});
	
	router.on(
		"route:loadPage",
		function(page, results, sortBy, direction) {
			loadPage(page, results, sortBy, direction, null, null);
		}
	);
	
	router.on(
		"route:searchWithQuery",
		function(page, results, sortBy, direction, query) {
			loadPage(page, results, sortBy, direction, query, null);
		}
	);
	
	router.on(
		"route:searchWithQueryAndFields",
		function(page, results, sortBy, direction, query, fields) {
			loadPage(page, results, sortBy, direction, query, fields);
		}
	);
	
	return router;
};

var loadPage = function(pageNumber, results, sortBy, direction, query, fields){		
	var page = new ReferencePage();
	
	// Settings search string.
	page.query = query;
	
	// Settings fields to search.
	if(fields != null){
		if(fields != ""){
			page.fields = fields.split(";")			
		}else{
			page.fields = null;
		}
	}
	
	// Setting page number.
	page.page = pageNumber;
	
	// Setting page results.
	page.results = results;
	
	// Setting sortBy
	page.sortBy = sortBy;
	
	// Setting direction
	page.direction = direction;
		
	// Loading contrat page view.
	referencePageView = new ReferencePageView({model : page});
};
