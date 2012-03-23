-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 19, 2011 at 05:24 AM
-- Server version: 5.5.8
-- PHP Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dbtotnghiep`
--

-- --------------------------------------------------------

--
-- Table structure for table `album`
--

CREATE TABLE IF NOT EXISTS `album` (
  `MaAlbum` int(11) NOT NULL AUTO_INCREMENT,
  `TenAlbum` varchar(100) NOT NULL,
  `MoTa` text,
  `AnhDaiDien` varchar(200) DEFAULT NULL,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `NgayCapNhat` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`MaAlbum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `album`
--


-- --------------------------------------------------------

--
-- Table structure for table `albumchiase`
--

CREATE TABLE IF NOT EXISTS `albumchiase` (
  `MaAlbumChiaSe` int(11) NOT NULL AUTO_INCREMENT,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `TenAlbum` varchar(100) NOT NULL,
  `MoTa` text,
  PRIMARY KEY (`MaAlbumChiaSe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `albumchiase`
--


-- --------------------------------------------------------

--
-- Table structure for table `anh`
--

CREATE TABLE IF NOT EXISTS `anh` (
  `MaAnh` int(11) NOT NULL AUTO_INCREMENT,
  `MoTa` text,
  `Link` varchar(200) NOT NULL,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`MaAnh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `anh`
--


-- --------------------------------------------------------

--
-- Table structure for table `anhmoi`
--

CREATE TABLE IF NOT EXISTS `anhmoi` (
  `MaAnhMoi` int(11) NOT NULL AUTO_INCREMENT,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `MaAnh_Nhom` int(11) NOT NULL,
  PRIMARY KEY (`MaAnhMoi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `anhmoi`
--


-- --------------------------------------------------------

--
-- Table structure for table `anh_album`
--

CREATE TABLE IF NOT EXISTS `anh_album` (
  `MaAnh_Album` int(11) NOT NULL AUTO_INCREMENT,
  `MaAnh` int(11) NOT NULL,
  `MaAlbum` int(11) NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ThuTu` tinyint(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`MaAnh_Album`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `anh_album`
--


-- --------------------------------------------------------

--
-- Table structure for table `anh_nhom`
--

CREATE TABLE IF NOT EXISTS `anh_nhom` (
  `MaAnh_Nhom` int(11) NOT NULL AUTO_INCREMENT,
  `MaAnh` int(11) NOT NULL,
  `MaNhom` varchar(11) DEFAULT NULL,
  `MaAlbumChiaSe` int(11) DEFAULT NULL,
  `NgayChiaSe` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TrangThai` tinyint(1) NOT NULL DEFAULT '1',
  `ViTriGPS` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`MaAnh_Nhom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `anh_nhom`
--


-- --------------------------------------------------------

--
-- Table structure for table `baiviet`
--

CREATE TABLE IF NOT EXISTS `baiviet` (
  `MaBaiViet` int(11) NOT NULL AUTO_INCREMENT,
  `TieuDe` text NOT NULL,
  `NoiDung` text NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TrangThai` tinyint(1) NOT NULL DEFAULT '1',
  `MaTaiKhoan` varchar(11) NOT NULL,
  PRIMARY KEY (`MaBaiViet`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `baiviet`
--


-- --------------------------------------------------------

--
-- Table structure for table `baivietmoi`
--

CREATE TABLE IF NOT EXISTS `baivietmoi` (
  `MaBaiVietMoi` int(11) NOT NULL AUTO_INCREMENT,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `MaBaiViet_Nhom` int(11) NOT NULL,
  PRIMARY KEY (`MaBaiVietMoi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `baivietmoi`
--


-- --------------------------------------------------------

--
-- Table structure for table `baiviet_chude`
--

CREATE TABLE IF NOT EXISTS `baiviet_chude` (
  `MaBaiViet_ChuDe` int(11) NOT NULL AUTO_INCREMENT,
  `MaBaiViet` int(11) NOT NULL,
  `MaChuDe` int(11) NOT NULL,
  `ThuTu` tinyint(3) NOT NULL DEFAULT '1',
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`MaBaiViet_ChuDe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `baiviet_chude`
--


-- --------------------------------------------------------

--
-- Table structure for table `baiviet_nhom`
--

CREATE TABLE IF NOT EXISTS `baiviet_nhom` (
  `MaBaiViet_Nhom` int(11) NOT NULL AUTO_INCREMENT,
  `MaBaiViet` int(11) NOT NULL,
  `NgayChiaSe` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TrangThai` tinyint(1) NOT NULL DEFAULT '1',
  `ViTriGPS` varchar(100) DEFAULT NULL,
  `MaNhom` varchar(11) DEFAULT NULL,
  `MaDanhMuc` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaBaiViet_Nhom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `baiviet_nhom`
--


-- --------------------------------------------------------

--
-- Table structure for table `binhluananh`
--

CREATE TABLE IF NOT EXISTS `binhluananh` (
  `MaBinhLuan` int(11) NOT NULL AUTO_INCREMENT,
  `NoiDung` text NOT NULL,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ViTriGPS` varchar(100) DEFAULT NULL,
  `MaAnh_Nhom` int(11) DEFAULT NULL,
  `MaAlbumChiaSe` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaBinhLuan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `binhluananh`
--


-- --------------------------------------------------------

--
-- Table structure for table `binhluanbaiviet`
--

CREATE TABLE IF NOT EXISTS `binhluanbaiviet` (
  `MaBinhLuan` int(11) NOT NULL AUTO_INCREMENT,
  `NoiDung` text NOT NULL,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ViTriGPS` varchar(100) DEFAULT NULL,
  `MaBaiViet_Nhom` int(11) NOT NULL,
  PRIMARY KEY (`MaBinhLuan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `binhluanbaiviet`
--


-- --------------------------------------------------------

--
-- Table structure for table `chude`
--

CREATE TABLE IF NOT EXISTS `chude` (
  `MaChuDe` int(11) NOT NULL AUTO_INCREMENT,
  `TenChuDe` varchar(100) NOT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `NgayCapNhat` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `MaTaiKhoan` varchar(11) NOT NULL,
  PRIMARY KEY (`MaChuDe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `chude`
--


-- --------------------------------------------------------

--
-- Table structure for table `danhmucbaiviet_nhom`
--

CREATE TABLE IF NOT EXISTS `danhmucbaiviet_nhom` (
  `MaDanhMuc` int(11) NOT NULL AUTO_INCREMENT,
  `MaNhom` varchar(11) NOT NULL,
  `TenDanhMuc` text NOT NULL,
  `MoTa` text,
  `AnhDaiDien` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`MaDanhMuc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `danhmucbaiviet_nhom`
--


-- --------------------------------------------------------

--
-- Table structure for table `nhom`
--

CREATE TABLE IF NOT EXISTS `nhom` (
  `MaNhom` varchar(11) NOT NULL,
  `TenNhom` varchar(50) NOT NULL,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `AnhDaiDien` varchar(200) DEFAULT NULL,
  `MoTa` text,
  `QuyTac` text,
  `ViTriGPS` varchar(100) DEFAULT NULL,
  `NgayTao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`MaNhom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `nhom`
--


-- --------------------------------------------------------

--
-- Table structure for table `quyenhan`
--

CREATE TABLE IF NOT EXISTS `quyenhan` (
  `MaQuyen` int(11) NOT NULL AUTO_INCREMENT,
  `TenQuyen` varchar(50) NOT NULL,
  PRIMARY KEY (`MaQuyen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `quyenhan`
--


-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
--

CREATE TABLE IF NOT EXISTS `taikhoan` (
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

--
-- Dumping data for table `taikhoan`
--


-- --------------------------------------------------------

--
-- Table structure for table `taikhoan_nhom`
--

CREATE TABLE IF NOT EXISTS `taikhoan_nhom` (
  `MaTaiKhoan_Nhom` int(11) NOT NULL AUTO_INCREMENT,
  `MaTaiKhoan` varchar(11) NOT NULL,
  `MaNhom` varchar(11) NOT NULL,
  `QuyenHan` tinyint(1) DEFAULT NULL,
  `TrangThai` tinyint(1) NOT NULL DEFAULT '1',
  `NgayVaoNhom` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`MaTaiKhoan_Nhom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `taikhoan_nhom`
--

