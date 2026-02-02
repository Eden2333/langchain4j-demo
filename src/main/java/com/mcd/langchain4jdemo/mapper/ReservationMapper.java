package com.mcd.langchain4jdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mcd.langchain4jdemo.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {
}
