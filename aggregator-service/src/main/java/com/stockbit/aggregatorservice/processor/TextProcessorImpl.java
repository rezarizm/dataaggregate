package com.stockbit.aggregatorservice.processor;

import com.stockbit.aggregatorcore.gateway.IOGateway;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TextProcessorImpl implements TextProcessor{

    public static final String FILE_NAME = "test3.txt";

    @Autowired
    IOGateway ioGateway;



    @Override
    public void process() throws IOException {
        ioGateway.downloadFile( new URL(
                "https://drive.google.com/u/0/uc?id=1AXzRN4B2exraavA0cUIyVe0nun-61cB-&export=download"),
                FILE_NAME);
        List<String[]> data = ioGateway.readTxt(FILE_NAME, 0);
    }
}
