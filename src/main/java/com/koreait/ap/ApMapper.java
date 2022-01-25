package com.koreait.ap;

import com.koreait.ap.model.ApartmentInfoEntity;
import com.koreait.ap.model.LocationCodeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApMapper {
    //"구" 뽑아내기
    List<LocationCodeEntity> selLocationCode();
    //검사
    ApartmentInfoEntity selAptList(ApartmentInfoEntity entity);
    //삽입
    int insAptInfo(ApartmentInfoEntity[] arr);
}
