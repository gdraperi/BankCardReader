package com.sec.bankcardreader.creditCardNfcReader.iso7816emv;

import com.sec.bankcardreader.creditCardNfcReader.enums.TagTypeEnum;
import com.sec.bankcardreader.creditCardNfcReader.enums.TagValueTypeEnum;


public interface ITag {

	enum Class {
		UNIVERSAL, APPLICATION, CONTEXT_SPECIFIC, PRIVATE
	}

	boolean isConstructed();

	byte[] getTagBytes();

	String getName();

	String getDescription();

	TagTypeEnum getType();

	TagValueTypeEnum getTagValueType();

	Class getTagClass();

	int getNumTagBytes();

}
