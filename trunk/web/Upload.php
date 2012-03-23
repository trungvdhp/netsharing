<?php
/*
 	$cur_image=$_FILES['image']['name']; // đặt biến cur_image bằng tên file người dùng chọn .
    $ext_img = substr(strrchr($cur_image, '.'), 1);  //phân tích cắt chuỗi tên file để xác định kiểu file   
    if (($ext_img!= "jpg") && ($extension != "jpeg") && ($ext_img != "gif") && ($ext_img != "png"))  {
    //kiểm tra file nếu phần mở rộng không phải là các dạng trên thì...
    	die('Định dạng không hợp lệ'); //...thì hiện thông báo lỗi
    }
 * */

if (basename($_FILES['uploadedfile']['name']) != "") {
	$ThuMucAnh = "photo/" . basename($_FILES['uploadedfile']['name']);
	if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $ThuMucAnh)) 
        echo "true";
	 else
        echo "false";
    
} else 
	echo "<h1 style=\"font-family: 'Times New Roman';\">Not Found</h1>
			<p style=\"font-family: 'Times New Roman'; font-size: medium;\">The requested URL was not found on this server.</p>
			<hr />
			<address style=\"font-family: 'Times New Roman'; font-size: medium;\">Apache/2.2.21 (CentOS) Server</address>";

?>
