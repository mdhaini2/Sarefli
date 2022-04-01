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
$htmlJson = json_decode($html, true);

// Get the latest buy rate
$buyRates = reset($htmlJson);
$lastBuyRate = end($buyRates);

// Get the latest sell rate
$sellRates = end($htmlJson);
$lastSellRate = end($sellRates);

// Return to the front end the buy and sell rates as json object
echo  json_encode($lastSellRate). json_encode($lastBuyRate);        


}
