package com.project.vue.common.excel.DTO;

import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;

import lombok.Data;

@Data
public class ExcelDTO {
	private String fileName;
	private List<String> headerList;
	private List<String> colList;
	private List<BorderStyle> colStyle;
}
