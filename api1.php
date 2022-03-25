<?php 
    include("simple_html_dom.php"); //library to get built in functions that we used to get content from website 
    
        
            if($html= file_get_contents("https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP")){//getting the content from api rate that lirarate.org use 
                
                $html_json = json_decode($html,true); // convert $html from type String to json
    
                echo json_encode($html_json);   //return the rates to the front end as json
            } 
            else{ //if we get an error for requesting we will return to the front-end an error message
                echo json_encode("Request Error");
            }
         
        
       
    

   
    ?>