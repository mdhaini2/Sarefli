<?php
include("DB_info.php"); //connecting with the database 

$Amount = floatval($_POST["Amount"]); //get those data from the front end
$Internal = $_POST["Internal"];
$External = $_POST["External"];
$Daily_Rate = floatval($_POST["Daily_Rate"]);

$Result =0; //default result

date_default_timezone_set('Asia/Beirut'); //get date and time for each conversion
$date = date('Y-m-d H:i:s');

if($Internal == "USD"){ //converting the ammount to USD or LBP
    $Result = $Amount * $Daily_Rate;
}
else{
    $Result = $Amount / $Daily_Rate;

}

$query =   $mysqli->prepare("INSERT INTO conversions_history (amount,internal,external,daily_rate,result,time) VALUES('$Amount','$Internal',
'$External','$Daily_Rate','$Result','$date')"); //Storing the conversion to the database ("Conversions_history")

$query->execute(); //executing the query


echo json_encode($Result); //returning the result to the front end as JSON
?>