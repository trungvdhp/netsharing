<?php 
include 'Config.php';
include 'SimpleImage.php';
/*
$server = "localhost";
$userdb = "cnt48dhnet";
$passdb = "matkhaubimat!(*(00";
$namedb = "cnt48dhnet_VM"; 
$link = mysql_connect($server,$userdb,$passdb) or die ("Kết nối không thành công!");
mysql_select_db($namedb,$link) or die ("Không tìm thấy CSDL ".$namedb);
*/
$Case = $_REQUEST["CVM"];
$false = "false";
$true = "true";
$null = "null";
$KyTuChiaBanGhi = "@$%&";
$KyTuChiaTruongDL = "||||";
$ThuMucAnhGoc = "photo/";
$ThuMucAnhGocAvatar = "avatar/";
$AvatarMobile = "mobile/avatarM/";
$PhotoMobile = "mobile/photoM/";
$ChiTietAnh = "chitietanh/";
$AlbumChiaSe = "albumchiase/";
$WidthPhoto = 48;
$WidthAvatar = 40;
function get_user_info($user_id)
{
	$sql = "SELECT * FROM taikhoan WHERE mataikhoan = '".$user_id."'";
	$result = mysql_query($sql);
	$row=mysql_fetch_array($result);
	return $row;
}
do{
$re = false;
switch ($Case){
	case "DangKyTaiKhoan": {
		$TaiKhoan = $_REQUEST["xTaiKhoan"];
		$MatKhau = $_REQUEST["xMatKhau"];
		if ($TaiKhoan != "" && $MatKhau != "") {
			$sqlTaiKhoan = "SELECT MaTaiKhoan FROM taikhoan WHERE TaiKhoan = '".$TaiKhoan."'";
			$resultTaiKhoan = mysql_query($sqlTaiKhoan);
			if (mysql_num_rows($resultTaiKhoan) == 0) {
				$sqlInsert = "";
				
				$sqlMaTaiKhoan = "SELECT MaTaiKhoan FROM taikhoan WHERE MaTaiKhoan LIKE 'TKTD%' ORDER BY MaTaiKhoan DESC LIMIT 0,1";
				$resultMaTaiKhoan = mysql_query($sqlMaTaiKhoan) or die("Lệnh truy vấn không chính xác!");
				if (mysql_num_rows($resultMaTaiKhoan) == 1) {
					$MaTaiKhoanMoi = 0;
					$rowMaTaiKhoan = mysql_fetch_array($resultMaTaiKhoan);
					$MaTaiKhoan = substr($rowMaTaiKhoan["MaTaiKhoan"], 4);
					$MaTaiKhoan = $MaTaiKhoan + 1;
					$MaTaiKhoan = "TKTD".$MaTaiKhoan;
					$sqlInsert = "INSERT INTO taikhoan(MaTaiKhoan, TaiKhoan, MatKhau) VALUES ('".$MaTaiKhoan."', '".$TaiKhoan."', '".md5($MatKhau)."')";
				} else {
					$sqlInsert = "INSERT INTO taikhoan(MaTaiKhoan, TaiKhoan, MatKhau) VALUES ('TKTD0', '".$TaiKhoan."', '".md5($MatKhau)."')";
				}
				if ($sqlInsert != "") {
					$resultInsert = mysql_query($sqlInsert) or die("Lệnh truy vấn không chính xác!");
					if ($resultInsert != null) {
						echo $true;
					} else 
						echo $false . "1";
				} else 
					echo $false ."2";
			} else 
				echo "TonTai";
		} else 
			echo $false."3";
		break;
	}
	case "DangNhap": {
		$TaiKhoan = $_REQUEST["xTaiKhoan"];
		$MatKhau = $_REQUEST["xMatKhau"];
		if($TaiKhoan != "" && $MatKhau != "") {
			$sql = "SELECT MaTaiKhoan, TaiKhoan, MatKhau FROM taikhoan WHERE TaiKhoan='".$TaiKhoan."'";
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			$row = mysql_fetch_array($result);
			if($row != null) {
				if (md5($MatKhau) == $row["MatKhau"]) 
					echo $row["MaTaiKhoan"];
				else 
					echo $false;
			}else 
				echo $false;
		}else
			echo $false;
		break;
	}
	case "TaoBaiVietNhom": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$TieuDe = $_REQUEST["xTieuDe"];
		$NoiDung = $_REQUEST["xNoiDung"];
		$MaNhom=$_REQUEST['xMaNhom'];
		if($MaTaiKhoan != "" && $TieuDe != "" && $NoiDung != "") {
			$TieuDe =  addslashes(str_replace("$", "\n", str_replace("|", " ", $TieuDe)));
			$NoiDung =  addslashes(str_replace("$", "\n", str_replace("|", " ", $NoiDung)));
			$sqlInsert = "INSERT INTO baiviet(TieuDe, NoiDung, MaTaiKhoan) VALUES ('".$TieuDe."', '".$NoiDung."', '".$MaTaiKhoan."')";
			$result = mysql_query($sqlInsert) or die("Lệnh truy vấn không chính xác!");
			if($result != null) {
				$sqlSelect = "SELECT MaBaiViet FROM baiviet WHERE MaTaiKhoan = '".$MaTaiKhoan."' ORDER BY MaBaiViet DESC LIMIT 0,1";
				$resultSelect = mysql_query($sqlSelect) or die("Lệnh truy vấn không chính xác!");
				$row = mysql_fetch_array($resultSelect);
				if ($row != null){
					echo $row["MaBaiViet"].$KyTuChiaTruongDL;
					$_REQUEST['xMaBaiViet']=$row["MaBaiViet"];
					$Case="ChiaSeBaiVietNhom";
					$re=true;
				}
				else 
					echo $false;
			}else 
				echo $false;
		}else 
			echo $false;
		break;
	}
	case "TaoBaiVietCacNhom": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$TieuDe = $_REQUEST["xTieuDe"];
		$NoiDung = $_REQUEST["xNoiDung"];
		$MaNhom=$_REQUEST['xMaNhom'];
		if($MaTaiKhoan != "" && $TieuDe != "" && $NoiDung != "") {
			$TieuDe =  addslashes(str_replace("$", "\n", str_replace("|", " ", $TieuDe)));
			$NoiDung =  addslashes(str_replace("$", "\n", str_replace("|", " ", $NoiDung)));
			$sqlInsert = "INSERT INTO baiviet(TieuDe, NoiDung, MaTaiKhoan) VALUES ('".$TieuDe."', '".$NoiDung."', '".$MaTaiKhoan."')";
			$result = mysql_query($sqlInsert) or die("Lệnh truy vấn không chính xác!");
			if($result != null) {
				$sqlSelect = "SELECT MaBaiViet FROM baiviet WHERE MaTaiKhoan = '".$MaTaiKhoan."' ORDER BY MaBaiViet DESC LIMIT 0,1";
				$resultSelect = mysql_query($sqlSelect) or die("Lệnh truy vấn không chính xác!");
				$row = mysql_fetch_array($resultSelect);
				if ($row != null){
					echo $row["MaBaiViet"].$KyTuChiaTruongDL;
					if($MaNhom != "null"){
						$_REQUEST['xMaBaiViet']=$row["MaBaiViet"];
						$Case="ChiaSeBaiVietCacNhom";
						$re=true;
					}
					else
						echo $true;
				}
				else 
					echo $false;
			}else 
				echo $false;
		}else 
			echo $false;
		break;
	}
	case "ChiaSeBaiVietNhom": {
		$MaNhom=$_REQUEST['xMaNhom'];
		$MaBaiViet=$_REQUEST['xMaBaiViet'];
		$MaTaiKhoan = $_REQUEST['xMaTaiKhoan'];
		$sql="INSERT INTO baiviet_nhom(MaTaiKhoan,MaBaiViet,MaNhom,TrangThai) VALUES('$MaTaiKhoan',$MaBaiViet,'$MaNhom',1)";
		$result=mysql_query($sql) or die("Truy vấn ko chính xác!");
		$sql="SELECT MaBaiViet_Nhom FROM baiviet_nhom ORDER BY MaBaiViet_Nhom DESC LIMIT 0,1";
		$result = mysql_query($sql);
		$row=mysql_fetch_array($result);
		$MaBaiViet_Nhom=$row['MaBaiViet_Nhom'];
		$sql="SELECT MaTaiKhoan FROM taikhoan_nhom WHERE MaNhom = '".$MaNhom."'";
		$result=mysql_query($sql);
		while($row=mysql_fetch_array($result))
		{
			if($row["MaTaiKhoan"] != $MaTaiKhoan)
			{
				$sql1="INSERT INTO baivietmoi(MaTaiKhoan, MaBaiViet_Nhom) VALUES('".$row["MaTaiKhoan"]."', $MaBaiViet_Nhom)";
				$result1=mysql_query($sql1);
			}
		}
		echo $true;
		break;
	}
	case "ChiaSeBaiVietCacNhom": {
		$MaTaiKhoan = $_REQUEST['xMaTaiKhoan'];
		$MaBaiViet=$_REQUEST['xMaBaiViet'];
		$DSMaNhom = explode("|", $_REQUEST['xMaNhom']);
		$len = count($DSMaNhom);
		for($i=0; $i<$len; $i++)
		{
			$MaNhom=$DSMaNhom[$i];
			$sql="INSERT INTO baiviet_nhom(MaTaiKhoan,MaBaiViet,MaNhom,TrangThai) VALUES('$MaTaiKhoan',$MaBaiViet,'$MaNhom',1)";
			$result=mysql_query($sql) or die("Truy vấn không chính xác!");
			$sql="SELECT MaBaiViet_Nhom FROM baiviet_nhom ORDER BY MaBaiViet_Nhom DESC LIMIT 0,1";
			$result = mysql_query($sql);
			$row=mysql_fetch_array($result);
			$MaBaiViet_Nhom=$row['MaBaiViet_Nhom'];
			$sql="SELECT MaTaiKhoan FROM taikhoan_nhom WHERE MaNhom='$MaNhom'";
			$result=mysql_query($sql);
			while($row=mysql_fetch_array($result))
			{
				if($row['MaTaiKhoan']!=$MaTaiKhoan)
				{
					$sql1="INSERT INTO baivietmoi (MaTaiKhoan, MaBaiViet_Nhom) VALUES('".$row['MaTaiKhoan']."', $MaBaiViet_Nhom)";
					$result1=mysql_query($sql1);
				}
			}
		}
		echo $true;
		break;
	}
	case "SuaBaiViet": {
		$MaBaiViet = $_REQUEST["xMaBaiViet"];
		$TieuDe = $_REQUEST["xTieuDe"];
		$NoiDung = $_REQUEST["xNoiDung"];
		if($MaBaiViet != "" && $TieuDe != "" && $NoiDung != "") {
			$TieuDe =  addslashes(str_replace("$", "\n", str_replace("|", " ", $TieuDe)));
			$NoiDung =  addslashes(str_replace("$", "\n", str_replace("|", " ", $NoiDung)));
			$sql = "UPDATE baiviet SET TieuDe = '".$TieuDe."', ";
			$sql .= "NoiDung = '".$NoiDung."' ";
			$sql .= "WHERE MaBaiViet = '".$MaBaiViet."'";
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			echo ($result != null) ? $true : $false;
        } else
            echo $false;
        break;
	}
	case "SuaBinhLuan": {
		$MaBinhLuan = $_REQUEST["xMaBinhLuan"];
		$NoiDung = $_REQUEST["xNoiDung"];
		if($MaBinhLuan != "" && $NoiDung != "") {
			$NoiDung =  addslashes(str_replace("$", "\n", str_replace("|", " ", $NoiDung)));
			$sql = "UPDATE binhluanbaiviet SET NoiDung = '".$NoiDung."' WHERE MaBinhLuan = '".$MaBinhLuan."'";
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			echo ($result != null) ? $true : $false;
        } else
            echo $false;
        break;
	}
	case "XoaBinhLuan": {
		$MaBinhLuan = $_REQUEST["xMaBinhLuan"];
		if($MaBinhLuan != "") {
			$sql = "DELETE FROM binhluanbaiviet WHERE MaBinhLuan = '".$MaBinhLuan."'";
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			echo ($result != null) ? $true : $false;
        } else
            echo $false;
        break;
	}
	case "TaoAnh": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$TenFile = $_REQUEST["xTenFile"];
		//$MoTa = $_REQUEST["xMoTa"];
		$Random = $_REQUEST["xRandom"];
		if ($MaTaiKhoan != "" && $TenFile != "" && $Random != "") {
			$ThuMucAnh = $ThuMucAnhGoc . $Random . $TenFile;
			$sqlInsert = "INSERT INTO anh(Link, MaTaiKhoan) VALUES('".$ThuMucAnh."', '".$MaTaiKhoan."')";
			$result = mysql_query($sqlInsert) or die("Lệnh truy vấn không chính xác!");
			if ($result != null) {
				$image = new SimpleImage();
				$image->load($ThuMucAnh);
				if ($image->getWidth() > $WidthPhoto) {
					$image->resizeToWidth($WidthPhoto);
					$NewLink = $PhotoMobile . $Random . $TenFile;
					$image->save($NewLink);
				}
				
				$sqlSelect = "SELECT MaAnh FROM anh WHERE MaTaiKhoan = '".$MaTaiKhoan."' ORDER BY MaAnh DESC LIMIT 0,1";
				$resultSelect = mysql_query($sqlSelect) or die("Lệnh truy vấn không chính xác!");
				if (mysql_num_rows($resultSelect) > 0) {
					$rowSelect = mysql_fetch_array($resultSelect);
					echo $rowSelect["MaAnh"];
				} else
					echo $false;
			}
			else 
				echo $false;
		} else 	
			echo $false;
		break;
	}
	case "ChiaSeAnh": {
		$CountNhom = $_REQUEST["CountNhom"];
		$CountAnh = $_REQUEST["CountAnh"];
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$Check = true;
		if ($CountNhom != "" && $MaTaiKhoan != "" && $CountAnh > 0 && $CountNhom > 0) {
			for ($i = 0; $i < $CountNhom; $i++) {
				$MaNhom = $_REQUEST["CMN".$i]; // CMN = Client Mã Nhóm
				
				// TẠO ALBUM CHIA SẺ
				$sqlAlbumCS = "INSERT INTO albumchiase(MaTaiKhoan, MaNhom, TenAlbum) VALUES('".$MaTaiKhoan."', '".$MaNhom."', '')";
				$resultAlbumCS = mysql_query($sqlAlbumCS) or die("Lệnh truy vấn không chính xác!");
				if ($resultAlbumCS != null) {
					
					// Lấy về MaAlbumChiaSe vừa tạo
					$sqlSelectAlbumCS = "SELECT MaAlbumChiaSe FROM albumchiase 
								WHERE MaTaiKhoan = '".$MaTaiKhoan."' 
								ORDER BY MaAlbumChiaSe DESC LIMIT 0,1";
					$resultSelectAlbumCS = mysql_query($sqlSelectAlbumCS) or die("Lệnh truy vấn không chính xác!");
					if (mysql_num_rows($resultSelectAlbumCS) > 0) {
						$rowSelectAlbumCS = mysql_fetch_array($resultSelectAlbumCS); // Mã Album chia sẻ
						
						// TẠO ANH_NHOM: Nếu có nhiều ảnh thì for() ở đây
						for ($j = 0; $j < $CountAnh; $j++) {
							$MaAnh = $_REQUEST["xMaAnh".$j];
							$sqlAnh_Nhom = "INSERT INTO anh_nhom(MaTaiKhoan, MaNhom, MaAnh, MaAlbumChiaSe) VALUES('".$MaTaiKhoan."', '".$MaNhom."', '".$MaAnh."', '".$rowSelectAlbumCS["MaAlbumChiaSe"]."')";
							$resultAnh_Nhom = mysql_query($sqlAnh_Nhom) or die("Lệnh truy vấn không chính xác!");
							if ($resultAnh_Nhom == null)
								$Check = false;
						}
					} else 
						$Check = false;
				}else 
					$Check = false;
			}
			if ($Check)
				echo $true;
			else 
				echo $false;
		} else
			echo $false;
		break;
	}
	case "NhomBanLaThanhVien": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		if ($MaTaiKhoan != "") {
			$sqlTaiKhoan_Nhom = "SELECT tkn.MaNhom, n.TenNhom, n.MaTaiKhoan, tk.TaiKhoan FROM (SELECT MaNhom FROM taikhoan_nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."' and TrangThai = 1) tkn INNER JOIN nhom n ON n.MaNhom = tkn.MaNhom INNER JOIN taikhoan tk ON tk.MaTaiKhoan = n.MaTaiKhoan";
;
			$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
			while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
				echo $rowTaiKhoan_Nhom["MaNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TenNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TaiKhoan"]. $KyTuChiaTruongDL
					;
			}
		}else
			echo $false;
		break;
	}
	case "NhomBanTao": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		if ($MaTaiKhoan != "") {
			$sqlTaiKhoan_Nhom = "SELECT n.MaNhom, n.TenNhom, n.MaTaiKhoan, tk.TaiKhoan FROM nhom n INNER JOIN taikhoan tk ON tk.MaTaiKhoan = n.MaTaiKhoan WHERE tk.MaTaiKhoan = '".$MaTaiKhoan."'";
;
			$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
			while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
				echo $rowTaiKhoan_Nhom["MaNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TenNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TaiKhoan"]. $KyTuChiaTruongDL
					;
			}
		}else
			echo $false;
		break;
	}
	case "NhomBanThamGia": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		if ($MaTaiKhoan != "") {
			$sqlTaiKhoan_Nhom = "SELECT tkn.MaNhom, n.TenNhom, n.MaTaiKhoan, tk.TaiKhoan FROM (SELECT MaNhom FROM taikhoan_nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."' and TrangThai = 1 and MaNhom NOT IN(SELECT MaNhom FROM nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."'))tkn INNER JOIN nhom n ON n.MaNhom = tkn.MaNhom INNER JOIN taikhoan tk ON tk.MaTaiKhoan = n.MaTaiKhoan";
;
			$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
			while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
				echo $rowTaiKhoan_Nhom["MaNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TenNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TaiKhoan"]. $KyTuChiaTruongDL
					;
			}
		}else
			echo $false;
		break;
	}
	case "KhoBaiViet": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		if ($MaTaiKhoan != "") {
			$sql = "SELECT MaBaiViet, TieuDe, NoiDung, NgayTao FROM baiviet 
					WHERE MaTaiKhoan = '".$MaTaiKhoan."' 
					ORDER BY MaBaiViet DESC";
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($result) > 0) {
				while ($row = mysql_fetch_array($result)) {
					echo $row["MaBaiViet"] . $KyTuChiaTruongDL 
						. $row["TieuDe"] . $KyTuChiaTruongDL 
						. $row["NoiDung"] . $KyTuChiaTruongDL 
						. $row["NgayTao"] . $KyTuChiaBanGhi;
				}
			} else 
				echo $null;
		}else 
			echo $false;
		break;
	}
	case "KhoAnh": {
        $MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
        if ($MaTaiKhoan != "") {
            $sql = "SELECT * FROM anh WHERE MaTaiKhoan = '".$MaTaiKhoan."' ORDER BY MaAnh DESC";
            $result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
            if (mysql_num_rows($result) > 0) {
	            while ($row = mysql_fetch_array($result)) {
	            	$image = new SimpleImage();
	            	$Link = $image->checkImage($PhotoMobile, $row["Link"]);
	            	
	                echo $row["MaAnh"] . $KyTuChiaTruongDL
	                    . $row["MoTa"] . $KyTuChiaTruongDL
	                    . $Link . $KyTuChiaTruongDL
	                    . $row["NgayTao"] . $KyTuChiaBanGhi
	                    ;
	            }	
            } else
            	echo $null;
        } else
            echo $false;
        break;
	}
	case "ChiTietBaiVietCaNhan": {
		$MaBaiViet = $_REQUEST["xMaBaiViet"];
		if ($MaBaiViet != "") {
			$sqlSelect = "SELECT MaBaiViet, TieuDe, NoiDung, NgayTao FROM baiviet 
					WHERE MaBaiViet = '".$MaBaiViet."'";
			$resultSelect = mysql_query($sqlSelect) or die("Lệnh truy vấn không chính xác!");
			$row = mysql_fetch_array($resultSelect);
			if ($row != null)
				echo $row["MaBaiViet"] . $KyTuChiaTruongDL 
				. $row["TieuDe"] . $KyTuChiaTruongDL 
				. $row["NoiDung"]. $KyTuChiaTruongDL
				. $row["NgayTao"];
		}else 
			echo $false;
		break;
	}
	case "ChiTietTinMoi": {
		$MaBaiViet_Nhom = $_REQUEST["xMaBaiViet_Nhom"];
		$Check = true;
		if ($MaBaiViet_Nhom != "") {
			$sqlBinhLuan = "SELECT MaBinhLuan FROM binhluanbaiviet WHERE MaBaiViet_Nhom = '".$MaBaiViet_Nhom."'";
			$resultBinhLuan = mysql_query($sqlBinhLuan) or die("Lệnh truy vấn không chính xác!");
			$countBinhLuanBaiViet = mysql_num_rows($resultBinhLuan);
			
			$sql = "SELECT 	bvn.NgayChiaSe, bv.TieuDe, bv.NoiDung,  
							tk.TaiKhoan, tk.HoDem, tk.Ten, tk.NgaySinh, tk.Email, tk.GioiTinh, tk.DienThoai, tk.AnhDaiDien, tk.DiaChi, tk.NgayVaoTruong, 
							n.TenNhom 
					FROM baiviet_nhom bvn 
							INNER JOIN baiviet bv ON bvn.MaBaiViet = bv.MaBaiViet 
							INNER JOIN taikhoan tk ON bvn.MaTaiKhoan = tk.MaTaiKhoan 
							INNER JOIN nhom n ON bvn.MaNhom = n.MaNhom 
					WHERE bvn.MaBaiViet_Nhom = '".$MaBaiViet_Nhom."'";
			
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			$row = mysql_fetch_array($result);
			if ($row != null) {
				$image = new SimpleImage();
            	$AnhDaiDien = $image->checkImage($AvatarMobile, $row["AnhDaiDien"]);
				
				echo $row["NgayChiaSe"] . $KyTuChiaTruongDL
					. $row["TieuDe"] . $KyTuChiaTruongDL
					. $row["NoiDung"] . $KyTuChiaTruongDL
					. $row["TenNhom"] . $KyTuChiaTruongDL // Nhom
					. $row["TaiKhoan"] . $KyTuChiaTruongDL // TaiKhoan
					. $row["HoDem"] . $KyTuChiaTruongDL
					. $row["Ten"] . $KyTuChiaTruongDL
					. $row["NgaySinh"] . $KyTuChiaTruongDL
					. $row["Email"] . $KyTuChiaTruongDL
					. $row["GioiTinh"] . $KyTuChiaTruongDL
					. $row["DienThoai"] . $KyTuChiaTruongDL
					. $AnhDaiDien . $KyTuChiaTruongDL
					. $row["DiaChi"] . $KyTuChiaTruongDL
					. $row["NgayVaoTruong"] . $KyTuChiaTruongDL
					. $countBinhLuanBaiViet  
					;
			}
			
		}else 
			echo $false;
		break;
	}
	case "TaoBinhLuan": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$MaBaiViet_Nhom = $_REQUEST["xMaBaiViet_Nhom"];
		$NoiDung = $_REQUEST["xNoiDung"];
		if ($MaBaiViet_Nhom != "" && $NoiDung != "") {
			$NoiDung =  addslashes(str_replace("$", "\n", str_replace("|", " ", $NoiDung)));
			$sql = "INSERT INTO binhluanbaiviet(MaTaiKhoan, NoiDung, MaBaiViet_Nhom) VALUES('".$MaTaiKhoan."', '".$NoiDung."', '".$MaBaiViet_Nhom."')";
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			if ($result != null)
				echo $true;
			else 
				echo $false;
		}else 
			echo $false;
		break;
	}
	case "DanhSachBinhLuanBaiViet": {
		$MaBaiViet_Nhom = $_REQUEST["xMaBaiViet_Nhom"];
		if ($MaBaiViet_Nhom != null) {
			$sql = "SELECT MaBinhLuan, NoiDung, tk.MaTaiKhoan, tk.TaiKhoan, blbv.NgayTao 
					FROM binhluanbaiviet blbv INNER JOIN taikhoan tk ON blbv.MaTaiKhoan = tk.MaTaiKhoan WHERE MaBaiViet_Nhom = '".$MaBaiViet_Nhom."' ORDER BY MaBinhLuan DESC";
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			while ($row = mysql_fetch_array($result)) {
				echo $row["MaBinhLuan"] . $KyTuChiaTruongDL
					. $row["NoiDung"] . $KyTuChiaTruongDL
					. $row["MaTaiKhoan"] . $KyTuChiaTruongDL
					. $row["TaiKhoan"] . $KyTuChiaTruongDL
					. $row["NgayTao"] . $KyTuChiaTruongDL
					;
			} 
		}else 
			echo $false;
		break;
	}
	case "DanhSachBaiViet_Nhom" : {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		if ($MaTaiKhoan != "") {
			$sqlTaiKhoan_BaiViet_Nhom = "SELECT tkbvn.MaBaiViet_Nhom, tkbvn.NgayChiaSe, tk.TaiKhoan, tk.HoDem, tk.Ten, tk.AnhDaiDien, bv.TieuDe, bv.NoiDung, n.MaNhom, n.TenNhom
						FROM taikhoan_baiviet_nhom tkbvn 
							INNER JOIN taikhoan tk ON tkbvn.MaTaiKhoanChiaSe = tk.MaTaiKhoan 
							INNER JOIN baiviet_nhom bvn ON tkbvn.MaBaiViet_Nhom = bvn.MaBaiViet_Nhom
							INNER JOIN baiviet bv ON bvn.MaBaiViet = bv.MaBaiViet  
							INNER JOIN nhom n ON bvn.MaNhom = n.MaNhom 
						WHERE tkbvn.MaTaiKhoanNhan = '".$MaTaiKhoan."'";
			$resultTaiKhoan_BaiViet_Nhom = mysql_query($sqlTaiKhoan_BaiViet_Nhom);
			if (mysql_num_rows($resultTaiKhoan_BaiViet_Nhom) > 0) {
				while ($rowTaiKhoan_BaiViet_Nhom = mysql_fetch_array($resultTaiKhoan_BaiViet_Nhom)) {
					$sqlBinhLuanBaiViet = "SELECT MaBinhLuan FROM binhluanbaiviet WHERE MaBaiViet_Nhom = '".$rowTaiKhoan_BaiViet_Nhom["MaBaiViet_Nhom"]."'";
					$countBinhLuanBaiViet = mysql_num_rows(mysql_query($sqlBinhLuanBaiViet));
					
					$image = new SimpleImage();
            		$AnhDaiDien = $image->checkImage($AvatarMobile, $rowBaiViet_Nhom["AnhDaiDien"]);
            		
            		echo $rowTaiKhoan_BaiViet_Nhom["MaBaiViet_Nhom"] . $KyTuChiaTruongDL
						. SimpleImage::MySubString($rowTaiKhoan_BaiViet_Nhom["TieuDe"], 20) . $KyTuChiaTruongDL
						. SimpleImage::MySubString($rowTaiKhoan_BaiViet_Nhom["NoiDung"], 50) . $KyTuChiaTruongDL
						. $rowTaiKhoan_BaiViet_Nhom["NgayChiaSe"] . $KyTuChiaTruongDL
						. $rowTaiKhoan_BaiViet_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
						//. $rowTaiKhoan_BaiViet_Nhom["HoDem"] . $KyTuChiaTruongDL
						//. $rowTaiKhoan_BaiViet_Nhom["Ten"] . $KyTuChiaTruongDL
                        . $AnhDaiDien . $KyTuChiaTruongDL
						. $rowTaiKhoan_BaiViet_Nhom["MaNhom"] . $KyTuChiaTruongDL
						. $rowTaiKhoan_BaiViet_Nhom["TenNhom"] . $KyTuChiaTruongDL
						. $countBinhLuanBaiViet . $KyTuChiaBanGhi
						;
				}
				
				
			}
			// //////////////////////// //
			$sqlTaiKhoan_Nhom = "SELECT tkn.MaNhom, TenNhom 
							FROM taikhoan_nhom tkn INNER JOIN nhom n ON tkn.MaNhom = n.MaNhom 
							WHERE tkn.MaTaiKhoan = '".$MaTaiKhoan."' AND tkn.TrangThai = 1";
			$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
			// MỘT TÀI KHOẢN CÓ THỂ THAM GIA NHIỀU NHÓM
			// Nếu có quyền trưởng nhóm thì không có bản ghi dữ liệu trong bảng: TaiKhoan_Nhom
			while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
				$sqlBaiViet_Nhom = "SELECT MaBaiViet_Nhom, bv.NoiDung, bv.TieuDe, NgayChiaSe, TaiKhoan, tk.AnhDaiDien, HoDem, Ten 
								FROM baiviet_nhom bvn INNER JOIN baiviet bv ON bvn.MaBaiViet = bv.MaBaiViet 
								INNER JOIN taikhoan tk ON bvn.MaTaiKhoan = tk.MaTaiKhoan 
								WHERE MaNhom = '".$rowTaiKhoan_Nhom["MaNhom"]."' 
								ORDER BY MaBaiViet_Nhom DESC";
				
				$resultBaiViet_Nhom = mysql_query($sqlBaiViet_Nhom) or die("Lệnh truy vấn không chính xác!");
				while ($rowBaiViet_Nhom = mysql_fetch_array($resultBaiViet_Nhom)) {
					$sqlBinhLuanBaiViet = "SELECT MaBinhLuan FROM binhluanbaiviet WHERE MaBaiViet_Nhom = '".$rowBaiViet_Nhom["MaBaiViet_Nhom"]."'";
					$countBinhLuanBaiViet = mysql_num_rows(mysql_query($sqlBinhLuanBaiViet));
					
    				$image = new SimpleImage();
            		$AnhDaiDien = $image->checkImage($AvatarMobile, $rowBaiViet_Nhom["AnhDaiDien"]);
                    
					echo $rowBaiViet_Nhom["MaBaiViet_Nhom"] . $KyTuChiaTruongDL
						. SimpleImage::MySubString($rowBaiViet_Nhom["TieuDe"], 20) . $KyTuChiaTruongDL
						. SimpleImage::MySubString($rowBaiViet_Nhom["NoiDung"], 50) . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NgayChiaSe"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["HoDem"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["Ten"] . $KyTuChiaTruongDL
                        . $AnhDaiDien . $KyTuChiaTruongDL
						. $rowTaiKhoan_Nhom["MaNhom"] . $KyTuChiaTruongDL
						. $rowTaiKhoan_Nhom["TenNhom"] . $KyTuChiaTruongDL
						. $countBinhLuanBaiViet . $KyTuChiaBanGhi
						;
				}
			}
			// VỚI CÁC NHÓM CÓ QUYỀN TRƯỞNG NHÓM
			// Nếu có quyền trưởng nhóm thì không có bản ghi dữ liệu trong bảng: TaiKhoan_Nhom
			$sqlNhom = "SELECT MaNhom, TenNhom FROM nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."'";
			$resultNhom = mysql_query($sqlNhom) or die("Lệnh truy vấn không chính xác!");
			// MỘT TÀI KHOẢN CÓ THỂ THAM GIA NHIỀU NHÓM
			while ($rowNhom = mysql_fetch_array($resultNhom)) {
				$sqlBaiViet_Nhom = "SELECT MaBaiViet_Nhom, bv.NoiDung, bv.TieuDe, NgayChiaSe, TaiKhoan, tk.AnhDaiDien, HoDem, Ten 
								FROM baiviet_nhom bvn INNER JOIN baiviet bv ON bvn.MaBaiViet = bv.MaBaiViet 
								INNER JOIN taikhoan tk ON bvn.MaTaiKhoan = tk.MaTaiKhoan 
								WHERE MaNhom = '".$rowNhom["MaNhom"]."' 
								ORDER BY MaBaiViet_Nhom DESC";
				
				$resultBaiViet_Nhom = mysql_query($sqlBaiViet_Nhom) or die("Lệnh truy vấn không chính xác!");
				while ($rowBaiViet_Nhom = mysql_fetch_array($resultBaiViet_Nhom)) {
					$sqlBinhLuanBaiViet = "SELECT MaBinhLuan FROM binhluanbaiviet WHERE MaBaiViet_Nhom = '".$rowBaiViet_Nhom["MaBaiViet_Nhom"]."'";
					$countBinhLuanBaiViet = mysql_num_rows(mysql_query($sqlBinhLuanBaiViet));
					
    				$image = new SimpleImage();
            		$AnhDaiDien = $image->checkImage($AvatarMobile, $rowBaiViet_Nhom["AnhDaiDien"]);
                    
					echo $rowBaiViet_Nhom["MaBaiViet_Nhom"] . $KyTuChiaTruongDL
						. SimpleImage::MySubString($rowBaiViet_Nhom["TieuDe"], 20) . $KyTuChiaTruongDL
						. SimpleImage::MySubString($rowBaiViet_Nhom["NoiDung"], 50) . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NgayChiaSe"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["HoDem"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["Ten"] . $KyTuChiaTruongDL
                        . $AnhDaiDien . $KyTuChiaTruongDL
						. $rowNhom["MaNhom"] . $KyTuChiaTruongDL
						. $rowNhom["TenNhom"] . $KyTuChiaTruongDL
						. $countBinhLuanBaiViet . $KyTuChiaBanGhi
						;
				}
			}
		}else 
			echo $false;
		break;
	}
    case "CapNhatTaiKhoan": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
        $HoDem = $_REQUEST["xHoDem"];
		$Ten = $_REQUEST["xTen"];
        $Email = $_REQUEST["xEmail"];
		$GioiTinh = $_REQUEST["xGioiTinh"];
        $DienThoai = $_REQUEST["xDienThoai"];
		$DiaChi = $_REQUEST["xDiaChi"];
        if ($MaTaiKhoan != ""){
			$sql = "UPDATE taikhoan SET "
			."HoDem= '".$HoDem."', "
			."Ten = '".$Ten."', "
			."Email = '".$Email."', "
			."GioiTinh = '".$GioiTinh."', "
			."DienThoai = '".$DienThoai."', "
			."DiaChi = '".$DiaChi."' "
			."WHERE MaTaiKhoan = '".$MaTaiKhoan."'"
			;
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			if ($result != null)
				echo $true;
			else
				echo $false;
        } else
            echo $false;
        break;
    }
    case "TimKiemNhom": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$TuKhoa = $_REQUEST['xTuKhoa'];
		if ($MaTaiKhoan != "") {
			$sqlTaiKhoan_Nhom = "SELECT n.MaNhom,n.TenNhom, tk.MaTaiKhoan, tk.TaiKhoan,n.NgayTao  FROM nhom n INNER JOIN taikhoan tk ON tk.MaTaiKhoan = n.MaTaiKhoan WHERE TenNhom LIKE '%".$TuKhoa."%' AND MaNhom NOT IN (SELECT MaNhom FROM taikhoan_nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."')";
			$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
			while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
				echo $rowTaiKhoan_Nhom["MaNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TenNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["NgayTao"] . $KyTuChiaTruongDL
					;
			}
		}else
			echo $false;
    	break;
    }
	case "NhomChuaChiaSe": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$MaBaiViet = $_REQUEST["xMaBaiViet"];
		if ($MaTaiKhoan != "" && $MaBaiViet != "") {
			$sqlTaiKhoan_Nhom = "SELECT tkn.MaNhom, n.TenNhom, n.MaTaiKhoan, tk.TaiKhoan FROM (SELECT MaNhom FROM taikhoan_nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."' and TrangThai = 1) tkn INNER JOIN nhom n ON n.MaNhom = tkn.MaNhom INNER JOIN taikhoan tk ON tk.MaTaiKhoan = n.MaTaiKhoan AND n.MaNhom NOT IN (SELECT MaNhom FROM baiviet_nhom WHERE MaBaiViet = '".$MaBaiViet."' AND MaTaiKhoan ='".$MaTaiKhoan."')";
			$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
			while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
				echo $rowTaiKhoan_Nhom["MaNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TenNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
					;
			}
		}else
			echo $false;
    	break;
    }
	case "NhomDaChiaSe": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$MaBaiViet = $_REQUEST["xMaBaiViet"];
		if ($MaTaiKhoan != "" && $MaBaiViet != "") {
			$sqlTaiKhoan_Nhom ="SELECT bvn.MaNhom, n.TenNhom, bvn.MaTaiKhoan, tk.TaiKhoan FROM baiviet_nhom bvn INNER JOIN nhom n ON n.MaNhom = bvn.MaNhom INNER JOIN taikhoan tk ON tk.MaTaiKhoan = bvn.MaTaiKhoan WHERE bvn.MaBaiViet = '".$MaBaiViet."' AND bvn.MaTaiKhoan = '".$MaTaiKhoan."'";
			$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
			while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
				echo $rowTaiKhoan_Nhom["MaNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TenNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
					;
			}
		}else
			echo $false;
    	break;
    }
	case "XoaBaiViet": {
		$MaBaiViet = $_REQUEST["xMaBaiViet"];
		if ($MaBaiViet != "") {
			$sqlBaiViet_Nhom ="SELECT MaNhom FROM baiviet_nhom WHERE MaBaiViet = '".$MaBaiViet."' ORDER BY MaBaiViet DESC LIMIT 0,1";
			$resultBaiViet_Nhom = mysql_query($sqlBaiViet_Nhom) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($resultBaiViet_Nhom) == 0) {
				$sql = "DELETE FROM baiviet WHERE MaBaiViet='$MaBaiViet'";
				$result = mysql_query($sql) or die ("Lệnh truy vấn không chính xác!");
				echo ($result != null) ? $true : $false;
			}
			else echo $false;
		}else
			echo $false;
    	break;
    }
    case "ChiTietNhomTimKiem": {
    	$MaNhom = $_REQUEST["xMaNhom"];
    	if ($MaNhom != "") {
    		$sqlNhom = "SELECT * FROM nhom WHERE MaNhom = '".$MaNhom."'";
    		$resultNhom = mysql_query($sqlNhom) or die("Lệnh truy vấn không chính xác!");
    		if (mysql_num_rows($resultNhom) > 0) {
    			$rowNhom = mysql_fetch_array($resultNhom);
    			$sqlThanhVien = "SELECT MaTaiKhoan_Nhom FROM taikhoan_nhom WHERE TrangThai = 1 AND MaNhom = '".$rowNhom["MaNhom"]."'";
    			$resultThanhVien = mysql_query($sqlThanhVien) or die("Lệnh truy vấn không chính xác2!");
    			$CountThanhVien = mysql_num_rows($resultThanhVien) + 1; // Cộng thêm Tài khoản trưởng nhóm
    			$image = new SimpleImage();
            	$AnhDaiDien = $image->checkImage($AvatarMobile, $rowNhom["AnhDaiDien"]);
    			echo $rowNhom["MaNhom"] . $KyTuChiaTruongDL
    				. $rowNhom["TenNhom"] . $KyTuChiaTruongDL
    				. $AnhDaiDien . $KyTuChiaTruongDL
    				. $rowNhom["MoTa"] . $KyTuChiaTruongDL
    				. $rowNhom["NgayTao"] . $KyTuChiaTruongDL
    				. $CountThanhVien . $KyTuChiaTruongDL
    				;
    		} else 
    			echo $false;
    	} else 
    		echo $false;
    	break;
    }
    case "ThamGiaNhom": {
    	$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
    	$MaNhom = $_REQUEST["xMaNhom"];    	
    	if ($MaTaiKhoan != "" && $MaNhom != "") {
    		$sqlSelectTaiKhoan_Nhom = "SELECT TrangThai FROM taikhoan_nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."' AND MaNhom = '".$MaNhom."'";
    		$resultSelectTaiKhoan_Nhom = mysql_query($sqlSelectTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
    		if (mysql_num_rows($resultSelectTaiKhoan_Nhom) > 0) {
    			$rowSelectTaiKhoan_Nhom = mysql_fetch_array($resultSelectTaiKhoan_Nhom);
    			echo $rowSelectTaiKhoan_Nhom["TrangThai"];
    		} else {
    			$sqlTruongNhom = "SELECT MaNhom FROM nhom WHERE MaNhom = '".$MaNhom."' AND MaTaiKhoan = '".$MaTaiKhoan."'";
    			$resultTruongNhom = mysql_query($sqlTruongNhom) or die("Lệnh truy vấn không chính xác!");
    			if (mysql_num_rows($resultTruongNhom) > 0) {
    				echo "TruongNhom";
    			} else {
    				/*$sqlNhom = "SELECT LoaiNhom FROM nhom WHERE MaNhom = '".$MaNhom."'";
    				$resultNhom = mysql_query($sqlNhom);
    				$rowNhom = mysql_fetch_array($resultNhom);
    				$TrangThai = "";
    				if ($rowNhom["LoaiNhom"] == 0)
    					$TrangThai = "0";
    				elseif ($rowNhom["LoaiNhom"] == 1)
    					$TrangThai = "1";*/
    				$TrangThai=0;
    				$sqlInsertTaiKhoan_Nhom = "INSERT INTO taikhoan_nhom(MaTaiKhoan, MaNhom, TrangThai) VALUES('".$MaTaiKhoan."', '".$MaNhom."', '".$TrangThai."')";
		    		$result = mysql_query($sqlInsertTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
		    		if ($result != null) {
		    			if ($TrangThai == "0")
		    				echo $true;
		    			elseif ($TrangThai == "1")
		    				echo "ThanhCong";
		    			
		    		} else 
		    			echo $false;
    			}
    		}
    	} else 
    		echo $false;
    	break;
    }
    case "DanhSachNhom": {
    	$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$Check = true;
		if ($MaTaiKhoan != "") {
			$sqlTruongNhom = "SELECT MaNhom, TenNhom, AnhDaiDien FROM nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."'";
			$resultTruongNhom = mysql_query($sqlTruongNhom) or die("Lệnh truy vấn không chính xác!");
			while ($rowTruongNhom = mysql_fetch_array($resultTruongNhom)) {
				$image = new SimpleImage();
            	$AnhDaiDien = $image->checkImage($AvatarMobile, $rowTruongNhom["AnhDaiDien"]);
				
				echo $rowTruongNhom["MaNhom"] . $KyTuChiaTruongDL
					. $rowTruongNhom["TenNhom"] . $KyTuChiaTruongDL 
					. $AnhDaiDien . $KyTuChiaTruongDL
					. "1" . $KyTuChiaBanGhi
					;
			}
			$sqlTaiKhoan_Nhom = "SELECT tv.MaNhom, n.TenNhom, n.AnhDaiDien 
					FROM taikhoan_nhom tv INNER JOIN nhom n ON tv.MaNhom = n.MaNhom 
					WHERE tv.TrangThai = 1 AND tv.MaTaiKhoan = '".$MaTaiKhoan."'";
			$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
			while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
				$image = new SimpleImage();
            	$AnhDaiDien = $image->checkImage($AvatarMobile, $rowTaiKhoan_Nhom["AnhDaiDien"]);
				
				echo $rowTaiKhoan_Nhom["MaNhom"] . $KyTuChiaTruongDL
					.$rowTaiKhoan_Nhom["TenNhom"] . $KyTuChiaTruongDL
					. $AnhDaiDien . $KyTuChiaTruongDL
					. "0" . $KyTuChiaBanGhi
					;
			}
			
			if (mysql_num_rows($resultTruongNhom) + mysql_num_rows($resultTaiKhoan_Nhom) <= 0)
				$Check = false;
			if (!$Check)
				echo $null;
		}else
			echo $false;
		break;
    }
	case "DSYeuCauThamGiaNhom": {
    	$MaNhom = $_REQUEST["xMaNhom"];
    	if ($MaNhom != "") {
			$sqlTaiKhoan_Nhom = "SELECT tkn.MaTaiKhoan_Nhom, tk.TaiKhoan, tk.MaTaiKhoan, n.MaNhom, n.TenNhom, tkn.NgayVaoNhom 
						FROM taikhoan_nhom tkn 
							INNER JOIN taikhoan tk ON tkn.MaTaiKhoan = tk.MaTaiKhoan 
							INNER JOIN nhom n ON tkn.MaNhom = n.MaNhom 
						WHERE tkn.TrangThai = 0 AND tkn.MaNhom = '".$MaNhom."'";
			$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($resultTaiKhoan_Nhom) > 0) {
				while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
					/*$image = new SimpleImage();
					$AnhDaiDien = $image->checkImage($AvatarMobile, $rowTaiKhoan_Nhom["AnhDaiDien"]);*/
					
					echo $rowTaiKhoan_Nhom["MaTaiKhoan_Nhom"] . $KyTuChiaTruongDL
						//. $AnhDaiDien . $KyTuChiaTruongDL
						. $rowTaiKhoan_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
						. $rowTaiKhoan_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
						. $rowTaiKhoan_Nhom["MaNhom"] . $KyTuChiaTruongDL
						. $rowTaiKhoan_Nhom["TenNhom"] . $KyTuChiaTruongDL
						. $rowTaiKhoan_Nhom["NgayVaoNhom"]. $KyTuChiaTruongDL
					;
				}
			}else echo $null;
    	} else echo $false;
    	break;
    }
    case "DSYeuCauThamGiaNhomCuaBan": {
    	$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
    	$CountTaiKhoan_Nhom = 0;
    	if ($MaTaiKhoan != "") {
    		$sqlNhom = "SELECT MaNhom FROM nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."'";
    		$resultNhom = mysql_query($sqlNhom) or die("Lệnh truy vấn không chính xác!");
    		if (mysql_num_rows($resultNhom) > 0) {
    			while ($rowNhom = mysql_fetch_array($resultNhom)) {
    				$MaNhom = $rowNhom["MaNhom"];
    				$sqlTaiKhoan_Nhom = "SELECT tkn.MaTaiKhoan_Nhom, tk.TaiKhoan, tk.MaTaiKhoan, n.MaNhom, n.TenNhom, tkn.NgayVaoNhom FROM taikhoan_nhom tkn INNER JOIN taikhoan tk ON tkn.MaTaiKhoan = tk.MaTaiKhoan INNER JOIN nhom n ON tkn.MaNhom = n.MaNhom WHERE tkn.TrangThai = 0 AND tkn.MaNhom = '".$MaNhom."' AND tkn.MaTaiKhoan <>'".$MaTaiKhoan."'";
    				$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
    				if (mysql_num_rows($resultTaiKhoan_Nhom) > 0) {
	    				$CountTaiKhoan_Nhom += mysql_num_rows($resultTaiKhoan_Nhom);
	    				while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
	    					/*$image = new SimpleImage();
            				$AnhDaiDien = $image->checkImage($AvatarMobile, $rowTaiKhoan_Nhom["AnhDaiDien"]);*/
            				
            				echo $rowTaiKhoan_Nhom["MaTaiKhoan_Nhom"] . $KyTuChiaTruongDL
            					//. $AnhDaiDien . $KyTuChiaTruongDL
            					. $rowTaiKhoan_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
            					. $rowTaiKhoan_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
            					. $rowTaiKhoan_Nhom["MaNhom"] . $KyTuChiaTruongDL
            					. $rowTaiKhoan_Nhom["TenNhom"] . $KyTuChiaTruongDL
            					. $rowTaiKhoan_Nhom["NgayVaoNhom"]. $KyTuChiaTruongDL
            				;
	    				}
    				}
    			} // END While
    			echo ($CountTaiKhoan_Nhom == 0) ? $null : "";
    		} else 
    			echo $null;
    	} else 
    		echo $false;
    	break;
    }
    case "DuyetYeuCauThamGia": {
    	$MaTaiKhoan_Nhom = $_REQUEST["xMaTaiKhoan_Nhom"];
    	$XuLy = $_REQUEST["xXuLy"];
    	if ($MaTaiKhoan_Nhom != "" && $XuLy != "") {
    		if ($XuLy == "DongY") 
    			$sql = "UPDATE taikhoan_nhom SET TrangThai = 1 WHERE MaTaiKhoan_Nhom = '".$MaTaiKhoan_Nhom."'";
    		elseif ($XuLy == "KhongDongY") 
    			$sql = "DELETE FROM taikhoan_nhom WHERE MaTaiKhoan_Nhom = '".$MaTaiKhoan_Nhom."'";
    		else 
    			$sql = "";
    			
    		if ($sql != "") {
    			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
    			echo ($result != null) ? $true : $false;
    		} else 
    			echo $false;
    	} else 
    		echo $false;
    	break;
    }
    case "ChiTietAnh": {
    	$MaAnh = $_REQUEST["xMaAnh"];
    	$WidthScreen = $_REQUEST["xWidthScreen"];
    	$HeightScreen = $_REQUEST["xHeightScreen"];
    	if ($MaAnh != "" && is_numeric($WidthScreen) && is_numeric($HeightScreen)) {
    		$WidthScreen = $WidthScreen - 2;
    		$HeightScreen = $HeightScreen - 10;
    		$sql = "SELECT Link FROM anh WHERE MaAnh = '".$MaAnh."'";
    		$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
    		if (mysql_num_rows($result) > 0) {
    			$row = mysql_fetch_array($result);
    			
            	$image = new SimpleImage();
            	$Link = $image->ChiTietAnh($row["Link"], $WidthScreen, $HeightScreen, $ChiTietAnh);
            	echo ($Link != false) ? ($Link . $KyTuChiaTruongDL . $MaAnh) : $false;
    		} else 
    			echo $false;
    	} else 
    		echo $false;
    	break;
    }
    case "AnhTruoc": {
    	$MaAnh = $_REQUEST["xMaAnh"];
    	$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
    	$WidthScreen = $_REQUEST["xWidthScreen"];
    	$HeightScreen = $_REQUEST["xHeightScreen"];
    	if ($MaAnh != "" && $MaTaiKhoan != "" && is_numeric($WidthScreen) && is_numeric($HeightScreen)) {
    		$WidthScreen = $WidthScreen - 2;
    		$HeightScreen = $HeightScreen - 10;
    		
    		if ($MaAnh == $null) 
    			$sql = "SELECT MaAnh, Link FROM anh WHERE MaTaiKhoan = '".$MaTaiKhoan."'ORDER BY MaAnh ASC LIMIT 0,1";
    		else 
    			$sql = "SELECT MaAnh, Link FROM anh WHERE MaTaiKhoan = '".$MaTaiKhoan."' AND MaAnh > ".$MaAnh." ORDER BY MaAnh ASC LIMIT 0,1";
    		$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
    		if (mysql_num_rows($result) > 0) {
    			$row = mysql_fetch_array($result);
    			$image = new SimpleImage();
    			$Link = $image->ChiTietAnh($row["Link"], $WidthScreen, $HeightScreen, $ChiTietAnh);
    			echo ($Link != false) ? ($Link . $KyTuChiaTruongDL . $row["MaAnh"]) : $false;
    		} else 
    			echo $null;
    	} else 
    		echo $false;
    	break;
    }
	case "AnhSau": {
    	$MaAnh = $_REQUEST["xMaAnh"];
    	$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
    	$WidthScreen = $_REQUEST["xWidthScreen"];
    	$HeightScreen = $_REQUEST["xHeightScreen"];
    	if ($MaAnh != "" && $MaTaiKhoan != "" && is_numeric($WidthScreen) && is_numeric($HeightScreen)) {
    		$WidthScreen = $WidthScreen - 2;
    		$HeightScreen = $HeightScreen - 10;
    		
    		if ($MaAnh == $null) 
    			$sql = "SELECT MaAnh, Link FROM anh WHERE MaTaiKhoan = '".$MaTaiKhoan."'ORDER BY MaAnh DESC LIMIT 0,1";
    		else 
    			$sql = "SELECT MaAnh, Link FROM anh WHERE MaTaiKhoan = '".$MaTaiKhoan."' AND MaAnh < ".$MaAnh." ORDER BY MaAnh DESC LIMIT 0,1";
    		$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
    		if (mysql_num_rows($result) > 0) {
    			$row = mysql_fetch_array($result);
    			$image = new SimpleImage();
    			$Link = $image->ChiTietAnh($row["Link"], $WidthScreen, $HeightScreen, $ChiTietAnh);
    			echo ($Link != false) ? ($Link . $KyTuChiaTruongDL . $row["MaAnh"]) : $false;
    		} else 
    			echo $null;
    	} else 
    		echo $false;
    	break;
    }  
	case "IsTruongNhom": {
		$MaNhom = $_REQUEST["xMaNhom"];
    	$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
    	if ($MaTaiKhoan != "" && $MaNhom != "") {
    		$sqlNhom = "SELECT MaNhom FROM nhom WHERE MaNhom = '".$MaNhom."' AND MaTaiKhoan = '".$MaTaiKhoan."'";
    		$resultNhom = mysql_query($sqlNhom) or die("Lệnh truy vấn không chính xác!");
    		echo (mysql_num_rows($resultNhom) == 1) ? "1" : "0";
    	} else 
    		echo $false;
		break;
	}
	case "ChiTietNhom": {
    	$MaNhom = $_REQUEST["xMaNhom"];
    	if ($MaNhom != "") {
    		$sqlNhom = "SELECT MoTa, QuyTac, NgayTao FROM nhom WHERE MaNhom = '".$MaNhom."'";
    		$resultNhom = mysql_query($sqlNhom) or die("Lệnh truy vấn không chính xác!");
    		if (mysql_num_rows($resultNhom) > 0) {
    			$rowNhom = mysql_fetch_array($resultNhom);
    			
    			$sqlThanhVien = "SELECT MaTaiKhoan_Nhom FROM taikhoan_nhom WHERE TrangThai = 1 AND MaNhom = '".$MaNhom."'";
    			$resultThanhVien = mysql_query($sqlThanhVien) or die("Lệnh truy vấn không chính xác2!");
    			$CountThanhVien = mysql_num_rows($resultThanhVien); 
    			$sqlBaiViet="SELECT COUNT(*) AS SoBaiViet FROM baiviet_nhom WHERE manhom='".$MaNhom."'";
				$resultBaiViet=mysql_query($sqlBaiViet);
				$rowBaiViet=mysql_fetch_array($resultBaiViet);
    			//$image = new SimpleImage();
            	//$AnhDaiDien = $image->checkImage($AvatarMobile, $rowNhom["AnhDaiDien"]);
    			
            	//$IsTruongNhom = ($rowNhom["MaTaiKhoan"] == $MaTaiKhoan) ? "1" : 0;
            	
    			echo $rowNhom["MoTa"] . $KyTuChiaTruongDL
    				. $rowNhom["QuyTac"] . $KyTuChiaTruongDL
    				. $rowNhom["NgayTao"] . $KyTuChiaTruongDL
					. $rowBaiViet['SoBaiViet']. $KyTuChiaTruongDL
    				. $CountThanhVien. $KyTuChiaTruongDL
    				;
    		} else 
    			echo $false;
    	} else 
    		echo $false;
    	break;
    }
	case "XemAnhTrongAlbumChiaSe": {
		$MaAlbumChiaSe = $_REQUEST["xMaAlbumChiaSePK"];
		$WidthScreen = $_REQUEST["xWidthScreen"];
    	$HeightScreen = $_REQUEST["xHeightScreen"];
    	$Start = $_REQUEST["xStart"];
    	if ($MaAlbumChiaSe != "" && is_numeric($WidthScreen) && is_numeric($HeightScreen)) {
    		$WidthScreen = $WidthScreen - 2;
    		$HeightScreen = $HeightScreen - 10;
    		
    		$sqlAnh = "SELECT a.Link 
    				FROM anh_nhom an INNER JOIN anh a ON an.MaAnh = a.MaAnh 
    				WHERE an.MaAlbumChiaSe = '".$MaAlbumChiaSe."' ORDER BY MaAnh_Nhom DESC LIMIT ".$Start.",1";
    		$resultAnh = mysql_query($sqlAnh) or die("Lệnh truy vấn không chính xác!");
    		if (mysql_num_rows($resultAnh) > 0) {
    			$rowAnh = mysql_fetch_array($resultAnh);
    			
    			$image = new SimpleImage();
    			$Link = $image->ChiTietAnh($rowAnh["Link"], $WidthScreen, $HeightScreen, $ChiTietAnh);
    			
    			echo $Link;
    		} else 
    			echo $null;
    	} else 
    		echo $false;
		break;
	}
	case "DanhSachBaiVietMoi": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		if ($MaTaiKhoan != "") {
			$sqlBaiViet_Nhom = "SELECT bvn.MaBaiViet_Nhom,bvn.MaBaiViet, bv.TieuDe, bv.NoiDung, bv.NgayTao, bvn.NgayChiaSe, tk.MaTaiKhoan, tk.TaiKhoan,bvn.MaTaiKhoan AS MaTaiKhoanChiaSe
							FROM baiviet_nhom bvn 
								INNER JOIN baiviet bv ON bvn.MaBaiViet = bv.MaBaiViet
								INNER JOIN taikhoan tk ON bv.MaTaiKhoan = tk.MaTaiKhoan  
								INNER JOIN baivietmoi bvm ON bvm.MaBaiViet_Nhom=bvn.MaBaiViet_Nhom
							WHERE bvm.MaTaiKhoan='$MaTaiKhoan' AND bvn.TrangThai = 1 
							ORDER BY MaBaiVietMoi DESC";
			$resultBaiViet_Nhom = mysql_query($sqlBaiViet_Nhom) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($resultBaiViet_Nhom) > 0) {
				while ($rowBaiViet_Nhom = mysql_fetch_array($resultBaiViet_Nhom)) {
					$sqlBinhLuanBaiViet = "SELECT MaBinhLuan FROM binhluanbaiviet WHERE MaBaiViet_Nhom = '".$rowBaiViet_Nhom["MaBaiViet_Nhom"]."'";
					$countBinhLuanBaiViet = mysql_num_rows(mysql_query($sqlBinhLuanBaiViet));
					$TaiKhoanChiaSe = get_user_info($rowBaiViet_Nhom["MaTaiKhoanChiaSe"]);
                    //11 truong
					echo $rowBaiViet_Nhom["MaBaiViet_Nhom"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["MaBaiViet"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["TieuDe"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NoiDung"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NgayTao"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NgayChiaSe"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["MaTaiKhoanChiaSe"] . $KyTuChiaTruongDL
						. $countBinhLuanBaiViet . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom['TaiKhoan'] . $KyTuChiaTruongDL
						. $TaiKhoanChiaSe['TaiKhoan'] . $KyTuChiaTruongDL
						;
				}
			} else 
				echo $null;
		}else 
			echo $false;
		break;
	}
	case "BaiVietDaChiaSeBanTao": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		if ($MaTaiKhoan != "") {
			$sqlBaiViet_Nhom = "SELECT DISTINCT bv.MaBaiViet, TieuDe, NoiDung, bv.NgayTao, bv.MaTaiKhoan, tk.TaiKhoan FROM baiviet_nhom bvn INNER JOIN baiviet bv ON bv.MaBaiViet = bvn.MaBaiViet INNER JOIN taikhoan tk ON tk.MaTaiKhoan=bv.MaTaiKhoan WHERE bvn.MaTaiKhoan='$MaTaiKhoan' AND bv.MaTaiKhoan='$MaTaiKhoan' ORDER BY bv.MaBaiViet DESC";
			$resultBaiViet_Nhom = mysql_query($sqlBaiViet_Nhom) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($resultBaiViet_Nhom) > 0) {
				while ($rowBaiViet_Nhom = mysql_fetch_array($resultBaiViet_Nhom)) {
					$sqlBinhLuanBaiViet = "SELECT MaBinhLuan FROM binhluanbaiviet WHERE MaBaiViet_Nhom = '".$rowBaiViet_Nhom["MaBaiViet_Nhom"]."'";
					$countBinhLuanBaiViet = mysql_num_rows(mysql_query($sqlBinhLuanBaiViet));
					$TaiKhoanChiaSe = get_user_info($rowBaiViet_Nhom["MaTaiKhoanChiaSe"]);
                    //11 truong
					echo $rowBaiViet_Nhom["MaBaiViet"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["TieuDe"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NoiDung"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NgayTao"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
						;
				}
			} else 
				echo $null;
		}else 
			echo $false;
		break;
	}
	case "BaiVietDaChiaSeNguoiKhacTao": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		if ($MaTaiKhoan != "") {
			$sqlBaiViet_Nhom = "SELECT DISTINCT bv.MaBaiViet, TieuDe, NoiDung, bv.NgayTao, bv.MaTaiKhoan, tk.TaiKhoan FROM baiviet_nhom bvn INNER JOIN baiviet bv ON bv.MaBaiViet = bvn.MaBaiViet INNER JOIN taikhoan tk ON tk.MaTaiKhoan=bv.MaTaiKhoan WHERE bvn.MaTaiKhoan='$MaTaiKhoan' AND bv.MaTaiKhoan <> '$MaTaiKhoan' ORDER BY bv.MaBaiViet DESC";
			$resultBaiViet_Nhom = mysql_query($sqlBaiViet_Nhom) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($resultBaiViet_Nhom) > 0) {
				while ($rowBaiViet_Nhom = mysql_fetch_array($resultBaiViet_Nhom)) {
					$sqlBinhLuanBaiViet = "SELECT MaBinhLuan FROM binhluanbaiviet WHERE MaBaiViet_Nhom = '".$rowBaiViet_Nhom["MaBaiViet_Nhom"]."'";
					$countBinhLuanBaiViet = mysql_num_rows(mysql_query($sqlBinhLuanBaiViet));
					$TaiKhoanChiaSe = get_user_info($rowBaiViet_Nhom["MaTaiKhoanChiaSe"]);
                    //11 truong
					echo $rowBaiViet_Nhom["MaBaiViet"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["TieuDe"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NoiDung"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NgayTao"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
						;
				}
			} else 
				echo $null;
		}else 
			echo $false;
		break;
	}
	case "BaiVietChuaChiaSe": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		if ($MaTaiKhoan != "") {
			$sqlBaiViet_Nhom = "SELECT MaBaiViet, TieuDe, NoiDung, bv.NgayTao, bv.MaTaiKhoan, tk.TaiKhoan FROM baiviet bv INNER JOIN taikhoan tk ON tk.MaTaiKhoan=bv.MaTaiKhoan WHERE bv.MaTaiKhoan='$MaTaiKhoan' AND MaBaiViet NOT IN (SELECT DISTINCT MaBaiViet FROM baiviet_nhom WHERE MaTaiKhoan='$MaTaiKhoan') ORDER BY MaBaiViet DESC";
			$resultBaiViet_Nhom = mysql_query($sqlBaiViet_Nhom) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($resultBaiViet_Nhom) > 0) {
				while ($rowBaiViet_Nhom = mysql_fetch_array($resultBaiViet_Nhom)) {
					$sqlBinhLuanBaiViet = "SELECT MaBinhLuan FROM binhluanbaiviet WHERE MaBaiViet_Nhom = '".$rowBaiViet_Nhom["MaBaiViet_Nhom"]."'";
					$countBinhLuanBaiViet = mysql_num_rows(mysql_query($sqlBinhLuanBaiViet));
					$TaiKhoanChiaSe = get_user_info($rowBaiViet_Nhom["MaTaiKhoanChiaSe"]);
                    //11 truong
					echo $rowBaiViet_Nhom["MaBaiViet"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["TieuDe"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NoiDung"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NgayTao"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
						;
				}
			} else 
				echo $null;
		}else 
			echo $false;
		break;
	}
	case "DanhSachBaiVietTheoNhom": {
		$MaNhom = $_REQUEST["xMaNhom"];
		if ($MaNhom != "") {
			$sqlBaiViet_Nhom = "SELECT bvn.MaBaiViet_Nhom,bvn.MaBaiViet, bv.TieuDe, bv.NoiDung, bv.NgayTao, bvn.NgayChiaSe, tk.MaTaiKhoan, tk.TaiKhoan,bvn.MaTaiKhoan AS MaTaiKhoanChiaSe FROM baiviet_nhom bvn INNER JOIN baiviet bv ON bvn.MaBaiViet = bv.MaBaiViet INNER JOIN taikhoan tk ON bv.MaTaiKhoan = tk.MaTaiKhoan WHERE bvn.MaNhom = '".$MaNhom."' AND bvn.TrangThai = 1 ORDER BY MaBaiViet_Nhom DESC";
			$resultBaiViet_Nhom = mysql_query($sqlBaiViet_Nhom) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($resultBaiViet_Nhom) > 0) {
				while ($rowBaiViet_Nhom = mysql_fetch_array($resultBaiViet_Nhom)) {
					$sqlBinhLuanBaiViet = "SELECT MaBinhLuan FROM binhluanbaiviet WHERE MaBaiViet_Nhom = '".$rowBaiViet_Nhom["MaBaiViet_Nhom"]."'";
					$countBinhLuanBaiViet = mysql_num_rows(mysql_query($sqlBinhLuanBaiViet));
					$TaiKhoanChiaSe = get_user_info($rowBaiViet_Nhom["MaTaiKhoanChiaSe"]);
                    //11 truong
					echo $rowBaiViet_Nhom["MaBaiViet_Nhom"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["MaBaiViet"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["TieuDe"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NoiDung"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NgayTao"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["NgayChiaSe"] . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom["MaTaiKhoanChiaSe"] . $KyTuChiaTruongDL
						. $countBinhLuanBaiViet . $KyTuChiaTruongDL
						. $rowBaiViet_Nhom['TaiKhoan'] . $KyTuChiaTruongDL
						. $TaiKhoanChiaSe['TaiKhoan'] . $KyTuChiaTruongDL
						;
				}
			} else 
				echo $null;
		}else 
			echo $false;
		break;
	}
	case "XemBaiVietMoi": {
		$MaTaiKhoan=$_REQUEST['xMaTaiKhoan'];
		$MaBaiViet_Nhom = $_REQUEST['xMaBaiViet_Nhom'];
		$sql="DELETE from baivietmoi where MaTaiKhoan='$MaTaiKhoan' AND MaBaiViet_Nhom=$MaBaiViet_Nhom";
		mysql_query($sql);
		echo $true;
		
		break;
	}
	case "BinhLuanAnh": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$MaAlbumChiaSe = $_REQUEST["xMaAlbumChiaSe"];
		$NoiDung = $_REQUEST["xNoiDung"];
		if ($MaTaiKhoan != "" && $MaAlbumChiaSe != "" && $NoiDung != "") {
			$sql = "INSERT INTO binhluananh(MaTaiKhoan, NoiDung, MaAlbumChiaSe) VALUES('".$MaTaiKhoan."', '".$NoiDung."', '".$MaAlbumChiaSe."')";
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			if ($result != null)
				echo $true;
			else 
				echo $false;
		} else 
			echo $false;
		break;
	}
	case "XemBinhLuanAnh": {
		$MaAlbumChiaSe = $_REQUEST["xMaAlbumChiaSe"];
		if ($MaAlbumChiaSe != null) {
			$sql = "SELECT NoiDung, tk.TaiKhoan, tk.Ten, tk.AnhDaiDien, bla.NgayTao 
					FROM binhluananh bla INNER JOIN taikhoan tk ON bla.MaTaiKhoan = tk.MaTaiKhoan 
					WHERE MaAlbumChiaSe = '".$MaAlbumChiaSe."' ORDER BY MaBinhLuan DESC";
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			while ($row = mysql_fetch_array($result)) {
				$image = new SimpleImage();
            	$AnhDaiDien = $image->checkImage($AvatarMobile, $row["AnhDaiDien"]);
            	
				echo $row["NoiDung"] . $KyTuChiaTruongDL
					. $row["TaiKhoan"] . $KyTuChiaTruongDL
					. $row["NgayTao"] . $KyTuChiaTruongDL
					. $row["Ten"] . $KyTuChiaTruongDL
					. $AnhDaiDien . $KyTuChiaBanGhi;
			} 
		}else 
			echo $false;
		break;
	}
	case "DanhSachAlbumChiaSe": {
		$MaNhom = $_REQUEST["xMaNhom"];
		$Width = $_REQUEST["xWidth"];
		if ($MaNhom != "" && is_numeric($Width)) {
			$Width = $Width - 80;
				
			$sqlAlbumChiaSe = "SELECT acs.MaAlbumChiaSe, acs.TenAlbum, tk.TaiKhoan, tk.HoDem, tk.Ten, tk.AnhDaiDien 
					FROM albumchiase acs INNER JOIN taikhoan tk ON acs.MaTaiKhoan = tk.MaTaiKhoan 
					WHERE MaNhom = '".$MaNhom."' 
					ORDER BY acs.MaAlbumChiaSe DESC";
			$resultAlbumChiaSe = mysql_query($sqlAlbumChiaSe) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($resultAlbumChiaSe) > 0) {
				while ($rowAlbumChiaSe = mysql_fetch_array($resultAlbumChiaSe)) {
					$sqlAnh = "SELECT a.Link, an.NgayChiaSe 
							FROM anh_nhom an INNER JOIN anh a ON an.MaAnh = a.MaAnh 
							WHERE an.MaAlbumChiaSe = '".$rowAlbumChiaSe["MaAlbumChiaSe"]."' 
							ORDER BY an.MaAnh_Nhom DESC LIMIT 0,1";
					$resultAnh = mysql_query($sqlAnh) or die("Lệnh truy vấn không chính xác!");
					if (mysql_num_rows($resultAnh) == 1) {
						$rowAnh = mysql_fetch_array($resultAnh);
						
						$image = new SimpleImage();
    					$Link = $image->ChiTietAnh($rowAnh["Link"], $Width, 10000, $AlbumChiaSe);
    					$AnhDaiDien = $image->checkImage($AvatarMobile, $rowAlbumChiaSe["AnhDaiDien"]);
    					
    					$sqlAnh_Nhom = "SELECT MaAnh_Nhom FROM anh_nhom WHERE MaAlbumChiaSe = '".$rowAlbumChiaSe["MaAlbumChiaSe"]."'";
						$resultAnh_Nhom = mysql_query($sqlAnh_Nhom) or die("Lệnh truy vấn không chính xác!");
						$Count = mysql_num_rows($resultAnh_Nhom);
    					echo $rowAlbumChiaSe["MaAlbumChiaSe"] . $KyTuChiaTruongDL
    						. $rowAlbumChiaSe["TenAlbum"] . $KyTuChiaTruongDL
    						. $rowAlbumChiaSe["TaiKhoan"] . $KyTuChiaTruongDL
    						. $rowAlbumChiaSe["HoDem"] . $KyTuChiaTruongDL
    						. $rowAlbumChiaSe["Ten"] . $KyTuChiaTruongDL
    						. $Link . $KyTuChiaTruongDL
    						. $rowAnh["NgayChiaSe"] . $KyTuChiaTruongDL 
    						. $Count . $KyTuChiaTruongDL 
    						. $AnhDaiDien . $KyTuChiaBanGhi
    						;
					}
				}
			} else 
				echo $null;
		} else 
			echo $false;
		break;
	}
	case "AlbumChiaSeMoi" : {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$Width = $_REQUEST["xWidth"];
		if ($MaTaiKhoan != "" && is_numeric($Width)) {
			$Width = $Width - 80;
			
			$sqlTaiKhoan_AlbumChiaSe = "SELECT tkacs.MaAlbumChiaSe, tkacs.NgayChiaSe, acs.TenAlbum, tk.TaiKhoan, tk.HoDem, tk.Ten, tk.AnhDaiDien, n.MaNhom, n.TenNhom  
						FROM taikhoan_albumchiase tkacs 
							INNER JOIN albumchiase acs ON tkacs.MaAlbumChiaSe = acs.MaAlbumChiaSe 
							INNER JOIN taikhoan tk ON tkacs.MaTaiKhoanChiaSe = tk.MaTaiKhoan
							INNER JOIN nhom n ON acs.MaNhom = n.MaNhom
						WHERE tkacs.MaTaiKhoanNhan = '".$MaTaiKhoan."'";
			$resultTaiKhoan_AlbumChiaSe = mysql_query($sqlTaiKhoan_AlbumChiaSe);
			if (mysql_num_rows($resultTaiKhoan_AlbumChiaSe) > 0) {
				while ($rowTaiKhoan_AlbumChiaSe = mysql_fetch_array($resultTaiKhoan_AlbumChiaSe)) {
					$sqlBinhLuanAnh = "SELECT MaBinhLuan FROM binhluananh WHERE MaAlbumChiaSe = '".$rowTaiKhoan_AlbumChiaSe["MaAlbumChiaSe"]."'";
					$countBinhLuanAnh = mysql_num_rows(mysql_query($sqlBinhLuanAnh));
					
					$sqlAnh = "SELECT a.Link 
							FROM anh_nhom an INNER JOIN anh a ON an.MaAnh = a.MaAnh 
							WHERE an.MaAlbumChiaSe = '".$rowTaiKhoan_AlbumChiaSe["MaAlbumChiaSe"]."' 
							ORDER BY an.MaAnh_Nhom DESC LIMIT 0,1";
					$resultAnh = mysql_query($sqlAnh) or die("Lệnh truy vấn không chính xác!");
					if (mysql_num_rows($resultAnh) == 1) {
						$rowAnh = mysql_fetch_array($resultAnh);
						
						$image = new SimpleImage();
    					$Link = $image->ChiTietAnh($rowAnh["Link"], $Width, 10000, $AlbumChiaSe);
    					$AnhDaiDien = $image->checkImage($AvatarMobile, $rowTaiKhoan_AlbumChiaSe["AnhDaiDien"]);
    					
    					// Số lượng ảnh trong album
    					$sqlAnh_Nhom = "SELECT MaAnh_Nhom FROM anh_nhom WHERE MaAlbumChiaSe = '".$rowTaiKhoan_AlbumChiaSe["MaAlbumChiaSe"]."'";
						$resultAnh_Nhom = mysql_query($sqlAnh_Nhom) or die("Lệnh truy vấn không chính xác!");
						$Count = mysql_num_rows($resultAnh_Nhom);
    					echo $rowTaiKhoan_AlbumChiaSe["MaAlbumChiaSe"] . $KyTuChiaTruongDL
    						. $rowTaiKhoan_AlbumChiaSe["TenAlbum"] . $KyTuChiaTruongDL
    						. $rowTaiKhoan_AlbumChiaSe["TaiKhoan"] . $KyTuChiaTruongDL
    						. $rowTaiKhoan_AlbumChiaSe["HoDem"] . $KyTuChiaTruongDL
    						. $rowTaiKhoan_AlbumChiaSe["Ten"] . $KyTuChiaTruongDL
    						. $Link . $KyTuChiaTruongDL
    						. $rowTaiKhoan_AlbumChiaSe["NgayChiaSe"] . $KyTuChiaTruongDL 
    						. $Count . $KyTuChiaTruongDL 
    						. $AnhDaiDien . $KyTuChiaTruongDL
    						. $rowTaiKhoan_AlbumChiaSe["MaNhom"] . $KyTuChiaTruongDL
							. $rowTaiKhoan_AlbumChiaSe["TenNhom"] . $KyTuChiaTruongDL
							. $countBinhLuanAnh . $KyTuChiaBanGhi
    						;
					}
				}
			}
			
			// //////////////////// //
			$sqlTaiKhoan_Nhom = "SELECT tkn.MaNhom, TenNhom 
							FROM taikhoan_nhom tkn INNER JOIN nhom n ON tkn.MaNhom = n.MaNhom 
							WHERE tkn.MaTaiKhoan = '".$MaTaiKhoan."' AND tkn.TrangThai = 1";
			$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
			// LẤY VỀ NHỮNG NHÓM NGƯỜI DÙNG CÓ QUYỀN THÀNH VIÊN
			// Nếu có quyền trưởng nhóm thì không có bản ghi dữ liệu trong bảng: TaiKhoan_Nhom
			while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
				$sqlAlbumChiaSe = "SELECT acs.MaAlbumChiaSe, acs.TenAlbum, tk.TaiKhoan, tk.HoDem, tk.Ten, tk.AnhDaiDien 
						FROM albumchiase acs INNER JOIN taikhoan tk ON acs.MaTaiKhoan = tk.MaTaiKhoan 
						WHERE MaNhom = '".$rowTaiKhoan_Nhom["MaNhom"]."' 
						ORDER BY acs.MaAlbumChiaSe DESC";
				$resultAlbumChiaSe = mysql_query($sqlAlbumChiaSe) or die("Lệnh truy vấn không chính xác!");
				while ($rowAlbumChiaSe = mysql_fetch_array($resultAlbumChiaSe)) {
					$sqlBinhLuanAnh = "SELECT MaBinhLuan FROM binhluananh WHERE MaAlbumChiaSe = '".$rowAlbumChiaSe["MaAlbumChiaSe"]."'";
					$countBinhLuanAnh = mysql_num_rows(mysql_query($sqlBinhLuanAnh));
					
					$sqlAnh = "SELECT a.Link, an.NgayChiaSe 
							FROM anh_nhom an INNER JOIN anh a ON an.MaAnh = a.MaAnh 
							WHERE an.MaAlbumChiaSe = '".$rowAlbumChiaSe["MaAlbumChiaSe"]."' 
							ORDER BY an.MaAnh_Nhom DESC LIMIT 0,1";
					$resultAnh = mysql_query($sqlAnh) or die("Lệnh truy vấn không chính xác!");
					if (mysql_num_rows($resultAnh) == 1) {
						$rowAnh = mysql_fetch_array($resultAnh);
						
						$image = new SimpleImage();
    					$Link = $image->ChiTietAnh($rowAnh["Link"], $Width, 10000, $AlbumChiaSe);
    					$AnhDaiDien = $image->checkImage($AvatarMobile, $rowAlbumChiaSe["AnhDaiDien"]);
    					
    					// Số lượng ảnh trong album
    					$sqlAnh_Nhom = "SELECT MaAnh_Nhom FROM anh_nhom WHERE MaAlbumChiaSe = '".$rowAlbumChiaSe["MaAlbumChiaSe"]."'";
						$resultAnh_Nhom = mysql_query($sqlAnh_Nhom) or die("Lệnh truy vấn không chính xác!");
						$Count = mysql_num_rows($resultAnh_Nhom);
    					echo $rowAlbumChiaSe["MaAlbumChiaSe"] . $KyTuChiaTruongDL
    						. $rowAlbumChiaSe["TenAlbum"] . $KyTuChiaTruongDL
    						. $rowAlbumChiaSe["TaiKhoan"] . $KyTuChiaTruongDL
    						. $rowAlbumChiaSe["HoDem"] . $KyTuChiaTruongDL
    						. $rowAlbumChiaSe["Ten"] . $KyTuChiaTruongDL
    						. $Link . $KyTuChiaTruongDL
    						. $rowAnh["NgayChiaSe"] . $KyTuChiaTruongDL 
    						. $Count . $KyTuChiaTruongDL 
    						. $AnhDaiDien . $KyTuChiaTruongDL
    						. $rowTaiKhoan_Nhom["MaNhom"] . $KyTuChiaTruongDL
							. $rowTaiKhoan_Nhom["TenNhom"] . $KyTuChiaTruongDL
							. $countBinhLuanAnh . $KyTuChiaBanGhi
    						;
					}
				}
			}
			
			
			// LẤY VỀ CÁC NHÓM NGƯỜI DÙNG CÓ QUYỀN TRƯỞNG NHÓM
			$sqlNhom = "SELECT MaNhom, TenNhom FROM nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."'";
			$resultNhom = mysql_query($sqlNhom) or die("Lệnh truy vấn không chính xác!");
			while ($rowNhom = mysql_fetch_array($resultNhom)) {
				$sqlAlbumChiaSe = "SELECT acs.MaAlbumChiaSe, acs.TenAlbum, tk.TaiKhoan, tk.HoDem, tk.Ten, tk.AnhDaiDien 
						FROM albumchiase acs INNER JOIN taikhoan tk ON acs.MaTaiKhoan = tk.MaTaiKhoan 
						WHERE MaNhom = '".$rowNhom["MaNhom"]."' 
						ORDER BY acs.MaAlbumChiaSe DESC";
				$resultAlbumChiaSe = mysql_query($sqlAlbumChiaSe) or die("Lệnh truy vấn không chính xác!");
				while ($rowAlbumChiaSe = mysql_fetch_array($resultAlbumChiaSe)) {
					$sqlBinhLuanAnh = "SELECT MaBinhLuan FROM binhluananh WHERE MaAlbumChiaSe = '".$rowAlbumChiaSe["MaAlbumChiaSe"]."'";
					$countBinhLuanAnh = mysql_num_rows(mysql_query($sqlBinhLuanAnh));
					
					$sqlAnh = "SELECT a.Link, an.NgayChiaSe 
							FROM anh_nhom an INNER JOIN anh a ON an.MaAnh = a.MaAnh 
							WHERE an.MaAlbumChiaSe = '".$rowAlbumChiaSe["MaAlbumChiaSe"]."' 
							ORDER BY an.MaAnh_Nhom DESC LIMIT 0,1";
					$resultAnh = mysql_query($sqlAnh) or die("Lệnh truy vấn không chính xác!");
					if (mysql_num_rows($resultAnh) == 1) {
						$rowAnh = mysql_fetch_array($resultAnh);
						
						$image = new SimpleImage();
    					$Link = $image->ChiTietAnh($rowAnh["Link"], $Width, 10000, $AlbumChiaSe);
    					$AnhDaiDien = $image->checkImage($AvatarMobile, $rowAlbumChiaSe["AnhDaiDien"]);
    					
    					// Số lượng ảnh trong album
    					$sqlAnh_Nhom = "SELECT MaAnh_Nhom FROM anh_nhom WHERE MaAlbumChiaSe = '".$rowAlbumChiaSe["MaAlbumChiaSe"]."'";
						$resultAnh_Nhom = mysql_query($sqlAnh_Nhom) or die("Lệnh truy vấn không chính xác!");
						$Count = mysql_num_rows($resultAnh_Nhom);
    					echo $rowAlbumChiaSe["MaAlbumChiaSe"] . $KyTuChiaTruongDL
    						. $rowAlbumChiaSe["TenAlbum"] . $KyTuChiaTruongDL
    						. $rowAlbumChiaSe["TaiKhoan"] . $KyTuChiaTruongDL
    						. $rowAlbumChiaSe["HoDem"] . $KyTuChiaTruongDL
    						. $rowAlbumChiaSe["Ten"] . $KyTuChiaTruongDL
    						. $Link . $KyTuChiaTruongDL
    						. $rowAnh["NgayChiaSe"] . $KyTuChiaTruongDL 
    						. $Count . $KyTuChiaTruongDL 
    						. $AnhDaiDien . $KyTuChiaTruongDL
    						. $rowNhom["MaNhom"] . $KyTuChiaTruongDL
							. $rowNhom["TenNhom"] . $KyTuChiaTruongDL
							. $countBinhLuanAnh . $KyTuChiaBanGhi
    						;
					}
				}
			}
		}else 
			echo $false;
		break;
	}
	case "ChiTietAlbumChiaSe": {
		$MaAlbumChiaSe = $_REQUEST["xMaAlbumChiaSePK"];
		$WidthScreen = $_REQUEST["xWidthScreen"];
    	$HeightScreen = $_REQUEST["xHeightScreen"];
		$Check = true;
		if ($MaAlbumChiaSe != "" && is_numeric($WidthScreen) && is_numeric($HeightScreen)) {
			$WidthScreen = $WidthScreen - 2;
    		$HeightScreen = $HeightScreen - 10;
			
			$sqlBinhLuan = "SELECT MaBinhLuan FROM binhluananh WHERE MaAlbumChiaSe = '".$MaAlbumChiaSe."'";
			$resultBinhLuan = mysql_query($sqlBinhLuan) or die("Lệnh truy vấn không chính xác!");
			$countBinhLuanAnh = mysql_num_rows($resultBinhLuan);
			
			//tk.TaiKhoan, tk.HoDem, tk.Ten, tk.NgaySinh, tk.Email, tk.GioiTinh, tk.DienThoai, tk.AnhDaiDien, tk.DiaChi, tk.NgayVaoTruong,
			$sql = "SELECT 	an.NgayChiaSe, a.Link, a.MaAnh, a.MoTa,  
							n.TenNhom 
					FROM albumchiase acs 
							INNER JOIN anh_nhom an ON acs.MaAlbumChiaSe = an.MaAlbumChiaSe
							INNER JOIN anh a ON an.MaAnh = a.MaAnh
							INNER JOIN nhom n ON acs.MaNhom = n.MaNhom 
					WHERE acs.MaAlbumChiaSe = '".$MaAlbumChiaSe."'";
			
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			while ($row = mysql_fetch_array($result)) {
				$image = new SimpleImage();
            	//$Link = $image->checkImage($PhotoMobile, $row["Link"]);
				$Link = $image->ChiTietAnh($row["Link"], $WidthScreen, $HeightScreen, $ChiTietAnh);
				echo $row["NgayChiaSe"] . $KyTuChiaTruongDL
					. $row["MaAnh"] . $KyTuChiaTruongDL
					. $Link . $KyTuChiaTruongDL
					. $row["MoTa"] . $KyTuChiaTruongDL
					. $row["TenNhom"] . $KyTuChiaTruongDL 
					. $countBinhLuanAnh . $KyTuChiaBanGhi
					;
			}
		}else 
			echo $false;
		break;
	}
	case "ThongTinTaiKhoan": {
		$TaiKhoan = $_REQUEST["xMaTaiKhoan"];
		if($TaiKhoan != "" ) {
			$sql = "SELECT * FROM taikhoan WHERE MaTaiKhoan='".$TaiKhoan."'";
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			$row = mysql_fetch_array($result);
			if($row != null) {
		 		echo $row["HoDem"] . $KyTuChiaTruongDL
    				. $row["Ten"] . $KyTuChiaTruongDL
    				. $row["NgaySinh"] . $KyTuChiaTruongDL
    				. $row["Email"] . $KyTuChiaTruongDL
    				. $row["GioiTinh"] . $KyTuChiaTruongDL
    				. $row["DienThoai"] . $KyTuChiaTruongDL
    				. $row["DiaChi"] . $KyTuChiaTruongDL
    				. $row["NgayTao"] . $KyTuChiaTruongDL
    				;
			}else 
				echo $false;
		}else
			echo $false;
		break;
	}
	case "TaoNhom": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$TenNhom = $_REQUEST["xTenNhom"];
		if ($MaTaiKhoan != "" && $TenNhom != "") {
			$sqlInsertNhom = "";
			$sqlMaNhom = "SELECT MaNhom FROM nhom WHERE MaNhom LIKE 'NTD%' ORDER BY MaNhom DESC LIMIT 0,1";
			$resultMaNhom = mysql_query($sqlMaNhom) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($resultMaNhom) == 1) {
				$MaNhomMoi = 0;
				$rowMaNhom = mysql_fetch_array($resultMaNhom);
				$MaNhom = substr($rowMaNhom["MaNhom"], 3);
				$MaNhom = $MaNhom + 1;
				$MaNhom = "NTD".$MaNhom;
				$sqlInsertNhom = "INSERT INTO nhom(MaNhom, TenNhom, MaTaiKhoan) VALUES ('".$MaNhom."', '".$TenNhom."', '".$MaTaiKhoan."')";
			} else {
				$sqlInsertNhom = "INSERT INTO nhom(MaNhom, TenNhom, MaTaiKhoan) VALUES ('NTD0', '".$TenNhom."', '".$MaTaiKhoan."')";
			}
			if ($sqlInsertNhom != "") {
				$resultInsertNhom = mysql_query($sqlInsertNhom) or die("Lệnh truy vấn không chính xác!");
				if ($resultInsertNhom != null) {
					$sql = "SELECT MaNhom FROM nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."' ORDER BY MaNhom DESC LIMIT 0,1";
					$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
					$row = mysql_fetch_array($result);
					echo $row["MaNhom"];
					$sql="INSERT INTO taikhoan_nhom(MaTaiKhoan,MaNhom,TrangThai) values('$MaTaiKhoan','".$row['MaNhom']."',1)";
					mysql_query($sql);
				} else 
					echo $false;
			} else 
				echo $false;
		} else 
			echo $false;
		break;
	}
	case "ChiaSeBaiVietKhac": {
		$MaBaiViet_Nhom = $_REQUEST["xMaBaiViet_Nhom"];
		$Count = $_REQUEST["Count"];
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$Check = true;
		if ($MaBaiViet_Nhom != "" && $Count != "" && $MaTaiKhoan != "") {
			$sqlMaBaiViet = "SELECT MaBaiViet FROM baiviet_nhom WHERE MaBaiViet_Nhom = '".$MaBaiViet_Nhom."'";
			$resultMaBaiViet = mysql_query($sqlMaBaiViet) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($resultMaBaiViet) == 1) {
				$rowMaBaiViet = mysql_fetch_array($resultMaBaiViet);
				$MaBaiViet = $rowMaBaiViet["MaBaiViet"];
				
				for ($i = 0; $i < $Count; $i++) {
					$MaNhom = $_REQUEST["CMN".$i];
					$sql = "INSERT INTO baiviet_nhom(MaTaiKhoan, MaNhom, MaBaiViet) VALUES('".$MaTaiKhoan."','".$MaNhom."','".$MaBaiViet."')";
					$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
					if ($result == null)
						$Check = false;
				}
				if ($Check)
					echo $true;
				else 
					echo $false;
			} else 
				echo $false;
		}else
			echo $false;
		break;
	}
	case "ChiaSeAnhKhac": {
		$CountNhom = $_REQUEST["CountNhom"];
		$MaAlbumChiaSe = $_REQUEST["xMaAlbumChiaSe"];
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$Check = true;
		if ($CountNhom != ""  && $CountNhom > 0 && $MaTaiKhoan != "" && $MaAlbumChiaSe != "") {
			for ($i = 0; $i < $CountNhom; $i++) {
				$MaNhom = $_REQUEST["CMN".$i]; // CMN = Client Mã Nhóm
				
				// TẠO ALBUM CHIA SẺ
				$sqlAlbumCS = "INSERT INTO albumchiase(MaTaiKhoan, MaNhom, TenAlbum) VALUES('".$MaTaiKhoan."', '".$MaNhom."', '')";
				$resultAlbumCS = mysql_query($sqlAlbumCS) or die("Lệnh truy vấn không chính xác!");
				if ($resultAlbumCS != null) {
					
					// Lấy về MaAlbumChiaSe vừa tạo
					$sqlSelectAlbumCS = "SELECT MaAlbumChiaSe FROM albumchiase 
								WHERE MaTaiKhoan = '".$MaTaiKhoan."' 
								ORDER BY MaAlbumChiaSe DESC LIMIT 0,1";
					$resultSelectAlbumCS = mysql_query($sqlSelectAlbumCS) or die("Lệnh truy vấn không chính xác!");
					if (mysql_num_rows($resultSelectAlbumCS) > 0) {
						$rowSelectAlbumCS = mysql_fetch_array($resultSelectAlbumCS); // Mã Album chia sẻ
						
						// TẠO ANH_NHOM: Nếu có nhiều ảnh thì for() ở đây
						$sqlMaAnh = "SELECT MaAnh FROM anh_nhom WHERE MaAlbumChiaSe = '".$MaAlbumChiaSe."'";
						$resultMaAnh = mysql_query($sqlMaAnh) or die("Lệnh truy vấn không chính xác!");
						while ($rowMaAnh = mysql_fetch_array($resultMaAnh)) {
							$MaAnh = $rowMaAnh["MaAnh"];
							$sqlAnh_Nhom = "INSERT INTO anh_nhom(MaTaiKhoan, MaNhom, MaAnh, MaAlbumChiaSe) VALUES('".$MaTaiKhoan."', '".$MaNhom."', '".$MaAnh."', '".$rowSelectAlbumCS["MaAlbumChiaSe"]."')";
							$resultAnh_Nhom = mysql_query($sqlAnh_Nhom) or die("Lệnh truy vấn không chính xác!");
							if ($resultAnh_Nhom == null)
								$Check = false;
						}
					} else 
						$Check = false;
				}else 
					$Check = false;
			}
			if ($Check)
				echo $true;
			else 
				echo $false . "1";
		} else
			echo $false . "2";
		break;
	}
	case "DanhSachThanhVienNhom": {
		$MaNhom = $_REQUEST["xMaNhom"];
		if ($MaNhom != "") {
			$sqlTruongNhom = "SELECT tk.TaiKhoan, tk.MaTaiKhoan, n.NgayTao
					FROM nhom n INNER JOIN taikhoan tk ON n.MaTaiKhoan = tk.MaTaiKhoan  
					WHERE MaNhom = '".$MaNhom."'";
			$resultTruongNhom = mysql_query($sqlTruongNhom) or die("Lệnh truy vấn không chính xác! 12");
			if (mysql_num_rows($resultTruongNhom) > 0) {
				$rowTruongNhom = mysql_fetch_array($resultTruongNhom);
				/*$image = new SimpleImage();
    			$AnhDaiDien = $image->checkImage($AvatarMobile, $rowTruongNhom["AnhDaiDien"]);*/
				$MaTruongNhom = $rowTruongNhom["MaTaiKhoan"];
    			echo $rowTruongNhom["MaTaiKhoan"] . $KyTuChiaTruongDL
					. $rowTruongNhom["TaiKhoan"] . $KyTuChiaTruongDL 
    				. $rowTruongNhom["NgayTao"] . $KyTuChiaTruongDL
    				;
			}
			$sqlThanhVien = "SELECT tk.MaTaiKhoan, tk.TaiKhoan, tkn.NgayVaoNhom
					FROM taikhoan_nhom tkn INNER JOIN taikhoan tk ON tkn.MaTaiKhoan = tk.MaTaiKhoan 
					WHERE tkn.MaNhom = '".$MaNhom."' AND tkn.TrangThai = 1 AND tkn.MaTaiKhoan != '".$MaTruongNhom."'";
			$resultThanhVien = mysql_query($sqlThanhVien) or die("Lệnh truy vấn không chính xác! 21");
			if (mysql_num_rows($resultThanhVien) > 0) {
				while ($rowThanhVien = mysql_fetch_array($resultThanhVien)) {
					/*$image = new SimpleImage();
    				$AnhDaiDien = $image->checkImage($AvatarMobile, $rowThanhVien["AnhDaiDien"]);*/
    				echo $rowThanhVien["MaTaiKhoan"] . $KyTuChiaTruongDL
						. $rowThanhVien["TaiKhoan"] . $KyTuChiaTruongDL 
	    				. $rowThanhVien["NgayVaoNhom"] . $KyTuChiaTruongDL
	    				; 
				}
			}
		} else 
			echo $false;
		break;
	}
	case "TaoTaiKhoan_BaiViet_Nhom": {
		$MaTaiKhoanChiaSe = $_REQUEST["xMaTaiKhoanChiaSe"];
		$MaTaiKhoanNhan = $_REQUEST["xMaTaiKhoanNhan"];
		$MaBaiViet_Nhom = $_REQUEST["xMaBaiViet_Nhom"];
		if ($MaTaiKhoanChiaSe != "" && $MaTaiKhoanNhan != "" && $MaBaiViet_Nhom != "") {
			$sqlInsert = "INSERT INTO taikhoan_baiviet_nhom(MaTaiKhoanChiaSe, MaTaiKhoanNhan, MaBaiViet_Nhom) VALUES('".$MaTaiKhoanChiaSe."', '".$MaTaiKhoanNhan."', '".$MaBaiViet_Nhom."')";
			$result = mysql_query($sqlInsert) or die("Lệnh truy vấn không chính xác!");
			echo ($result != null) ? $true : $false;
		} else 
			echo $false;
		break;
	}
	case "TaoTaiKhoan_AlbumChiaSe": {
		$MaTaiKhoanChiaSe = $_REQUEST["xMaTaiKhoanChiaSe"];
		$MaTaiKhoanNhan = $_REQUEST["xMaTaiKhoanNhan"];
		$MaAlbumChiaSe = $_REQUEST["xMaAlbumChiaSe"];
		if ($MaTaiKhoanChiaSe != "" && $MaTaiKhoanNhan != "" && $MaAlbumChiaSe != "") {
			$sqlInsert = "INSERT INTO taikhoan_albumchiase(MaTaiKhoanChiaSe, MaTaiKhoanNhan, MaAlbumChiaSe) VALUES('".$MaTaiKhoanChiaSe."', '".$MaTaiKhoanNhan."', '".$MaAlbumChiaSe."')";
			$result = mysql_query($sqlInsert) or die("Lệnh truy vấn không chính xác!");
			echo ($result != null) ? $true : $false;
		} else 
			echo $false;
		break;
	}
	case "DanhSachChuDe": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		if ($MaTaiKhoan != "") {
			$sqlChuDe = "SELECT MaChuDe, TenChuDe, NgayCapNhat FROM chude WHERE MaTaiKhoan = '".$MaTaiKhoan."'";
			$resultChuDe = mysql_query($sqlChuDe);
			if (mysql_num_rows($resultChuDe) > 0) {
				while ($rowChuDe = mysql_fetch_array($resultChuDe)) {
					$sqlBaiViet_ChuDe = "SELECT MaBaiViet_ChuDe FROM baiviet_chude WHERE MaChuDe = '".$rowChuDe["MaChuDe"]."'";
					$resultBaiViet_ChuDe = mysql_query($sqlBaiViet_ChuDe);
					$Count = mysql_num_rows($resultBaiViet_ChuDe);
					echo $rowChuDe["MaChuDe"] . $KyTuChiaTruongDL
						. $rowChuDe["TenChuDe"] . $KyTuChiaTruongDL
						. $Count . $KyTuChiaTruongDL
						. $rowChuDe["NgayCapNhat"] . $KyTuChiaBanGhi;
				}
			} else 
				echo $null;
		} else 
			echo $false;
		break;
	}
	case "TaoChuDe": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
		$TenChuDe = $_REQUEST["xTenChuDe"];
		if ($MaTaiKhoan != "" && $TenChuDe != "") {
			$sqlInsert = "INSERT INTO chude(MaTaiKhoan, TenChuDe, NgayCapNhat) VALUES ('".$MaTaiKhoan."', '".$TenChuDe."', curdate())";
			$resultInsert = mysql_query($sqlInsert);
			if ($resultInsert != null) {
				$sqlChuDe = "SELECT MaChuDe FROM chude WHERE MaTaiKhoan = '".$MaTaiKhoan."' ORDER BY MaChuDe DESC LIMIT 0,1";
				$resultChuDe = mysql_query($sqlChuDe);
				if (mysql_num_rows($resultChuDe) == 1) {
					$rowChuDe = mysql_fetch_array($resultChuDe);
					echo $rowChuDe["MaChuDe"];
				} else
					echo $false;
			} else 
				echo $false;		
		} else 
			echo $false;
		break;
	}
	case "TaoBaiViet_ChuDe":{
		$MaBaiViet_Nhom = $_REQUEST["xMaBaiViet_Nhom"];
		$MaChuDe = $_REQUEST["xMaChuDe"];
		if ($MaBaiViet_Nhom != "" && $MaChuDe != "") {
			$sqlBaiViet_Nhom = "SELECT MaBaiViet FROM baiviet_nhom WHERE MaBaiViet_Nhom = '".$MaBaiViet_Nhom."'";
			$resultBaiViet_Nhom = mysql_query($sqlBaiViet_Nhom);
			if (mysql_num_rows($resultBaiViet_Nhom) == 1) {
				$rowBaiViet_Nhom = mysql_fetch_array($resultBaiViet_Nhom);
				$sqlInsert = "INSERT INTO baiviet_chude(MaChuDe, MaBaiViet) VALUES ('".$MaChuDe."', '".$rowBaiViet_Nhom["MaBaiViet"]."')";
				$resultInsert = mysql_query($sqlInsert);
				if ($resultInsert != null)
					echo $true;
				else 
					echo $false;
			} else 
				echo $false;
		} else 
			echo $false;
		break;
	}
	case "BaiVietTongHop": {
		$MaChuDe = $_REQUEST["xMaChuDe"];
		if ($MaChuDe != "") {
			$sql = "SELECT bv.MaBaiViet, bv.TieuDe, bv.NoiDung, bvcd.NgayTao 
					FROM baiviet_chude bvcd INNER JOIN baiviet bv ON bvcd.MaBaiViet = bv.MaBaiViet 
					WHERE MaChuDe = '".$MaChuDe."' 
					ORDER BY MaBaiViet_ChuDe DESC";
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($result) > 0) {
				while ($row = mysql_fetch_array($result)) {
					echo $row["MaBaiViet"] . $KyTuChiaTruongDL 
						. $row["TieuDe"] . $KyTuChiaTruongDL 
						. $row["NoiDung"] . $KyTuChiaTruongDL 
						. $row["NgayTao"] . $KyTuChiaBanGhi;
				}
			} else 
				echo $null;
		}else 
			echo $false;
		break;
	}
	case "SuaNhom": {
		$MaNhom = $_REQUEST["xMaNhom"];
        $TenNhom = $_REQUEST["xTenNhom"];
        $MoTa = $_REQUEST['xMoTa'];
        $QuyTac = $_REQUEST["xLuat"];
        if ($TenNhom != "" && $MaNhom != "") {
                $sql = "UPDATE nhom SET "; 
                $sql .= "TenNhom = '".$TenNhom."', ";
                $sql .= "MoTa = '".$MoTa."', ";
                $sql .= "QuyTac = '".$QuyTac."' ";
                $sql .= " WHERE MaNhom = '".$MaNhom."'";
				//echo $sql;
                $result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
				echo ($result != null) ? $true : $false;
        } else
            echo $false;
        break;
    }
    case "DoiMatKhau": {
		$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
        $MatKhau = $_REQUEST["xMatKhau"];
        if ($MaTaiKhoan != "" && $MatKhau != "") {
                $sql = "UPDATE taikhoan SET "
				."MatKhau = '".md5($MatKhau)."' "
				."WHERE MaTaiKhoan = '".$MaTaiKhoan."'"
				;
                $result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
                if ($result != null)
                    echo $true;
                else
                    echo $false;
        } else
            echo $false;
        break;
    }
	default: echo "<h1 style=\"font-family: 'Times New Roman';\">Not Found</h1>
			<p style=\"font-family: 'Times New Roman'; font-size: medium;\">The requested URL was not found on this server.</p>
			<hr />
			<address style=\"font-family: 'Times New Roman'; font-size: medium;\">Apache/2.2.21 (CentOS) Server</address>";
}
}while($re);
	
?>