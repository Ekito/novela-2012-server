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

		this.context.lineWidth = $.constant.BRUSH_SIZE;
		this.context.strokeStyle = "rgba(" + $.constant.BRUSH_COLOR[0] + ", " + $.constant.BRUSH_COLOR[1] + ", " + $.constant.BRUSH_COLOR[2] + ", " + 0.05 * $.constant.BRUSH_PRESSURE + ")";

		if ($.constant.IS_SAFARI) {
			this.beginPath();
		}

		this.context.moveTo(this.prevMouseX, this.prevMouseY);
		this.context.lineTo(mouseX, mouseY);

		if ($.constant.IS_SAFARI) {
			this.context.stroke();
		}

		for (i = 0; i < this.points.length; i++)
		{
			dx = this.points[i][0] - this.points[this.count][0];
			dy = this.points[i][1] - this.points[this.count][1];
			d = dx * dx + dy * dy;

			if (d < 4000 && Math.random() > (d / 2000))
			{
				if ($.constant.IS_SAFARI) {
					this.context.beginPath();
				}

				this.context.moveTo( this.points[this.count][0] + (dx * 0.3), this.points[this.count][1] + (dy * 0.3));
				this.context.lineTo( this.points[i][0] - (dx * 0.3), this.points[i][1] - (dy * 0.3));

				if ($.constant.IS_SAFARI) {
					this.context.stroke();
				}
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
