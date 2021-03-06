package com.project.vue.member;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.vue.common.StringCryptoConverter;
import com.project.vue.role.RoleEntity;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "member")
public class MemberEntity implements UserDetails {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@NotBlank
	@Column(columnDefinition = "varchar(255)")
	@Convert(converter = StringCryptoConverter.class)
	private String userId;

	@NotBlank
	@Column(columnDefinition = "varchar(255)")
	@Convert(converter = StringCryptoConverter.class)
	private String userName;

	@NotBlank
	@Column(columnDefinition = "varchar(255)")
	private String userPwd;

	@NotNull
	private Integer age;

	@NotBlank
	@Column(columnDefinition = "varchar(255)")
	@Convert(converter = StringCryptoConverter.class)
	private String email;

	@NotBlank
	@Column(columnDefinition = "varchar(4)")
	private String gender;

	@NotBlank
	@Column(columnDefinition = "varchar(255)")
	@Convert(converter = StringCryptoConverter.class)
	private String phone;

	@NotNull
	@OneToOne
	@JoinColumn(name="role", referencedColumnName = "role_Key")
	private RoleEntity role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role.getRoleKey();
            }
        });
        return collect;
	}

	@Override
	public String getPassword() {
		return this.userPwd;
	}

	@Override
	public String getUsername() {
		return this.userId;
	}

    /**
     * ?????? ?????? ??????
     *  true : ????????????
     *  false : ?????????
     */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

    /**
     * ?????? ?????? ??????
     *  true : ????????? ??????
     *  false : ??????
     */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

    /**
     * ?????? ???????????? ?????? ??????
     *  true : ?????? ??????
     *  false : ?????????
     */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

    /**
     * ?????? ????????? ??????
     *  true : ????????????
     *  false : ????????? ??????
     */
	@Override
	public boolean isEnabled() {
		return true;
	}

}
