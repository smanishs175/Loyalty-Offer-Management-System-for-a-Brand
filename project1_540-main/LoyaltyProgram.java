import java.sql.SQLException;
import java.util.Scanner;

public class LoyaltyProgram {

    static Scanner sc = new Scanner( System.in );

    public static void main ( String[] args ) throws ClassNotFoundException, SQLException {
        home();
    }

    public static void home () throws ClassNotFoundException, SQLException {

        final boolean cont = true;
        while ( cont ) {
            System.out.println( "Welcome to the Loyalty Program!" );
            System.out.println( "1) Login\n2) Sign Up\n3) showQueries\n4) Exit" );

            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;

            if ( choice == -1 ) {
                System.out.println( "Please Enter valid Input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    login();
                    break;
                case 2:
                    signUp();
                    break;
                case 3:
                    showQueries();
                    break;
                case 4:
                    System.exit( 0 );
                default:
                    System.out.println( "Please enter valid Input" );
                    break;
            }

        }
    }

    public static boolean isInteger ( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch ( final Exception e ) {
            return false;
        }
    }

    public static boolean isLong ( String input ) {
        try {
            Long.parseLong( input );
            return true;
        }
        catch ( final Exception e ) {
            return false;
        }
    }

    static void login () throws ClassNotFoundException, SQLException {

        while ( true ) {

            System.out.println( "USERID: " );
            final String USERID = sc.nextLine();

            System.out.println( "Password: " );
            final String PASSWORD = sc.nextLine();

            System.out.println( "Login\n1) Sign-in\n2) Go Back" );
            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;

            if ( choice == -1 ) {
                System.out.println( "Please Enter valid Input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    final String buildQuery = "SELECT pkanaki.validateLogin('%s','%s') as STATUS FROM dual";
                    final String query = String.format( buildQuery, USERID, PASSWORD );
                    final String status = Database.validate( query );
                    if ( status.equals( "1" ) ) {
                        CustomerUtils.customerLanding( USERID );
                    }
                    else if ( status.equals( "2" ) ) {
                        BrandUtils.brandLanding( USERID );
                    }
                    else if ( status.equals( "3" ) ) {
                        AdminUtils.adminLanding();
                    }
                    else {
                        System.out.println( "Invalid credentials" );
                    }
                    break;
                case 2:
                    return;
                default:
                    System.out.println( "Invalid choice" );
                    return;
            }
        }

    }

    static void signUp () throws ClassNotFoundException, SQLException {

        while ( true ) {
            System.out.println( "User Type\n1) Brand Sign-up\n2) Customer Sign-up\n3) Go Back" );
            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Please Enter valid Input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    brandSignUp();
                    return;
                case 2:
                    customerSignUp();
                    return;
                case 3:
                    return;
                default:
                    System.out.println( "Invalid choice" );
                    break;
            }

        }

    }

    public static void brandSignUp () throws ClassNotFoundException, SQLException {

        while ( true ) {
            System.out.println( "Enter the brandid: " );
            final String BRANDID = sc.nextLine();

            System.out.println( "Enter the password: " );
            final String password = sc.nextLine();

            System.out.println( "Enter the brand's name: " );
            final String name = sc.nextLine();

            System.out.println( "Enter the address: " );
            final String address = sc.nextLine();

            System.out.println(
                    "You have entered  Password = " + password + " Name = " + name + " Address = " + address );

            System.out.println( "Press 1 to confirm\n1) Sign-up\n2) Go Back" );
            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Please Enter valid Input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    final String builderQuery = "INSERT INTO PKANAKI.BRAND (BRANDID, PASSWORD, BRANDNAME, JOINDATE, ADDRESS) VALUES('%s','%s', '%s', SYSDATE , '%s')";
                    final String query = String.format( builderQuery, BRANDID, password, name, address );
                    Database.executeUpdate( query );
                    return;
                case 2:
                    return;
                default:
                    System.out.println( "Invalid choice" );
                    break;
            }
        }

    }

    public static void customerSignUp () throws ClassNotFoundException, SQLException {
        while ( true ) {

            System.out.println( "Enter the customerid: " );
            final String CUSTOMERID = sc.nextLine();

            System.out.println( "Enter the customer's password: " );
            final String password = sc.nextLine();

            System.out.println( "Enter the Customer Name: " );
            final String name = sc.nextLine();

            System.out.println( "Enter your phone number: " );
            String handleInput = sc.nextLine();
            final long phoneNumber = ( isLong( handleInput ) ) ? Long.parseLong( handleInput ) : -1L;
            if ( phoneNumber == -1L ) {
                System.out.println( "Please Enter valid Input" );
                continue;
            }

            System.out.println( "Enter your address: " );
            final String address = sc.nextLine();

            System.out.println( "You have entered  " + password + " " + name + " " + phoneNumber + " " + address );

            System.out.println( "Press 1 to confirm\n1) Sign-up\n2) Go Back" );
            handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Please Enter valid Input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    final String buildQuery = "INSERT INTO PKANAKI.CUSTOMER(CUSTOMERID, PASSWORD, CUSTOMERNAME, PHONE, ADDRESS) VALUES('%s', '%s', '%s', %d, '%s')";
                    final String query = String.format( buildQuery, CUSTOMERID, password, name, phoneNumber, address );
                    final int result = Database.executeUpdate( query );
                    return;
                case 2:
                    return;
                default:
                    System.out.println( "Invalid choice" );
                    break;
            }
        }

    }

    static void showQueries () throws ClassNotFoundException, SQLException {

        while ( true ) {
            System.out.println( "Select Option\n1) List all customers that are not apart of Brand02's program" );
            System.out.println(
                    "2) List customers that have joined a loyalty program but have not participated in any activity in that program (list the customerid and the loyalty program id)" );
            System.out.println( "3) List the rewards that are part of Brand01 loyalty program" );
            System.out.println(
                    "4) List all the loyalty programs that include “refer a friend” as an activity in at least one of their reward rules" );
            System.out.println(
                    "5) For Brand01, list for each activity type in their loyalty program, the number instances that have occured" );
            System.out.println( "6) List customers of Brand01 that have redeemed at least twice." );
            System.out.println( "7) All brands where total number of points redeemed overall is less than 500 points" );
            System.out.println(
                    "8) For C0003, and Brand02, number of activities they have done in the period of 08/1/2021 and 9/30/2021" );
            System.out.println( "9) Go back" );

            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Please Enter valid Input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    // Answer: C002 C004
                    final String query1 = "SELECT C.CUSTOMERID FROM PKANAKI.CUSTOMER C WHERE C.CUSTOMERID NOT IN (SELECT W.CUSTOMERID FROM PKANAKI.ENROLLED W WHERE W.BRANDID = 'Brand02')";
                    Database.executeQuery( query1 );
                    break;
                case 2:
                    // Answer: No Rows satisfy
                    final String query2 = "SELECT W.CUSTOMERID,L.LOYALTYPROGRAMCODEID  FROM PKANAKI.LP L, PKANAKI.WALLET W WHERE L.BRANDID = W.BRANDID AND W.POINTSEARNED = 0";
                    Database.executeQuery( query2 );
                    break;
                case 3:
                    // Answer: Gift Card Free Product
                    final String query3 = "SELECT R.NAME FROM PKANAKI.BRAND_REWARDTYPE B, PKANAKI.REWARDCATEGORY R WHERE B.BRANDID = 'Brand01' AND B.REWARDCATEGORYCODE = R.REWARDCATEGORYCODE ";
                    Database.executeQuery( query3 );
                    break;
                case 4:
                    // Answer: MegaCenter TechSups
                    final String query4 = "SELECT L.NAME FROM PKANAKI.RERULETABLE RET, PKANAKI.ACTIVITYCATEGORY AC, PKANAKI.LP L WHERE  L.BRANDID = RET.BRANDID AND RET.ACTIVITYCATEGORYCODE = AC.ACTIVITYCATEGORYCODE AND AC.NAME = 'Refer a friend'";
                    Database.executeQuery( query4 );
                    break;
                case 5:
                    // Answer: A02 11 A01 14
                    final String query5 = "SELECT L.ACTIVITYCATEGORYCODE, COUNT(*) FROM PKANAKI.LOYALTYACTIVITY L WHERE L.BRANDID = 'Brand01' GROUP BY L.ACTIVITYCATEGORYCODE ";
                    Database.executeQuery( query5 );
                    break;
                case 6:
                    // Answer: C005
                    final String query6 = "SELECT RA.CUSTOMERID FROM PKANAKI.REWARDACTIVITY RA WHERE RA.BRANDID = 'Brand01' GROUP BY RA.CUSTOMERID HAVING COUNT(*) > 1";
                    Database.executeQuery( query6 );
                    break;
                case 7:
                    // Answer: Brand01
                    final String query7 = "SELECT DISTINCT RA.BRANDID FROM PKANAKI.REWARDACTIVITY RA GROUP BY RA.BRANDID HAVING SUM(RA.POINTSREDEEMED) < 500";
                    Database.executeQuery( query7 );
                    break;
                case 8:
                    // Answer: 4
                    final String query8 = "SELECT COUNT(*) FROM PKANAKI.LOYALTYACTIVITY L WHERE L.CUSTOMERID = 'C0003' AND L.BRANDID = 'Brand02' AND L.ACTIVITYDATE >= DATE '2021-08-01' AND L.ACTIVITYDATE <= DATE '2021-09-30'";
                    Database.executeQuery( query8 );
                    break;
                default:
                    System.out.println( "Invalid option." );
                    return;
            }

        }

    }

}
