package com.project.vue.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.project.vue.common.excel.annotation.ExcelOptions;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeEntity {

	@CreatedDate
	@ExcelOptions(headerName = "작성일", columnStyle = BorderStyle.DASH_DOT, columnWidth = 8000)
	/** 작성일 */
	private String registDate;

	@LastModifiedDate
	@ExcelOptions(headerName = "수정일", columnStyle = BorderStyle.DASH_DOT)
	/** 수정일 */
	private String modifyDate;

    @PrePersist
    /** insert 연산시 실행 */
    public void onPrePersist() {
    	this.registDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    	this.modifyDate = this.registDate;
    }

    @PreUpdate
    /** update 연산시 실행 */
    public void onPreUpdate(){
    	this.modifyDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}
