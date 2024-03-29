package com.project.vue.user.member;

import javax.transaction.Transactional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.vue.common.Utils;
import com.project.vue.common.auth.WebAuthenticationProvider;
import com.project.vue.common.exception.BizException;
import com.project.vue.common.exception.ErrorCode;
import com.project.vue.user.payload.MemberSignUpRequest;
import com.project.vue.user.role.RoleEnum;
import com.project.vue.user.role.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final WebAuthenticationProvider webAuthenticationProvider;

	/**
	 * 유저 정보 DB 저장
	 * @param req MemberSignUpRequest
	 */
	@Transactional
    public void save(MemberSignUpRequest req) {
		if (isUserId(req.getUserId())) throw new BizException("잘못된 접근입니다.", ErrorCode.BAD_REQUEST);

		String uuid = Utils.getUUID32();
		while (memberRepository.existsById(uuid)) uuid = Utils.getUUID32();

    	MemberEntity entity = MemberEntity.builder()
    			.memberUid(uuid)
    			.userId(req.getUserId())
    			.userPwd(passwordEncoder.encode(req.getUserPwd()))
    			.userName(req.getUserName())
    			.gender(req.getGender())
    			.age(req.getAge())
    			.email(req.getEmail())
    			.phone(req.getPhone())
    			.role(roleRepository.findByRoleKey(RoleEnum.USER.getRoleKey())
    	    			.orElseThrow(() -> new BizException("데이터베이스 에서 해당 권한을 찾을 수 없습니다.", ErrorCode.NOT_FOUND))).build();

    	memberRepository.save(entity);

    	autoLogin(req.getUserId(), req.getUserPwd());
    }

    /**
     * 유저 ID DB 존재 여부
     * @param userId
     * @return boolean
     */
    public boolean isUserId(String userId) {
    	return memberRepository.findByUserId(userId).isPresent();
    }

    /**
     * 자동 로그인
     * @param userId 유저 ID
     * @param userPwd 유저 비밀번호
     */
    private void autoLogin(String userId, String userPwd) {
        Authentication authenticatedUser = 
        		webAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userId, userPwd));
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser); // 인증 객체를 SecurityContextHolder에 설정
    }
}
