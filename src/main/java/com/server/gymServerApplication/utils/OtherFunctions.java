package com.server.gymServerApplication.utils;


import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

public class OtherFunctions {


    /**
     * chuyen filename thanh byte[]
     *
     * @param fileName {@link String}
     * @return {@link byte[]}
     */
    public static byte[] UploadImg(String fileName) throws Exception {
        try (InputStream inputStream = Objects.requireNonNull(OtherFunctions.class.getClassLoader()
                .getResourceAsStream(fileName), "File not found: " + fileName)) {
            return inputStream.readAllBytes();
        }
    }

    public static byte[] convertMultipartFileToByteArray(MultipartFile file){
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is null or empty");
        }
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param urlImage {@link String} URL
     * @return {@link byte[]}     * @throws Exception
     */
    public static byte[] converUrltoByteArray(String urlImage) throws Exception {
        URL url = new URL(urlImage);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed to fetch image. HTTP code: " + connection.getResponseCode());
        }

        try (InputStream inputStream = connection.getInputStream();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray(); // Trả về byte[]
        }
    }

    /**
     * @param imageData {@link byte[]}
     * @return {@link BufferedImage}
     * @throws IOException log erro with IOException
     */
    public static BufferedImage convertByteArrayToImage(byte[] imageData) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(imageData)) {
            return ImageIO.read(bais);
        }
    }


    /**
     * lay ngay gio he thonng va chuyen thanh {@link String}
     *
     * @return {@link String} ngay he thong voi format dd/MM/yyyy
     */

    public static String DateSystem() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


    /**
     * random 1 ma code voi 6 so tu 000001 - 999999
     *
     * @return {@link String} code random
     */
    public static String generateRandomNumberString() {
        return String.format("%06d", new Random().nextInt(100000));
    }



    /**
     * chuyen danh sach thanh danh sach String id
     *
     * @param list   {@link List<T>}
     * @param method {@link Function<T,String>}
     * @param <T>    {@link Object}
     */


    public static <T> List<String> getListStringID(List<T> list,
                                                   Function<T, String> method) {
        return List.copyOf(list.stream().map(method).toList());
    }

    public static <T, M> List<M> getListObject(List<T> list,
                                               Function<T, M> method) {
        return list.stream()
                .map(method)
                .toList();
    }

    public static String generateRandomString(String id) {
        Random random = new Random();
        int length = 5;
        StringBuilder randomPart = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            randomPart.append(digit);
        }
        return id + "-" + randomPart.toString();
    }

    public static String shortenString(String input, int maxLength) {
        if (input == null || input.length() <= maxLength) {
            return input;
        }
        int lastSpaceIndex = input.lastIndexOf(" ", maxLength);
        if (lastSpaceIndex == -1) {
            return input.substring(0, maxLength) + "...";
        }
        return input.substring(0, lastSpaceIndex) + "...";
    }

}