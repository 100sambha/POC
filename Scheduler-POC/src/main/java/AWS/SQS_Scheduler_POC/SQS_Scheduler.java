package AWS.SQS_Scheduler_POC;

import java.util.Properties;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.scheduler.SchedulerClient;
import software.amazon.awssdk.services.scheduler.model.CreateScheduleRequest;
import software.amazon.awssdk.services.scheduler.model.DeleteScheduleRequest;
import software.amazon.awssdk.services.scheduler.model.FlexibleTimeWindow;
import software.amazon.awssdk.services.scheduler.model.FlexibleTimeWindowMode;
import software.amazon.awssdk.services.scheduler.model.Target;
import software.amazon.awssdk.services.scheduler.model.UpdateScheduleRequest;

public class SQS_Scheduler {
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
		Target target = Target.builder()
				.roleArn("arn:aws:iam::323500295462:role/service-role/Amazon_EventBridge_Scheduler_SQS_4b37930cd0")
				.arn("arn:aws:sqs:us-east-2:323500295462:mq-aithentic-poc")
				.input("Message for scheduleArn: 'arn:aws:sqs:us-east-2:323500295462:mq-aithentic-poc', scheduledTime: '2023/04/26 14:55'")
				.build();
		return target;
	}

	public static void createScheduleRequest(SchedulerClient client, Target ecsTarget) {
		CreateScheduleRequest createScheduleRequest = CreateScheduleRequest.builder().name("TestForDailyFrequency")
				.scheduleExpression("cron(30 10 ? * 3,6 *)").target(ecsTarget)
				.flexibleTimeWindow(FlexibleTimeWindow.builder().mode(FlexibleTimeWindowMode.OFF).build()).build();
		client.createSchedule(createScheduleRequest);
		System.out.println("Created schedule with rate expression and an Amazon SQS templated target");
	}

	public static void updateScheduleRequest(SchedulerClient client, Target ecsTarget) {
		UpdateScheduleRequest updateScheduleRequest = UpdateScheduleRequest.builder().name("TestForDailyFrequency")
				.scheduleExpression("cron(0 8 1 * ? *)").target(ecsTarget)
				.flexibleTimeWindow(FlexibleTimeWindow.builder().mode(FlexibleTimeWindowMode.OFF).build()).build();
		client.updateSchedule(updateScheduleRequest);
		System.out.println("Updated schedule with rate expression and an Amazon SQS templated target");
	}
	
	public static void deleteScheduleRequest(SchedulerClient client) {
		DeleteScheduleRequest deleteScheduleRequest = DeleteScheduleRequest.builder().name("TestForDailyFrequency")
				.build();
		client.deleteSchedule(deleteScheduleRequest);
		System.out.println("Deleted scheduler schedule with rate expression and an Amazon SQS templated target");
	}
}
