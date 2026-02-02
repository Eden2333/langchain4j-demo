package com.mcd.langchain4jdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName("reservation")
@Accessors(chain = true)
public class Reservation {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private String gender;
    
    private String phone;
    
    private LocalDateTime communicationTime;
    
    private String province;
    
    private Integer estimatedScore;
}