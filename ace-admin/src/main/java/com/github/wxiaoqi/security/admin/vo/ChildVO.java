package com.github.wxiaoqi.security.admin.vo;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/12/1316:48
 *    desc    : 输入描述
 * </pre>
 */
public class ChildVO {

    private String childName;
    private String cellphone;
    private int id;//孩子id

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
