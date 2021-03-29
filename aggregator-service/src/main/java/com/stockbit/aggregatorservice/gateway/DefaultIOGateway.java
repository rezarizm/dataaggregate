package com.stockbit.aggregatorservice.gateway;

import com.stockbit.aggregatorcore.gateway.IOGateway;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultIOGateway implements IOGateway {
    @Override
    public void downloadFile(URL url, String fileName) throws IOException {
        FileUtils.copyURLToFile(url, new File(fileName));
    }

    @Override
    public List<String[]> readTxt(String filename, Integer skippedLine) throws IOException {
        List <String> lines = Files.readAllLines(Paths.get(filename));
        lines.remove(skippedLine);
        List<String[]> txtData = new ArrayList<>();
        for(String lineDetails: lines){
            String[] arrOfLine = lineDetails.split("|");
            txtData.add(arrOfLine);
        }
        return  txtData;
    }
}
