package com.example.extractor.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ChecksumUtills {

    public static String md5(File file) throws IOException {
        return DigestUtils.md5Hex(Files.newInputStream(file.toPath()));
    }

    public static String sha256(File file) throws IOException {
        return DigestUtils.sha256Hex(Files.newInputStream(file.toPath()));
    }

    public static String md5(String content) throws IOException {
        return DigestUtils.md5Hex(content);
    }

    public static String sha256(String content) throws IOException {
        return DigestUtils.sha256Hex(content);
    }
}
