<?php 
    include("simple_html_dom.php");
    $html= file_get_html("https://lirarate.org");
    $title = $html->find("//[@id='site-content']");
    $name = $title[0]->innertext;
    echo $name;
    ?>