package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class result {
    String code;
    String msg;
    Object data;

    public static result DB_ERROR=new result("500","数据库错误",null);
    public static result SYS_ERROR=new result("-2","系统错误",null);
    public static result SUCCESS=new result("200","成功",null);

    public static result success(){
        Object data=new Object();
        result ans=new result("400","success",data);
        return ans;
    }
    public result(Object obj) {
        this.code = "200";
        this.msg = "success";
        this.data = obj;
    }
}
