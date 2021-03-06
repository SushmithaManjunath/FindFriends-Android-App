<?php

include "config.php";

$input = json_decode(file_get_contents('php://input'), true);
$fullname = $input["full_name"];
$email = $input["email"];
$password = $input["password"];
echo $fullname;
$latitude = floatval(0);
$longitude = floatval(0);

// Create connection to database
$conn = new mysqli(dbservername, dbusername, dbpassword, dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$email = $conn->real_escape_string($email);
$password = $conn->real_escape_string($password);


//check for an existing table users
$query = "SELECT email from users where email = '$email'";
$result = $conn->query($query);
if (!$result || $result->num_rows == 0){
	$query = "INSERT INTO users(email, full_name, password, last_active_time, latitude, longitude, token) 
				VALUES ('$email', '$fullname', '$password', now(), $latitude, $longitude, '')";
	$result = $conn->query($query);
	if (!$result){
		echo "\tError while creating user : " . $conn->error . "\n";
	} else{
		echo "\tUser created successfully! \n";
	}
} else{
	echo "\tEmail already exists! \n";
}

$conn->close();
?>