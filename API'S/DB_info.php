<?php
$db_host = "localhost";
$db_user = "mdhaini2";
$db_pass = "ldr_0Fe_kVC(.L8-";
$db_name = "CURRENCY_CONVERTER";

$mysqli= new mysqli($db_host,$db_user,$db_pass,$db_name);
if(mysqli_connect_errno()){
    die("Connection failed");   
}
?>