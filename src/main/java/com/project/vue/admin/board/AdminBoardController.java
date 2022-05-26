package com.project.vue.admin.board;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.vue.common.ExcelDownload;
import com.project.vue.common.SimpleResponse;
import com.project.vue.common.Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("api")
@RequiredArgsConstructor
public class AdminBoardController {
	
	private final AdminBoardService boardService;
	
	private final ExcelDownload excelDownload;
	
	@GetMapping("posts")
	public ResponseEntity<Page<AdminBoardEntity>> boardList(
			@RequestParam int pageIndex, 
			@RequestParam int pageSize,
			@RequestParam(required = false) String sortKey,
			@RequestParam(required = false) String order,
			@RequestParam(required = false) String srchKey,
			@RequestParam(required = false) String srchVal) {
		return ResponseEntity.ok(boardService.findAll(pageIndex, pageSize, sortKey, order, srchKey, srchVal));
	}
	
	@GetMapping("posts/{id}")
	public ResponseEntity<AdminBoardEntity> boardFindById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(boardService.findById(id));
	}

	@PostMapping("create")
	public ResponseEntity<SimpleResponse> boardCreate(@RequestBody AdminBoardEntity board) {
		log.debug("create board : {}",board);
		boardService.save(board);
		return ResponseEntity.ok(SimpleResponse.builder().message("글이 등록되었습니다.").build());
	}

	@PutMapping("update/{id}")
	public ResponseEntity<SimpleResponse> boardUpdate(@RequestBody AdminBoardEntity board) {
		log.debug("update board : {}",board);
		boardService.save(board);
		return ResponseEntity.ok(SimpleResponse.builder().message("글이 수정되었습니다.").build());
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<SimpleResponse> boardDelete(@PathVariable("id") Long id) {
		log.debug("delete board id : {}",id);
		boardService.deleteById(id);
		return ResponseEntity.ok(SimpleResponse.builder().message("글이 삭제되었습니다.").build());
	}
	
	@GetMapping("excel")
	public ResponseEntity<ByteArrayResource> excel() {
		try {
			String sheetName = "게시판";
	        List<String> headerList = Arrays.asList("No", "제목", "내용", "작성자", "조회수");   
	        List<String> colList = Utils.getColList(AdminBoardEntity.class);
	        List<AdminBoardEntity> dataList = boardService.findAll();
	        
	        ByteArrayOutputStream stream = excelDownload.buildExcelDocumentSXSSF(sheetName, headerList, colList, dataList);
 
        	String fileName = sheetName+"_"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))+".xlsx";
			String orgFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			
			return ResponseEntity.ok()
					 //attachement = 로컬에 저장, filename = 다운로드시 파일 이름 지정 
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=" + orgFileName +";")
					.header(HttpHeaders.CONTENT_TYPE, "ms-vnd/excel") 
					.body(new ByteArrayResource(stream.toByteArray()));
		} catch(Exception e) {
			return new ResponseEntity<ByteArrayResource>(HttpStatus.CONFLICT);
		}
	}
}
