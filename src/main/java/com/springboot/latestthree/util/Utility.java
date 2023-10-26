package com.springboot.latestthree.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utility {

	public static ByteArrayOutputStream readImageToBos(String imagePath) throws IOException {
		Path filePath = Paths.get(imagePath);
		byte[] imageBytes = Files.readAllBytes(filePath);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(imageBytes);

		return outputStream;
	}

}
