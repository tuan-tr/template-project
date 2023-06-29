package com.tth.persistence.entity;

import com.tth.common.jpa.AuditEntity;
import com.tth.common.jpa.NanoidGenerator;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "_group")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserGroup extends AuditEntity {

	@Id
	@GenericGenerator(name = "nanoid-generator", strategy = NanoidGenerator.NAME)
	@GeneratedValue(generator = "nanoid-generator", strategy = GenerationType.IDENTITY)
	private String id;

	private String userId;

	private String groupId;

}
