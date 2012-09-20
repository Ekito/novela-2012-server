$.map = {

	/* OpenLayers map object */
	olMap: null,

	/* OpenLayers Sketchytrack layer that contains every displayed paths */
	olTracks: null,

	init: function(data) {

		this.olMap = new OpenLayers.Map("map");

		/* kinetic effect on the map */
		this.olMap.addControl(new OpenLayers.Control.Navigation({dragPanOptions: {enableKinetic: true}}));

		this.addCenterMapBtn();

		/* White layer */
		var wms = new OpenLayers.Layer.Image(
			"White layer",
			$("#map").data("bg"),
			this.olMap.maxExtent,
	   		new OpenLayers.Size(1125,558),
			{isBaseLayer: true});

		/* uncomment this if you want to display a proper map in background */
		//var wms = new OpenLayers.Layer.WMS( "OpenLayers WMS", "http://vmap0.tiles.osgeo.org/wms/vmap0", {layers: 'basic'} );

		this.olMap.addLayer(wms);

		/* Build Sketchytrack layer */
		this.olTracks = new Sketchytrack.Layer("SampleTrack");
		for(var i in data) 
		{
	 		this.addTrack(i,data[i]);
		}
		this.olMap.addLayer(this.olTracks);

		this.showAllTracks();

	},

	/*
	Format of parameters:
	---------------------

		point = {"lat":43.601364,"lon":1.441976};
		points = [
			{"lat":43.601364,"lon":1.441976},
			...
			{"lat":43.601384,"lon":1.442043}
		];
		options = {
			redraw: [Boolean],	// redraw the layer
			center: [Boolean]	// call showAllTracks
		}

	*/

	/* center the map to display every points of Sketchytrack layer */
	showAllTracks: function() {
		var bounds = this.olTracks.globalBounds;
		this.olMap.zoomToExtent(bounds);
	},

	setPosition: function(point) {

	},

	/* add a track */
	addTrack: function(id,points,options) {
		this.olTracks.addTrack(id,points);
		this.runOptions(options);
	},

	/* add a point to a track */
	addPointToTrack: function(id,point,options) {
		this.olTracks.addPointToTrack(id,point);
		this.runOptions(options);
	},

	/* add multiple points to a track */
	addPointsToTrack: function(id,points,options) {
		this.olTracks.addPointsToTrack(id,points);
		this.runOptions(options);
	},

	/* PRIVATE METHODS */
	runOptions: function(options) {
		if (!options) return;

		if (options.redraw)
			this.olTracks.redraw();

		if (options.center)
			this.showAllTracks();
	},

	addCenterMapBtn: function() {
		$(".olButton.olControlZoomOut")
		.after(
			"<a onclick='$.map.showAllTracks();' class='olControlCenter olButton'>"+
				"<i class='icon-globe icon-white'></i>"+
			"</a>"
		);
	}
}