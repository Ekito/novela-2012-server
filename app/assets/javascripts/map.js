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

		this.moveToCenter();

	},

	/* center the map to display every points of Sketchytrack layer */
	moveToCenter: function() {
		var bounds = this.olTracks.globalBounds;
		this.olMap.zoomToExtent(bounds);
	},

	/* add a track */
	addTrack: function(id,track,options) {
		this.olTracks.addTrack(id,track);
		this.runOptions(options);
	},

	addPointToTrack: function(id,point,options) {
		this.olTracks.addPointToTrack(id,point);
		this.runOptions(options);
	},

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
			this.moveToCenter();
	},

	addCenterMapBtn: function() {
		$(".olButton.olControlZoomOut").after("<a onclick='$.map.moveToCenter();' class='olControlCenter olButton'><i class='icon-globe icon-white'></i></a>")
	}
}