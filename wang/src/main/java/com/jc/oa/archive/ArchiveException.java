package com.jc.oa.archive;

import com.jc.system.CustomException;

public class ArchiveException extends CustomException {

	public ArchiveException() {
	}

	public ArchiveException(String msg) {
		super(msg);
	}

	public ArchiveException(Throwable e) {
		super(e);
	}

	public ArchiveException(String msg, Throwable e) {
		super(msg, e);
	}

}
