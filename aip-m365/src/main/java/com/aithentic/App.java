package com.aithentic;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.json.JSONArray;

//import com.aithentic.model.Product_User;
import com.microsoft.graph.callrecords.models.CallRecord;
import com.microsoft.graph.models.LicenseDetails;
import com.microsoft.graph.models.LicenseUnitsDetail;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.ServicePlanInfo;
import com.microsoft.graph.models.SubscribedSku;
import com.microsoft.graph.models.Subscription;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.LicenseDetailsCollectionPage;
import com.microsoft.graph.requests.MessageCollectionPage;
import com.microsoft.graph.requests.OrganizationCollectionPage;
import com.microsoft.graph.requests.SubscribedSkuCollectionPage;
import com.microsoft.graph.requests.SubscriptionCollectionPage;
import com.microsoft.graph.requests.UserCollectionPage;
import com.microsoft.graph.requests.UserDeltaCollectionPage;

public class App {
	private static Logger LOGGER = Logger.getLogger(App.class);
//	private static Logger LOGGER = LoggerFactory.getLogger(App.class);
	

	public static void main(String[] args) {
		LOGGER.info("Java Graph Tutorial");
		LOGGER.info("");

		final Properties oAuthProperties = new Properties();
		try {
			System.out.println("Current Folder" + System.getProperty("user.dir"));
			oAuthProperties.load(new FileInputStream("src/main/resources/oAuth.properties"));
		} catch (IOException e) {
			LOGGER.error(
					"Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details.");
			return;
		}

		initializeGraph(oAuthProperties);

		greetUser();

		Scanner input = new Scanner(System.in);
		int choice = -1;

		while (choice != 0) {
			System.out.println("Please choose one of the following options:");
			System.out.println("0. Exit");
			System.out.println("1. Display access token");
			System.out.println("2. List my inbox");
			System.out.println("3. Send mail");
			System.out.println("4. List users (required app-only)");
			System.out.println("5. Get User By ID");
			System.out.println("6. Get Delta Users");
			System.out.println("7. Get list of License Details");
			System.out.println("8. Get List Of Subscribed skus");
			System.out.println("9. Get SubScribed SKU by ID");
			System.out.println("10. Get Office365 Activations User Detail");
			System.out.println("11. Get Office365 Activations User Counts");
			System.out.println("12. Get Office365 Active User Detail");
			System.out.println("13. Get Office365 Active User Counts");
			System.out.println("14. Get Office365 Services User Counts");
			System.out.println("15. Get M365 App User Detail");
			System.out.println("16. Get M365 App User Counts");
			System.out.println("17. List Subscriptions");
			System.out.println("18. Get Subscriptions");
			System.out.println("19. Get Call Record");
			System.out.println("20. Get Teams User Activity User Detail");
			System.out.println("21. Get Teams User Activity Counts");
			System.out.println("22. Get Teams User Activity User Counts");
			System.out.println("23. Get Email Activity User Detail");
			System.out.println("24. Get Email Activity Counts");
			System.out.println("25. Get Email Activity User Counts");
			System.out.println("26. Get Office365 Groups Activity Detail");
			System.out.println("27. Get Office365 Groups Activity Counts");
			System.out.println("28. Get Office365 Groups Activity Group Counts");
			System.out.println("29. Get Office365 Groups Activity Storage");
			System.out.println("30. Get Office365 Groups Activity File Counts");
			System.out.println("31. Get One Drive Activity User Detail");
			System.out.println("32. Get One Drive Activity User Counts");
			System.out.println("33. Get One Drive Activity File Counts");
			System.out.println("34. Get One Drive Usage Account Detail");
			System.out.println("35. Get One Drive Usage Account Counts");
			System.out.println("36. Get One Drive Usage File Counts");
			System.out.println("37. Get One Drive Usage Storage");
			System.out.println("38. Get Share Point Activity User Detail");
			System.out.println("39. Get Share Point Activity File Counts");
			System.out.println("40. Get Share Point Activity User Counts");
			System.out.println("41. Get Share Point Activity Pages");
			System.out.println("42. Get Share Point Site Usage Detail");
			System.out.println("43. Get Share Point Site Usage File Counts");
			System.out.println("44. Get Share Point Site Usage Site Counts");
			System.out.println("45. Get Share Point Site Usage Storage");
			System.out.println("46. Get Share Point Site Usage Pages");
			System.out.println("47. Get Yammer Activity User Detail");
			System.out.println("48. Get Yammer Activity Counts");
			System.out.println("49. Get Yammer Activity User Counts");
			System.out.println("50. Get Yammer Groups Activity Detail");
			System.out.println("51. Get Yammer Groups Activity Group Counts");
			System.out.println("52. Get Yammer Groups Activity Counts");
			System.out.println("53. Get Tenant ID");

			try {
				choice = input.nextInt();
			} catch (InputMismatchException ex) {
				LOGGER.warn("Input incorrect format");
			}

			input.nextLine();

			switch (choice) {
			case 0:
				LOGGER.info("Good Bye.....");
				break;
			case 1:
				displayAccessToken();
				break;
			case 2:
				listInbox();
				break;
			case 3:
				sendMail();
				break;
			case 4:
				listUsers();
				break;
			case 5:
				getUserByID();
				break;
			case 6:
				getDeltaUsers();
				break;
			case 7:
				listLicenseDetails();
				break;
			case 8:
				getSubscribedSkus();
				break;
			case 9:
				getSubscribedSku();
				break;
			case 10:
				getOffice365ActivationsUserDetail();
				break;
			case 11:
				getOffice365ActivationsUserCounts();
				break;
			case 12:
				getOffice365ActiveUserDetail();
				break;
			case 13:
				getOffice365ActiveUserCounts();
				break;
			case 14:
				getOffice365ServicesUserCounts();
				break;
			case 15:
				getM365AppUserDetail();
				break;
			case 16:
				getM365AppUserCounts();
				break;
			case 17:
				listSubscriptions();
				break;
			case 18:
				getSubscriptions();
				break;
			case 19:
				getCallRecord();
				break;
			case 20:
				getTeamsUserActivityUserDetail();
				break;
			case 21:
				getTeamsUserActivityCounts();
				break;
			case 22:
				getTeamsUserActivityUserCounts();
				break;
			case 23:
				getEmailActivityUserDetail();
				break;
			case 24:
				getEmailActivityCounts();
				break;
			case 25:
				getEmailActivityUserCounts();
				break;
			case 26:
				getOffice365GroupsActivityDetail();
				break;
			case 27:
				getOffice365GroupsActivityCounts();
				break;
			case 28:
				getOffice365GroupsActivityGroupCounts();
				break;
			case 29:
				getOffice365GroupsActivityStorage();
				break;
			case 30:
				getOffice365GroupsActivityFileCounts();
				break;
			case 31:
				getOneDriveActivityUserDetail();
				break;
			case 32:
				getOneDriveActivityUserCounts();
				break;
			case 33:
				getOffice365GroupsActivityFileCounts();
				break;
			case 34:
				getOneDriveUsageAccountDetail();
				break;
			case 35:
				getOneDriveUsageAccountCounts();
				break;
			case 36:
				getOneDriveUsageFileCounts();
				break;
			case 37:
				getOneDriveUsageStorage();
				break;
			case 38:
				getSharePointActivityUserDetail();
				break;
			case 39:
				getSharePointActivityFileCounts();
				break;
			case 40:
				getSharePointActivityUserCounts();
				break;
			case 41:
				getSharePointActivityPages();
				break;
			case 42:
				getSharePointSiteUsageDetail();
				break;
			case 43:
				getSharePointSiteUsageFileCounts();
				break;
			case 44:
				getSharePointSiteUsageSiteCounts();
				break;
			case 45:
				getSharePointSiteUsageStorage();
				break;
			case 46:
				getSharePointSiteUsagePages();
				break;
			case 47:
				getYammerActivityUserDetail();
				break;
			case 48:
				getYammerActivityCounts();
				break;
			case 49:
				getYammerActivityUserCounts();
				break;
			case 50:
				getYammerGroupsActivityDetail();
				break;
			case 51:
				getYammerGroupsActivityGroupCounts();
				break;
			case 52:
				getYammerGroupsActivityCounts();
				break;
			case 53:
				org();
				break;
			default:
				LOGGER.warn("Invalid choice");
			}
		}

		input.close();
	}

	private static void initializeGraph(Properties properties) {
		try {
			Graph.initializeGraphForUserAuth(properties, challenge -> LOGGER.info(challenge.getMessage()));
		} catch (Exception e) {
			LOGGER.error("Error initializing Graph for user auth");
			LOGGER.error(e.getMessage());
		}
	}

	private static void greetUser() {
		try {
			final User user = Graph.getUser();
			final String email = user.mail == null ? user.userPrincipalName : user.mail;
			LOGGER.info("Hello, " + user.displayName + "!");
			LOGGER.info("Email: " + email);
		} catch (Exception e) {
			LOGGER.error("Error getting user");
			LOGGER.error(e.getMessage());
		}
	}

	public static String displayAccessToken() {
		try {
			final String accessToken = Graph.getAppOnlyToken();
			LOGGER.info("Access token: " + accessToken);
			return accessToken;
		} catch (Exception e) {
			LOGGER.error("Error getting access token");
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	private static void listInbox() {
		try {
			final MessageCollectionPage messages = Graph.getInbox();

			// Output each message's details
			for (Message message : messages.getCurrentPage()) {
				LOGGER.info("Message: " + message.subject);
				LOGGER.info("  From: " + message.from.emailAddress.name);
				LOGGER.info("  Status: " + (message.isRead ? "Read" : "Unread"));
				LOGGER.info("  Received: " + message.receivedDateTime
						// Values are returned in UTC, convert to local time zone
						.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
						.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
				LOGGER.debug("  -------------***----------- *** ----------------: ");
			}

			final Boolean moreMessagesAvailable = messages.getNextPage() != null;
			LOGGER.info("\nMore messages available? " + moreMessagesAvailable);
		} catch (Exception e) {
			LOGGER.error("Error getting inbox");
			LOGGER.error(e.getMessage());
		}
	}

	private static void sendMail() {
		try {
			final User user = Graph.getUser();
			final String email = user.mail == null ? user.userPrincipalName : user.mail;

			Graph.sendMail("Testing Microsoft Graph API Auto generated Mail", "Hello world!", email);
			LOGGER.info("\nMail sent.");
		} catch (Exception e) {
			LOGGER.error("Error sending mail");
			LOGGER.error(e.getMessage());
		}
	}

	private static void listUsers() {
		try {
			final UserCollectionPage users = Graph.getUsers();
//			Product_User product_User = new Product_User();
//			List<Product_User> u = new ArrayList<>();
			for (User user : users.getCurrentPage()) {
				LOGGER.info(user.businessPhones);
				LOGGER.info(user.displayName);
				LOGGER.info(user.givenName);
				LOGGER.info(user.jobTitle);
				LOGGER.info(user.mail);
				LOGGER.info(user.mobilePhone);
				LOGGER.info(user.officeLocation);
				LOGGER.info(user.preferredLanguage);
				LOGGER.info(user.surname);
				LOGGER.info(user.userPrincipalName);
				LOGGER.info(user.id);
			}

			final Boolean moreUsersAvailable = users.getNextPage() != null;
			LOGGER.info("\nMore users available? " + moreUsersAvailable);
//			LOGGER.info(u.size());
		} catch (Exception e) {
			LOGGER.error("Error getting users");
			LOGGER.error(e.getMessage());
			e.printStackTrace();

		}
	}

	private static void getUserByID() {
		try {
			String id = "a536d76b-ecdf-4bc4-96b8-268bd168572c";
			final User user = Graph.getUserByID(id);
			final String email = user.mail == null ? user.userPrincipalName : user.mail;
			LOGGER.info("Hello, " + user.displayName + "!");
			LOGGER.info("Job Title: " + user.jobTitle);
			LOGGER.info("Email: " + email);
			LOGGER.info("About Me: " + user.aboutMe);
			LOGGER.debug("  -------------***----------- *** ----------------: ");
			
			
			
		} catch (Exception e) {
			LOGGER.error("Error in fetching user\n" + e.getMessage());
		}
	}

	private static void getDeltaUsers() {
		try {
			UserDeltaCollectionPage deltaUser = Graph.getDeltaUsers();
			for (User user : deltaUser.getCurrentPage()) {
				LOGGER.info("user Id: " + user.id);
				LOGGER.info("Display Name: " + user.displayName);
				LOGGER.info("Job Title: " + user.jobTitle);
				LOGGER.info("Mobile Phone: " + user.mobilePhone);
				LOGGER.info("  Email: " + user.mail);
				LOGGER.debug("  -------------***----------- *** ----------------: ");
			}
		} catch (Exception e) {
			LOGGER.error("Error in fetching list of delta users\n" + e.getMessage());
		}
	}

	private static void listLicenseDetails() {
		try {
			LicenseDetailsCollectionPage licenses = Graph.listLicenseDetails();
			for (LicenseDetails license : licenses.getCurrentPage()) {
				LOGGER.info("ID : " + license.id);
				LOGGER.info("skuID : " + license.skuId);
				LOGGER.info("Sku Part Number : " + license.skuPartNumber);
				LOGGER.info("OData Type : " + license.oDataType);
				LOGGER.debug("  -------------***----------- *** ----------------: ");
				for (ServicePlanInfo plan : license.servicePlans) {
					LOGGER.info("  appliesTo: " + plan.appliesTo);
					LOGGER.info("  oDataType: " + plan.oDataType);
					LOGGER.info("  provisioningStatus: " + plan.provisioningStatus);
					LOGGER.info("  servicePlanId: " + plan.servicePlanId);
					LOGGER.info("  servicePlanName: " + plan.servicePlanName);
					if (!plan.additionalDataManager().isEmpty())
						LOGGER.info("  additionalDataManager: " + plan.additionalDataManager().values());
					LOGGER.debug("  -------------***----------- *** ----------------: ");

				}
			}
			LOGGER.debug("  -------------***----------- *** ----------------: ");

		} catch (Exception e) {
			LOGGER.error("Error in getting list of license details\n" + e.getMessage());
		}
	}

	private static void getSubscribedSkus() {
		System.out.println("----------------------------------------------------------------------");
		try {
			System.out.println("Before Print SKU part Number");
			SubscribedSkuCollectionPage data = Graph.getSubscribedSkus();
			LOGGER.info("SubscribedSkuCollectionRequestBuilder: " + data);

			for (SubscribedSku sku : data.getCurrentPage()) {
				System.out.println("SKU part No : " + sku.skuPartNumber);
				LOGGER.info("  ID: " + sku.id);

				LOGGER.info("  capabilityStatus: " + sku.capabilityStatus);
				LOGGER.info("  appliesTo: " + sku.appliesTo);
				LOGGER.info("  PartNumber: " + sku.skuPartNumber);
				LOGGER.info("  consumedUnits: " + sku.consumedUnits);
				LOGGER.info("  oDataType: " + sku.oDataType);
				LOGGER.info("  prepaidUnits: " + sku.prepaidUnits);
				LOGGER.info("  SKU Part No : " + sku.skuPartNumber);
				System.out.println("after Print SKU part Number");

				LicenseUnitsDetail prepaidUnits = sku.prepaidUnits;

				LOGGER.info("  enabled: " + prepaidUnits.enabled);
				LOGGER.info("  suspended: " + prepaidUnits.suspended);
				LOGGER.info("  warning: " + prepaidUnits.warning);

				for (ServicePlanInfo plan : sku.servicePlans) {
					LOGGER.info("  appliesTo: " + plan.appliesTo);
					LOGGER.info("  oDataType: " + plan.oDataType);
					LOGGER.info("  provisioningStatus: " + plan.provisioningStatus);
					LOGGER.info("  servicePlanId: " + plan.servicePlanId);
					LOGGER.info("  servicePlanName: " + plan.servicePlanName);

					if (!plan.additionalDataManager().isEmpty())
						LOGGER.info("  additionalDataManager: " + plan.additionalDataManager().values());
					LOGGER.debug("  -------------***----------- *** ----------------: ");

				}

				LOGGER.debug("  -------------***----------- *** ----------------: ");

			}

		} catch (Exception e) {
			LOGGER.error("exception" + e.getStackTrace());
			LOGGER.error("Error while get Subscribed Skus");
			LOGGER.error(e.getMessage());
		}
	}

	public static void getSubscribedSku() {
		try {
			String id = "00864b60-6615-48a0-b1da-667795698405_18181a46-0d4e-45cd-891e-60aabd171b4e";
			SubscribedSku sku = Graph.getSubscribedSku(id);
			LOGGER.info("ID: " + sku.id);
			LOGGER.info("sku ID: " + sku.skuId);

			LOGGER.info("sku applied To: " + sku.appliesTo);
			LOGGER.info("sku capability: " + sku.capabilityStatus);
			LOGGER.info("sku Part No: " + sku.skuPartNumber);
			LOGGER.info("sku Prepaid Units: " + sku.prepaidUnits);
			LOGGER.info("sku Prepaid Units Details :");
			LOGGER.info("\tIsEnabled" + sku.prepaidUnits.enabled);
			LOGGER.info("\tIsSuspended" + sku.prepaidUnits.suspended);
			LOGGER.info("\tIsWarning" + sku.prepaidUnits.warning);
			LOGGER.info("sku Service Plans Details: ");
			for (ServicePlanInfo plan : sku.servicePlans) {

				LOGGER.info("\tappliesTo: " + plan.appliesTo);
				LOGGER.info("\toDataType: " + plan.oDataType);
				LOGGER.info("\tprovisioningStatus: " + plan.provisioningStatus);
				LOGGER.info("\tservicePlanId: " + plan.servicePlanId);
				LOGGER.info("\tservicePlanName: " + plan.servicePlanName);
				if (!plan.additionalDataManager().isEmpty())
					LOGGER.info("  additionalDataManager: " + plan.additionalDataManager().values());
				LOGGER.debug("  -------------***----------- *** ----------------: ");

			}
			LOGGER.debug("  -------------***----------- *** ----------------: ");
		} catch (Exception e) {
			LOGGER.error("Error while getting subscribed sku\n" + e.getMessage());
		}
	}

	public static void getOffice365ActivationsUserDetail() {
		try {
			JSONArray in = Graph.getOffice365ActivationsUserDetail();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOffice365ActivationsUserCounts() {
		try {
			JSONArray in = Graph.getOffice365ActivationsUserCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOffice365ActiveUserDetail() {
		try {
			JSONArray in = Graph.getOffice365ActiveUserDetail();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOffice365ActiveUserCounts() {
		try {
			JSONArray in = Graph.getOffice365ActiveUserCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOffice365ServicesUserCounts() {
		try {
			JSONArray in = Graph.getOffice365ServicesUserCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getM365AppUserDetail() {
		try {
			JSONArray in = Graph.getM365AppUserDetail();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getM365AppUserCounts() {
		try {
			JSONArray in = Graph.getM365AppUserCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void listSubscriptions() {
		try {
			SubscriptionCollectionPage in = Graph.listSubscriptions();
			for (Subscription sub : in.getCurrentPage()) {
				LOGGER.info("  ID: " + sub.id);
				LOGGER.info("  applicationId: " + sub.applicationId);
				LOGGER.info("  applicationId: " + sub.changeType);
				LOGGER.info("  applicationId: " + sub.clientState);
				LOGGER.info("  applicationId: " + sub.creatorId);
				LOGGER.info("  applicationId: " + sub.encryptionCertificate);
				LOGGER.info("  applicationId: " + sub.encryptionCertificateId);
				LOGGER.info("  latestSupportedTlsVersion: " + sub.latestSupportedTlsVersion);
				LOGGER.info("  latestSupportedTlsVersion: " + sub.latestSupportedTlsVersion);
				LOGGER.info("  lifecycleNotificationUrl: " + sub.lifecycleNotificationUrl);
				LOGGER.info("  notificationUrl: " + sub.notificationUrl);
				LOGGER.info("  notificationUrl: " + sub.notificationUrlAppId);
				LOGGER.info("  resource: " + sub.resource);
				LOGGER.info("  expirationDateTime: " + sub.expirationDateTime);
				LOGGER.info("  includeResourceData: " + sub.includeResourceData);
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getSubscriptions() {
		try {
			String id = "";
			Subscription sub = Graph.getSubscriptions(id);
			LOGGER.info("  ID: " + sub.id);
			LOGGER.info("  applicationId: " + sub.applicationId);
			LOGGER.info("  applicationId: " + sub.changeType);
			LOGGER.info("  applicationId: " + sub.clientState);
			LOGGER.info("  applicationId: " + sub.creatorId);
			LOGGER.info("  applicationId: " + sub.encryptionCertificate);
			LOGGER.info("  applicationId: " + sub.encryptionCertificateId);
			LOGGER.info("  latestSupportedTlsVersion: " + sub.latestSupportedTlsVersion);
			LOGGER.info("  latestSupportedTlsVersion: " + sub.latestSupportedTlsVersion);
			LOGGER.info("  lifecycleNotificationUrl: " + sub.lifecycleNotificationUrl);
			LOGGER.info("  notificationUrl: " + sub.notificationUrl);
			LOGGER.info("  notificationUrl: " + sub.notificationUrlAppId);
			LOGGER.info("  resource: " + sub.resource);
			LOGGER.info("  expirationDateTime: " + sub.expirationDateTime);
			LOGGER.info("  includeResourceData: " + sub.includeResourceData);
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getCallRecord() {
		String id = "";
		try {
			CallRecord callRecord = Graph.getCallRecord(id);
			LOGGER.info(" id: " + callRecord.id);
			LOGGER.info(" joinWebUrl: " + callRecord.joinWebUrl);
			LOGGER.info(" version: " + callRecord.version);
			LOGGER.info(" endDateTime: " + callRecord.endDateTime);
			LOGGER.info(" lastModifiedDateTime: " + callRecord.lastModifiedDateTime);
			LOGGER.info(" organizer: " + callRecord.organizer);
			LOGGER.info(" participants: " + callRecord.participants);
			LOGGER.info(" sessions: " + callRecord.sessions);
			LOGGER.info(" startDateTime: " + callRecord.startDateTime);
			LOGGER.info(" type: " + callRecord.type);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void getTeamsUserActivityUserDetail() {
		try {
			JSONArray in = Graph.getTeamsUserActivityUserDetail();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getTeamsUserActivityCounts() {
		try {
			JSONArray in = Graph.getTeamsUserActivityCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getTeamsUserActivityUserCounts() {
		try {
			JSONArray in = Graph.getTeamsUserActivityUserCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getEmailActivityUserDetail() {
		try {
			JSONArray in = Graph.getEmailActivityUserDetail();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getEmailActivityCounts() {
		try {
			JSONArray in = Graph.getEmailActivityCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getEmailActivityUserCounts() {
		try {
			JSONArray in = Graph.getEmailActivityUserCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOffice365GroupsActivityDetail() {
		try {
			JSONArray in = Graph.getOffice365GroupsActivityDetail();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOffice365GroupsActivityCounts() {
		try {
			JSONArray in = Graph.getOffice365GroupsActivityCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOffice365GroupsActivityGroupCounts() {
		try {
			JSONArray in = Graph.getOffice365GroupsActivityGroupCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOffice365GroupsActivityStorage() {
		try {
			JSONArray in = Graph.getOffice365GroupsActivityStorage();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void getOffice365GroupsActivityFileCounts() {
		try {
			JSONArray in = Graph.getOffice365GroupsActivityFileCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOneDriveActivityUserDetail() {
		try {
			JSONArray in = Graph.getOneDriveActivityUserDetail();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOneDriveActivityUserCounts() {
		try {
			JSONArray in = Graph.getOneDriveActivityUserCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOneDriveActivityFileCounts() {
		try {
			JSONArray in = Graph.getOneDriveActivityFileCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOneDriveUsageAccountDetail() {
		try {
			JSONArray in = Graph.getOneDriveUsageAccountDetail();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOneDriveUsageAccountCounts() {
		try {
			JSONArray in = Graph.getOneDriveUsageAccountCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOneDriveUsageFileCounts() {
		try {
			JSONArray in = Graph.getOneDriveUsageFileCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getOneDriveUsageStorage() {
		try {
			JSONArray in = Graph.getOneDriveUsageStorage();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getSharePointActivityUserDetail() {
		try {
			JSONArray in = Graph.getSharePointActivityUserDetail();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getSharePointActivityFileCounts() {
		try {
			JSONArray in = Graph.getSharePointActivityFileCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getSharePointActivityUserCounts() {
		try {
			JSONArray in = Graph.getSharePointActivityUserCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getSharePointActivityPages() {
		try {
			JSONArray in = Graph.getSharePointActivityPages();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	public static void getSharePointSiteUsageDetail() {
		try {
			JSONArray in = Graph.getSharePointSiteUsageDetail();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getSharePointSiteUsageFileCounts() {
		try {
			JSONArray in = Graph.getSharePointSiteUsageFileCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getSharePointSiteUsageSiteCounts() {
		try {
			JSONArray in = Graph.getSharePointSiteUsageSiteCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getSharePointSiteUsageStorage() {
		try {
			JSONArray in = Graph.getSharePointSiteUsageStorage();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getSharePointSiteUsagePages() {
		try {
			JSONArray in = Graph.getSharePointSiteUsagePages();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getYammerActivityUserDetail() {
		try {
			JSONArray in = Graph.getYammerActivityUserDetail();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getYammerActivityCounts() {
		try {
			JSONArray in = Graph.getYammerActivityCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void getYammerActivityUserCounts() {
		try {
			JSONArray in = Graph.getYammerActivityUserCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}
	
	public static void getYammerGroupsActivityDetail() {
		try {
			JSONArray in = Graph.getYammerGroupsActivityDetail();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}
	
	public static void getYammerGroupsActivityGroupCounts() {
		try {
			JSONArray in = Graph.getYammerGroupsActivityGroupCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}
	
	public static void getYammerGroupsActivityCounts() {
		try {
			JSONArray in = Graph.getYammerGroupsActivityCounts();
			for (Object object : in) {
				LOGGER.info(object.toString());
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	public static void org() {
		try {
			OrganizationCollectionPage in = Graph.org();
			System.out.println(in);
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

}