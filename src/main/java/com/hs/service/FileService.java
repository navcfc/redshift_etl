package com.hs.service;

import com.hs.constants.Constants;
import com.hs.util.PGUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileService {

    String timeFilePath = PGUtil.getProperty(Constants.TIME_FILE_PATH);

    public String fetchTimestamp() throws IOException {
        File file = new File(timeFilePath);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        st = br.readLine();

        return st;
    }

}
