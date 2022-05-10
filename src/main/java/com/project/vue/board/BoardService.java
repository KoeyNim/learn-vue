package com.project.vue.board;

import java.io.File;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.vue.common.SimpleResponse;
import com.project.vue.file.FileRepository;
import com.project.vue.specification.SearchSpecification;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	private final FileRepository fileRepository;
	
	private final JPAQueryFactory queryFactory;
	
	// 수정시간이 바뀌게되는 이슈로 인해 querydsl로 세부 조작
	@Transactional
	public void saveCount(Long id) {
		QBoardEntity qBoardEntity = QBoardEntity.boardEntity;
		queryFactory.update(qBoardEntity)
					.set(qBoardEntity.count, qBoardEntity.count.add(1) )
					.where(qBoardEntity.id.eq(id))
					.execute();
	}
	
	@Transactional
	public void save(BoardEntity board) {
		BoardEntity findBoard = null;
		if (ObjectUtils.isNotEmpty(board.getId())) { // board.id 값이 비어있지 않은지 확인 (비어있으면 등록상황이다.)
				
				findBoard = boardRepository.findById(board.getId()).orElseThrow(); // 수정 전에 저장된 board 객체를 찾는다.

			if (ObjectUtils.isNotEmpty(findBoard.getFileEntity()) // 수정전 board 객체의 파일이 비어있는지 확인
					&& ObjectUtils.notEqual(board.getFileEntity().getId(), findBoard.getFileEntity().getId())) { // 저장할 파일과 저장 되어있는 파일의 id값 일치여부 확인

				fileRepository.deleteById(findBoard.getFileEntity().getId()); // 조건에 모두 만족하는 파일 데이터를 삭제 (수정되어 필요없는 파일)
			}
		} else {
			board.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		boardRepository.save(board);
	}
	
    public Page<BoardEntity> findAll(int pageIndex, int pageSize, String srchKey, String srchVal) {
    	
    	PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by("registDate").descending());
    	
		return boardRepository.findAll(
				SearchSpecification.searchBoardSpecification(srchKey, srchVal), pageRequest);
    }
    
    public List<BoardEntity> findAll() {
		return boardRepository.findAll();
    }
    
    public BoardEntity findById(Long id) {
		return boardRepository.findById(id).orElseThrow();
    }
    
    public void deleteById(Long id) {
    	BoardEntity findBoard = findById(id);
    	if(ObjectUtils.isNotEmpty(findBoard.getFileEntity())) {
        	File file = new File(findBoard.getFileEntity().getFilePath() + findBoard.getFileEntity().getFileNm());
        	boardRepository.deleteById(id);
//        	if (file.exists()) {
        		file.delete();
//        	}
        	return;
    	}
    	boardRepository.deleteById(id);
    }
    
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<SimpleResponse> exception2() {
//		fileRepository.deleteById(board.getId());
		return ResponseEntity
					.badRequest()
					.body(SimpleResponse.builder()
					.success(false)
					.message("잘못된 요청입니다. 222")
					.build());
	}
	
}