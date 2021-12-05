import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class BrandUtils {

    static Scanner sc = LoyaltyProgram.sc;

    public static boolean isInteger ( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch ( final Exception e ) {
            return false;
        }
    }

    public static void brandLanding ( String BRANDID ) throws ClassNotFoundException, SQLException {

        while ( true ) {
            System.out.println(
                    "\nSelect an option\n1) Add Loyalty Program\n2) Add RE Rules\n3) Update RE Rules\n4) Add RR Rule\n5) Update RR Rule\n6) Validate Loyalty Program \n7) Logout" );

            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Invalid choice" );
                continue;
            }
            switch ( choice ) {
                case 1:
                    addLoyaltyProgram( BRANDID );
                    break;
                case 2:
                    // addRERule(BRANDID);
                    updateRERule( BRANDID );
                    break;
                case 3:
                    updateRERule( BRANDID );
                    break;
                case 4:
                    // addRRRule(BRANDID);
                    updateRRRule( BRANDID );
                    break;
                case 5:
                    updateRRRule( BRANDID );
                    break;
                case 6:
                    validateLoyaltyProgram( BRANDID );
                    break;
                case 7:
                    // logout
                    return;
                default:
                    System.out.println( "Invalid choice" );
                    break;
            }
        }

    }

    public static void addLoyaltyProgram ( String bRANDID ) throws ClassNotFoundException, SQLException {

        while ( true ) {
            System.out.println( "Choose an option from the menu\n1) Regular\n2) Tier\n3) Go back" );

            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Invalid choice" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    regular( bRANDID );
                    break;
                case 2:
                    tier( bRANDID );
                    break;
                case 3:
                    return;
                default:
                    System.out.println( "Invalid input" );
                    break;
            }
        }

    }

    private static void regular ( String bRANDID ) throws ClassNotFoundException, SQLException {

        while ( true ) {
            System.out.println(
                    "Regular\n0) Create Regular Loyalty Program\n1) Activity Types\n2) Reward Types\n3) Go back" );

            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            switch ( choice ) {
                case 0:
                    createRegularLoyaltyProgram( bRANDID );
                    break;
                case 1:
                    activityTypes( bRANDID );
                    break;
                case 2:
                    rewardTypes( bRANDID );
                    break;
                case 3:
                    return;
                default:
                    System.out.println( "Invalid input" );
                    break;
            }
        }
    }

    private static void createRegularLoyaltyProgram ( String BRANDID ) throws ClassNotFoundException, SQLException {
        while ( true ) {
            System.out.println( "Please enter LoyaltyProgramName" );
            final String LPNAME = sc.nextLine();

            System.out.println( "Please enter a 5 digit Loyalyty CODE (A code that you may want to remember)" );
            final String LOYALTYPROGRAMCODEID = sc.nextLine();

            System.out.println( "Confirm Create\n1) Yes\n2) Go back" );
            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    final String buildQuery = "INSERT INTO PKANAKI.LP (LOYALTYPROGRAMCODEID, NAME, STATE, BRANDID,TIER) VALUES('%s', '%s','INACTIVE' ,'%s',0)";
                    final String query = String.format( buildQuery, LOYALTYPROGRAMCODEID, LPNAME, BRANDID );
                    Database.executeUpdate( query );
                    return;
                case 2:
                    return;
                default:
                    System.out.println( "Invalid input" );
                    break;
            }
        }
    }

    public static void activityTypes ( String bRANDID ) throws ClassNotFoundException, SQLException {
        while ( true ) {
            System.out.println( "Activity Types\n1) Purchase\n2) Leave a review\n3) Refer a friend\n4) Go back" );
            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    // TODO DOUBLE CHECK THIS
                    // System.out.println("Please enter RERULECODE");
                    // String RERULECODE1 = sc.nextLine();
                    final String buildQuery1 = "INSERT INTO PKANAKI.BRAND_ACTIVITYTYPE (BRANDID, ACTIVITYCATEGORYCODE) SELECT '%s',ACTIVITYCATEGORYCODE from PKANAKI.ActivityCategory where UPPER(Name) like '%s'";
                    final String query1 = String.format( buildQuery1, bRANDID, "%PURCHASE%" );
                    Database.executeUpdate( query1 );
                    break;
                case 2:
                    // System.out.println("Please enter RERULECODE");
                    // String RERULECODE2 = sc.nextLine();
                    final String buildQuery2 = "INSERT INTO PKANAKI.BRAND_ACTIVITYTYPE (BRANDID, ACTIVITYCATEGORYCODE) SELECT '%s',ACTIVITYCATEGORYCODE from PKANAKI.ActivityCategory where UPPER(Name) like '%s'";
                    final String query2 = String.format( buildQuery2, bRANDID, "%REVIEW%" );
                    Database.executeUpdate( query2 );
                    break;
                case 3:
                    // System.out.println("Please enter RERULECODE");
                    // String RERULECODE3 = sc.nextLine();
                    final String buildQuery3 = "INSERT INTO PKANAKI.BRAND_ACTIVITYTYPE (BRANDID, ACTIVITYCATEGORYCODE) SELECT '%s',ACTIVITYCATEGORYCODE from PKANAKI.ActivityCategory where UPPER(Name) like '%s'";
                    final String query3 = String.format( buildQuery3, bRANDID, "%REFER%" );
                    Database.executeUpdate( query3 );
                    break;
                case 4:
                    return;
                default:
                    System.out.println( "Invalid input" );
                    break;
            }
        }
    }

    public static void rewardTypes ( String bRANDID ) throws ClassNotFoundException, SQLException {
        while ( true ) {
            System.out.println( "Enter the quantity/no of instances to generate" );
            String handleInput = sc.nextLine();
            final int instances = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( instances == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            System.out.println( "Activity Types\n1) Gift Card\n2) Free Product\n3) Go back" );
            handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    final String buildQuery0 = "DELETE FROM PKANAKI.BRAND_REWARDTYPE WHERE BRANDID = '%s' AND REWARDCATEGORYCODE = (SELECT RewardCategoryCode from PKANAKI.RewardCategory where UPPER(NAME) like '%s')";
                    final String query0 = String.format( buildQuery0, bRANDID, "%GIFT%" );
                    final String buildQuery1 = "INSERT INTO PKANAKI.BRAND_REWARDTYPE (BRANDID, REWARDCATEGORYCODE,INSTANCES) SELECT  '%s',RewardCategoryCode ,'%s' from PKANAKI.RewardCategory where UPPER(NAME) like '%s'  ";
                    final String query1 = String.format( buildQuery1, bRANDID, instances, "%GIFT%" );
                    System.out.println( "What's the value of each gift card(INT)?" );
                    handleInput = sc.nextLine();
                    final int giftvalue = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
                    if ( giftvalue == -1 ) {
                        System.out.println( "Invalid input" );
                        continue;
                    }
                    // System.out.println("What's the expiry date of each gift
                    // card?");
                    // String expiryDate = sc.nextLine();
                    // String buildQuery2 = "INSERT INTO
                    // PKANAKI.BRAND_REWARD_GIFTCARD (BRANDID,
                    // REWARDCODE,VALUE,EXPIRY) SELECT '%s',RewardCategoryCode
                    // ,'%s','%s' from PKANAKI.RewardCategory where UPPER(NAME)
                    // like '%s'";
                    // String query2 =
                    // String.format(buildQuery1,BRANDID,giftvalue,expiryDate,"%GIFT%");
                    Database.executeUpdate( query0 );
                    Database.executeUpdate( query1 );
                    // Database.executeUpdate(query2);
                    break;
                case 2:
                    final String buildQuery3 = "DELETE FROM PKANAKI.BRAND_REWARDTYPE WHERE BRANDID = '%s' AND REWARDCATEGORYCODE = (SELECT RewardCategoryCode from PKANAKI.RewardCategory where UPPER(NAME) like '%s')";
                    final String query3 = String.format( buildQuery3, bRANDID, "%FREE%" );
                    final String buildQuery4 = "INSERT INTO PKANAKI.BRAND_REWARDTYPE (BRANDID, REWARDCATEGORYCODE,INSTANCES) SELECT  '%s',RewardCategoryCode ,'%s' from PKANAKI.RewardCategory where UPPER(NAME) like '%s'";
                    final String query4 = String.format( buildQuery4, bRANDID, instances, "%FREE%" );
                    Database.executeUpdate( query3 );
                    Database.executeUpdate( query4 );
                    break;
                case 3:
                    return;
                default:
                    System.out.println( "Invalid input" );
                    break;
            }
        }
    }

    private static void tier ( String BRANDID ) throws ClassNotFoundException, SQLException {
        while ( true ) {
            System.out.println(
                    "SELECT \n0) CreateTieredLoyaltyProgram\n1) Tier Set up\n2) Activity Types\n3) Reward Types\n4) Go back" );
            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            switch ( choice ) {
                case 0:
                    createTieredLoyaltyProgram( BRANDID );
                    break;
                case 1:
                    tierSetUp( BRANDID );
                    break;
                case 2:
                    activityTypes( BRANDID );
                    break;
                case 3:
                    rewardTypes( BRANDID );
                    break;
                case 4:
                    return;
                default:
                    System.out.println( "Invalid Input" );
                    break;
            }
        }
    }

    private static void createTieredLoyaltyProgram ( String BRANDID ) throws ClassNotFoundException, SQLException {
        while ( true ) {
            System.out.println( "Please enter TieredLoyaltyProgramName" );
            final String LPNAME = sc.nextLine();

            System.out.println( "Please enter a 5 digit Loyalyty CODE (A code that you may want to remember)" );
            final String LOYALTYPROGRAMCODEID = sc.nextLine();

            System.out.println( "Confirm Create\n1) Yes\n2) Go back" );
            final String handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    final String buildQuery = "INSERT INTO PKANAKI.LP (LOYALTYPROGRAMCODEID, NAME, STATE, BRANDID,TIER) VALUES('%s', '%s','INACTIVE' , '%s',1)";
                    final String query = String.format( buildQuery, LOYALTYPROGRAMCODEID, LPNAME, BRANDID );
                    Database.executeUpdate( query );
                    return;
                case 2:
                    return;
                default:
                    System.out.println( "Invalid input" );
                    break;
            }
        }
    }

    private static void tierSetUp ( String bRANDID ) throws ClassNotFoundException, SQLException {
        while ( true ) {
            System.out.println( "How many tiers? (2-3 tiers)\n" );

            String handleInput = sc.nextLine();
            final int tiers = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( tiers == -1 || tiers <= 1 || tiers >= 4 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            int isInvalid = 0;
            final String[] queries = new String[tiers];
            int lastPoint = -1, lastMultiplier = -1;

            for ( int i = 0; i < tiers; i++ ) {
                System.out.println( "Enter Name of Tier " + ( i + 1 ) + " : " );
                final String TIERNAME = sc.nextLine();

                System.out.println( "Points required to enter tier:" );
                handleInput = sc.nextLine();
                final int POINTSREQUIRED = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
                if ( POINTSREQUIRED == -1 || ( i > 0 && POINTSREQUIRED < lastPoint ) ) {
                    System.out.println( "Invalid input" );
                    isInvalid = 1;
                    break;
                }

                System.out.println( "Multipler used when entering tier:" );
                handleInput = sc.nextLine();
                final int MULTIPLIER = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
                if ( MULTIPLIER == -1 || ( i > 0 && MULTIPLIER < lastMultiplier ) ) {
                    System.out.println( "Invalid input" );
                    isInvalid = 1;
                    break;
                }
                final String buildQuery = "INSERT INTO PKANAKI.TIER (BRANDID, TIERLEVEL, TIERNAME, POINTSREQUIRED, MULTIPLIER) VALUES ('%s',%s,'%s',%s,%s)";
                queries[i] = String.format( buildQuery, bRANDID, ( i + 1 ), TIERNAME, POINTSREQUIRED, MULTIPLIER );
                lastPoint = POINTSREQUIRED;
                lastMultiplier = MULTIPLIER;
            }
            if ( isInvalid == 1 ) {
                continue;
            }

            System.out.println( "Tiers Set Up\n1) Confirm Tier Set up\n2) Go back" );
            handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    for ( int i = 0; i < queries.length; i++ ) {
                        Database.executeUpdate( queries[i] );
                    }
                    return;
                case 2:
                    return;
                default:
                    System.out.println( "Invalid input" );
                    break;
            }
        }

    }

    /**
     * For a loyalty program
     *
     * @param id
     * @param j
     */
    /*
     * private static void rewardTypes(int id, int type) { Scanner sc = new
     * Scanner(System.in); //get num of rewards from DB int rewardNum = 2; //get
     * list of reward types String[] types = {"Gift Card", "Free Product"}; int
     * i = 1; for (i = 1; i <= rewardNum; i++) { System.out.println("\t" + i +
     * ") " + types[i-1]); } System.out.println("\t" + i + ") Go back"); int
     * choice = sc.nextInt(); if (choice == 1) {
     * System.out.println("How many instances would you like to add?"); int
     * number = sc.nextInt();
     * System.out.println("What's the value of each gift card?"); int value =
     * sc.nextInt();
     * System.out.println("What's the expiry date of each gift card?"); String
     * expired = sc.next(); //insert into db rewardTypes(id, type); } else if
     * (choice == i) { if (type == 0) regular(id); else tier(id); } else {
     * System.out.println("How many instances would you like to add?"); int
     * number = sc.nextInt(); String name = types[choice - 1]; //add into db
     * rewardTypes(id, type); } }
     */
    /**
     *
     * NEED TO ADD EACH ACTIVITY TYPE FOR A BRAND AND CORRESPONDING NUMBER OF
     * POINTS
     *
     * @param id
     * @param type
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    /*
     * public static void addRERule(String BRANDID) throws
     * ClassNotFoundException, SQLException { while(true) { String buildQuery0 =
     * "SELECT BRANDID FROM PKANAKI.LP WHERE BRANDID = '%s'"; String query0 =
     * String.format(buildQuery0,BRANDID); ArrayList<ArrayList<String>> list =
     * Database.executeAndReturnQuery(query0); String RERULECODE = "";
     * if(list.size()>0) { RERULECODE = list.get(0).get(0); } else { System.out.
     * println("Couldn't find a LoyaltyProgram. Have you created the LoyaltyProgram?"
     * ); return; }
     * System.out.println("What is the ACTIVITYCATEGORYCODE for the rule?");
     * String ACTIVITYCATEGORYCODE = sc.nextLine();
     * System.out.println("What is the point value for the rule?"); String
     * handleInput = sc.nextLine(); int POINTS = (isInteger(handleInput))?
     * Integer.parseInt(handleInput):-1; if(POINTS==-1) {
     * System.out.println("Invalid input"); continue; }
     * System.out.println("Add RE Rules\n1) Add RE Rule\n2) Go back");
     * handleInput = sc.nextLine(); int choice = (isInteger(handleInput))?
     * Integer.parseInt(handleInput):-1; if(choice==-1) {
     * System.out.println("Invalid input"); continue; } switch (choice) { case
     * 1: String buildQuery =
     * "INSERT INTO PKANAKI.RERULETABLE(BRANDID, ACTIVITYCATEGORYCODE,POINTS,VERSION) VALUES('%s','%s','%s',%s,1)"
     * ; String query =
     * String.format(buildQuery,BRANDID,ACTIVITYCATEGORYCODE,POINTS);
     * Database.executeUpdate(query); break; case 2: return; default:
     * System.out.println("Invalid input"); break; } } }
     */

    public static void updateRERule ( String BRANDID ) throws ClassNotFoundException, SQLException {
        while ( true ) {
            final String buildQuery0 = "SELECT BRANDID FROM PKANAKI.LP WHERE BRANDID = '%s'";
            final String query0 = String.format( buildQuery0, BRANDID );

            final ArrayList<ArrayList<String>> list = Database.executeAndReturnQuery( query0 );
            if ( list.size() == 0 ) {
                System.out.println( "Couldn't find a LoyaltyProgram. Please create the LoyaltyProgram first." );
                return;
            }

            System.out.println( "What is the ACTIVITYCATEGORYCODE for the rule?" );
            final String ACTIVITYCATEGORYCODE = sc.nextLine();

            System.out.println( "What is the  point value for the rule?" );
            String handleInput = sc.nextLine();
            final int POINTS = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( POINTS == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            System.out.println( "RE Rules\n1) Confirm RE Rule\n2) Go back" );
            handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    final String buildQuery = "INSERT INTO PKANAKI.RERULETABLE(BRANDID, ACTIVITYCATEGORYCODE,POINTS,VERSION) SELECT '%s','%s','%s',COALESCE(MAX(VERSION), 0)+1 FROM PKANAKI.RERULETABLE WHERE BRANDID = '%s' AND  ACTIVITYCATEGORYCODE = '%s'";
                    final String query = String.format( buildQuery, BRANDID, ACTIVITYCATEGORYCODE, POINTS, BRANDID,
                            ACTIVITYCATEGORYCODE );
                    Database.executeUpdate( query );
                    break;
                case 2:
                    return;
                default:
                    System.out.println( "Invalid input" );
                    break;
            }
        }
    }

    /**
     * @throws SQLException
     * @throws ClassNotFoundException
     ****************************************************************************************************************/

    /*
     * public static void addRRRule(String bRANDID) throws
     * ClassNotFoundException, SQLException { while(true) { String buildQuery0 =
     * "SELECT BRANDID FROM PKANAKI.LP WHERE BRANDID = '%s'"; String query0 =
     * String.format(buildQuery0,bRANDID); ArrayList<ArrayList<String>> list =
     * Database.executeAndReturnQuery(query0); String RRRULECODE = "";
     * if(list.size()>0) { RRRULECODE = list.get(0).get(0); } else { System.out.
     * println("Couldn't find a LoyaltyProgram. Have you created the LoyaltyProgram?"
     * ); return; }
     * System.out.println("What is the REWARDCATEGORYCODE for the rule?");
     * String REWARDCATEGORYCODE = sc.nextLine(); String buildQuery1 =
     * "SELECT INSTANCES FROM PKANAKI.BRAND_REWARDTYPE WHERE  BRANDID = '%s' and REWARDCATEGORYCODE = '%s' "
     * ; String query1 = String.format(buildQuery1,bRANDID,REWARDCATEGORYCODE);
     * ArrayList<ArrayList<String>> list2 =
     * Database.executeAndReturnQuery(query1); String INSTANCES = "";
     * if(list2.size()>0) { INSTANCES = list2.get(0).get(0); } else {
     * System.out.
     * println("Couldn't find an Instance associated with REWARDCATEGORYCODE. Please add reward type first "
     * ); return; } System.out.println("What is the point value for the rule?");
     * String handleInput = sc.nextLine(); int POINTS =
     * (isInteger(handleInput))? Integer.parseInt(handleInput):-1;
     * if(POINTS==-1) { System.out.println("Invalid input"); continue; }
     * System.out.println("Add RR Rules\n1) Add RR Rule\n2) Go back");
     * handleInput = sc.nextLine(); int choice = (isInteger(handleInput))?
     * Integer.parseInt(handleInput):-1; if(choice==-1) {
     * System.out.println("Invalid input"); continue; } switch (choice) { case
     * 1: String buildQuery =
     * "INSERT INTO PKANAKI.RRRULETABLE(BRANDID,RRRULECODE, REWARDCATEGORYCODE,POINTS,INSTANCES,VERSION) SELECT '%s','%s','%s','%s','%s',COALESCE(MAX(VERSION), 0)+1 FROM PKANAKI.RRRULETABLE WHERE BRANDID = '%s' AND RRRULECODE = '%s' AND  REWARDCATEGORYCODE = '%s'"
     * ; String query =
     * String.format(buildQuery,bRANDID,RRRULECODE,REWARDCATEGORYCODE,POINTS,
     * INSTANCES,bRANDID,RRRULECODE,REWARDCATEGORYCODE);
     * Database.executeUpdate(query); break; case 2: return; default:
     * System.out.println("Invalid input"); break; } } }
     */

    public static void updateRRRule ( String BRANDID ) throws ClassNotFoundException, SQLException {
        while ( true ) {
            final String buildQuery0 = "SELECT BRANDID FROM PKANAKI.LP WHERE BRANDID = '%s'";
            final String query0 = String.format( buildQuery0, BRANDID );

            final ArrayList<ArrayList<String>> list = Database.executeAndReturnQuery( query0 );
            if ( list.size() == 0 ) {
                System.out.println( "Couldn't find a LoyaltyProgram. Please create the LoyaltyProgram first." );
                return;
            }

            System.out.println( "What is the REWARDCATEGORYCODE for the rule?" );
            final String REWARDCATEGORYCODE = sc.nextLine();

            final String buildQuery1 = "SELECT INSTANCES FROM PKANAKI.BRAND_REWARDTYPE WHERE  BRANDID = '%s' and REWARDCATEGORYCODE = '%s' ";
            final String query1 = String.format( buildQuery1, BRANDID, REWARDCATEGORYCODE );

            final ArrayList<ArrayList<String>> list2 = Database.executeAndReturnQuery( query1 );
            String INSTANCES = "";
            if ( list2.size() > 0 ) {
                INSTANCES = list2.get( 0 ).get( 0 );
            }
            else {
                System.out.println(
                        "Couldn't find an Instance associated with REWARDCATEGORYCODE. Please add the corresponding reward type first " );
                return;
            }

            System.out.println( "What is the point value for the rule?" );
            String handleInput = sc.nextLine();
            final int POINTS = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( POINTS == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            System.out.println( "RR Rules\n1) Confirm RR Rule\n2) Go back" );
            handleInput = sc.nextLine();
            final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
            if ( choice == -1 ) {
                System.out.println( "Invalid input" );
                continue;
            }

            switch ( choice ) {
                case 1:
                    // Build a database TRIGGER after insert here that changes
                    // null to version +1
                    final String buildQuery = "INSERT INTO PKANAKI.RRRULETABLE(BRANDID, REWARDCATEGORYCODE,POINTS,INSTANCES,VERSION) SELECT '%s','%s','%s','%s',COALESCE(MAX(VERSION), 0)+1 FROM PKANAKI.RRRULETABLE WHERE BRANDID = '%s' AND  REWARDCATEGORYCODE = '%s'";
                    final String query = String.format( buildQuery, BRANDID, REWARDCATEGORYCODE, POINTS, INSTANCES,
                            BRANDID, REWARDCATEGORYCODE );
                    Database.executeUpdate( query );
                    break;
                case 2:
                    return;
                default:
                    System.out.println( "Invalid input" );
                    break;
            }
        }
    }

    /**
     * @throws SQLException
     * @throws ClassNotFoundException
     *******************************************************************************************************************/

    /*
     * Could this be done inside of database?
     */
    public static void validateLoyaltyProgram ( String BRANDID ) throws ClassNotFoundException, SQLException {
        System.out.println( "Validate Loyalty Program\n1) Validate\n2) Go back" );

        final String handleInput = sc.nextLine();
        final int choice = ( isInteger( handleInput ) ) ? Integer.parseInt( handleInput ) : -1;
        if ( choice == -1 ) {
            System.out.println( "Invalid input" );
            return;
        }

        switch ( choice ) {
            case 1:
                // Get loyalty program from database
                final String query = "SELECT pkanaki.validateLP('%s') as STATUS FROM dual";
                final String buildQuery = String.format( query, BRANDID );
                final String status = Database.validate( buildQuery );
                if ( status.equals( "1" ) ) {
                    System.out.println( "PLease add alteast one RERULE" );
                    return;
                }
                else if ( status.equals( "2" ) ) {
                    System.out.println( "PLease add alteast one RRRULE" );
                    return;
                }
                else if ( status.equals( "3" ) ) {
                    System.out.println( "PLease add atleast 2 tiers" );
                    return;
                }
                else {
                    System.out.println( "DO you want to activate :- yes/no" );
                    final String toActivate = sc.nextLine();
                    if ( toActivate.toLowerCase().contains( "y" ) ) {
                        final String query1 = "UPDATE PKANAKI.LP SET STATE='ACTIVE' WHERE BRANDID='%s'";
                        final String buildQuery1 = String.format( query1, BRANDID );

                        Database.executeUpdate( buildQuery1 );
                        System.out.println( "Activated" );
                    }
                    return;
                }
            case 2:
                return;

        }

    }

}
