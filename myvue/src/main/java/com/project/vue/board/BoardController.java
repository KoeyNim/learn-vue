package com.project.vue.board;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.vue.common.Constants;
import com.project.vue.common.SimpleResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@ControllerAdvice
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX+"/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	@ResponseBody
	@GetMapping
	public ResponseEntity<List<BoardEntity>> BoardList() {
		return ResponseEntity.ok(boardService.findAll());
	}
	
	@GetMapping("/detail/{id}")
	public String BoardDetail(@PathVariable("id") Long id) {
		return "board/board-detail";
	}
	
	@GetMapping("/update/{id}")
	public ResponseEntity<Optional<BoardEntity>> BoardFindById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(boardService.findById(id));
	}
	
	@ResponseBody
	@PostMapping("/create")
	public ResponseEntity<SimpleResponse> BoardCreate(@RequestBody BoardEntity board) {
		boolean r = true;
		try {
			boardService.save(board);
		} catch (Exception e) {
			r = false;
		}
		return ResponseEntity.ok(SimpleResponse.builder()
					.success(r)
					.message(r ? "글이 등록되었습니다." : "잘못된 요청입니다.")
					.build());
	}

	/*
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<SimpleResponse> exception2() {
		return ResponseEntity
					.badRequest()
					.body(SimpleResponse.builder()
					.success(false)
					.message("잘못된 요청입니다. 222")
					.build());
	}
	*/

	@PutMapping("/update/{id}")
	public ResponseEntity<SimpleResponse> Boardupdate(@RequestBody BoardEntity board, @PathVariable("id") Long id) {
		log.debug("@@@@@@id{}",id);
		boolean r = true;
		try {
			board.setId(id);
			boardService.save(board);
		} catch (Exception e) {
			r = false;
		}
		return ResponseEntity.ok(SimpleResponse.builder()
					.success(r)
					.message(r ? "글이 수정되었습니다." : "잘못된 요청입니다.")
					.build());
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity BoardDelete(@PathVariable("id") Long id) {
		return null;
	}
}
