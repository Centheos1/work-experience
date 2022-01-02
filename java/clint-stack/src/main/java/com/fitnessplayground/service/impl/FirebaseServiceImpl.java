package com.fitnessplayground.service.impl;

import com.fitnessplayground.service.FirebaseService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import jdk.nashorn.internal.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FirebaseServiceImpl implements FirebaseService {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseServiceImpl.class);

    @Value("${firebase.baseurl}")
    private String BASE_URL;

    @Value("${firebase.api.key}")
    private String API_KEY;

    @Override
    public void deleteUser(String firebaseId) {
//        TODO: IMPLEMENT ME!
//        Need to be logged in a the user to get the active idToken for the user ... ???

    }

//    private Firestore initFirestore() {
//
//        JSONParser parser = new JSONParser();
//
//        Object credential = p
//
//        GoogleCredentials credentials = null;
//        try {
//            credentials = GoogleCredentials.getApplicationDefault();
//        } catch (IOException e) {
//            e.printStackTrace();
//            logger.error("Error getting google credentials: {}",e.getMessage());
//        }
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(credentials)
//                .setProjectId(projectId)
//                .build();
//        FirebaseApp.initializeApp(options);
//
//        Firestore db = FirestoreClient.getFirestore();
//
//
//    }
}
