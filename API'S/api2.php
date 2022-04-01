<?php

// Connecting to the database 
include("DB_info.php");

// Get data from the front end
$amount = floatval($_GET["Amount"]);
$internal = $_GET["Internal"];
$external = $_GET["External"];
$dailyRateBuy = floatval($_GET["Daily_Rate_Buy"]);   
$dailyRateSell = floatval($_GET["Daily_Rate_Sell"]);

// Initial result 
$resultBuy =0; 
$resultSell =0;

// Get date and time for each conversion
date_default_timezone_set('Asia/Beirut'); 
$date = date('Y-m-d H:i:s');

// Convert Buy and Sell rates from USD to LBP and vice versa  
if($internal == "USD"){ 
    $resultBuy = $amount * $dailyRateBuy;
    $resultSell = $amount * $dailyRateSell;
}
else{
    $resultBuy = $amount / $dailyRateBuy;
    $resultSell = $amount / $dailyRateSell;
    $resultBuy = round($resultBuy, 3);
    $resultSell = round($resultSell, 3);
    
}
// query to store the conversion to the database ("Conversions_history")
$query =   $mysqli->prepare("INSERT INTO conversions_history (amount,internal,external,buy_daily_rate,sell_daily_rate,buy_result,sell_result,time) VALUES('$amount','$internal',
'$external','$dailyRateBuy','$dailyRateSell','$resultBuy','$resultSell','$date')"); 

//  Executing the query
$query->execute(); 

 // return the buy and sell result rates to the front end as JSON object
echo json_encode($resultBuy)." ".json_encode($resultSell);
?>