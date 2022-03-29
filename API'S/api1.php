<?php
//library to get built in functions  we used to get content from web api
include("simple_html_dom.php"); 

// if we get error 401 when requesting for accesss, it will keep refreshing until it get away
while (!$html = @file_get_contents('https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP')) {
    header("refresh");
}

$html_json = json_decode($html, true);

//get the latest buy rate
$Buy_rates = reset($html_json);
$Last_buy_rate = end($Buy_rates);

//get the latest sell rate
$Sell_rates = end($html_json);
$Last_sale_rate = end($Sell_rates);

//return to the front end the buy and sell rates as json object
echo "Sell: " . json_encode($Last_sale_rate);
echo " Buy: " . json_encode($Last_buy_rate);

?>