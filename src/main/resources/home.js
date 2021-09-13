function outputTemp(vol, idt, idsvg) {
	let output = document.querySelector(idt);
	/* меняем значение над шкалой */
	output.value = vol + '°C';
	document.getElementById("homesvg").contentDocument.getElementById("idt").setAttribute("value", vol);
	
	/* должен поменяться текст на кртинке */
	let txt = document.getElementById('homesvg');
	/* меняем значение на картинке */
	txt.value = '💡: ' + strval(vol) + '°C';
}

function outputLight(vol, idl, idsvg, picsvg) {
	let output = document.querySelector(idl);
	output.value = vol + '%';
	
	/* должна измениться прозрачность заливки */
	let svgobj = document.getElementById('homesvg');
    svgdom = jQuery(svgobj.contentDocument); 
    jQuery(picsvg, svgdom).attr("fill-opacity", vol / 100); 
	
	/* должно меняться значение на кртинке */
	let txt = document.getElementById(idsvg);
	/* меняем значение на картинке */
	txt.value = 't' + strval(vol) + '%';
}
