package models;

public class ContactSellerTaskFactory {
	public static ContactSellerTask getTaskOfJerry() {
		ContactSellerTask task = new ContactSellerTask("Arrela",
				"JerryTask\\", "SEO Service.txt", new String[] { "Contact_1.PNG", "Contact_2.PNG", "Contact_3.PNG",
						"Contact_4.PNG","Contact_5.PNG" },
				Amazon_Country.US);
		task.setRobotSelectSQL(
				"select email,password from contactseller_robot_account where (block_time is null or datediff(now(),block_time)>3) and send_assigned is null limit 1");
		return task;
	}

	public static ContactSellerTask getTaskOfBens() {
		ContactSellerTask task = new ContactSellerTask("Stellar","BensTask\\", "Stellar Service.txt",
				new String[] { "Contacta.JPG", "Contactb.JPG", "Contactc.JPG" }, Amazon_Country.US);
		task.setRobotSelectSQL(
				"select email,password from contactseller_robot_account where (block_time is null or datediff(now(),block_time)>3) and send_assigned is null limit 1");
		return task;
	}

	public static ContactSellerTask getTaskOfKDC() {
		ContactSellerTask task = new ContactSellerTask("KDC","KDC\\", "KDC Service.txt", null, Amazon_Country.US);
		return task;
	}

}
