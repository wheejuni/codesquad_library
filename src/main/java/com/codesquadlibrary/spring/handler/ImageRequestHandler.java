package com.codesquadlibrary.spring.handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

public class ImageRequestHandler {

	public static boolean ImageInputRequest(BufferedImage img, String filepath) throws IOException {

		AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());

		File outfile = new File("/" + filepath + ".jpg"); 
		ImageIO.write(img, ".jpg", outfile);

		try {
			s3client.putObject(new PutObjectRequest("codesquad-library", filepath, outfile)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			return true;
		} catch (AmazonServiceException ase) {
			System.out.println("S3 업로드 중 오류 발생");
			ase.printStackTrace();
			return false;
		}

	}

}
