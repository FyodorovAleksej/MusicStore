package by.fyodorov.musicstore.specification.user.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.model.UserBonusType.USER_BONUS_ASSEMBLAGE;
import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserCashAfterAssemblageOperationCustomSelect implements UserCustomSelectSpecification {
        public static final String SUMMARY_KEY = "summary";
        private static final String SUMMARY_TABLE = "SUMMARY";
        private static final String USER_CASH_AFTER_SELECT = String.format(
                "SELECT (%s.%s - IF(%s.%s & %s, 0, (1 - %s.%s) * " +
                        "(SELECT %s.%s FROM %s WHERE %s.%s = ?))) AS %s " +
                        "FROM %s WHERE %s.%s = ? ;\n",

                USER_BD_TABLE, USER_CASH,
                USER_BD_TABLE, USER_BONUS,
                USER_BONUS_ASSEMBLAGE,
                USER_BD_TABLE, USER_DISCOUNT,
                ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_PRICE,
                ASSEMBLAGE_BD_TABLE,
                ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME,
                SUMMARY_TABLE,
                USER_BD_TABLE,
                USER_BD_TABLE, USER_USERNAME);

        private String userName;
        private String assemblageName;

        public UserCashAfterAssemblageOperationCustomSelect(String userName, String assemblageName) {
            this.userName = userName;
            this.assemblageName = assemblageName;
        }

        @Override
        public String toSqlClauses() {
            return USER_CASH_AFTER_SELECT;
        }

        @Override
        public String[] getArguments() {
            String[] result = new String[2];
            result[0] = assemblageName;
            result[1] = userName;
            return result;
        }

        @Override
        public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
            LinkedList<HashMap<String, String>> result = new LinkedList<>();
            try {
                while (set.next()) {
                    HashMap<String, String> arguments = new HashMap<>();
                    String assemblagePrice = Integer.toString(set.getInt(SUMMARY_TABLE));
                    arguments.put(SUMMARY_KEY, assemblagePrice);

                    result.add(arguments);
                }
            }
            catch (SQLException e) {
                throw new ConnectorException("can't read result set from User DB", e);
            }
            return result;
        }
    }
