Sketchytrack = {};

var BRUSH_SIZE = 1,
    BRUSH_PRESSURE = 2,
    COLOR = [0, 0, 0];

Sketchytrack.Layer = OpenLayers.Class(OpenLayers.Layer, {

	brush: null,

	canvas: null,

	tracks: null,

	tracksBounds: null,

	globalBounds: null,

	interpolatedPoints: null,

	initialize: function(name, options) {
		OpenLayers.Layer.prototype.initialize.apply(this, arguments);
		this.canvas = document.createElement('canvas');
		this.canvas.style.position = 'absolute';
		this.brush = new sketchy(this.canvas.getContext('2d'));

		this.tracks = [];
		this.tracksBounds = [];
		this.globalBounds = new OpenLayers.Bounds();
		this.interpolatedPoints = [];

	    // For some reason OpenLayers.Layer.setOpacity assumes there is
	    // an additional div between the layer's div and its contents.
	    var sub = document.createElement('div');
	    sub.appendChild(this.canvas);
	    this.div.appendChild(sub);
	},

	addTrack: function(track) {
		var localBounds = new OpenLayers.Bounds();
		var point = new OpenLayers.Geometry.Point();
		
		for (i in track) {
			
			point.x = track[i].lon;
			point.y = track[i].lat;
			
			localBounds.extend(point);		
			this.globalBounds.extend(point);
		}
		this.tracks.push(track);
		this.tracksBounds.push(localBounds);
	},

	moveTo: function(bounds, zoomChanged) {

		OpenLayers.Layer.prototype.moveTo.apply(this, arguments);

		if (!bounds.intersectsBounds(this.globalBounds)) 
			return;

	    // Pick some point on the map and use it to determine the offset
	    // between the map's 0,0 coordinate and the layer's 0,0 position.
	    var someLoc = new OpenLayers.LonLat(0,0);
	    var offsetX = this.map.getViewPortPxFromLonLat(someLoc).x -
	    this.map.getLayerPxFromLonLat(someLoc).x;
	    var offsetY = this.map.getViewPortPxFromLonLat(someLoc).y -
	    this.map.getLayerPxFromLonLat(someLoc).y;

	    this.canvas.width = this.map.getSize().w;
	    this.canvas.height = this.map.getSize().h;

	    var ctx = this.canvas.getContext('2d');

	    ctx.save(); // Workaround for a bug in Google Chrome
	    ctx.fillStyle = 'transparent';
	    ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
	    ctx.restore();

	    var track, pos;
	    var lonlat = new OpenLayers.LonLat();

	    var start;

	    console.log(this.tracks);

		this.brush.beginPath();
	    for (var i in this.tracks) {

	    	start = new Date().getMilliseconds();

	    	if (!bounds.intersectsBounds(this.tracksBounds[i])) 
	    		continue;

	    	track = this.tracks[i];

		    for (var j in track) {

		    	lonlat.lon = track[j].lon;
		    	lonlat.lat = track[j].lat;
		    	pos = this.map.getLayerPxFromLonLat(lonlat);
		    	
		    	if (j > 0) {
			    	this.brush.lineTo(pos.x + offsetX, pos.y + offsetY);
			    } else {
			    	this.brush.moveTo(pos.x + offsetX, pos.y + offsetY);
			    }
		    }
	    	
	    	// call this for optimization
	    	// this.brush.clearPoints();

			console.log(new Date().getMilliseconds()-start);
		}

		this.brush.stroke();

		this.brush.clearPoints();

	    // Unfortunately OpenLayers does not currently support layers that
	    // remain in a fixed position with respect to the screen location
	    // of the base layer, so this puts this layer manually back into
	    // that position using one point's offset as determined earlier.
	    this.canvas.style.left = (-offsetX) + 'px';
	    this.canvas.style.top = (-offsetY) + 'px';
	},

	CLASS_NAME: 'Sketchytrack.Layer'

});