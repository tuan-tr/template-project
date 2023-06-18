package com.tth.persistence.entity;

import com.tth.common.jpa.AuditEntity;
import com.tth.common.jpa.NanoidGenerator;
import com.tth.persistence.constant.UserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;

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
