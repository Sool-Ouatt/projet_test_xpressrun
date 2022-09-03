package com.xpress.auth.test.utils;

public enum ErrorMessages {
	MISSING_REQUIRED_FIELDS(
			"Un (des) champ(s) obligatoire(s) est(sont) manquant(s), Veuillez verifier la documentation de l'api "), 
	RECORD_NOT_FOUND("l'enregistrement avec l'id fourni est introuvable"), 
	USER_BANNED("Cet utilisateur est banni"),
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
