package com.github.wxiaoqi.security.api.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "igrowth_course_card_relation")
public class CourseCardRelation {

	@Column(name = "course_id")
	private Long courseId;

	@Column(name = "course_card_id")
	private Long courseCardId;

	/**  
	 * courseId.  
	 *  
	 * @return  the courseId  
	 * @since   JDK 1.8  
	 */
	public Long getCourseId() {
		return courseId;
	}

	/**  
	 * courseId.  
	 *  
	 * @param   courseId    the courseId to set  
	 * @since   JDK 1.8  
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	/**  
	 * courseCardId.  
	 *  
	 * @return  the courseCardId  
	 * @since   JDK 1.8  
	 */
	public Long getCourseCardId() {
		return courseCardId;
	}

	/**  
	 * courseCardId.  
	 *  
	 * @param   courseCardId    the courseCardId to set  
	 * @since   JDK 1.8  
	 */
	public void setCourseCardId(Long courseCardId) {
		this.courseCardId = courseCardId;
	}

}