$.map = {

	/* OpenLayers map object */
	olMap: null,

	/* OpenLayers Sketchytrack layer that contains every displayed paths */
	olTracks: null,

	usersLastTrack: null,
	nextTrackId: null,

	init: function(hideControls,data) {

		/* instanciation */
		if (hideControls) {
			this.olMap = new OpenLayers.Map("map",{controls:[]});
		} else {
			this.olMap = new OpenLayers.Map("map");
			/* kinetic effect on the map */
			this.olMap.addControl(new OpenLayers.Control.Navigation({dragPanOptions: {enableKinetic: true}}));

			this.addCenterMapBtn();
		}
		this.usersLastTrack = {};
		this.nextTrackId = 0;

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
		if (data) {
			for(var i in data) 
			{
		 		this.addTrack(i,data[i]);
			}
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
			center: [Boolean],	// call showAllTracks
			isMe: [Boolean]		// set primary color for this track and secondary for the others
		}

	*/

	/* center the map to display every points of Sketchytrack layer */
	showAllTracks: function() {
		var bounds = this.olTracks.globalBounds;
		this.olMap.zoomToExtent(bounds);
	},

	/* center the map to display every points of a track */
	showTrack: function(userId) {
		var id = this.getTrackIdForUser(userId);
		var bounds = this.olTracks.tracksBounds[id];
		if (!bounds) return;
		this.olMap.zoomToExtent(bounds);
	},

	/* center the map to a position */
	setCenter: function(point,level) {
		var lonlat = new OpenLayers.LonLat(point.lon, point.lat);
		this.olMap.setCenter(lonlat,level);
	},

	/* add a track */
	addTrack: function(userId,points,options) {
		var id = this.getTrackIdForUser(userId,true);
		this.olTracks.addTrack(id,points);
		this.runOptions(id,options);
	},

	/* add a point to a track */
	addPointToTrack: function(userId,point,isStart,options) {
		var id = this.getTrackIdForUser(userId,isStart);
		this.olTracks.addPointToTrack(id,point);
		this.runOptions(id,options);
	},

	/* add multiple points to a track */
	addPointsToTrack: function(userId,points,isStart,options) {
		var id = this.getTrackIdForUser(userId,isStart);
		this.olTracks.addPointsToTrack(id,points);
		this.runOptions(id,options);
	},

	/* PRIVATE METHODS */
	runOptions: function(id,options) {
		if (!options) return;

		if (options.redraw)
			this.olTracks.redraw();

		if (options.center)
			this.showAllTracks();

		if (options.isMe)
			this.olTracks.setIsMe(id);
	},

	addCenterMapBtn: function() {
		$(".olButton.olControlZoomOut")
		.after(
			"<a onclick='$.map.showAllTracks();' class='olControlCenter olButton'>"+
				"<i class='icon-globe icon-white'></i>"+
			"</a>"
		);
	},

	getTrackIdForUser: function(userId,start) {

		if (!this.usersLastTrack[userId] || start) {
			this.usersLastTrack[userId] = this.nextTrackId;
			this.nextTrackId++;
		}

		return this.usersLastTrack[userId];
	}
}



