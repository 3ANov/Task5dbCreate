import org.apache.commons.text.RandomStringGenerator;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Task5dbCreate {
    public static void main(String[] args) throws SQLException{
        char [][] pairs = {{'a','z'},{'A','Z'}};
        List<String> uniqueRandomStringList = new ArrayList<String>();

        String DB_URL = "jdbc:postgresql://127.0.0.1:5432/task5db";
        String USER = "postgres";
        String PASS = "gfhjkmjb";

        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).build();
        for (int i = 0; i < 100; i++) {
            //System.out.println(generator.generate(6));
            uniqueRandomStringList.add(generator.generate(6));
        }

        HashSet hs = new HashSet();
        hs.addAll(uniqueRandomStringList);
        uniqueRandomStringList.clear();
        uniqueRandomStringList.addAll(hs);
        //HashSet hs2 = new HashSet();
        //System.out.println(hs.size());
        hs.clear();
        
        //hs.add(new Pair(1,1));
        //hs.add(new Pair(1,1));


        /*
        for (int i = 0; i < 1000; i++) {
            hs.add(new Pair(new Random().nextInt(20)+1,new Random().nextInt(20)+1995));
        }

         */
        //System.out.println(hs.size());
        List<Pair> uniqueRandomPairList = new ArrayList<Pair>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                uniqueRandomPairList.add(new Pair(j+1,1995+i));
            }

        }
        /*

        for (int i = 0; i < 20; i++) {
            Pair t = uniqueRandomPairList.get(i);
            System.out.println(t.toString());

        }*/
        //uniqueRandomPairList.
        //uniqueRandomPairList.addAll(hs);










        //System.out.println((new Random().nextInt(20)+1)%20);



        //System.out.println(uniqueRandomStringList.size());
        /*
        for (String s:uniqueRandomStringList) {
            //System.out.println(s);

        }

         */


        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);


            List<String> tableNames = new ArrayList<String>();
            tableNames.add("Conference");
            tableNames.add("ConferenceEvent");


            String sql = "";
            for (String string:tableNames) {
                sql+="TRUNCATE "+string+" CASCADE;\n";
            }


            PreparedStatement deleteStatement = connection.prepareStatement(sql);
            deleteStatement.executeUpdate();

            sql = "INSERT INTO Conference (id,name) Values (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(sql);

            for(int i = 0;i < 20;i++){

                insertStatement.setInt(1,i+1);
                insertStatement.setString(2,uniqueRandomStringList.remove(i));
                insertStatement.executeUpdate();

                //System.out.printf("%d rows added \n", rows);

            }


            sql = "INSERT INTO ConferenceEvent (id,conference_id,year,total_papers,accepted_papers,acceptance_ratio) Values (?, ?, ?, ?, ?, ?)";

            insertStatement = connection.prepareStatement(sql);


            for(int i = 0;i < 100;i++){

                insertStatement.setInt(1,i+1);
                //System.out.println(i);
                Pair t = uniqueRandomPairList.get(i);
                //System.out.println(t.toString());
                insertStatement.setInt(2,t.getFirst());
                insertStatement.setInt(3,t.getSecond());
                Integer totalPapers = new Random().nextInt(300);
                insertStatement.setInt(4, totalPapers);
                //Integer acceptedPapers = new Random().nextInt(299);
                Float acceptanceRatio = new Random().nextFloat();
                insertStatement.setFloat(6, acceptanceRatio);
                Float acceptedPapers = totalPapers*acceptanceRatio;
                insertStatement.setInt(5,  acceptedPapers.intValue());
                insertStatement.executeUpdate();

                //System.out.printf("%d rows added \n", rows);

            }


            //sql = "SELECT ";






        } catch (SQLException e) {
            //System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }
        finally {
            if (connection != null) {
                connection.close();
            }

        }

    }
}
