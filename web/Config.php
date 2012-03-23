<?php
$server = "localhost";
$userdb = "root";
$passdb = "";
$namedb = "db_vimaru_mobile"; 
$link = mysql_connect($server,$userdb,$passdb) or die ("Kết nối không thành công!");
mysql_select_db($namedb,$link) or die ("Không tìm thấy CSDL ".$namedb);
mysql_query(" SET NAMES 'utf8'",$link);
?>
