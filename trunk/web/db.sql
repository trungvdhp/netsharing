/*
SQLyog Enterprise - MySQL GUI v8.12 
MySQL - 5.1.41 : Database - db_vimaru_mobile
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `album` */

DROP TABLE IF EXISTS `album`;

CREATE TABLE `album` (
  `MaAlbum` int(11) NOT NULL AUTO_INCREMENT,
  `TenAlbum` varchar(100) NOT NULL,
  `MoTa` text,
  `AnhDaiDien` varchar(200) DEFAULT NULL,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `NgayCapNhat` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`MaAlbum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `album` */

/*Table structure for table `albumchiase` */

DROP TABLE IF EXISTS `albumchiase`;

CREATE TABLE `albumchiase` (
  `MaAlbumChiaSe` int(11) NOT NULL AUTO_INCREMENT,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `TenAlbum` varchar(100) NOT NULL,
  `MoTa` text,
  PRIMARY KEY (`MaAlbumChiaSe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `albumchiase` */

/*Table structure for table `anh` */

DROP TABLE IF EXISTS `anh`;

CREATE TABLE `anh` (
  `MaAnh` int(11) NOT NULL AUTO_INCREMENT,
  `MoTa` text,
  `Link` varchar(200) NOT NULL,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`MaAnh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `anh` */

/*Table structure for table `anh_album` */

DROP TABLE IF EXISTS `anh_album`;

CREATE TABLE `anh_album` (
  `MaAnh_Album` int(11) NOT NULL AUTO_INCREMENT,
  `MaAnh` int(11) NOT NULL,
  `MaAlbum` int(11) NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ThuTu` tinyint(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`MaAnh_Album`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `anh_album` */

/*Table structure for table `anh_nhom` */

DROP TABLE IF EXISTS `anh_nhom`;

CREATE TABLE `anh_nhom` (
  `MaAnh_Nhom` int(11) NOT NULL AUTO_INCREMENT,
  `MaAnh` int(11) NOT NULL,
  `MaNhom` varchar(11) DEFAULT NULL,
  `MaAlbumChiaSe` int(11) DEFAULT NULL,
  `NgayChiaSe` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TrangThai` tinyint(1) NOT NULL DEFAULT '1',
  `ViTriGPS` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`MaAnh_Nhom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `anh_nhom` */

/*Table structure for table `anhmoi` */

DROP TABLE IF EXISTS `anhmoi`;

CREATE TABLE `anhmoi` (
  `MaAnhMoi` int(11) NOT NULL AUTO_INCREMENT,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `MaAnh_Nhom` int(11) NOT NULL,
  PRIMARY KEY (`MaAnhMoi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `anhmoi` */

/*Table structure for table `baiviet` */

DROP TABLE IF EXISTS `baiviet`;

CREATE TABLE `baiviet` (
  `MaBaiViet` int(11) NOT NULL AUTO_INCREMENT,
  `TieuDe` text NOT NULL,
  `NoiDung` text NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TrangThai` tinyint(1) NOT NULL DEFAULT '1',
  `MaTaiKhoan` varchar(11) NOT NULL,
  PRIMARY KEY (`MaBaiViet`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `baiviet` */

/*Table structure for table `baiviet_chude` */

DROP TABLE IF EXISTS `baiviet_chude`;

CREATE TABLE `baiviet_chude` (
  `MaBaiViet_ChuDe` int(11) NOT NULL AUTO_INCREMENT,
  `MaBaiViet` int(11) NOT NULL,
  `MaChuDe` int(11) NOT NULL,
  `ThuTu` tinyint(3) NOT NULL DEFAULT '1',
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`MaBaiViet_ChuDe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `baiviet_chude` */

/*Table structure for table `baiviet_nhom` */

DROP TABLE IF EXISTS `baiviet_nhom`;

CREATE TABLE `baiviet_nhom` (
  `MaBaiViet_Nhom` int(11) NOT NULL AUTO_INCREMENT,
  `MaTaiKhoan` varchar(50) DEFAULT NULL,
  `MaBaiViet` int(11) NOT NULL,
  `NgayChiaSe` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TrangThai` tinyint(1) NOT NULL DEFAULT '1',
  `ViTriGPS` varchar(100) DEFAULT NULL,
  `MaNhom` varchar(11) DEFAULT NULL,
  `MaDanhMuc` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaBaiViet_Nhom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `baiviet_nhom` */

/*Table structure for table `baivietmoi` */

DROP TABLE IF EXISTS `baivietmoi`;

CREATE TABLE `baivietmoi` (
  `MaBaiVietMoi` int(11) NOT NULL AUTO_INCREMENT,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `MaBaiViet_Nhom` int(11) NOT NULL,
  PRIMARY KEY (`MaBaiVietMoi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `baivietmoi` */

/*Table structure for table `binhluananh` */

DROP TABLE IF EXISTS `binhluananh`;

CREATE TABLE `binhluananh` (
  `MaBinhLuan` int(11) NOT NULL AUTO_INCREMENT,
  `NoiDung` text NOT NULL,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ViTriGPS` varchar(100) DEFAULT NULL,
  `MaAnh_Nhom` int(11) DEFAULT NULL,
  `MaAlbumChiaSe` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaBinhLuan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `binhluananh` */

/*Table structure for table `binhluanbaiviet` */

DROP TABLE IF EXISTS `binhluanbaiviet`;

CREATE TABLE `binhluanbaiviet` (
  `MaBinhLuan` int(11) NOT NULL AUTO_INCREMENT,
  `NoiDung` text NOT NULL,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ViTriGPS` varchar(100) DEFAULT NULL,
  `MaBaiViet_Nhom` int(11) NOT NULL,
  PRIMARY KEY (`MaBinhLuan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `binhluanbaiviet` */

/*Table structure for table `chude` */

DROP TABLE IF EXISTS `chude`;

CREATE TABLE `chude` (
  `MaChuDe` int(11) NOT NULL AUTO_INCREMENT,
  `TenChuDe` varchar(100) NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `NgayCapNhat` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `MaTaiKhoan` varchar(11) NOT NULL,
  PRIMARY KEY (`MaChuDe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `chude` */

/*Table structure for table `danhmucbaiviet_nhom` */

DROP TABLE IF EXISTS `danhmucbaiviet_nhom`;

CREATE TABLE `danhmucbaiviet_nhom` (
  `MaDanhMuc` int(11) NOT NULL AUTO_INCREMENT,
  `MaNhom` varchar(11) NOT NULL,
  `TenDanhMuc` text NOT NULL,
  `MoTa` text,
  `AnhDaiDien` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`MaDanhMuc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `danhmucbaiviet_nhom` */

/*Table structure for table `nhom` */

DROP TABLE IF EXISTS `nhom`;

CREATE TABLE `nhom` (
  `MaNhom` varchar(11) NOT NULL,
  `TenNhom` varchar(50) NOT NULL,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `AnhDaiDien` varchar(200) DEFAULT NULL,
  `MoTa` text,
  `QuyTac` text,
  `ViTriGPS` varchar(100) DEFAULT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `SoThanhVien` int(11) DEFAULT '0',
  `SoBaiViet` int(11) DEFAULT '0',
  PRIMARY KEY (`MaNhom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `nhom` */

insert  into `nhom`(`MaNhom`,`TenNhom`,`MaTaiKhoan`,`AnhDaiDien`,`MoTa`,`QuyTac`,`ViTriGPS`,`NgayTao`,`SoThanhVien`,`SoBaiViet`) values ('NTD1','CNT49 - ĐH1','TKTD0',NULL,'','',NULL,'2012-03-30 12:22:54',0,0),('NTD2','C#','TKTD0',NULL,NULL,NULL,NULL,'2012-03-30 11:04:17',0,0),('NTD3','PHP','TKTD0',NULL,NULL,NULL,NULL,'2012-03-30 11:04:24',0,0),('NTD4','F4','TKTD0',NULL,'Team bắn CS 1.6 ','Play for fun but wanna go pro =)) ',NULL,'2012-03-30 11:08:54',0,0);

/*Table structure for table `quyenhan` */

DROP TABLE IF EXISTS `quyenhan`;

CREATE TABLE `quyenhan` (
  `MaQuyen` int(11) NOT NULL AUTO_INCREMENT,
  `TenQuyen` varchar(50) NOT NULL,
  PRIMARY KEY (`MaQuyen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `quyenhan` */

/*Table structure for table `taikhoan` */

DROP TABLE IF EXISTS `taikhoan`;

CREATE TABLE `taikhoan` (
  `MaTaiKhoan` varchar(11) NOT NULL,
  `TaiKhoan` varchar(50) NOT NULL,
  `MatKhau` varchar(32) NOT NULL,
  `HoDem` varchar(32) NOT NULL,
  `Ten` varchar(32) NOT NULL,
  `NgaySinh` varchar(15) NOT NULL,
  `GioiTinh` tinyint(1) NOT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `DienThoai` varchar(20) DEFAULT NULL,
  `DiaChi` varchar(100) DEFAULT NULL,
  `AnhDaiDien` varchar(200) DEFAULT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `TrangThai` tinyint(1) NOT NULL DEFAULT '1',
  `NgayVaoTruong` varchar(15) DEFAULT NULL,
  `MaQuyen` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaTaiKhoan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `taikhoan` */

insert  into `taikhoan`(`MaTaiKhoan`,`TaiKhoan`,`MatKhau`,`HoDem`,`Ten`,`NgaySinh`,`GioiTinh`,`Email`,`DienThoai`,`DiaChi`,`AnhDaiDien`,`NgayTao`,`TrangThai`,`NgayVaoTruong`,`MaQuyen`) values ('TKTD0','Kid','0e4e946668cf2afc4299b462b812caca','','','',0,NULL,NULL,NULL,NULL,'2012-03-30 11:02:58',1,NULL,NULL),('TKTD1','Picasso','6693c7fd8c2e5d734210724001b0bd46','','','',0,NULL,NULL,NULL,NULL,'2012-03-30 11:05:20',1,NULL,NULL);

/*Table structure for table `taikhoan_nhom` */

DROP TABLE IF EXISTS `taikhoan_nhom`;

CREATE TABLE `taikhoan_nhom` (
  `MaTaiKhoan_Nhom` int(11) NOT NULL AUTO_INCREMENT,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `MaNhom` varchar(11) NOT NULL,
  `QuyenHan` tinyint(1) DEFAULT NULL,
  `TrangThai` tinyint(1) NOT NULL DEFAULT '1',
  `NgayVaoNhom` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`MaTaiKhoan_Nhom`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `taikhoan_nhom` */

insert  into `taikhoan_nhom`(`MaTaiKhoan_Nhom`,`MaTaiKhoan`,`MaNhom`,`QuyenHan`,`TrangThai`,`NgayVaoNhom`) values (1,'TKTD0','NTD0',NULL,1,'2012-03-30 11:03:21'),(2,'TKTD0','NTD1',NULL,1,'2012-03-30 11:03:44'),(3,'TKTD0','NTD2',NULL,1,'2012-03-30 11:04:17'),(4,'TKTD0','NTD3',NULL,1,'2012-03-30 11:04:24'),(5,'TKTD1','NTD0',NULL,1,'2012-03-30 12:21:34'),(6,'TKTD1','NTD1',NULL,1,'2012-03-30 12:21:30'),(7,'TKTD0','NTD4',NULL,1,'2012-03-30 11:06:13'),(8,'TKTD1','NTD4',NULL,1,'2012-03-30 11:06:54');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
