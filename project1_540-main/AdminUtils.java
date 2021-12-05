import java.sql.SQLException;
import java.util.Scanner;

public class AdminUtils {

    static Scanner sc = LoyaltyProgram.sc;

    public static boolean isLong ( String input ) {
        try {
            Long.parseLong( input );
            return true;
        }
        catch ( final Exception e ) {
            return false;
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

    public static void adminLanding () throws ClassNotFoundException, SQLException {

        while ( true ) {

            System.out.println(
                    "\nSelection an option\n1) Add brand\n2) Add customer\n3) Show brand’s info\n4) Show customer’s info\n5) Add activity type\n6) Add reward type\n7) Logout" );
            ;
            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    addBrand();
                    break;
                case 2:
                    addCustomer();
                    break;
                case 3:
                    showBrandInfo();
                    break;
                case 4:
                    showCustomerInfo();
                    break;
                case 5:
                    addActivityType();
                    break;
                case 6:
                    addRewardType();
                    break;
                case 7:
                    return;
                default:
                    System.out.println( "Invalid choice" );
                    break;
            }
        }

    }

    public static void addBrand () throws ClassNotFoundException, SQLException {
        LoyaltyProgram.brandSignUp();
    }

    public static void addCustomer () throws ClassNotFoundException, SQLException {

        LoyaltyProgram.customerSignUp();
    }

    public static void showBrandInfo () throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        System.out.println( "Please insert BRAND's userid" );
        final String branduserid = sc.nextLine();

        System.out.println( "User Type\n1) showBrandInfo \n2) Go Back" );
        final int choice = Integer.parseInt( sc.nextLine() );

        switch ( choice ) {
            case 1:
                Database.executeQuery( "SELECT * FROM PKANAKI.BRAND where brandid='" + branduserid + "'" );
                return;
            default:
                break;
        }

    }

    public static void showCustomerInfo () throws ClassNotFoundException, SQLException {

        System.out.println( "Please insert Customer's userid" );
        final String customeruserid = sc.nextLine();
        System.out.println( "User Type\n1) showCustomerInfo \n2) Go Back" );
        final int choice = Integer.parseInt( sc.nextLine() );

        switch ( choice ) {
            case 1:
                Database.executeQuery( "SELECT * FROM PKANAKI.CUSTOMER where customerid='" + customeruserid + "'" );
                return;
            default:
                break;
        }
    }

    public static void addActivityType () throws ClassNotFoundException, SQLException {

        while ( true ) {
            System.out.println( "Please type new Activity's code" );
            final String ACTIVITYCATEGORYCODE = sc.nextLine();
            System.out.println( "Please type new Activity Name" );
            final String NAME = ( sc.nextLine() );

            System.out.println( "You have entered " + ACTIVITYCATEGORYCODE + " " + NAME + " \n" );

            final String buildQuery = "INSERT INTO PKANAKI.ACTIVITYCATEGORY(ACTIVITYCATEGORYCODE, NAME) VALUES('%s','%s')";
            final String query = String.format( buildQuery, ACTIVITYCATEGORYCODE, NAME );

            System.out.println( "Select 1) addActivityType 2) Go Back \n" );

            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Please Enter valid Input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    Database.executeUpdate( query );
                    break;
                case 2:
                    return;
                default:
                    break;
            }
        }
    }

    public static void addRewardType () throws ClassNotFoundException, SQLException {
        while ( true ) {
            System.out.println( "Please type new Reward Code" );
            final String REWARDCATEGORYCODE = sc.nextLine();

            System.out.println( "Please type new Reward Name" );
            final String NAME = ( sc.nextLine() );

            System.out.println( "You have entered " + REWARDCATEGORYCODE + " " + NAME + " \n" );

            final String buildQuery = "INSERT INTO PKANAKI.REWARDCATEGORY (REWARDCATEGORYCODE, NAME) VALUES('%s', '%s')";
            final String query = String.format( buildQuery, REWARDCATEGORYCODE, NAME );

            System.out.println( "Select 1) addRewardType 2) Go Back \n" );

            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Please Enter valid Input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    Database.executeUpdate( query );
                    break;
                case 2:
                    return;
                default:
                    break;
            }
        }

    }

}
