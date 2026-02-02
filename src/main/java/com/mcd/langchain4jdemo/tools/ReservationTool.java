package com.mcd.langchain4jdemo.tools;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mcd.langchain4jdemo.entity.Reservation;
import com.mcd.langchain4jdemo.service.ReservationService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ReservationTool {

    private final ReservationService reservationService;

    @Tool("添加志愿指导服务")
    public void addReservation(
            @P("考生姓名") String name,
            @P("考生性别") String gender,
            @P("考生电话") String phone,
            @P("预约时间") String time,
            @P("考生所在省份") String province,
            @P("考生分数") String score
    ) {
        reservationService.save(new Reservation().setName(name)
                .setGender(gender)
                .setPhone(phone)
                .setCommunicationTime(LocalDateTime.parse(time))
                .setProvince(province)
                .setEstimatedScore(Integer.valueOf(score)));
    }

    @Tool("根据电话号码查询预约信息")
    public void getReservationByPhone(@P("考生电话") String phone) {
        reservationService.getOne(new QueryWrapper<Reservation>().eq("phone", phone));

    }
}
