package com.tth.common.jpa;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.tth.common.util.NanoIdUtils;

public class NanoidGenerator implements IdentifierGenerator {

	public static final String NAME = "com.tth.common.jpa.NanoidGenerator";

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		return NanoIdUtils.randomNanoId();
	}

}
