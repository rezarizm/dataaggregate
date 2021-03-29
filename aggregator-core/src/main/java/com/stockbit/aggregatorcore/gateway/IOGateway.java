package com.stockbit.aggregatorcore.gateway;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface IOGateway {

    void downloadFile(URL url, String outputFileName) throws IOException;

    List<String[]> readTxt(String filename, Integer skippedLine) throws IOException;


}
