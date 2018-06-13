package com.github.wxiaoqi.security.admin.vo;

import com.github.wxiaoqi.security.api.entity.MCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/12/1119:07
 *    desc    : 输入描述
 * </pre>
 */
public class IgrowthMCourseVO extends MCourse {

    private int courseListSize;//某个机构下某个孩子的课程数

    private List<MCourse> mcourseList = new ArrayList<MCourse>();

    public int getCourseListSize() {
        return courseListSize;
    }

    public void setCourseListSize(int courseListSize) {
        this.courseListSize = courseListSize;
    }

    public List<MCourse> getMcourseList() {
        return mcourseList;
    }

    public void setMcourseList(List<MCourse> mcourseList) {
        this.mcourseList = mcourseList;
    }

}
