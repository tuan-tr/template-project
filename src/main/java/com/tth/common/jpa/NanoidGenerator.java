package com.tth.common.jpa;

import com.tth.common.util.NanoIdUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class NanoidGenerator implements IdentifierGenerator {

	public static final String NAME = "com.tth.common.jpa.NanoidGenerator";
	public static final String SIMPLE_NAME = "NanoidGenerator";

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		return NanoIdUtils.randomNanoId();
	}

}
