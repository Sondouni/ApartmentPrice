package com.koreait.ap;

import com.koreait.ap.model.ApartmentInfoEntity;
import com.koreait.ap.model.ApartmentInfoVO;
import com.koreait.ap.model.SearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ApController {

    @Autowired
    private ApService service;

    @GetMapping("/")
    public String main(Model model){
        model.addAttribute("list",service.selLocationCode());
        return "main";
    }

    @PostMapping("/result")
    @ResponseBody
    public Map<String, List<ApartmentInfoVO>> result(@RequestBody SearchDTO dto){
        System.out.println(dto);
        Map<String, List<ApartmentInfoVO>> result = new HashMap<>();
        List<ApartmentInfoVO> list = service.getData(dto);
        result.put("result",list);
        return result;
    }
}
