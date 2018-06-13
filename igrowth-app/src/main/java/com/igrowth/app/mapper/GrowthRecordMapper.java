package com.igrowth.app.mapper;

import com.github.wxiaoqi.security.api.entity.GrowthRecord;
import com.igrowth.app.vo.MarkerInfoVO;
import com.igrowth.app.vo.SumObjVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface GrowthRecordMapper extends Mapper<GrowthRecord> {

    public List<GrowthRecord> findGrowthRecordByDate(@Param("childId") Long childId, Date monday, Date sunnday);

    public MarkerInfoVO findMarkinfoByParam(@Param("childId") Long childId,@Param("courseId") Long courseId,Date date);
    
    public Integer findCoureSumByChildId(@Param("childId") Long childId);

	public Double findStudyHoursSumByChildId(@Param("childId") Long childId);

    public List<SumObjVO> sumLabelInfo(@Param("childId") Long childId);
}