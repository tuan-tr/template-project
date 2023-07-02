package com.tth.persistence.entity;

import com.tth.common.jpa.AuditEntity;
import com.tth.common.jpa.NanoidGenerator;
import com.tth.persistence.constant.UserStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Collection;

@Entity
@Table(name = "_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User extends AuditEntity {

	@Version
	private Integer version;

	@Id
	@GeneratedValue(generator = NanoidGenerator.SIMPLE_NAME, strategy = GenerationType.IDENTITY)
	private String id;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	private String name;

	private OffsetDateTime effectiveStart;

	private OffsetDateTime effectiveEnd;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Collection<UserGroup> userGroups;

}
