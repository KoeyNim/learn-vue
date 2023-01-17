package com.project.vue.role;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

	private final RoleRepository roleRepository;
	
	/** @PostConstruct : 의존성(Bean)이 초기화 된 직후에 한번만 실행 (서버 시작시 한번만 실행) */
	@Transactional @PostConstruct
	public void initRole() {
		/** 권한 계층 초기 데이터 삽입 */
		if(roleRepository.count() == 0) {
			log.debug("## initRole");
			List<RoleEntity> list = new ArrayList<>();
			/** ADMIN 권한 체크 후 list에 추가 */
			for (RoleEnum role : RoleEnum.values()) {
				RoleEntity entity = new RoleEntity();
				if (role.getRoleKey().equals("ROLE_ADMIN")) {
					entity.setRoleKey(role.getRoleKey());
					list.add(entity);
					continue;
				}
			entity.setRoleKey(role.getRoleKey());
			entity.setParent(list.get(list.size()-1));
			list.add(entity);
			}
			roleRepository.saveAll(list);
		}
	}

	/** 계층권한 String Builder **/
	public String BuildRoleHierarchy() {
        log.debug("## BuildRoleHierarchy");
        List<RoleEntity> list = roleRepository.findAll();

        Iterator<RoleEntity> iterator = list.iterator();
        StringBuilder concatRoles = new StringBuilder();
        while (iterator.hasNext()) {
        	RoleEntity entity = iterator.next();
            if (ObjectUtils.isNotEmpty(entity.getParent())) {
                concatRoles.append(entity.getParent().getRoleKey());
                concatRoles.append(" > ");
                concatRoles.append(entity.getRoleKey());
                concatRoles.append("\n");
            }
        }
        return concatRoles.toString();
	}

}
