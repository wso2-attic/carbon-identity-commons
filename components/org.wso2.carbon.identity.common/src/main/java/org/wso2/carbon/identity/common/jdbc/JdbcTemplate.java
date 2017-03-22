/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.common.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * A utility class to call JDBC with lambda expressions.
 */
public class JdbcTemplate {

    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);
    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Executes a query on JDBC and return the result as a list of domain objects.
     *
     * @param query     the SQL query with the parameter placeholders.
     * @param rowMapper Row mapper functional interface
     * @param <E>  type of the Exception which may be thrown by the "RowMapper", and which supposed to be handled by
     *           the caller method.
     * @param <T> type of the result
     * @return List of domain objects of required type.
     * @throws E on execute query
     * @throws DataAccessException on execute query
     */
    public <E extends Exception, T extends Object> List<T> executeQuery(final String query, RowMapper<T, E> rowMapper)
            throws E, DataAccessException {
        return executeQuery(query, rowMapper, null);
    }

    /**
     * Executes a query on JDBC and return the result as a list of domain objects.
     *
     * @param query       the SQL query with the parameter placeholders.
     * @param rowMapper   Row mapper functional interface
     * @param queryFilter parameters for the SQL query parameter replacement.
     * @param <E>  type of the Exception which may be thrown by the "RowMapper", and which supposed to be handled by
     *           the caller method.
     * @param <T> type of the result
     * @return List of domain objects of required type.
     * @throws E on execute query
     * @throws DataAccessException on execute query
     */
    public <E extends Exception, T extends Object> List<T> executeQuery(String query,
        RowMapper<T, E> rowMapper, QueryFilter queryFilter) throws E, DataAccessException {

        List<T> result = new ArrayList();
        try (Connection connection = dataSource.getConnection()) {
            NamedPreparedStatement namedPreparedStatement = new NamedPreparedStatement(connection, query);
            try (PreparedStatement preparedStatement = namedPreparedStatement.getPreparedStatement()) {

                if (queryFilter != null) {
                    queryFilter.filter(namedPreparedStatement);
                }
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    int i = 0;
                    while (resultSet.next()) {
                        T row = rowMapper.mapRow(resultSet, i);
                        result.add(row);
                        i++;
                    }
                }
            }
        } catch (SQLException e) {
            logDebugInfo(
                    "There has been an error performing the database query. The query is {}, and the Parameters " +
                            "are {}", e, query, queryFilter);
            throw new DataAccessException("Error in performing Database query: " + query, e);
        }
        return result;
    }

    /**
     * Executes a query on JDBC and return the result as a domain object.
     *
     * @param query       the SQL query with the parameter placeholders.
     * @param rowMapper   Row mapper functional interface
     * @param queryFilter parameters for the SQL query parameter replacement.
     * @param <E>  type of the Exception which may be thrown by the "RowMapper", and which supposed to be handled by
     *           the caller method.
     * @param <T>         type of the result
     * @return domain object of required type.
     * @throws E on execute query
     * @throws DataAccessException on execute query
     */
    public <E extends Exception, T extends Object> T fetchSingleRecord(String query, RowMapper<T, E> rowMapper,
                                                                QueryFilter queryFilter) throws E, DataAccessException {

        T result = null;
        try (Connection connection = dataSource.getConnection()) {
            NamedPreparedStatement namedPreparedStatement = new NamedPreparedStatement(connection, query);
            try (PreparedStatement preparedStatement = namedPreparedStatement.getPreparedStatement()) {
                if (queryFilter != null) {
                    queryFilter.filter(namedPreparedStatement);
                }
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        result = rowMapper.mapRow(resultSet, 0);
                    }
                    if (resultSet.next()) {
                        logDebugInfo("There are more records than one found for query: {} for the parameters {}", query,
                                queryFilter);
                        throw new DataAccessException("There are more records than one found for query: " + query);
                    }
                }
            }
        } catch (SQLException e) {
            logDebugInfo(
                    "There has been an error performing the database query. The query is {}, and the parameters are {}",
                    e, query, rowMapper, queryFilter);
            throw new DataAccessException("Error in performing database query: " + query, e);
        }
        return result;
    }

    public void executeUpdate(final String query, QueryFilter queryFilter) throws DataAccessException {
        try (Connection connection = dataSource.getConnection();) {
            NamedPreparedStatement namedPreparedStatement = new NamedPreparedStatement(connection, query);
            try (PreparedStatement preparedStatement = namedPreparedStatement.getPreparedStatement()) {
                if (queryFilter != null) {
                    queryFilter.filter(namedPreparedStatement);
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            logDebugInfo("Error in performing database update: {} with parameters {}", query, queryFilter);
            throw new DataAccessException("Error in performing database update: " + query, e);
        }
    }

    /**
     * Executes the jdbc insert/update query.
     *
     * @param query The SQL for insert/update.
     * @param <T>   type of the result
     * @throws DataAccessException on execute query
     */
    public <T extends Object> void executeUpdate(final String query) throws DataAccessException {
        try (Connection connection = dataSource.getConnection()) {
            NamedPreparedStatement namedPreparedStatement = new NamedPreparedStatement(connection, query);
            doInternalUpdate(null, namedPreparedStatement);
        } catch (SQLException e) {
            logDebugInfo("Error in performing database update: {}", query);
            throw new DataAccessException("Error in performing database update: " + query, e);
        }
    }

    /**
     * Executes the jdbc insert/update query.
     *
     * @param query           The SQL for insert/update.
     * @param queryFilter     Query filter to prepared statement parameter binding.
     * @param bean            the Domain object to be inserted/updated.
     * @param fetchInsertedId for fetch auto incremented Id
     * @param <T>             type of the result
     * @return auto incremented Id
     * @throws DataAccessException on execute query
     */
    public <T extends Object> int executeInsert(String query, QueryFilter queryFilter, T bean, boolean fetchInsertedId)
            throws DataAccessException {

        try (Connection connection = dataSource.getConnection()) {
            NamedPreparedStatement namedPreparedStatement = new NamedPreparedStatement(connection, query);
            doInternalUpdate(queryFilter, namedPreparedStatement);
            if (fetchInsertedId) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Mapping generated key (Auto Increment ID) to the object");
                }
                try (ResultSet generatedKeys = namedPreparedStatement.getPreparedStatement().getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int resultId = generatedKeys.getInt(1);
                        if (logger.isDebugEnabled()) {
                            logger.debug("Newly inserted ID (Auto Increment ID) is {} for the bean {} ", resultId,
                                    bean);
                        }
                        return resultId;
                    } else {
                        throw new SQLException("Creating the record failed with Auto-Generated ID, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            logDebugInfo("Error in performing database insert: {} with parameters {}", query, queryFilter);
            throw new DataAccessException("Error in performing database insert: " + query, e);
        }
        return 0;
    }

    private <T extends Object> void doInternalUpdate(QueryFilter queryFilter,
            NamedPreparedStatement namedPreparedStatement) throws SQLException, DataAccessException {
        if (queryFilter != null) {
            queryFilter.filter(namedPreparedStatement);
        }
        namedPreparedStatement.getPreparedStatement().executeUpdate();
    }

    private void logDebugInfo(String s, Object... params) {
        logDebugInfo(s, null, params);
    }

    private void logDebugInfo(String s, Exception e, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug(MessageFormatter.arrayFormat(s, params).getMessage(), e);
        }
    }
}
