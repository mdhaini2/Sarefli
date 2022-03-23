<?php
include("DB_info.php");

$Amount = floatval($_POST["Amount"]);
$Internal = $_POST["Internal"];
$External = $_POST["External"];
$Daily_Rate = floatval($_POST["Daily_Rate"]);
$Result =0;
date_default_timezone_set('Asia/Beirut');
$date = date('Y-m-d H:i:s');

if($Internal == "USD"){
    $Result = $Amount * $Daily_Rate;
}
else{
    $Result = $Amount / $Daily_Rate;

}
$query =   $mysqli->prepare("INSERT INTO conversions_history (amount,internal,external,daily_rate,result,time) VALUES('$Amount','$Internal',
'$External','$Daily_Rate','$Result','$date')"); 

$query->execute();


echo json_encode($Result);
?>