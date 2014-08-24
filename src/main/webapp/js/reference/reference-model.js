window.Reference = Backbone.Model.extend({
	idAttribute: "ean13",
	url : function() {
		var base = "reference";
		if (this.isNew() || this.id == null){
			return base;
		}else{
			return 
		}
		
		base = base + (base.charAt(base.length - 1) == '/' ? '' : '/') + this.id;

		if(this.reset){
			base += "?reset";
		}

		return base;
	},
});

window.ReferencePage = Backbone.Model.extend({
	model : Reference,
	url : function(){
		var url = "reference.json?page=" + this.page + "&resultPerPage="
				+ this.results;

		if (this.sortBy != null) {
			url += "&sortBy=" + this.sortBy;
		}

		if (this.direction != null) {
			url += "&direction=" + this.direction;
		}

		return url;
	}
});