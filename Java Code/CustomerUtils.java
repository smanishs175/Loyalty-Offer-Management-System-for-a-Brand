import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class CustomerUtils {


	static Scanner sc = LoyaltyProgram.sc;
	static void customerLanding( String CUSTOMERID ) throws ClassNotFoundException, SQLException
	{
		while(true)
		{
			System.out.println("Select an option\n1) Enroll in Loyalty Program\n2) Reward Activities\n3) View Wallet\n4) Redeem Points\n5) Logout" );

			String handleInput = sc.nextLine();
			int choice = (isInteger(handleInput))?  Integer.parseInt(handleInput):-1;
			if(choice==-1) 
			{
				System.out.println("Invalid choice");
				continue;
			}

			switch ( choice ) 
			{
			case 1:
				enroll( CUSTOMERID );
				break;
			case 2:
				rewardActivity( CUSTOMERID );
				break;
			case 3:
				viewWallet( CUSTOMERID );
				break;
			case 4:
				redeemPoints( CUSTOMERID );
				break;
			case 5:
				return;
			default:
				System.out.println("Invalid choice");
				break;
			}
		}
	}

	private static void viewWallet(String cUSTOMERID) throws ClassNotFoundException, SQLException {

		System.out.println("Printing the wallet contents");
		String builderQuery1 = "SELECT * FROM PKANAKI.WALLET WHERE CUSTOMERID = '%s'";
		String query1 = String.format(builderQuery1, cUSTOMERID);
		Database.executeQuery(query1);

		while(true) 
		{
			System.out.println("Choose\n1) Go back");
			String handleInput = sc.nextLine();
			int choice = (isInteger(handleInput))?  Integer.parseInt(handleInput):-1;
			if(choice==-1)
			{
				System.out.println("Invalid choice");
				continue;
			}

			switch ( choice ) 
			{
			case 1:
				return;
			default:
				System.out.println("Invalid choice");
				break;
			}
		}
	}

	public static void enroll ( String CUSTOMERID ) throws ClassNotFoundException, SQLException {

		while(true)
		{
			System.out.println("Please select Loyalty Program using the names below");
			String builderQuery1 = "SELECT BRANDID, NAME FROM PKANAKI.LP WHERE STATE = 'ACTIVE' AND  BRANDID NOT IN (SELECT BRANDID FROM PKANAKI.ENROLLED WHERE customerId = '%s')";
			String query1 = String.format(builderQuery1, CUSTOMERID);

			int returnStatus = Database.executeQuery(query1);
			if(returnStatus==-1)
				return;

			System.out.println("Please input the BRANDID and it's LPNAME from the above one after the other wherein you want to enroll into");
			System.out.println("Enter BRANDID now");

			String BRANDID = sc.nextLine();

			System.out.println("Enter LPNAME now");
			String LPNAME = sc.nextLine();

			System.out.println("Choose\n1) Enroll in Loyalty Program\n2) Go back");

			String handleInput = sc.nextLine();
			int choice = (isInteger(handleInput))?  Integer.parseInt(handleInput):-1;
			if(choice==-1)
			{
				System.out.println("Invalid choice");
				continue;
			}

			switch ( choice ) 
			{
			case 1:
				String buildQuery = "INSERT INTO PKANAKI.ENROLLED(CUSTOMERID,BRANDID,NAME) VALUES ('%s','%s','%s')";
				String query = String.format(buildQuery,CUSTOMERID,BRANDID,LPNAME );
				Database.executeUpdate(query);
				break;
			case 2:
				return;
			default:
				System.out.println("Invalid choice");
				break;
			}
		}
	}

	public static void rewardActivity ( String CUSTOMERID ) throws ClassNotFoundException, SQLException 
	{
		while(true)
		{
			System.out.println("Please select Loyalty Program using the names below");

			String builderQuery1 = "SELECT BRANDID, NAME FROM PKANAKI.ENROLLED WHERE customerId = '%s'";
			String query1 = String.format(builderQuery1, CUSTOMERID);
			Database.executeQuery(query1);

			System.out.println("Please input the BRANDID, LPNAME you want to perform Activity into");
			System.out.println("Enter BRANDID now");
			String BRANDID = sc.nextLine();

			System.out.println("Enter LPNAME now");
			String LPNAME = sc.nextLine();

			String buildQuery = "SELECT T2.ACTIVITYCATEGORYCODE, T2.NAME FROM  PKANAKI.RERULETABLE T1, PKANAKI.ACTIVITYCATEGORY T2 WHERE T1.BRANDID = '%s'  AND T1.ACTIVITYCATEGORYCODE = T2.ACTIVITYCATEGORYCODE ";
			String query = String.format(buildQuery, BRANDID );
			ArrayList<ArrayList<String>> alist  = Database.executeAndReturnQuery(query);

			if(alist.size()==0)
			{
				System.out.println("No activities");
				return;
			}

			HashMap<String,String> hm = new HashMap<String,String>();
			for(int i=0;i<alist.size();i++) {
				if(alist.get(i).get(1).toLowerCase().contains("purchase"))
				{
					hm.put("purchase", alist.get(i).get(0));
					System.out.println("Choose 1) Purchase");
				}
				else if(alist.get(i).get(1).toLowerCase().contains("review"))
				{
					hm.put("review",alist.get(i).get(0));
					System.out.println("Choose 2) Leave a Review");
				}
				else if(alist.get(i).get(1).toLowerCase().contains("refer"))
				{
					hm.put("refer", alist.get(i).get(0));
					System.out.println("Choose 3) Refer a friend");
				}
			}


			String handleInput = sc.nextLine();
			int choice = (isInteger(handleInput))?  Integer.parseInt(handleInput):-1;
			if(choice==-1)
			{
				System.out.println("Invalid choice");
				continue;
			}

			switch ( choice )
			{
			case 1:
				doActivity(BRANDID,hm.get("purchase"),CUSTOMERID,"purchase");
				return;
			case 2:
				doActivity(BRANDID,hm.get("review"),CUSTOMERID,"review");
				return;
			case 3:
				doActivity(BRANDID,hm.get("refer"),CUSTOMERID,"refer");
				return;
			case 4:
				return;
			default:
				System.out.println("Invalid choice");
				break;
			}
		}
	}


	public static void doActivity(String BRANDID, String ACTIVITYCODE, String CUSTOMERID,String toPrint) throws ClassNotFoundException, SQLException
	{	
		while(true)
		{

			if(toPrint.equals("purchase"))
			{
				System.out.println(toPrint);
				System.out.println("Do you want to enter giftcard code, if yes then please enter the GIFTCARDCODE else enter NO");
				String giftcardcode = sc.nextLine();			
			}

			System.out.println("Do you want to enter sample datetime to generate the sample data, if yes then enter date in mm/dd/yyyy ");
			String sampleDate = sc.nextLine();



			System.out.println("Select an option\n1) Confirm "+toPrint+"\n2) Go back");

			String handleInput = sc.nextLine();
			int choice = (isInteger(handleInput))?  Integer.parseInt(handleInput):-1;
			if(choice==-1) 
			{
				System.out.println("Invalid choice");
				continue;
			} 

			switch ( choice ) 
			{
			case 1:
				//Need to write a trigger that calculates the tier and accordingly populates the points*multipler value.
				//String buildQuery = "INSERT INTO PKANAKI.LOYALTYACTIVITY (LOYALTYPROGRAMCODEID,CUSTOMERID,ACTIVITYCODE,EARNEDPOINTS,DATE) VALUES('%s','%s','%s',null,%s)";
				if(!sampleDate.toLowerCase().equals("no"))
				{
					Random rand = new Random();
					int random_seconds = rand.nextInt(60);
					int random_minutes = rand.nextInt(60);
					System.out.println("Please enter the number of transactions that you want to do on "+sampleDate);
					String totalTransactions  = sc.nextLine();
					int transactions  = isInteger(totalTransactions)? Integer.parseInt(totalTransactions):-1;
					if(transactions !=-1)
					{
						for(int i=0;i<transactions;i++)
						{
							String sampleTime = sampleDate + " "+i+":"+random_minutes+":"+random_seconds;
							String buildQuery0 = "CALL PKANAKI.INSERT_SAMPLE_LOYALTYACTIVITY('%s','%s','%s','%s','%s')";
							String query0 = String.format(buildQuery0,CUSTOMERID,BRANDID, ACTIVITYCODE,sampleTime,"MM/DD/YYYY HH24:MI:SS");
							Database.executeUpdate(query0);
							//System.out.println(query0);
						}
					}
					else
					{
						System.out.println("Please enter valid number of transactions");
					}
				}
				else
				{
					String buildQuery = "CALL PKANAKI.INSERT_LOYALTYACTIVITY('%s','%s','%s')";
					String query = String.format(buildQuery,CUSTOMERID,BRANDID, ACTIVITYCODE);
					Database.executeUpdate(query);
				}
				return;
			case 2:
				return;
			default:
				System.out.println("Invalid choice");
				break;
			}
		}
	}

	public static void redeemPoints ( String cUSTOMERID ) throws ClassNotFoundException, SQLException
	{

		while(true)
		{
			System.out.println("Please select Loyalty Program using the names below");
			String query0 = "SELECT BRANDID, NAME FROM PKANAKI.ENROLLED WHERE customerId = '%s'";
			String buildQuery0 = String.format(query0,cUSTOMERID );

			Database.executeQuery(buildQuery0);

			System.out.println("Please input the BRANDID, LPNAME you want to redeem Reward");
			System.out.println("Enter BRANDID now");
			String BRANDID = sc.nextLine();

			System.out.println("Enter LPNAME now");
			String LPNAME = sc.nextLine();

			String buildQuery = "SELECT T2.REWARDCATEGORYCODE, T2.NAME FROM PKANAKI.RRRULETABLE T1, PKANAKI.REWARDCATEGORY T2 WHERE  T1.REWARDCATEGORYCODE = T2.REWARDCATEGORYCODE  AND T1.BRANDID = '%s'";
			String query = String.format(buildQuery, BRANDID );
			ArrayList<ArrayList<String>> alist  = Database.executeAndReturnQuery(query);

			if(alist.size()==0)
			{
				System.out.println("No Rewards");
				return;
			}

			HashMap<String,String>  hm = new HashMap<String,String>();

			System.out.println("Enter the quantity of instances that you want to redeem");
			String handleInput = sc.nextLine();
			int INSTANCES = (isInteger(handleInput))?  Integer.parseInt(handleInput):-1;
			if(INSTANCES==-1)
			{
				System.out.println("Invalid choice");
				continue;
			}

			for(int i=0;i<alist.size();i++) {
				if(alist.get(i).get(1).toLowerCase().contains("gift"))
				{
					hm.put("gift",alist.get(i).get(0));
					System.out.println("Choose 1) Gift card");
				}
				else if(alist.get(i).get(1).toLowerCase().contains("free"))
				{
					hm.put("free",alist.get(i).get(0));
					System.out.println("Choose 2) Free Product");
				}
			}
			System.out.println("3) Go back");

			handleInput = sc.nextLine();
			int choice = (isInteger(handleInput))?  Integer.parseInt(handleInput):-1;
			if(choice==-1)
			{
				System.out.println("Invalid choice");
				continue;
			}

			switch ( choice )
			{
			case 1:
				System.out.println("Do you want to enter sample datetime to generate the sample data, if yes then enter date in mm/dd/yyyy else enter no");
				String sampleDate = sc.nextLine();
				if(!sampleDate.toLowerCase().contains("no"))
				{
					Random rand = new Random();
					int random_seconds = rand.nextInt(60);
					int random_minutes = rand.nextInt(60);

					String sampleTime = sampleDate + "00:"+random_minutes+":"+random_seconds;
					String buildQuery1 = "CALL PKANAKI.INSERT_SAMPLE_REWARDACTIVITY('%s','%s', '%s',%s,'%s','%s')";
					String query1 = String.format(buildQuery1,cUSTOMERID,BRANDID,hm.get("gift"),INSTANCES,sampleTime,"MM/DD/YYYY HH24:MI:SS");
					Database.executeUpdate(query1);
				}
				else
				{
					String buildQuery1 = "CALL PKANAKI.INSERT_REWARDACTIVITY('%s','%s', '%s',%s)";
					String query1 = String.format(buildQuery1,cUSTOMERID,BRANDID,hm.get("gift"),INSTANCES);
					Database.executeUpdate(query1);	
				}
				break;
			case 2:
				System.out.println("Do you want to enter sample datetime to generate the sample data, if yes then enter date in mm/dd/yyyy else enter no");
				String sampleDate2 = sc.nextLine();
				if(!sampleDate2.toLowerCase().contains("no"))
				{
					Random rand = new Random();
					int random_seconds = rand.nextInt(60);
					int random_minutes = rand.nextInt(60);

					String sampleTime = sampleDate2 + "00:"+random_minutes+":"+random_seconds;
					String buildQuery1 = "CALL PKANAKI.INSERT_SAMPLE_REWARDACTIVITY('%s','%s', '%s',%s,'%s','%s')";
					String query1 = String.format(buildQuery1,cUSTOMERID,BRANDID,hm.get("free"),INSTANCES,sampleTime,"MM/DD/YYYY HH24:MI:SS");
					Database.executeUpdate(query1);
				}
				else
				{
					String buildQuery2 = "CALL PKANAKI.INSERT_REWARDACTIVITY('%s','%s', '%s',%s)";
					String query2 = String.format(buildQuery2,cUSTOMERID,BRANDID,hm.get("free"),INSTANCES);
					Database.executeUpdate(query2);
				}
				break;
			case 3:
				return;
			default:
				System.out.println("Invalid choice");
				break;
			}

		}

	}

	public static boolean isInteger( String input ) {
		try {
			Integer.parseInt( input );
			return true;
		}
		catch( Exception e ) {
			return false;
		}
	}

}
