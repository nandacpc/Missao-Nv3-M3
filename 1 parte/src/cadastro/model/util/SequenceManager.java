package cadastro.model.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Nanda
 */

public class SequenceManager {
    private static final String SELECT_SEQUENCE_SQL = "SELECT sequence_value FROM sequences WHERE sequence_name = ?";
    private static final String UPDATE_SEQUENCE_SQL = "UPDATE sequences SET sequence_value = ? WHERE sequence_name = ?";

    public int getValue(String sequenceName) {
        Connection connection = null;
        PreparedStatement selectStatement = null;
        PreparedStatement updateStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConectorBD.getConnection();
            connection.setAutoCommit(false);

            selectStatement = connection.prepareStatement(SELECT_SEQUENCE_SQL);
            selectStatement.setString(1, sequenceName);
            resultSet = selectStatement.executeQuery();

            int nextValue = 1;

            if (resultSet.next()) {
                nextValue = resultSet.getInt("sequence_value") + 1;

                updateStatement = connection.prepareStatement(UPDATE_SEQUENCE_SQL);
                updateStatement.setInt(1, nextValue);
                updateStatement.setString(2, sequenceName);
                updateStatement.executeUpdate();
            }

            connection.commit();

            return nextValue;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                }
            }
            return 0; 
        } finally {
            ConectorBD.close(resultSet);
            ConectorBD.close(selectStatement);
            ConectorBD.close(updateStatement);
            ConectorBD.close(connection);
        }
    }
}