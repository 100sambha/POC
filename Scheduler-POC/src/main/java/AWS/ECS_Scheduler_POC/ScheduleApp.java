package AWS.ECS_Scheduler_POC;

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
			System.out.println("Unable to read aws configuration file.");
		}

		SchedulerClient client = ECSSchedulerApp.client(region, oAuthProperties);

		Target target = ECSSchedulerApp.target();

		Scanner sc = new Scanner(System.in);
		System.out.println("Choose Option for ECS Scheduler :");
		System.out.println("0.Exit\n1.Create\n2.Update\n3.Delete\n4.Get");
		int choice = sc.nextInt();
		sc.close();
		switch (choice) {
		case 1:
			ECSSchedulerApp.createScheduleRequest(client, target);
			break;
		case 2:
			ECSSchedulerApp.updateScheduleRequest(client, target);
			break;
		case 3:
			ECSSchedulerApp.deleteScheduleRequest(client);
			break;
//		case 4:
//			ECSScheduler.getSchedulerRequest(client,target);
		default:System.exit(0);
		}

	}
}
