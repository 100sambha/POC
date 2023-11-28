package AWS.ECS_Scheduler_POC;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.json.JSONObject;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class EcsSchedulerApp2 {
    private static final String SECRET_NAME = "aithentic-db-details";
    private static final String PROPERTY_FILE_PATH = "./application.properties";
    private static final String ACCESS_KEY = "AKIAUWUQ6YUTLVENLLKA";
    private static final String SECRET_KEY = "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";

    public static void main(String[] args) {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        SecretsManagerClient secretsManagerClient = SecretsManagerClient.builder().region(Region.US_EAST_2).credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).build();
        GetSecretValueRequest secretValueRequest = GetSecretValueRequest.builder().secretId(SECRET_NAME).build();
        GetSecretValueResponse secretValueResponse = secretsManagerClient.getSecretValue(secretValueRequest);
        String secretValue = secretValueResponse.secretString();
        JSONObject obj = new JSONObject(secretValue);
        
        System.out.println(obj);
        
        Properties properties = new Properties();

        try {
        	System.out.println();
            properties.load(EcsSchedulerApp2.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = "";
        properties.setProperty("db.username", "your-db-username");
        properties.setProperty("db.password", "your-db-password");

        try {
            properties.store(new FileOutputStream(PROPERTY_FILE_PATH), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        secretsManagerClient.close();
    }
}
