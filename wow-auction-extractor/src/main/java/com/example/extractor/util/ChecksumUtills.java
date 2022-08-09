package com.example.extractor.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ChecksumUtills {

    private static final String SHA256 = "SHA-256";
    private static final String MD5 = "MD5";

    public static String md5(File file) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(file));
    }

    public static String sha256(File file) throws IOException {
        return DigestUtils.sha256Hex(new FileInputStream(file));
    }
}
