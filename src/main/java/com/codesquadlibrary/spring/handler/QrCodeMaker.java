package com.codesquadlibrary.spring.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.http.MediaType;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrCodeMaker {

	public byte[] makeQr(String uniquecode) throws IOException, WriterException{
		

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitmatrix = qrCodeWriter.encode(uniquecode, BarcodeFormat.QR_CODE, 200, 200);

			MatrixToImageWriter.writeToStream(bitmatrix, MediaType.IMAGE_PNG.getSubtype(), baos,
					new MatrixToImageConfig());

			return baos.toByteArray();
	}
}
