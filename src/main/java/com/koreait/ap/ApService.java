package com.koreait.ap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.koreait.ap.model.ApartmentInfoEntity;
import com.koreait.ap.model.ApartmentInfoVO;
import com.koreait.ap.model.LocationCodeEntity;
import com.koreait.ap.model.SearchDTO;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ApService {
    @Autowired
    private ApMapper apMapper;

    public List<LocationCodeEntity> selLocationCode(){
        System.out.println(apMapper.selLocationCode());
        return apMapper.selLocationCode();
    }

    public List<ApartmentInfoVO> getData(SearchDTO dto){
        //로케이션코드 가져오기
        dto.setLocationcode(apMapper.selloca_cd(dto));
        //체크
        ApartmentInfoEntity apinfo = new ApartmentInfoEntity();
        apinfo.setDealmonth(dto.getMonth()+"");
        apinfo.setDealyear(dto.getYear()+"");
        apinfo.setExcd(dto.getExcd());

        List<ApartmentInfoVO> resultList = apMapper.selAptList(apinfo);
        System.out.println("0이상이면 데이터베이스에 값이 있다 : "+resultList.size());
        if(resultList.size()==0) {
            //가져오는값이 없다면 통신
            String lawdCd = dto.getExcd();
            String dealYm = String.format("%s%02d", dto.getYear(), dto.getMonth());
            System.out.println("dealYm : " + dealYm);
            String serviceKey = "Y2UOCkD8Ilv2gViPGV33ddNTTQfRi92i8mRzUeQX+NgSiNTO3gp9hJZX4J6u8uXucMM6RdRBoGxMn6XHfsEzNA==";
            String url = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";

            UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("LAWD_CD", lawdCd)
                    .queryParam("DEAL_YMD", dealYm)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("numOfRows", 5000)
                    .build(false);

            RestTemplate rest = new RestTemplate();
            rest.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

            final HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

            HttpEntity<String> entity = new HttpEntity(headers);

            ResponseEntity<String> responseEntity = rest.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            String result = responseEntity.getBody();
            //        System.out.println(XML.toJSONObject(result)); xml을 Json으로 ( 라이브러리 추가 )

            ObjectMapper om = new JsonMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //받고싶은것만 convert해서 annotation으로 받을때 FAIL_ON_UNKNOWN_PROPERTIES를 안주면 에러가 난다
            JsonNode jsonNode = null;
            ApartmentInfoEntity[] arr = null;
            int mapperresult = 0;
            try {
                jsonNode = om.readTree(result);
                arr = om.treeToValue(jsonNode.path("response").path("body").path("items").path("item"), ApartmentInfoEntity[].class);
                //추가 값 넣어주기
                for (ApartmentInfoEntity insertEntity : arr) {
                    String newdealAm = insertEntity.getDealamount().replaceAll(" ", "").replaceAll(",", "");
                    insertEntity.setDealamount(newdealAm);
                    insertEntity.setExcd(dto.getExcd());
                    insertEntity.setLocationcode(dto.getLocationcode());
                }
                //insert
                System.out.println("select했더니 없어서 insert합니다");
                mapperresult = apMapper.insAptInfo(arr);
                //insert후 select다시
                resultList = apMapper.selAptList(apinfo);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return resultList;
    }
}
