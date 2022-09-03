package com.xpress.auth.test.utils;

public enum ErrorMessages {
	MISSING_REQUIRED_FIELDS(
			"Un (des) champ(s) obligatoire(s) est(sont) manquant(s), Veuillez verifier la documentation de l'api "), 
	RECORD_ALREADY_EXIST("Cet utilisateur existe déjà)"), 
	INTERNAL_SERVER_ERROR("Internal Server Error"), 
	RECORD_NOT_FOUND("l'enregistrement avec l'id fourni est introuvable"), 
	AUTHENTICATION_FAILED("Authentication failed"), 
	COUND_NOT_UPDATE_RECORD("Could not update the Record"), 
	COUND_NOT_DELETE_RECORD("Could not delete the Record"), 
	EMAIL_ADDRESS_NOT_VERIFIED("Email Address Could not be Verified"),
	IP_ADD_BANNED("Cet adresse ip est banni");

	private String errorMessage;

	ErrorMessages(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
}
