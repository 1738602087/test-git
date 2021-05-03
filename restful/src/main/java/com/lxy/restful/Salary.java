package com.lxy.restful;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 每月工资信息
 */
@Setter
@Getter
@ToString
public class Salary {
    private Long id;
    //员工id
    private Long employeeId;
    //工资
    private BigDecimal money;
    //所属时间
    @JsonFormat(pattern = "yyyy-MM",timezone = "GMT+8")
    private Date date;

    public Salary(Long id, Long employeeId, BigDecimal money, Date date) {
        this.id = id;
        this.employeeId = employeeId;
        this.money = money;
        this.date = date;
    }

    public Salary() {
    }
}
