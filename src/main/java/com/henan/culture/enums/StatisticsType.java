package com.henan.culture.enums;

public enum StatisticsType {
    QuestionRight(1,"答题正确数量"),
    QuestionWrong(2,"答题错误数量"),
    ShareNormal(3,"主页分享总数量"),
    ShareTop(4,"顶部分享总次数"),
    ShareRelive(5,"重生分享总次数"),
    LoginTimes(6,"登录总次数"),
    PinTuSuc(7,"拼图成功"),
    PinTuFail(8,"拼图失败"),
    ;

    StatisticsType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private int type;
    private String desc;

    public int getType() {
        return type;
    }
}
