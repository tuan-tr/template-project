package com.tth.persistence.entity;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tth.common.jpa.AuditEntity;
import com.tth.common.jpa.NanoidGenerator;
import com.tth.persistence.constant.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User extends AuditEntity {

	@Id
	@GenericGenerator(name = "nanoid-generator", strategy = NanoidGenerator.NAME)
	@GeneratedValue(generator = "nanoid-generator", strategy = GenerationType.IDENTITY)
	private String id;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	private String name;

	private OffsetDateTime effectiveFrom;

	private OffsetDateTime effectiveTo;

}
