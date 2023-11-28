package AWS.SQS_Scheduler_POC;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.scheduler.SchedulerClient;
import software.amazon.awssdk.services.scheduler.model.Target;

public class ScheduleApp {
	public static void main(String[] args) {

		System.out.println("Hello World!");

		Region region = Region.of("us-east-2");
		final Properties oAuthProperties = new Properties();
		try {
			oAuthProperties.load(ScheduleApp.class.getResourceAsStream("../Scheduler_POC/aws.properties"));
		} catch (IOException e) {
			System.out.println("Unable to read aws configuration.");
		}

		SchedulerClient client = SQS_Scheduler.client(region, oAuthProperties);

		Target target = SQS_Scheduler.target();

		Scanner sc = new Scanner(System.in);
		System.out.println("Choose Option for SQS Scheduler :");
		System.out.println("0.Exit\n1.Create\n2.Update\n3.Delete");
		int choice = sc.nextInt();
		sc.close();
		switch (choice) {
		case 1:
			SQS_Scheduler.createScheduleRequest(client, target);
			break;
		case 2:
			SQS_Scheduler.updateScheduleRequest(client, target);
			break;
		case 3:
			SQS_Scheduler.deleteScheduleRequest(client);
			break;
		default:System.exit(0);
		}

	}
}
