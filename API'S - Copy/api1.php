<?php
// Library to get built in functions  we used to get content from web api
include("simple_html_dom.php"); 

// If we get error 401 when requesting for accesss, we send an error request message.
if(!$html = @file_get_contents('file:///D:/Desktop/rates.json')) {
    $error = "Error";
    
    echo json_encode($error);
}
 // Else we fetch the latest sell and buy rates from the api.
else{
$html_json = json_decode($html, true);

// Get the latest buy rate
$Buy_rates = reset($html_json);
$Last_buy_rate = end($Buy_rates);

// Get the latest sell rate
$Sell_rates = end($html_json);
$Last_sell_rate = end($Sell_rates);

// Return to the front end the buy and sell rates as json object
echo  json_encode($Last_sell_rate). json_encode($Last_buy_rate);        


}
