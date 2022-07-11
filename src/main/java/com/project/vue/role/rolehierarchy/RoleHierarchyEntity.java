package com.project.vue.role.rolehierarchy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.project.vue.role.RoleEnum;

import lombok.Data;

@Entity
@Table(name = "ROLE_HIERARCHY")
@Data
public class RoleHierarchyEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING) // 상수가 아닌 String으로 저장
    private RoleEnum roleName;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent", referencedColumnName = "role_name")
    private RoleHierarchyEntity parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
    private Set<RoleHierarchyEntity> roleHierarchy = new HashSet<RoleHierarchyEntity>();

}