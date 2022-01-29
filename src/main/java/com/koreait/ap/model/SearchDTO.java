package com.koreait.ap.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDTO {
    private int year;
    private int month;
    private int locationcode;
    private String excd;
}
