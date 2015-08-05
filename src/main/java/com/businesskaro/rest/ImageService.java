package com.businesskaro.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.businesskaro.imageservice.ImageUpload;

@RestController
public class ImageService {

	@Autowired
	ImageUpload imageUpload;
	
	@RequestMapping(value = "/services/upload", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public String uploadImage(@RequestParam("file") MultipartFile file){
		String imageUrl="";
		try {
			imageUrl = imageUpload.uploadImage(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageUrl;
	}
}
