package com.tth.persistence.entity;

import com.tth.common.jpa.AuditEntity;
import com.tth.common.jpa.NanoidGenerator;
import com.tth.persistence.constant.GroupStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "_group")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Group extends AuditEntity {

	@Id
	@GeneratedValue(generator = NanoidGenerator.SIMPLE_NAME, strategy = GenerationType.IDENTITY)
	private String id;

	@Enumerated(EnumType.STRING)
	private GroupStatus status;

	private String name;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private Collection<UserGroup> userGroups;

}
