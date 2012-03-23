<?php 
class SimpleImage {
 
   var $image;
   var $image_type;
 
   function load($filename) {
 
      $image_info = getimagesize($filename);
      $this->image_type = $image_info[2];
      if( $this->image_type == IMAGETYPE_JPEG ) {
 
         $this->image = imagecreatefromjpeg($filename);
      } elseif( $this->image_type == IMAGETYPE_GIF ) {
 
         $this->image = imagecreatefromgif($filename);
      } elseif( $this->image_type == IMAGETYPE_PNG ) {
 
         $this->image = imagecreatefrompng($filename);
      }
   }
   function save($filename, $image_type=IMAGETYPE_JPEG, $compression=75, $permissions=null) {
 
      if( $image_type == IMAGETYPE_JPEG ) {
         imagejpeg($this->image,$filename,$compression);
      } elseif( $image_type == IMAGETYPE_GIF ) {
 
         imagegif($this->image,$filename);
      } elseif( $image_type == IMAGETYPE_PNG ) {
 
         imagepng($this->image,$filename);
      }
      if( $permissions != null) {
 
         chmod($filename,$permissions);
      }
   }
   function output($image_type=IMAGETYPE_JPEG) {
 
      if( $image_type == IMAGETYPE_JPEG ) {
         imagejpeg($this->image);
      } elseif( $image_type == IMAGETYPE_GIF ) {
 
         imagegif($this->image);
      } elseif( $image_type == IMAGETYPE_PNG ) {
 
         imagepng($this->image);
      }
   }
   function getWidth() {
 
      return imagesx($this->image);
   }
   function getHeight() {
 
      return imagesy($this->image);
   }
   function resizeToHeight($height) {
 
      $ratio = $height / $this->getHeight();
      $width = $this->getWidth() * $ratio;
      $this->resize($width,$height);
   }
 
   function resizeToWidth($width) {
      $ratio = $width / $this->getWidth();
      $height = $this->getheight() * $ratio;
      $this->resize($width,$height);
   }
 
   function scale($scale) {
      $width = $this->getWidth() * $scale/100;
      $height = $this->getheight() * $scale/100;
      $this->resize($width,$height);
   }
 
   function resize($width,$height) {
      $new_image = imagecreatetruecolor($width, $height);
      imagecopyresampled($new_image, $this->image, 0, 0, 0, 0, $width, $height, $this->getWidth(), $this->getHeight());
      $this->image = $new_image;
   }
 
   	function getImageName($ImageName) {
   		$ViTriSource = strpos($ImageName,'/');
   		return substr($ImageName, $ViTriSource+1);
   	}
   
   	function checkImage($DuongDan ,$LinkImage) {
   		$Link = "";
    	if ($LinkImage != "" && file_exists($LinkImage)) {
    		// BẮT ĐẦU XỬ LÝ
    		$image = new SimpleImage();
	    	// Lấy về Tên File theo CSDL
	    	$TenFile = $image->getImageName($LinkImage);
	    	// Tạo Link mới để Client load (ảnh bé)
			$LinkMobile = $DuongDan . $TenFile;
			// Nếu tồn tại file ảnh đã Resize thì gán, nếu ko tồn tại thì gán link từ csdl
			if (file_exists($LinkMobile)) {
				$Link = $LinkMobile;
			} else 
				$Link = $LinkImage;
    	}
    	return $Link;
   	}
   	
   	function ChiTietAnh($Link, $WidthScreen, $HeightScreen, $DuongDan) {
   		if ($Link != "" && file_exists($Link)) {
	    	$image = new SimpleImage();
			$image->load($Link);
			$WidthImg = $image->getWidth();
			$HeightImg = $image->getHeight();
			if ($WidthImg > $WidthScreen || $HeightImg > $HeightScreen) {
				$TyLeImg = $WidthImg / $HeightImg;
				$TyLeScreen = $WidthScreen / $HeightScreen;
				if ($TyLeImg > $TyLeScreen) 
					$image->resizeToWidth($WidthScreen);
				else 
					$image->resizeToHeight($HeightScreen);
				
				$TenFile = $image->getImageName($Link);
				$NewLink = $DuongDan . $TenFile;
				$image->save($NewLink);
				
				return (file_exists($NewLink)) ? $NewLink : false;
			} else 
	    		return $Link;
    	} else 
    		return false;
   	}
   	
	public static function MySubString($vString,$vMax)
	{
		if(strlen($vString) <= $vMax) 
			return $vString;
		$st = substr($vString, 0, $vMax);
		while($st[strlen($st)-1] != ' ') { //TRONG KHI KÝ TỰ CUỐI != ' ' THÌ CẮT 1 KÝ TỰ Ở CUỖI
			$st=substr($st, 0, strlen($st) - 1);
		}
		if(strlen($st) < strlen($vString)) // NẾU CHUỖI ĐƯỢC CẮT NHỎ HƠN CHUỖI BĐ THÌ THÊM '...' 
			return $st.'...';
		else 
			return $st;
	}
}
?>