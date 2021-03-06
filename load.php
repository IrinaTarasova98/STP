<?php 
	/* создание массивов */
	$lights = array(0, 0, 0, 0, 0);
	$temper = array(20, 20, 20, 20);

	/* загрузка сохраненных значений */
	$file = file("values.txt");
	for ($i = 0; $i < 5; $i++) {
		$lights[$i] = intval($file[$i]);
		if ($lights[$i] > 100) $lights[$i] = 100;
		if ($lights[$i] < 0) $lights[$i] = 0;
	}
	for ($i = 0; $i < 4; $i++) {
		$temper[$i] = intval($file[$i + 5]);
		if ($temper[$i] > 50) $temper[$i] = 50;
		if ($temper[$i] < 20) $temper[$i] = 20;
	}
?>
