<?php
$curl = curl_init(); 
curl_setopt($curl, CURLOPT_URL,'https://lirarate.org/');
curl_exec($curl);