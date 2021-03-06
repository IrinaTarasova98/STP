function saveForm(){
	var lights = [];
	var temper = [];
	
	lights[0] = document.getElementById('slider1').value;
	lights[1] = document.getElementById('slider3').value;
	lights[2] = document.getElementById('slider5').value;
	lights[3] = document.getElementById('slider7').value;
	lights[4] = document.getElementById('slider9').value;
	
	temper[0] = document.getElementById('slider2').value;
	temper[1] = document.getElementById('slider4').value;
	temper[2] = document.getElementById('slider6').value;
	temper[3] = document.getElementById('slider8').value;
	
	$.ajax({
		url:'save.php',
		type: "POST",
		dataType: "html",
		data: { arrLights: lights,
				arrTemper: temper }
	})
}