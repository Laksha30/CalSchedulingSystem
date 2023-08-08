package com.example.CalScheduler.service;

import com.example.CalScheduler.Constants;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

@Service
public class GoogleCredentialsService {
	
	@Autowired
	private Environment environment;
	
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private String clientId = environment.getProperty(Constants.GOOGLE_CLIENT_ID_STRING);

    private String clientSecret = environment.getProperty(Constants.GOOGLE_CLIENT_SECRET_STRING);

    private final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/calendar");

    public Credential getCredentials(String authorizationCode) throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets clientSecrets = new GoogleClientSecrets();
        clientSecrets.getDetails().setClientId(clientId);
        clientSecrets.getDetails().setClientSecret(clientSecret);

        AuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("online")
                .build();

        GoogleTokenResponse tokenResponse = (GoogleTokenResponse) flow.newTokenRequest(authorizationCode).execute();
        return flow.createAndStoreCredential(tokenResponse, "user");
    }

    public String buildAuthorizationUrl() throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets clientSecrets = new GoogleClientSecrets();
        clientSecrets.getDetails().setClientId(clientId);
        clientSecrets.getDetails().setClientSecret(clientSecret);

        AuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("online")
                .build();

        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
        return authorizationUrl.build();
    }
}

