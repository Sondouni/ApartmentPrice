package com.koreait.ap;

import com.koreait.ap.model.ApartmentInfoEntity;
import com.koreait.ap.model.ApartmentInfoVO;
import com.koreait.ap.model.LocationCodeEntity;
import com.koreait.ap.model.SearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApMapper {
    //"구" 뽑아내기
    List<LocationCodeEntity> selLocationCode();
    //"loca_cd"뽑아내기
    int selloca_cd(SearchDTO dto);
    //검사
    List<ApartmentInfoVO> selAptList(ApartmentInfoEntity entity);
    //삽입
    int insAptInfo(ApartmentInfoEntity[] arr);
}
