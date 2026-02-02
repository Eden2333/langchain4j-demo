package com.mcd.langchain4jdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcd.langchain4jdemo.entity.Reservation;
import com.mcd.langchain4jdemo.mapper.ReservationMapper;
import com.mcd.langchain4jdemo.service.ReservationService;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements ReservationService {
}
