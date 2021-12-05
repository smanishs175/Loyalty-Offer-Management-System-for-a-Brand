import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
    static String       user    = "pkanaki";
    static String       passwd  = "200369201";

    public static int executeQuery ( String query ) throws ClassNotFoundException, SQLException {

        System.out.println( query );
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int flag = 0;
        try {

            Class.forName( "oracle.jdbc.OracleDriver" );

            conn = DriverManager.getConnection( jdbcURL, user, passwd );
            stmt = conn.createStatement();

            rs = stmt.executeQuery( query );
            final ResultSetMetaData meta = rs.getMetaData();
            final int columnCount = meta.getColumnCount();

            for ( int i = 1; i <= columnCount; i++ ) {
                System.out.print( meta.getColumnName( i ) + " " );
            }
            System.out.println();

            while ( rs.next() ) {
                flag = 1;
                for ( int c = 1; c <= columnCount; c++ ) {
                    final Object val = rs.getObject( c );
                    if ( val != null ) {
                        System.out.print( val.toString() + " " );
                    }
                }
                System.out.println();
            }

            if ( flag == 0 ) {
                System.out.println( " No Rows found that satisfies your situation." );
            }

        }
        catch ( final java.sql.SQLException e ) {
            System.out.println( "**************ERROR MESSAGE START**************" );
            System.out.println( "ErrorCode = " + e.getErrorCode() + " Message =  " + e.getMessage() );
            System.out.println( "**************ERROR MESSAGE END **************" );
            // e.printStackTrace();
        }
        finally {
            stmt.close();
            conn.close();

            if ( rs != null ) {
                rs.close();
            }
        }
        if ( flag == 0 ) {
            return -1;
        }
        return 0;
    }

    public static ArrayList<ArrayList<String>> executeAndReturnQuery ( String query ) {
        System.out.println( query );

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        final ArrayList<ArrayList<String>> alist = new ArrayList<ArrayList<String>>();
        try {

            try {
                Class.forName( "oracle.jdbc.OracleDriver" );

                conn = DriverManager.getConnection( jdbcURL, user, passwd );
                stmt = conn.createStatement();

                rs = stmt.executeQuery( query );
                final ResultSetMetaData meta = rs.getMetaData();
                final int columnCount = meta.getColumnCount();

                while ( rs.next() ) {
                    final ArrayList<String> Temp = new ArrayList<String>();
                    for ( int c = 1; c <= columnCount; c++ ) {
                        final Object val = rs.getObject( c );
                        if ( val != null ) {
                            // System.out.print(val.toString() + " ");
                            Temp.add( val.toString() );
                        }
                        else {
                            Temp.add( "NULL" );
                        }
                    }
                    alist.add( Temp );
                    // System.out.println();
                }

            }
            finally {
                stmt.close();
                conn.close();
                if ( rs != null ) {
                    rs.close();
                }
            }

        }
        catch ( final Throwable oops ) {
            oops.printStackTrace();
        }

        return alist;
    }

    public static int executeUpdate ( String query ) throws SQLException, ClassNotFoundException {

        System.out.println( query );

        Connection conn = null;
        Statement stmt = null;
        int num = 0;
        try {
            Class.forName( "oracle.jdbc.OracleDriver" );

            conn = DriverManager.getConnection( jdbcURL, user, passwd );
            stmt = conn.createStatement();

            num = stmt.executeUpdate( query );
            System.out.println( "Success" );

        }
        catch ( final java.sql.SQLException e ) {
            System.out.println( "**************ERROR**************" );
            System.out.println( "ErrorCode = " + e.getErrorCode() + " Message =  " + e.getMessage() );
            System.out.println( "**************ERROR**************" );
            // e.printStackTrace();
        }
        finally {
            stmt.close();
            conn.close();
        }

        return num;

    }

    public static String validate ( String query ) throws SQLException, ClassNotFoundException {

        System.out.println( query );

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String loginStatus = "0";
        try {
            Class.forName( "oracle.jdbc.OracleDriver" );
            conn = DriverManager.getConnection( jdbcURL, user, passwd );
            stmt = conn.createStatement();

            rs = stmt.executeQuery( query );

            while ( rs.next() ) {
                loginStatus = rs.getString( "STATUS" );
            }

            System.out.println( "SUCCESS" );
        }
        catch ( final java.sql.SQLException e ) {
            System.out.println( "**************ERROR MESSAGE START**************" );
            System.out.println( "ErrorCode = " + e.getErrorCode() + " Message =  " + e.getMessage() );
            System.out.println( "**************ERROR MESSAGE END **************" );
            // e.printStackTrace();
        }
        finally {
            stmt.close();
            conn.close();
            if ( rs != null ) {
                rs.close();
            }
        }

        return loginStatus;
    }
}
