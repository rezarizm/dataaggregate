package com.stockbit.aggregatorcore.usecase.consumefile;

public interface ConsumeFileInputBoundary {
    void consume(ConsumeFileRequestModel requestModel, ConsumeFileOutputBoundary presenter);
}
