package com.igrowth.app.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.igrowth.app.biz.CourseBiz;

@RestController
@RequestMapping("igrowthMLesson")
public class IgrowthMLessonController extends BaseController<CourseBiz,Course> {

}