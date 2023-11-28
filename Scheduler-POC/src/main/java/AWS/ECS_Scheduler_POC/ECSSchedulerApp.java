package AWS.ECS_Scheduler_POC;

import java.time.Clock;
import java.time.Instant;
import java.util.Properties;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.scheduler.SchedulerClient;
import software.amazon.awssdk.services.scheduler.model.AssignPublicIp;
import software.amazon.awssdk.services.scheduler.model.AwsVpcConfiguration;
import software.amazon.awssdk.services.scheduler.model.CapacityProviderStrategyItem;
import software.amazon.awssdk.services.scheduler.model.CreateScheduleRequest;
import software.amazon.awssdk.services.scheduler.model.DeleteScheduleRequest;
import software.amazon.awssdk.services.scheduler.model.EcsParameters;
import software.amazon.awssdk.services.scheduler.model.FlexibleTimeWindow;
import software.amazon.awssdk.services.scheduler.model.FlexibleTimeWindowMode;
import software.amazon.awssdk.services.scheduler.model.GetScheduleRequest;
import software.amazon.awssdk.services.scheduler.model.GetScheduleResponse;
import software.amazon.awssdk.services.scheduler.model.LaunchType;
import software.amazon.awssdk.services.scheduler.model.NetworkConfiguration;
import software.amazon.awssdk.services.scheduler.model.ScheduleState;
import software.amazon.awssdk.services.scheduler.model.Target;
import software.amazon.awssdk.services.scheduler.model.UpdateScheduleRequest;

public class ECSSchedulerApp {
	public static SchedulerClient client(Region region, final Properties oAuthProperties) {
		String accessKeyId = oAuthProperties.getProperty("aws.accessKeyId");
		String secretAccessKey = oAuthProperties.getProperty("aws.secretAccessKey");

		AwsCredentialsProvider awsCredentialsProvider;
		AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
		awsCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

		SchedulerClient client = SchedulerClient.builder().region(region).credentialsProvider(awsCredentialsProvider)
				.build();

		return client;
	}

	public static Target target() {
//		Target target = Target.builder().roleArn("arn/:aws:iam::323500295462:role/service-role/Amazon_EventBridge_Scheduler_ECS_b0bb7096e5")
		Target target = Target.builder().roleArn("arn:aws:iam::323500295462:role/service-role/Amazon_EventBridge_Scheduler_ECS_a1c83b1a67")
//		Target target = Target.builder().roleArn("arn:aws:iam::323500295462:role/role-ecs-aithentic-poc")
				.arn("arn:aws:ecs:us-east-2:323500295462:cluster/Devcluster")
				.ecsParameters(ecsParameters())
				.build();
		return target;
	}

	private static EcsParameters ecsParameters() {
		AwsVpcConfiguration vpcConfiguration = AwsVpcConfiguration.builder().subnets(
				"subnet-0e683dd42d1c1792e,subnet-07c99039fc8170a04")//,subnet-0a43ef3e803bf64fc,subnet-0af7e68bb918f43ae")
				.securityGroups("sg-055b23fa5b784e7de")
				.assignPublicIp(AssignPublicIp.ENABLED)
				.build();

		NetworkConfiguration configuration = NetworkConfiguration.builder().awsvpcConfiguration(vpcConfiguration)
				.build();
		
		CapacityProviderStrategyItem capacityProviderStrategy = CapacityProviderStrategyItem.builder().base(1)
				.capacityProvider("FARGATE_SPOT")
				.weight(1)
				.build();

		return EcsParameters.builder().launchType(LaunchType.FARGATE).platformVersion("1.4.0")
				.networkConfiguration(configuration)
//				.group("testGroup")
//				.referenceId("1")
				.enableECSManagedTags(true).enableExecuteCommand(true).taskCount(1)
				.taskDefinitionArn("arn:aws:ecs:us-east-2:323500295462:task-definition/sip_new")
				.capacityProviderStrategy(capacityProviderStrategy)
				.build();
	}

	public static void createScheduleRequest(SchedulerClient client, Target ecsTarget) {
		CreateScheduleRequest createScheduleRequest = CreateScheduleRequest.builder()
				.flexibleTimeWindow(FlexibleTimeWindow.builder().mode(FlexibleTimeWindowMode.OFF).build())
				.state(ScheduleState.ENABLED).name("TestFor4Min").description("Creating Schedule for testing")
				.target(ecsTarget).scheduleExpression("Rate(4 minutes)").scheduleExpressionTimezone("Asia/Calcutta")
				.groupName("M365Scheduler").startDate(Instant.now(Clock.systemUTC()).plusSeconds(120)).build();

		client.createSchedule(createScheduleRequest);
		System.out.println("Created schedule with rate expression and an Amazon ECS templated target");
	}

	public static void updateScheduleRequest(SchedulerClient client, Target ecsTarget) {
		UpdateScheduleRequest updateScheduleRequest = UpdateScheduleRequest.builder().name("TestFor3Min")
				.state(ScheduleState.ENABLED)
				.flexibleTimeWindow(FlexibleTimeWindow.builder().mode(FlexibleTimeWindowMode.OFF).build())
				.description("Creating Schedule for testing").target(ecsTarget).scheduleExpression("Rate(120 minutes)")
				.scheduleExpressionTimezone("UTC").startDate(Instant.now(Clock.systemUTC()).plusSeconds(120)).build();

		client.updateSchedule(updateScheduleRequest);
		System.out.println("Updated schedule with rate expression and an Amazon ECS templated target");
	}

	public static void deleteScheduleRequest(SchedulerClient client) {
		DeleteScheduleRequest deleteScheduleRequest = DeleteScheduleRequest.builder().name("TestFor4Min")
				.groupName("M365Scheduler")
				.build();
		client.deleteSchedule(deleteScheduleRequest);

		System.out.println("Deleted scheduler schedule with rate expression and an Amazon ECS templated target");

	}
}
