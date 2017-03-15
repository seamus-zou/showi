/**
 * 
 */

function sm(d, e, f) {
	f = f || 0;
	var g = '', x = +e.slice(-1), e = e.slice(x, e.length);
	var a, b, c;
	if (!f) {
		a = 13, b = 12, c = 14
	} else {
		a = 12, b = 11, c = 13
	}
	for (var i = 0, len = d.length; i < len; i++) {
		var h = e.indexOf(d[i]);
		if (h === a) {
			g += '.'
		} else if (h === b || h === c) {
			g += '0'
		} else {
			g += h
		}
	}
	return g
}