window.ReferencePageView = Backbone.View.extend({
	adjustTableSize : function(){
		$(".references").height($(window).innerHeight() - ($("body").innerHeight() - $(".references").innerHeight()));
	},
	
	el : $('.referencePage-container'),
	
    events : {
    	'click .delete button' : 'deleteContract',
    },
    
    generateUrl : function(query, fields, page, results, sortBy, direction){
    	var hrefUrl = "#" +
	        page + "/" + 
	        results + "/" +
	        sortBy + "/" +
	        direction;
    
	    if(query != null && query != ""){
	    	hrefUrl += "/" + query;
	    	
	    	if(fields != null && fields.length > 0){
	    		hrefUrl += "/";
	    		var first = true;
	    		for(var fieldIndex in fields){
	    			if(!first){
	    				hrefUrl += ";";
	    			}
	    			
	    			hrefUrl += fields[fieldIndex];
	    			first = false;
	    		}
	    	}
	    }
	    
	    return hrefUrl;
    },
    
    initialize : function() {
        /*--- binding ---*/
        _.bindAll(this, 'render');
        /*---------------*/
        
        // Shows loading gif.
        $(this.$el.selector).html(_.template($('#loading-template').html())());

    	this.model.fetch({success : this.render});
    	
        this.template = _.template($('#referencePage-template').html());
        
        $(window).resize(this.adjustTableSize);
    },
    	
    render : function(){
    	var page = this.model.toJSON();
    	
    	var renderedContent = this.template({page : page});
    	$(this.el).html(renderedContent);
        
    	this.adjustTableSize();
    	
        var referencePageView = this;
        
        $("table .date").each(function(index, element){
        	var dateInput = $(element);
        	
        	dateInput.html(moment(parseInt(dateInput.html())).format("YYYY-MM-DD"));
        });

        $(".pager .previous a").attr(
            "href", 
            this.generateUrl(
            	this.model.query, 
            	this.model.fields, 
            	(parseInt(this.model.page) - 1), 
            	this.model.results, 
            	this.model.sortBy, 
            	this.model.direction
            ), 
            {trigger: true}
        );

        $(".pager .next a").attr(
            "href", 
            this.generateUrl(
            	this.model.query, 
            	this.model.fields, 
            	(parseInt(this.model.page) + 1), 
            	this.model.results, 
            	this.model.sortBy, 
            	this.model.direction
            ), 
            {trigger: true}
        );

        $(".references thead th a").each(function(index, element){
            var direction = referencePageView.model.direction;
            var sortBy = $(this).attr("data-sortBy");
            if(sortBy == referencePageView.model.sortBy){
                $(element).closest("th").find(direction == "ASC" ? ".ascending" : ".descending").removeClass("hide");

                direction = direction == "ASC" ? "DESC" : "ASC";

            }
            
            $(element).attr(
                "href",
                referencePageView.generateUrl(
            		referencePageView.model.query, 
            		referencePageView.model.fields, 
                	referencePageView.model.page, 
                	referencePageView.model.results, 
                	sortBy, 
                	direction
                ), 
                {trigger: true}
            );
        });
        
        $(".references .loadIntoErp").click(function(index, element){
        	var uploadToErpIcon = $(this);
        	var loadingIcon = $(this).parent().find(".uploadingToErp");
        	var okIcon = $(this).parent().find(".glyphicon-ok");
        	
        	uploadToErpIcon.hide();
        	loadingIcon.removeClass("hidden").show();
        	
        	var ean13 = $(this).parent().parent().attr("data-reference-ean13");
        	
        	$.ajax({
				url: "reference?publishToErp&ean13=" + ean13,
        	}).always(function(){
        		loadingIcon.hide();
        	}).done(function() {
        		okIcon.removeClass("hidden").show();
    		}).fail(function() {
    			uploadToErpIcon.show();
    		});
        });

        $('.references table').floatThead({
            scrollContainer: function($table){
                return $table.closest('.references');
            }
        });

        return this;
    }
});