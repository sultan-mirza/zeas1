package com.itc.zeas.exception;

public class ZeasException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String error;
	protected int errorCode;
	protected String inputValue;
	
	public ZeasException() {
	}
	
	public ZeasException(int errorCode,String errorMsg,String inputVlaue){
		
		this.errorCode=errorCode;
		this.error=errorMsg;
		this.inputValue=inputVlaue;

	}
	
	@Override
	public String toString() {
		return this.error+ " "+this.inputValue;
	}

	public String getError() {
		return error;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getInputValue() {
		return inputValue;
	}
}
