package com.android.similarwx.beans;

/**
 * Created by Administrator on 2018/4/14.
 */

public class RuleBean extends BaseBean {
    private String name;
    private String num1;
    private String num2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    @Override
    public String toString() {
        return "RuleBean{" +
                "name='" + name + '\'' +
                ", num1='" + num1 + '\'' +
                ", num2='" + num2 + '\'' +
                '}';
    }
}
