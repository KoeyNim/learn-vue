package com.project.vue.common.image;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
	List<ImageEntity> findAllByBoardSeqno(Long boardSeqno);
	Optional<ImageEntity> findByFileNm(String fileNm);
	void deleteByFileNm(String fileNm);
}
