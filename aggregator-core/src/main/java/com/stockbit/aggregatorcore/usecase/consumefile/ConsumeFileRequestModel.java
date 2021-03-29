package com.stockbit.aggregatorcore.usecase.consumefile;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsumeFileRequestModel {

    List<String[]> data;

}
