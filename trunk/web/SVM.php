<?php 
if(isset($_REQUEST['sid'])) session_id($_REQUEST['sid']);
session_start();
include 'Config.php';
$Case = $_REQUEST["CVM"];
$false = "false";
$true = "true";
$null = "null";
$KyTuChiaTruongDL = "||||";
function get_user_info($user_id)
{
	$sql = "SELECT * FROM taikhoan WHERE mataikhoan = '".$user_id."'";
	$result = mysql_query($sql);
	$row=mysql_fetch_array($result);
	return $row;
}
if($Case=='DangNhap')
{
			$TaiKhoan = $_REQUEST["xTaiKhoan"];
			$MatKhau = $_REQUEST["xMatKhau"];
			if($TaiKhoan != "" && $MatKhau != "") {
				$sql = "SELECT MaTaiKhoan, TaiKhoan, MatKhau FROM taikhoan WHERE TaiKhoan='".$TaiKhoan."'";
				$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
				$row = mysql_fetch_array($result);
				if($row != null) {
					if (md5($MatKhau) == $row["MatKhau"]){
						$_SESSION['MaTaiKhoan'] = $row['MaTaiKhoan'];
						echo $row["MaTaiKhoan"] . $KyTuChiaTruongDL . session_id();
					}
					else 
						echo $false;
				}else 
					echo $false;
			}else
				echo $false;
}
else if(isset($_SESSION['MaTaiKhoan']))
{
	$_REQUEST['xMaTaiKhoan']=$_SESSION['MaTaiKhoan'];
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
					$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
					echo ($result != null) ? $true : $false;
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
		case "DanhSachYeuCauThamGiaNhom": {
			$MaNhom = $_REQUEST["xMaNhom"];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			if ($MaNhom != "") {
				$sqlTaiKhoan_Nhom = "SELECT tkn.MaTaiKhoan_Nhom, tk.TaiKhoan, tk.MaTaiKhoan, n.MaNhom, n.TenNhom, tkn.NgayVaoNhom 
							FROM taikhoan_nhom tkn 
								INNER JOIN taikhoan tk ON tkn.MaTaiKhoan = tk.MaTaiKhoan 
								INNER JOIN nhom n ON tkn.MaNhom = n.MaNhom 
							WHERE tkn.TrangThai = 0 AND tkn.MaNhom = '".$MaNhom."' LIMIT $Id, $PageSize";
				$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
				if (mysql_num_rows($resultTaiKhoan_Nhom) > 0) {
					while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
						echo $rowTaiKhoan_Nhom["MaTaiKhoan_Nhom"] . $KyTuChiaTruongDL
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
		case "DanhSachYeuCauThamGiaNhomCuaBan": {
			$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
			$CountTaiKhoan_Nhom = 0;
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			if ($MaTaiKhoan != "") {
				$sqlNhom = "SELECT MaNhom FROM nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."'";
				$resultNhom = mysql_query($sqlNhom) or die("Lệnh truy vấn không chính xác!");
				if (mysql_num_rows($resultNhom) > 0) {
					while ($rowNhom = mysql_fetch_array($resultNhom)) {
						$MaNhom = $rowNhom["MaNhom"];
						$sqlTaiKhoan_Nhom = "SELECT tkn.MaTaiKhoan_Nhom, tk.TaiKhoan, tk.MaTaiKhoan, n.MaNhom, n.TenNhom, tkn.NgayVaoNhom FROM taikhoan_nhom tkn INNER JOIN taikhoan tk ON tkn.MaTaiKhoan = tk.MaTaiKhoan INNER JOIN nhom n ON tkn.MaNhom = n.MaNhom WHERE tkn.TrangThai = 0 AND tkn.MaNhom = '".$MaNhom."' AND tkn.MaTaiKhoan <>'".$MaTaiKhoan."' LIMIT $Id, $PageSize";
						$resultTaiKhoan_Nhom = mysql_query($sqlTaiKhoan_Nhom) or die("Lệnh truy vấn không chính xác!");
						if (mysql_num_rows($resultTaiKhoan_Nhom) > 0) {
							$CountTaiKhoan_Nhom += mysql_num_rows($resultTaiKhoan_Nhom);
							while ($rowTaiKhoan_Nhom = mysql_fetch_array($resultTaiKhoan_Nhom)) {
								echo $rowTaiKhoan_Nhom["MaTaiKhoan_Nhom"] . $KyTuChiaTruongDL
									. $rowTaiKhoan_Nhom["MaTaiKhoan"] . $KyTuChiaTruongDL
									. $rowTaiKhoan_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
									. $rowTaiKhoan_Nhom["MaNhom"] . $KyTuChiaTruongDL
									. $rowTaiKhoan_Nhom["TenNhom"] . $KyTuChiaTruongDL
									. $rowTaiKhoan_Nhom["NgayVaoNhom"]. $KyTuChiaTruongDL
								;
							}
						}
					}
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
		case "HuyTuCach": {
			$MaNhom = $_REQUEST["xMaNhom"];
			$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
			$sql = "DELETE FROM taikhoan_nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."' AND MaNhom = '".$MaNhom."'";
			$result = mysql_query($sql) or die("Lệnh truy vấn không chính xác!");
			echo ($result != null) ? $true : $false;
			break;
		}
		case "DanhSachThanhVienNhom": {
			$MaNhom = $_REQUEST["xMaNhom"];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			$TuKhoa = $_REQUEST["xTuKhoa"];
			if ($MaNhom != "") {
				$sqlTruongNhom = "SELECT tk.TaiKhoan, tk.MaTaiKhoan, n.NgayTao FROM nhom n INNER JOIN taikhoan tk ON n.MaTaiKhoan = tk.MaTaiKhoan WHERE MaNhom = '".$MaNhom."'";
				$resultTruongNhom = mysql_query($sqlTruongNhom) or die("Lệnh truy vấn không chính xác!");
				if (mysql_num_rows($resultTruongNhom) > 0) {
					$rowTruongNhom = mysql_fetch_array($resultTruongNhom);
					$MaTruongNhom = $rowTruongNhom["MaTaiKhoan"];
					echo $rowTruongNhom["MaTaiKhoan"] . $KyTuChiaTruongDL
						. $rowTruongNhom["TaiKhoan"] . $KyTuChiaTruongDL 
						. $rowTruongNhom["NgayTao"] . $KyTuChiaTruongDL
						;
				}
				$sqlThanhVien = "SELECT tk.MaTaiKhoan, tk.TaiKhoan, tkn.NgayVaoNhom
						FROM taikhoan_nhom tkn INNER JOIN taikhoan tk ON tkn.MaTaiKhoan = tk.MaTaiKhoan WHERE tkn.MaNhom = '".$MaNhom."' AND tkn.TrangThai = 1 AND tkn.MaTaiKhoan != '".$MaTruongNhom."' AND (tk.TaiKhoan LIKE '%$TuKhoa%' OR tk.HoDem LIKE '%$TuKhoa%' OR tk.Ten LIKE '%$TuKhoa%') LIMIT $Id, $PageSize";
				$resultThanhVien = mysql_query($sqlThanhVien) or die("Lệnh truy vấn không chính xác!");
				if (mysql_num_rows($resultThanhVien) > 0) {
					while ($rowThanhVien = mysql_fetch_array($resultThanhVien)) {
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
		case "TimKiemBaiVietNhom": {
			$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$TuKhoa = $_REQUEST["xTuKhoa"];
			$Id = $PageId * $PageSize;
			if ($MaTaiKhoan != "") {
				$sqlBaiViet_Nhom = "SELECT bvn.MaBaiViet_Nhom,bvn.MaBaiViet, bv.TieuDe, bv.NoiDung, bv.NgayTao, bvn.NgayChiaSe, tk.MaTaiKhoan, tk.TaiKhoan,bvn.MaTaiKhoan AS MaTaiKhoanChiaSe, n.TenNhom FROM baiviet_nhom bvn INNER JOIN baiviet bv ON bvn.MaBaiViet = bv.MaBaiViet INNER JOIN taikhoan tk ON bv.MaTaiKhoan = tk.MaTaiKhoan INNER JOIN nhom n ON n.MaNhom = bvn.MaNhom WHERE bvn.MaNhom IN (SELECT MaNhom FROM taikhoan_nhom WHERE MaTaiKhoan = '$MaTaiKhoan' AND TrangThai=1) AND (bv.TieuDe LIKE '%$TuKhoa%' OR bv.NoiDung LIKE '%$TuKhoa%' OR tk.TaiKhoan LIKE '%$TuKhoa%' OR n.TenNhom LIKE '$TuKhoa%') ORDER BY MaBaiViet_Nhom DESC LIMIT $Id, $PageSize";
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
							. $rowBaiViet_Nhom["TaiKhoan"] . $KyTuChiaTruongDL
							. $TaiKhoanChiaSe["TaiKhoan"] . $KyTuChiaTruongDL
							. $rowBaiViet_Nhom["TenNhom"] . $KyTuChiaTruongDL
							;
					}
				} else 
					echo $null;
			}else 
				echo $false;
			break;
		}
		case "TimKiemThanhVien": {
			$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			$TuKhoa = $_REQUEST["xTuKhoa"];
			$sqlThanhVien = "SELECT DISTINCT tk.MaTaiKhoan, tk.TaiKhoan FROM taikhoan_nhom tkn INNER JOIN taikhoan tk ON tkn.MaTaiKhoan = tk.MaTaiKhoan WHERE tkn.MaNhom IN (SELECT MaNhom FROM taikhoan_nhom WHERE MaTaiKhoan = '$MaTaiKhoan' AND TrangThai=1) AND (tk.TaiKhoan LIKE '%$TuKhoa%' OR tk.HoDem LIKE '%$TuKhoa%' OR tk.Ten LIKE '%$TuKhoa%') LIMIT $Id, $PageSize";
			$resultThanhVien = mysql_query($sqlThanhVien) or die("Lệnh truy vấn không chính xác!");
			if (mysql_num_rows($resultThanhVien) > 0) {
				while ($rowThanhVien = mysql_fetch_array($resultThanhVien)) {
					echo $rowThanhVien["MaTaiKhoan"] . $KyTuChiaTruongDL
						. $rowThanhVien["TaiKhoan"] . $KyTuChiaTruongDL 
						; 
				}
			}
			else echo $false;
			break;
		}
		case "TimKiemNhom": {
			$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
			$TuKhoa = $_REQUEST['xTuKhoa'];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			if ($MaTaiKhoan != "") {
				$sqlTaiKhoan_Nhom = "SELECT n.MaNhom,n.TenNhom, tk.MaTaiKhoan, tk.TaiKhoan,n.NgayTao  FROM nhom n INNER JOIN taikhoan tk ON tk.MaTaiKhoan = n.MaTaiKhoan WHERE TenNhom LIKE '%".$TuKhoa."%' AND MaNhom NOT IN (SELECT MaNhom FROM taikhoan_nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."') LIMIT $Id, $PageSize";
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
		case "NhomBanLaThanhVien": {
			$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			$TenNhom = $_REQUEST["xTenNhom"];
			$TaiKhoan = $_REQUEST["xTaiKhoan"];
			if ($MaTaiKhoan != "") {
				$sqlTaiKhoan_Nhom = "SELECT tkn.MaNhom, n.TenNhom, n.MaTaiKhoan, tk.TaiKhoan FROM (SELECT MaNhom FROM taikhoan_nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."' and TrangThai = 1) tkn INNER JOIN nhom n ON n.MaNhom = tkn.MaNhom INNER JOIN taikhoan tk ON tk.MaTaiKhoan = n.MaTaiKhoan WHERE n.TenNhom LIKE '%$TenNhom%' AND tk.TaiKhoan LIKE '%$TaiKhoan%' LIMIT $Id, $PageSize";
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
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			$TenNhom = $_REQUEST["xTenNhom"];
			if ($MaTaiKhoan != "") {
				$sqlTaiKhoan_Nhom = "SELECT n.MaNhom, n.TenNhom, n.MaTaiKhoan, tk.TaiKhoan FROM nhom n INNER JOIN taikhoan tk ON tk.MaTaiKhoan = n.MaTaiKhoan WHERE tk.MaTaiKhoan = '".$MaTaiKhoan."' AND n.TenNhom LIKE '%$TenNhom%' LIMIT $Id, $PageSize";
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
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			$TenNhom = $_REQUEST["xTenNhom"];
			$TaiKhoan = $_REQUEST["xTaiKhoan"];
			if ($MaTaiKhoan != "") {
				$sqlTaiKhoan_Nhom = "SELECT tkn.MaNhom, n.TenNhom, n.MaTaiKhoan, tk.TaiKhoan FROM (SELECT MaNhom FROM taikhoan_nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."' and TrangThai = 1 and MaNhom NOT IN(SELECT MaNhom FROM nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."'))tkn INNER JOIN nhom n ON n.MaNhom = tkn.MaNhom INNER JOIN taikhoan tk ON tk.MaTaiKhoan = n.MaTaiKhoan WHERE n.TenNhom LIKE '%$TenNhom%' AND tk.TaiKhoan LIKE '%$TaiKhoan%' LIMIT $Id, $PageSize";
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
		case "NhomChuaChiaSe": {
			$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
			$MaBaiViet = $_REQUEST["xMaBaiViet"];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			if ($MaTaiKhoan != "" && $MaBaiViet != "") {
				$sqlTaiKhoan_Nhom = "SELECT tkn.MaNhom, n.TenNhom, n.MaTaiKhoan, tk.TaiKhoan FROM (SELECT MaNhom FROM taikhoan_nhom WHERE MaTaiKhoan = '".$MaTaiKhoan."' and TrangThai = 1) tkn INNER JOIN nhom n ON n.MaNhom = tkn.MaNhom INNER JOIN taikhoan tk ON tk.MaTaiKhoan = n.MaTaiKhoan AND n.MaNhom NOT IN (SELECT MaNhom FROM baiviet_nhom WHERE MaBaiViet = '".$MaBaiViet."' AND MaTaiKhoan ='".$MaTaiKhoan."') LIMIT $Id, $PageSize";
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
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			if ($MaTaiKhoan != "" && $MaBaiViet != "") {
				$sqlTaiKhoan_Nhom ="SELECT bvn.MaNhom, n.TenNhom, bvn.MaTaiKhoan, tk.TaiKhoan FROM baiviet_nhom bvn INNER JOIN nhom n ON n.MaNhom = bvn.MaNhom INNER JOIN taikhoan tk ON tk.MaTaiKhoan = bvn.MaTaiKhoan WHERE bvn.MaBaiViet = '".$MaBaiViet."' AND bvn.MaTaiKhoan = '".$MaTaiKhoan."' LIMIT $Id, $PageSize";
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
		case "DanhSachBaiVietTheoNhom": {
			$MaNhom = $_REQUEST["xMaNhom"];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$TieuDe = $_REQUEST["xTieuDe"];
			$NoiDung = $_REQUEST["xNoiDung"];
			$TaiKhoan = $_REQUEST["xTaiKhoan"];
			$Id = $PageId * $PageSize;
			if ($MaNhom != "") {
				$sqlBaiViet_Nhom = "SELECT bvn.MaBaiViet_Nhom,bvn.MaBaiViet, bv.TieuDe, bv.NoiDung, bv.NgayTao, bvn.NgayChiaSe, tk.MaTaiKhoan, tk.TaiKhoan,bvn.MaTaiKhoan AS MaTaiKhoanChiaSe FROM baiviet_nhom bvn INNER JOIN baiviet bv ON bvn.MaBaiViet = bv.MaBaiViet INNER JOIN taikhoan tk ON bv.MaTaiKhoan = tk.MaTaiKhoan WHERE bvn.MaNhom = '".$MaNhom."' AND bv.TieuDe LIKE '%$TieuDe%' AND bv.NoiDung LIKE '%$NoiDung%' AND tk.TaiKhoan LIKE '%$TaiKhoan%' ORDER BY MaBaiViet_Nhom DESC LIMIT $Id, $PageSize";
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
			$DanhSachMaNhom = explode("|", $_REQUEST['xMaNhom']);
			$len = count($DanhSachMaNhom);
			for($i=0; $i<$len; $i++)
			{
				$MaNhom=$DanhSachMaNhom[$i];
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
		case "ChiTietBaiViet": {
			$MaBaiViet = $_REQUEST["xMaBaiViet"];
			if ($MaBaiViet != "") {
				$sqlSelect = "SELECT TieuDe, NoiDung, NgayTao FROM baiviet 
						WHERE MaBaiViet = '".$MaBaiViet."'";
				$resultSelect = mysql_query($sqlSelect) or die("Lệnh truy vấn không chính xác!");
				$row = mysql_fetch_array($resultSelect);
				if ($row != null)
					echo $row["TieuDe"] . $KyTuChiaTruongDL 
						. $row["NoiDung"]. $KyTuChiaTruongDL
						. $row["NgayTao"];
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
		case "DanhSachBaiVietMoi": {
			$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			$TieuDe = $_REQUEST["xTieuDe"];
			$NoiDung = $_REQUEST["xNoiDung"];
			$TaiKhoan = $_REQUEST["xTaiKhoan"];
			if ($MaTaiKhoan != "") {
				$sqlBaiViet_Nhom = "SELECT bvn.MaBaiViet_Nhom,bvn.MaBaiViet, bv.TieuDe, bv.NoiDung, bv.NgayTao, bvn.NgayChiaSe, tk.MaTaiKhoan, tk.TaiKhoan,bvn.MaTaiKhoan AS MaTaiKhoanChiaSe FROM baiviet_nhom bvn INNER JOIN baiviet bv ON bvn.MaBaiViet = bv.MaBaiViet INNER JOIN taikhoan tk ON bv.MaTaiKhoan = tk.MaTaiKhoan INNER JOIN baivietmoi bvm ON bvm.MaBaiViet_Nhom=bvn.MaBaiViet_Nhom WHERE bvm.MaTaiKhoan='$MaTaiKhoan' AND bvn.TrangThai = 1 AND bv.TieuDe LIKE '%$TieuDe%' AND bv.NoiDung LIKE '%$NoiDung%' AND tk.TaiKhoan LIKE '%$TaiKhoan%' ORDER BY MaBaiVietMoi DESC LIMIT $Id, $PageSize";
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
		case "BaiVietBanTaoVaChiaSe": {
			$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			$TieuDe = $_REQUEST["xTieuDe"];
			$NoiDung = $_REQUEST["xNoiDung"];
			if ($MaTaiKhoan != "") {
				$sqlBaiViet_Nhom = "SELECT DISTINCT bv.MaBaiViet, TieuDe, NoiDung, bv.NgayTao, bv.MaTaiKhoan, tk.TaiKhoan FROM baiviet_nhom bvn INNER JOIN baiviet bv ON bv.MaBaiViet = bvn.MaBaiViet INNER JOIN taikhoan tk ON tk.MaTaiKhoan=bv.MaTaiKhoan WHERE bvn.MaTaiKhoan = '$MaTaiKhoan' AND bv.MaTaiKhoan = '$MaTaiKhoan'  AND bv.TieuDe LIKE '%$TieuDe%' AND bv.NoiDung LIKE '%$NoiDung%' ORDER BY bv.MaBaiViet DESC LIMIT $Id, $PageSize";
				$resultBaiViet_Nhom = mysql_query($sqlBaiViet_Nhom) or die("Lệnh truy vấn không chính xác!");
				if (mysql_num_rows($resultBaiViet_Nhom) > 0) {
					while ($rowBaiViet_Nhom = mysql_fetch_array($resultBaiViet_Nhom)) {
						//6 truong
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
		case "BaiVietNguoiKhacChiaSe": {
			$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			$TieuDe = $_REQUEST["xTieuDe"];
			$NoiDung = $_REQUEST["xNoiDung"];
			$TaiKhoan = $_REQUEST["xTaiKhoan"];
			if ($MaTaiKhoan != "") {
				$sqlBaiViet_Nhom = "SELECT DISTINCT bv.MaBaiViet, TieuDe, NoiDung, bv.NgayTao, bv.MaTaiKhoan, tk.TaiKhoan FROM baiviet_nhom bvn INNER JOIN baiviet bv ON bv.MaBaiViet = bvn.MaBaiViet INNER JOIN taikhoan tk ON tk.MaTaiKhoan=bv.MaTaiKhoan WHERE bvn.MaTaiKhoan <> '$MaTaiKhoan' AND bv.MaTaiKhoan <> '$MaTaiKhoan' AND bv.TieuDe LIKE '%$TieuDe%' AND bv.NoiDung LIKE '%$NoiDung%' AND tk.TaiKhoan LIKE '%$TaiKhoan%' AND bvn.MaNhom IN (SELECT MaNhom FROM taikhoan_nhom WHERE MaTaiKhoan = '$MaTaiKhoan' AND TrangThai = 1) ORDER BY bv.MaBaiViet DESC LIMIT $Id, $PageSize";
				$resultBaiViet_Nhom = mysql_query($sqlBaiViet_Nhom) or die("Lệnh truy vấn không chính xác!");
				if (mysql_num_rows($resultBaiViet_Nhom) > 0) {
					while ($rowBaiViet_Nhom = mysql_fetch_array($resultBaiViet_Nhom)) {
						//6 truong
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
		case "BaiVietBanChuaChiaSe": {
			$MaTaiKhoan = $_REQUEST["xMaTaiKhoan"];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			$TieuDe = $_REQUEST["xTieuDe"];
			$NoiDung = $_REQUEST["xNoiDung"];
			if ($MaTaiKhoan != "") {
				$sqlBaiViet_Nhom = "SELECT MaBaiViet, TieuDe, NoiDung, bv.NgayTao, bv.MaTaiKhoan, tk.TaiKhoan FROM baiviet bv INNER JOIN taikhoan tk ON tk.MaTaiKhoan=bv.MaTaiKhoan WHERE bv.MaTaiKhoan = '$MaTaiKhoan' AND bv.TieuDe LIKE '%$TieuDe%' AND bv.NoiDung LIKE '%$NoiDung%' AND MaBaiViet NOT IN (SELECT DISTINCT MaBaiViet FROM baiviet_nhom WHERE MaTaiKhoan = '$MaTaiKhoan') ORDER BY MaBaiViet DESC LIMIT $Id, $PageSize";
				$resultBaiViet_Nhom = mysql_query($sqlBaiViet_Nhom) or die("Lệnh truy vấn không chính xác!");
				if (mysql_num_rows($resultBaiViet_Nhom) > 0) {
					while ($rowBaiViet_Nhom = mysql_fetch_array($resultBaiViet_Nhom)) {
						//6 truong
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
		case "DanhSachBinhLuanBaiViet": {
			$MaBaiViet_Nhom = $_REQUEST["xMaBaiViet_Nhom"];
			$PageSize = $_REQUEST["xPageSize"];
			$PageId = $_REQUEST["xPageId"];
			$Id = $PageId * $PageSize;
			if ($MaBaiViet_Nhom != null) {
				$sql = "SELECT MaBinhLuan, NoiDung, tk.MaTaiKhoan, tk.TaiKhoan, blbv.NgayTao 
						FROM binhluanbaiviet blbv INNER JOIN taikhoan tk ON blbv.MaTaiKhoan = tk.MaTaiKhoan WHERE MaBaiViet_Nhom = '".$MaBaiViet_Nhom."' ORDER BY MaBinhLuan DESC LIMIT $Id, $PageSize";
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
		default: echo "<h1 style=\"font-family: 'Times New Roman';\">Not Found</h1>
				<p style=\"font-family: 'Times New Roman'; font-size: medium;\">The requested URL was not found on this server.</p>
				<hr />
				<address style=\"font-family: 'Times New Roman'; font-size: medium;\">Apache/2.2.21 (CentOS) Server</address>";
	}
	}while($re);
}
else
{
	echo "Bạn chưa đăng nhập!";
}
	
?>