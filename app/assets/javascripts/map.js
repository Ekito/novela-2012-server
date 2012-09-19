$.map = {

	data: null,

	olMap: null,

	tracks: null,


	init: function(data) {

		this.data = data;

		this.olMap = new OpenLayers.Map("map");

		//this.olMap.addControl(new OpenLayers.Control.Navigation({dragPanOptions: {enableKinetic: true}}));

		var wms = new OpenLayers.Layer.Image(
			"White layer",
			$("#map").data("bg"),
			this.olMap.maxExtent,
	   		new OpenLayers.Size(1125,558),
			{isBaseLayer: true});

		//var wms = new OpenLayers.Layer.WMS( "OpenLayers WMS", "http://vmap0.tiles.osgeo.org/wms/vmap0", {layers: 'basic'} );

		this.olMap.addLayer(wms);

		this.tracks = new Sketchytrack.Layer("SampleTrack");
		for(var i in this.data) 
		{
	 		this.tracks.addTrack(this.data[i]);
		}
		this.olMap.addLayer(this.tracks);

		this.moveToCenter();

	},

	moveToCenter: function() {
		var bounds = this.tracks.globalBounds;
		this.olMap.zoomToExtent(bounds);
	}
}