<?php 
	/* сохранение данных */
	
		$arrLights = $_POST['arrLights'];
		$arrTemper = $_POST['arrTemper'];
		
	($arrLights);
	
	$f = fopen('values.txt', 'w');
	for ($i = 0; $i < 5; $i++) {
		fwrite($f, $arrLights[$i]."\r\n");
	}
	for ($i = 0; $i < 4; $i++) {
		fwrite($f, $arrTemper[$i]."\r\n");
	}
		
	fclose($f);
	header('Location: index.php');
?>