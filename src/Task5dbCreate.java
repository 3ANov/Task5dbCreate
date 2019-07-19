import org.apache.commons.text.RandomStringGenerator;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Task5dbCreate {
    public static void main(String[] args) throws SQLException{



        String DB_URL = "jdbc:postgresql://127.0.0.1:5432/task5db";
        String USER = "postgres";
        String PASS = "gfhjkmjb";



        List<Pair> uniqueRandomPairList = new ArrayList<Pair>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                uniqueRandomPairList.add(new Pair(j+1,1995+i));
            }

        }










        //System.out.println((new Random().nextInt(20)+1)%20);




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
            List<String> temp = UniqueListOfStringGenerator.generate(6);


            for(int i = 0;i < 20;i++){

                insertStatement.setInt(1,i+1);
                insertStatement.setString(2,temp.remove(i));
                insertStatement.executeUpdate();

                //System.out.printf("%d rows added \n", rows);

            }


            sql = "INSERT INTO ConferenceEvent (id,conference_id,year,total_papers,accepted_papers,acceptance_ratio) Values (?, ?, ?, ?, ?, ?)";

            insertStatement = connection.prepareStatement(sql);
            Random random =  new Random();


            for(int i = 0;i < 100;i++){

                insertStatement.setInt(1,i+1);
                //System.out.println(i);
                Pair pair = uniqueRandomPairList.get(i);
                //System.out.println(t.toString());
                insertStatement.setInt(2,pair.getFirst());
                insertStatement.setInt(3,pair.getSecond());
                Integer totalPapers = random.nextInt(300);
                insertStatement.setInt(4, totalPapers);
                //Integer acceptedPapers = new Random().nextInt(299);
                Float acceptanceRatio = random.nextFloat();
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
