<?php
// Library to get built in functions we used to get content from web API
include("simple_html_dom.php"); 

// Send error request message incase of error 401
if(!$html = @file_get_contents('https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP')) {
    $error = "Error";
    echo json_encode($error);
}
 // Else fetch the latest sell and buy rates from the API
else{

$htmlJson = json_decode($html, true);

// Get the latest buy rate
$buyRates = reset($htmlJson);
$lastBuyRate = end($buyRates);

// Get the latest sell rate
$sellRates["Sell"] = end($htmlJson);
$lastSellRate["Buy"] = end($sellRates);

$ratesValues = (object) [
    'Buy' => end($lastBuyRate),
    'Sell' => end($lastSellRate)
];

// Return the buy and sell rates as JSON objects
echo  json_encode($ratesValues);       
}

