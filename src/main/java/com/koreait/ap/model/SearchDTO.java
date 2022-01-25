package com.koreait.ap.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDTO {
    private int year;
    private int month;
    private int locationcode;
    private String excd;
}
