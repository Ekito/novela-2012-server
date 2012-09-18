var map;

var initDocument = function() {
	map = new OpenLayers.Map('map');

	var wms = new OpenLayers.Layer.Image(
		"White layer",
		$("#map").data("bg"),
		map.maxExtent,
   		new OpenLayers.Size(1125,558),
		{isBaseLayer: true});

	//var wms = new OpenLayers.Layer.WMS( "OpenLayers WMS", "http://vmap0.tiles.osgeo.org/wms/vmap0", {layers: 'basic'} );


	map.addLayer(wms);

	/* SKETCHY PATH */
	
	var tracks = new Sketchytrack.Layer("SampleTrack");
		//console.log(stubs);
	for(var i in stubs) 
	{
		//console.log(stubs[i].length);
		//for(var i=0; i<100; i++) 
		{
	 		tracks.addTrack(stubs[i]);
	 	}
	 	map.addLayer(tracks);
	}

	/* HEATMAP EXAMPLE */
	//var heat = new Heatmap.Layer("Heatmap");
	//heat.addSource(new Heatmap.Source(
	//	new OpenLayers.LonLat(melaniePath[0].lat,melaniePath[0].lon),null,10000));
	//heat.addSource(new Heatmap.Source(
	//	new OpenLayers.LonLat(melaniePath[1].lat,melaniePath[1].lon)));
	//map.addLayer(heat);

	/* SIMPLE PATH */
	//for(var i=0; i<100; i++) 
	{
		//displaySimplePath(map,melaniePath);
	}


	map.setCenter([stubs[0][0].lon,stubs[0][0].lat],15);
	//map.zoomToMaxExtent();

};

var displaySimplePath = function(map,jsonpoints) {
	var lineLayer = new OpenLayers.Layer.Vector("Line Layer"); 

	map.addLayer(lineLayer);                    
	map.addControl(new OpenLayers.Control.DrawFeature(lineLayer, OpenLayers.Handler.Path));                                     
	var points = new Array();

	for(var i in jsonpoints) {
		points.push(new OpenLayers.Geometry.Point(jsonpoints[i].lat,jsonpoints[i].lon));
	}
	

	var line = new OpenLayers.Geometry.LineString(points);

	var style = { 
	  strokeColor: '#000000', 
	  strokeOpacity: 0.5,
	  strokeWidth: 1
	};

	var lineFeature = new OpenLayers.Feature.Vector(line, null, style);
	lineLayer.addFeatures([lineFeature]);
};

$(initDocument);