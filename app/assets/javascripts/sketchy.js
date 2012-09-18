function sketchy( context )
{
	this.init( context );
}

sketchy.prototype =
{
	context: null,

	prevMouseX: null, prevMouseY: null,

	points: null, count: null,

	init: function( context )
	{
		//console.log("init");
		this.context = context;
		this.context.globalCompositeOperation = 'source-over';

		this.points = new Array();
		this.count = 0;
	},

	destroy: function()
	{
	},

	beginPath: function() {
		this.context.beginPath();
	},

	moveTo: function( mouseX, mouseY )
	{
		//console.log("strokeStart "+mouseX+" "+mouseY);
		this.prevMouseX = mouseX;
		this.prevMouseY = mouseY;
	},

	lineTo: function( mouseX, mouseY )
	{
		//console.log("stroke "+mouseX+" "+mouseY);
		var i, dx, dy, d;

		this.points.push( [ mouseX, mouseY ] );

		this.context.lineWidth = BRUSH_SIZE;
		this.context.strokeStyle = "rgba(" + COLOR[0] + ", " + COLOR[1] + ", " + COLOR[2] + ", " + 0.05 * BRUSH_PRESSURE + ")";

		this.context.moveTo(this.prevMouseX, this.prevMouseY);
		this.context.lineTo(mouseX, mouseY);

		for (i = 0; i < this.points.length; i++)
		{
			dx = this.points[i][0] - this.points[this.count][0];
			dy = this.points[i][1] - this.points[this.count][1];
			d = dx * dx + dy * dy;

			if (d < 4000 && Math.random() > (d / 2000))
			{
				this.context.moveTo( this.points[this.count][0] + (dx * 0.3), this.points[this.count][1] + (dy * 0.3));
				this.context.lineTo( this.points[i][0] - (dx * 0.3), this.points[i][1] - (dy * 0.3));
			}
		}

		this.prevMouseX = mouseX;
		this.prevMouseY = mouseY;

		this.count ++;
	},

	stroke: function() {
		this.context.stroke();
	},

	clearPoints: function()
	{
		if (this.points) {
			this.points.length = 0;
			this.count = 0;
		}
	}
}
