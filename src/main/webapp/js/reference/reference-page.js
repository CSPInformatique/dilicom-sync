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
	
//	$(window).resize(function(){
//		referencePageView.adjustTableSize();
//	});
});

var initRouter = function(){
	var router = new Backbone.Router({
		routes: {
			":page/:results/:sortBy/:direction" : "loadPage",  // #1/1/50/startDate/ASC
		}
	});
	
	router.on(
		"route:loadPage",
		function(page, results, sortBy, direction) {
			loadPage(page, results, sortBy, direction);
		}
	);
	
	return router;
};

var loadPage = function(pageNumber, results, sortBy, direction){		
	var page = new ReferencePage();
	
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
