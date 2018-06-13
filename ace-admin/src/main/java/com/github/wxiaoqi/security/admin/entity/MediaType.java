package com.github.wxiaoqi.security.admin.entity;

/**
 * Created by Jason on 16/6/30.
 */
public enum MediaType {
    IMAGE,AUDIO,VIDEO,THUMBNAIL,NORMALFILE;
    public static MediaType get(String str){
        for(MediaType p : values()){
            if(p.toString().equals(str)){
                return p;
            }
        }
        return NORMALFILE;
    }

    public static int getIntMediaType(MediaType type){
        if(IMAGE.equals(type)){
            return 0;
        }else if(AUDIO.equals(type)){
            return 1;
        }else if(VIDEO.equals(type)){
            return 2;
        }else if(THUMBNAIL.equals(type)){
            return 3;
        }else{
            return 4;
        }

    }
}
