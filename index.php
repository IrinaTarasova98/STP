<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="style.css">
  <link rel="shortcut icon" href="home-icon.png" type="image/png">
  <script type="text/javascript" src="jquery.js"></script>  
  <title>Умный дом</title>
</head>

<body class="page">

	<header class="page-header">
		<div class="container_head">
			<h1 class="page-title" align="center">План умного дома</h1>
		</div>
	</header>
	<main>

		<?php require_once "load.php";?>
	
		<section class="functional_place">	
			<div id="container">
				<div class="part"> <object type="image/svg+xml" data="home.svg" id="homesvg"> </object> </div>
				<div class="part">
					<table>
							<tr>
								<th>           </th>
								<th>Свет       </th>
								<th>Температура</th>
							</tr>
							
							<tr>
								<td>Прихожая       </td>
								<td>
									<output for="slider1" id="light0"><?php echo $lights[0]; ?> %</output><br>
									<input name="light[0]" type="range" id="slider1" min="0" max="100" value="<?=$lights[0]?>" step="1" oninput="outputLight(value, '#light0', '#d1XMTvuzz', '#a1q5hVfomy')">
								</td>
								<td>
									<output for="slider2" id="temp0"><?php echo $temper[0]; ?> °C</output><br>
									<input name="temper[0]" type="range" id="slider2" min="20" max="50" value="<?=$temper[0]?>" step="1" oninput="outputTemp(value, '#temp0', '#c2Kkv5l4K2')">
								</td>
							</tr>
							<tr>
								<td>Кухня          </td>
								<td>
									<output for="slider3" id="light1"><?php echo $lights[1]; ?> %</output><br>
									<input name="light[1]" type="range" id="slider3" min="0" max="100" value="<?=$lights[1]?>" step="1" oninput="outputLight(value, '#light1', '#d6A9rrWj1W', '#a2JYuLnd3N')">
								</td>
								<td>
									<output for="slider4" id="temp1"><?php echo $temper[1]; ?> °C</output><br>
									<input name="temper[1]" type="range" id="slider4" min="20" max="50" value="<?=$temper[1]?>" step="1" oninput="outputTemp(value, '#temp1', '#b1Q6Tb1kGC')">
								</td>
							</tr>
								
							<tr>
								<td>Ванная         </td>
								<td>
									<output for="slider5" id="light2"><?php echo $lights[2]; ?> %</output><br>
									<input name="light[2]" type="range" id="slider5" min="0" max="100" value="<?=$lights[2]?>" step="1" oninput="outputLight(value, '#light2', '#a4Un7R4Ic', '#clE4vAh4X')">
								</td>
								<td>
									<output for="slider6" id="temp2"><?php echo $temper[2]; ?> °C</output><br>
									<input name="temper[2]" type="range" id="slider6" min="20" max="50" value="<?=$temper[2]?>" step="1" oninput="outputTemp(value, '#temp2', '#b3UJEcGoza')">
								</td>
							</tr>
								
							<tr>
								<td>Комната        </td>
								<td>
									<output for="slider7" id="light3"><?php echo $lights[3]; ?> %</output><br>
									<input name="light[3]" type="range" id="slider7" min="0" max="100" value="<?=$lights[3]?>" step="1" oninput="outputLight(value, '#light3', '#ad8qocp7f', '#bd14lpn6i')">
								</td>
								<td>
									<output for="slider8" id="temp3"><?php echo $temper[3]; ?> °C</output><br>
									<input name="temper[3]" type="range" id="slider8" min="20" max="50" value="<?=$temper[3]?>" step="1" oninput="outputTemp(value, '#temp3', '#c5I1LTsLLc')">
								</td>
							</tr>
								
							<tr>
								<td>Балкон         </td>
								<td>
									<output for="slider9" id="light4"><?php echo $lights[4]; ?> %</output><br>
									<input name="light[4]" type="range" id="slider9" min="0" max="100" value="<?=$lights[4]?>" step="1" oninput="outputLight(value, '#light4', '#b1TURKYP0j', '#d5nn3nv3ZF')">
								</td>
								<td> 
									<input type="button" id="submit" value="Сохранить" onclick="saveForm()">
								</td>
							</tr>
					</table>
				</div>
			</div>
		</section>
  </main>

  <script src="home.js"></script>
  <script src="save.js"></script>
  
</body>
</html>
