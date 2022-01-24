package com.uoc.ease_care_backend;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

@SpringBootApplication
public class EaseCareBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EaseCareBackendApplication.class, args);
          try {
            ClassLoader classLoader = EaseCareBackendApplication.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());
            FileInputStream serviceAccount =
                    new FileInputStream(file.getAbsolutePath());

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://easecare-a3fe5-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
