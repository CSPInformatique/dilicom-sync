window.ReferencePageView = Backbone.View.extend({
	adjustTableSize : function(){
		$(".references").height($(window).innerHeight() - ($("body").innerHeight() - $(".references").innerHeight()));
	},
	
	el : $('.referencePage-container'),
	
    events : {
    	'click .delete button' : 'deleteContract',
    },
    
    initialize : function() {
        /*--- binding ---*/
        _.bindAll(this, 'render');
        /*---------------*/
        
        // Shows loading gif.
        $(this.$el.selector).html(_.template($('#loading-template').html())());

    	this.model.fetch({success : this.render});
    	
        this.template = _.template($('#referencePage-template').html());


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
            "#" +
            	(parseInt(this.model.page) - 1) + "/" + 
            	this.model.results + "/" +
            	this.model.sortBy + "/" +
            	this.model.direction, 
            {trigger: true}
        );

        $(".pager .next a").attr(
            "href", 
            "#" + 
                (parseInt(this.model.page) + 1) + "/" + 
                this.model.results + "/" +
                this.model.sortBy + "/" +
                this.model.direction, 
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
                "#" +
	                referencePageView.model.page + "/" + 
	                referencePageView.model.results + "/" +
                    sortBy + "/" +
                    direction, 
                {trigger: true}
            );
        });

//        $('.references table').floatThead({
//            scrollContainer: function($table){
//                return $table.closest('.references');
//            }
//        });

        return this;
    }
});