package com.aithentic;

import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;

import com.microsoft.graph.callrecords.models.CallRecord;
import com.microsoft.graph.models.LicenseDetails;
import com.microsoft.graph.models.SubscribedSku;
import com.microsoft.graph.models.Subscription;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.LicenseDetailsCollectionPage;
import com.microsoft.graph.requests.SubscribedSkuCollectionPage;
import com.microsoft.graph.requests.SubscriptionCollectionPage;
import com.microsoft.graph.requests.UserCollectionPage;
import com.microsoft.graph.requests.UserDeltaCollectionPage;

public class AppTest {
	
	@Test
	public void initializeGraph() {
		final Properties oAuthProperties = new Properties();
		try {
			oAuthProperties.load(new FileInputStream("src/main/resources/oAuth.properties"));
		} catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Graph.initializeGraphForUserAuth(oAuthProperties, challenge -> System.out.println(challenge.getMessage()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testListUsers() throws Exception {
		initializeGraph();
		UserCollectionPage users = Graph.getUsers();
		Assert.assertNotNull(users);
		for (User user : users.getCurrentPage()) {
			Assert.assertNotNull(user);
		}
	}
	
	@Test
	public void testGetUserByID() {
		initializeGraph();
		try {
			String id = "92717991-8073-4a2b-b7af-4df1fb0a325a";
			final User user = Graph.getUserByID(id);
			Assert.assertNotNull(user);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetDeltaUsers() {
		initializeGraph();
		try {
			UserDeltaCollectionPage deltaUser = Graph.getDeltaUsers();
			Assert.assertNotNull(deltaUser);
			for (User user : deltaUser.getCurrentPage()) {
				Assert.assertNotNull(user);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testListLicenseDetails() {
		initializeGraph();
		try {	
			LicenseDetailsCollectionPage licenses = Graph.listLicenseDetails();
			Assert.assertNotNull(licenses);
			for (LicenseDetails license : licenses.getCurrentPage()) {
				Assert.assertNotNull(license);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetSubscribedSku() {
		initializeGraph();
		SubscribedSku subscribeSku;
		try {
			subscribeSku = Graph.getSubscribedSku("00864b60-6615-48a0-b1da-667795698405_18181a46-0d4e-45cd-891e-60aabd171b4e");
			Assert.assertNotNull(subscribeSku);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetSubscribedSkus() {
		initializeGraph();
		SubscribedSkuCollectionPage subscribeSkus;
		try {
			subscribeSkus = Graph.getSubscribedSkus();
			Assert.assertNotNull(subscribeSkus);
			for (SubscribedSku sku : subscribeSkus.getCurrentPage()) {
				Assert.assertNotNull(sku);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOffice365ActivationsUserDetail() {
		initializeGraph();
		try {
			JSONArray in = Graph.getOffice365ActivationsUserDetail();
			for (Object object : in) {
				Assert.assertNotNull(object);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOffice365ActivationsUserCounts() {
		initializeGraph();
		try {
			JSONArray in = Graph.getOffice365ActivationsUserCounts();
			for (Object object : in) {
				Assert.assertNotNull(object);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void getOffice365ActiveUserDetail() {
		initializeGraph();
		try {
			JSONArray in = Graph.getOffice365ActiveUserDetail();
			for (Object object : in) {
				Assert.assertNotNull(object);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void getOffice365ActiveUserCounts() {
		initializeGraph();
		try {
			JSONArray in = Graph.getOffice365ActiveUserCounts();
			for (Object object : in) {
				Assert.assertNotNull(object);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void getOffice365ServicesUserCounts() {
		initializeGraph();
		try {
			JSONArray in = Graph.getOffice365ServicesUserCounts();
			for (Object object : in) {
				Assert.assertNotNull(object);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void getM365AppUserDetail() {
		initializeGraph();
		try {
			JSONArray in = Graph.getM365AppUserDetail();
			for (Object object : in) {
				Assert.assertNotNull(object);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void getM365AppUserCounts() {
		initializeGraph();
		try {
			JSONArray in = Graph.getM365AppUserCounts();
			Assert.assertNotNull(in);
			for (Object object : in) {
				Assert.assertNotNull(object);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testListSubscriptions() {
		initializeGraph();
		try {
			SubscriptionCollectionPage listSubscriptions = Graph.listSubscriptions();
			Assert.assertNotNull(listSubscriptions);
			for (Subscription Subscription : listSubscriptions.getCurrentPage()) {
				Assert.assertNotNull(Subscription);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetSubscription() {
		initializeGraph();
		try {
			Subscription subscription = Graph.getSubscriptions("");
			Assert.assertNotNull(subscription);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetCallRecord() {
		initializeGraph();
		try {
			CallRecord call = Graph.getCallRecord("");
			Assert.assertNotNull(call);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetTeamsUserActivityUserDetail() {
		initializeGraph();
		JSONArray teamsUserActivityUserDetail;
		try {
			teamsUserActivityUserDetail = Graph.getTeamsUserActivityUserDetail();
					
			Assert.assertNotNull(teamsUserActivityUserDetail);
			for (Object object : teamsUserActivityUserDetail)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetTeamsUserActivityCounts() {
		initializeGraph();
		JSONArray teamsUserActivityCount;
		try {
			teamsUserActivityCount = Graph.getTeamsUserActivityCounts();
					
			Assert.assertNotNull(teamsUserActivityCount);
			for (Object object : teamsUserActivityCount)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetTeamsUserActivityUserCounts() {
		initializeGraph();
		JSONArray teamsUserActivityUserCounts;
		try {
			teamsUserActivityUserCounts = Graph.getTeamsUserActivityUserCounts();
					
			Assert.assertNotNull(teamsUserActivityUserCounts);
			for (Object object : teamsUserActivityUserCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetEmailActivityUserDetail() {
		initializeGraph();
		JSONArray emailActivityUserDetail;
		try {
			emailActivityUserDetail = Graph.getEmailActivityUserDetail();
					
			Assert.assertNotNull(emailActivityUserDetail);
			for (Object object : emailActivityUserDetail)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetEmailActivityCounts() {
		initializeGraph();
		JSONArray emailActivityCount;
		try {
			emailActivityCount = Graph.getEmailActivityCounts();
			Assert.assertNotNull(emailActivityCount);
			for (Object object : emailActivityCount)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetEmailActivityUserCounts() {
		initializeGraph();
		JSONArray emailActivityUserCount;
		try {
			emailActivityUserCount = Graph.getEmailActivityUserCounts();
					
			Assert.assertNotNull(emailActivityUserCount);
			for (Object object : emailActivityUserCount)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOffice365GroupsActivityDetail() {
		initializeGraph();
		JSONArray office365GroupsActivityDetail;
		try {
			office365GroupsActivityDetail = Graph.getOffice365GroupsActivityDetail();
					
			Assert.assertNotNull(office365GroupsActivityDetail);
			for (Object object : office365GroupsActivityDetail)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOffice365GroupsActivityCounts() {
		initializeGraph();
		JSONArray office365GroupsActivityCounts;
		try {
			office365GroupsActivityCounts = Graph.getOffice365GroupsActivityCounts();
					
			Assert.assertNotNull(office365GroupsActivityCounts);
			for (Object object : office365GroupsActivityCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOffice365GroupsActivityGroupCounts() {
		initializeGraph();
		JSONArray office365GroupsActivityGroupCounts;
		try {
			office365GroupsActivityGroupCounts = Graph.getOffice365GroupsActivityGroupCounts();
					
			Assert.assertNotNull(office365GroupsActivityGroupCounts);
			for (Object object : office365GroupsActivityGroupCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOffice365GroupsActivityStorage() {
		initializeGraph();
		JSONArray office365GroupsActivityStorage;
		try {
			office365GroupsActivityStorage = Graph.getOffice365GroupsActivityStorage();
			Assert.assertNotNull(office365GroupsActivityStorage);
			for (Object object : office365GroupsActivityStorage)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOffice365GroupsActivityFileCounts() {
		initializeGraph();
		JSONArray office365GroupsActivityFileCounts;
		try {
			office365GroupsActivityFileCounts = Graph.getOffice365GroupsActivityFileCounts();
					
			Assert.assertNotNull(office365GroupsActivityFileCounts);
			for (Object object : office365GroupsActivityFileCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOneDriveActivityUserDetail() {
		initializeGraph();
		JSONArray oneDriveActivityUserDetail;
		try {
			oneDriveActivityUserDetail = Graph.getOneDriveActivityUserDetail();
					
			Assert.assertNotNull(oneDriveActivityUserDetail);
			for (Object object : oneDriveActivityUserDetail)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

	@Test
	public void testGetOneDriveActivityUserCounts() {
		initializeGraph();
		JSONArray oneDriveActivityUserCounts;
		try {
			oneDriveActivityUserCounts = Graph.getOneDriveActivityUserCounts();
			Assert.assertNotNull(oneDriveActivityUserCounts);
			for (Object object : oneDriveActivityUserCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOneDriveActivityFileCounts() {
		initializeGraph();
		JSONArray oneDriveActivityFileCounts;
		try {
			oneDriveActivityFileCounts = Graph.getOneDriveActivityFileCounts();
					
			Assert.assertNotNull(oneDriveActivityFileCounts);
			for (Object object : oneDriveActivityFileCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOneDriveUsageAccountDetail() {
		initializeGraph();
		JSONArray oneDriveUsageAccountDetail;
		try {
			oneDriveUsageAccountDetail = Graph.getOneDriveUsageAccountDetail();
					
			Assert.assertNotNull(oneDriveUsageAccountDetail);
			for (Object object : oneDriveUsageAccountDetail)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOneDriveUsageAccountCounts() {
		initializeGraph();
		JSONArray oneDriveUsageAccountCounts;
		try {
			oneDriveUsageAccountCounts = Graph.getOneDriveUsageAccountCounts();
					
			Assert.assertNotNull(oneDriveUsageAccountCounts);
			for (Object object : oneDriveUsageAccountCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOneDriveUsageFileCounts() {
		initializeGraph();
		JSONArray oneDriveUsageFileCounts;
		try {
			oneDriveUsageFileCounts = Graph.getOneDriveUsageFileCounts();
					
			Assert.assertNotNull(oneDriveUsageFileCounts);
			for (Object object : oneDriveUsageFileCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetOneDriveUsageStorage() {
		initializeGraph();
		JSONArray oneDriveUsageStorage;
		try {
			oneDriveUsageStorage = Graph.getOneDriveUsageStorage();
					
			Assert.assertNotNull(oneDriveUsageStorage);
			for (Object object : oneDriveUsageStorage)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetSharePointActivityUserDetail() {
		initializeGraph();
		JSONArray sharePointActivityUserDetail;
		try {
			sharePointActivityUserDetail = Graph.getSharePointActivityUserDetail();
					
			Assert.assertNotNull(sharePointActivityUserDetail);
			for (Object object : sharePointActivityUserDetail)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetSharePointActivityFileCounts() {
		initializeGraph();
		JSONArray sharePointActivityFileCounts;
		try {
			sharePointActivityFileCounts = Graph.getSharePointActivityFileCounts();
					
			Assert.assertNotNull(sharePointActivityFileCounts);
			for (Object object : sharePointActivityFileCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testGetSharePointActivityUserCounts() {
		initializeGraph();
		JSONArray sharePointActivityUserCounts;
		try {
			sharePointActivityUserCounts = Graph.getSharePointActivityUserCounts();
					
			Assert.assertNotNull(sharePointActivityUserCounts);
			for (Object object : sharePointActivityUserCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	//sHSRE POIUNt ACTIVITY PAGES
	@Test
	public void testGetSharePointActivityPages() {
		initializeGraph();
		JSONArray sharePointActivityPages;
		try {
			sharePointActivityPages = Graph.getSharePointActivityPages();
					
			Assert.assertNotNull(sharePointActivityPages);
			for (Object object : sharePointActivityPages)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	//usage deatails
	
	@Test
	public void testGetSharePointSiteUsageDetail() {
		initializeGraph();
		JSONArray sharePointSiteUsageDetail;
		try {
			sharePointSiteUsageDetail = Graph.getSharePointSiteUsageDetail();
					
			Assert.assertNotNull(sharePointSiteUsageDetail);
			for (Object object : sharePointSiteUsageDetail)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	//file comt
	
	@Test
	public void testGetSharePointSiteUsageFileCounts() {
		initializeGraph();
		JSONArray sharePointSiteUsageFileCounts;
		try {
			sharePointSiteUsageFileCounts = Graph.getSharePointSiteUsageFileCounts();
					
			Assert.assertNotNull(sharePointSiteUsageFileCounts);
			for (Object object : sharePointSiteUsageFileCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testgetSharePointSiteUsageSiteCounts() {
		initializeGraph();
		JSONArray sharePointSiteUsageSiteCounts;
		try {
			sharePointSiteUsageSiteCounts = Graph.getSharePointSiteUsageSiteCounts();
					
			Assert.assertNotNull(sharePointSiteUsageSiteCounts);
			for (Object object : sharePointSiteUsageSiteCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testgetSharePointSiteUsageStorage() {
		initializeGraph();
		JSONArray sharePointSiteUsageStorage;
		try {
			sharePointSiteUsageStorage = Graph.getSharePointSiteUsageStorage();
					
			Assert.assertNotNull(sharePointSiteUsageStorage);
			for (Object object : sharePointSiteUsageStorage)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testgetSharePointSiteUsagePages() {
		initializeGraph();
		JSONArray sharePointSiteUsagePages;
		try {
			sharePointSiteUsagePages = Graph.getSharePointSiteUsagePages();
					
			Assert.assertNotNull(sharePointSiteUsagePages);
			for (Object object : sharePointSiteUsagePages)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testgetYammerActivityUserDetail() {
		initializeGraph();
		JSONArray yammerActivityUserDetail;
		try {
			yammerActivityUserDetail = Graph.getYammerActivityUserDetail();
					
			Assert.assertNotNull(yammerActivityUserDetail);
			for (Object object : yammerActivityUserDetail)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testgetYammerActivityCounts() {
		initializeGraph();
		JSONArray yammerActivityCounts;
		try {
			yammerActivityCounts = Graph.getYammerActivityCounts();
					
			Assert.assertNotNull(yammerActivityCounts);
			for (Object object : yammerActivityCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testgetYammerActivityUserCounts() {
		initializeGraph();
		JSONArray yammerActivityUserCounts;
		try {
			yammerActivityUserCounts = Graph.getYammerActivityUserCounts();
					
			Assert.assertNotNull(yammerActivityUserCounts);
			for (Object object : yammerActivityUserCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testgetYammerGroupsActivityDetail() {
		initializeGraph();
		JSONArray yammerGroupsActivityDetail;
		try {
			yammerGroupsActivityDetail = Graph.getYammerGroupsActivityDetail();
					
			Assert.assertNotNull(yammerGroupsActivityDetail);
			for (Object object : yammerGroupsActivityDetail)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testgetYammerGroupsActivityGroupCounts() {
		initializeGraph();
		JSONArray yammerGroupsActivityGroupCounts;
		try {
			yammerGroupsActivityGroupCounts = Graph.getYammerGroupsActivityGroupCounts();
					
			Assert.assertNotNull(yammerGroupsActivityGroupCounts);
			for (Object object : yammerGroupsActivityGroupCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testgetYammerGroupsActivityCounts() {
		initializeGraph();
		JSONArray yammerGroupsActivityCounts;
		try {
			yammerGroupsActivityCounts = Graph.getYammerGroupsActivityCounts();
					
			Assert.assertNotNull(yammerGroupsActivityCounts);
			for (Object object : yammerGroupsActivityCounts)
			{
				Assert.assertNotNull(object);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}