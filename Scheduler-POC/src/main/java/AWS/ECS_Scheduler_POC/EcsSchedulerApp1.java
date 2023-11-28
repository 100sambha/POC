package AWS.ECS_Scheduler_POC;

import java.io.IOException;
import java.util.Properties;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.eventbridge.AmazonEventBridge;
import com.amazonaws.services.eventbridge.AmazonEventBridgeClientBuilder;
import com.amazonaws.services.eventbridge.model.AwsVpcConfiguration;
import com.amazonaws.services.eventbridge.model.EcsParameters;
import com.amazonaws.services.eventbridge.model.NetworkConfiguration;
import com.amazonaws.services.eventbridge.model.PutTargetsRequest;
import com.amazonaws.services.eventbridge.model.PutTargetsResult;
import com.amazonaws.services.eventbridge.model.Target;

public class EcsSchedulerApp1 {
	public static void main(String[] args) {

		final String RULE_NAME = "eventbridge-logs";
		final String CLUSTER_NAME = "aithentic-sip";
		final String TASK_DEFINITION = "sip_sch";
		final String CONTAINER_NAME = "sip";
		final String SUBNET_ID = "10.20.0.13";
		final String SECURITY_GROUP_ID = "sg-0cc4d233e024a4274";

		AmazonEventBridge eventBridge = clientCred();

		Target target = new Target().withRoleArn("arn:aws:iam::323500295462:role/role-ecs-aithentic-poc")
				.withArn(String.format("arn:aws:ecs:us-east-2:323500295462:cluster/aithentic-sip", CLUSTER_NAME))
				.withEcsParameters(new EcsParameters()
				.withTaskDefinitionArn(String.format("arn:aws:ecs:us-east-2:323500295462:task-definition/sip_new:2", TASK_DEFINITION))
				.withLaunchType("FARGATE")
				.withNetworkConfiguration(new NetworkConfiguration()
				.withAwsvpcConfiguration(new AwsVpcConfiguration()
				.withSubnets(SUBNET_ID)
				.withSecurityGroups(SECURITY_GROUP_ID)))
				.withTaskCount(1)
				.withPlatformVersion("LATEST")
				.withGroup(CONTAINER_NAME));

		PutTargetsRequest request = new PutTargetsRequest().withRule(RULE_NAME).withTargets(target.withId("1"));

		
		
		PutTargetsResult result = eventBridge.putTargets(request);
		System.out.println("Event rule " + RULE_NAME + " created with target ID " + result.getFailedEntryCount());
		
	}

	private static AmazonEventBridge clientCred() {
		final Properties oAuthProperties = new Properties();

		try {
			oAuthProperties.load(EcsSchedulerApp1.class.getResourceAsStream("../Scheduler_POC/aws.properties"));
		} catch (IOException e) {
			System.out.println("Unable to read aws configuration.");
		}
		String accessKeyId = oAuthProperties.getProperty("aws.accessKeyId");
		String secretAccessKey = oAuthProperties.getProperty("aws.secretAccessKey");

		BasicAWSCredentials creds = new BasicAWSCredentials(accessKeyId, secretAccessKey);

		AmazonEventBridge eventBridge = AmazonEventBridgeClientBuilder.standard().withRegion(Regions.US_EAST_2)
				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();
		return eventBridge;
	}
}