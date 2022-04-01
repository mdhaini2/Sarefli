<?php
include("DB_info.php"); //connecting with the database 

$Amount = floatval($_GET["Amount"]); //get those data from the front end
$Internal = $_GET["Internal"];
$External = $_GET["External"];
$Daily_Rate_Buy = floatval($_GET["Daily_Rate_Buy"]);   
$Daily_Rate_Sell = floatval($_GET["Daily_Rate_Sell"]);

$Result_buy =0; //default result
$Result_sell =0;

date_default_timezone_set('Asia/Beirut'); //get date and time for each conversion
$date = date('Y-m-d H:i:s');

if($Internal == "USD"){ //converting the ammount to USD or LBP for the sell and buy rates
    $Result_buy = $Amount * $Daily_Rate_Buy;
    $Result_sell = $Amount * $Daily_Rate_Sell;
}
else{
    $Result_buy = $Amount / $Daily_Rate_Buy;
    $Result_sell = $Amount / $Daily_Rate_Sell;
    
}

$query =   $mysqli->prepare("INSERT INTO conversions_history (amount,internal,external,buy_daily_rate,sell_daily_rate,buy_result,sell_result,time) VALUES('$Amount','$Internal',
'$External','$Daily_Rate_Buy','$Daily_Rate_Sell','$Result_buy','$Result_sell','$date')"); //Storing the conversion to the database ("Conversions_history")

$query->execute(); //executing the query


echo json_encode($Result_buy)." ".json_encode($Result_sell); //returning the result to the front end as JSON
?>