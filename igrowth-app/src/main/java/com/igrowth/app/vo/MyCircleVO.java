package com.igrowth.app.vo;

import com.github.wxiaoqi.security.api.entity.MyCircle;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/12/413:43
 *    desc    : 输入描述
 * </pre>
 */
public class MyCircleVO extends MyCircle{

    private String circleName;//圈子名称

    private String circleIcon;//圈子图标

    private String circleBackground;//圈子背景图

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getCircleIcon() {
        return circleIcon;
    }

    public void setCircleIcon(String circleIcon) {
        this.circleIcon = circleIcon;
    }

    public String getCircleBackground() {
        return circleBackground;
    }

    public void setCircleBackground(String circleBackground) {
        this.circleBackground = circleBackground;
    }
}
