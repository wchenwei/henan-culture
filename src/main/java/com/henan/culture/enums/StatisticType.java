package com.henan.culture.enums;

public enum StatisticType {
    QuestionRight(1,"答题正确数量"),
    QuestionWrong(2,"答题错误数量"),
    ShareNormal(3,"主页分享总数量"),
    ShareTop(4,"顶部分享总次数"),
    ShareRelive(5,"重生分享总次数"),
    LoginTimes(6,"登录总次数"),
        ;
    private Integer type;
    private String desc;

    StatisticType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
