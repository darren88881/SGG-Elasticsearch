package com.itguigu.es.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author darren
 * @create 2023-04-23 16:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String user;
    private String sex;
    private Integer age;
}
