package com.tth.persistence.entity;

import com.tth.common.jpa.AuditEntity;
import com.tth.common.jpa.NanoidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_group")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserGroup extends AuditEntity {

	@Id
	@GeneratedValue(generator = NanoidGenerator.SIMPLE_NAME, strategy = GenerationType.IDENTITY)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "id", updatable = false)
	private User user;

	@Column(insertable = false, updatable = false)
	private String userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupId", referencedColumnName = "id", updatable = false)
	private Group group;

	@Column(insertable = false, updatable = false)
	private String groupId;

}
