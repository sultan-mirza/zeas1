package com.itc.zeas.streaming;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itc.zeas.streaming.model.StreamingEntity;
import com.itc.zeas.utility.connection.ConnectionUtility;

public class TransformationRule {
	private Logger LOG = Logger.getLogger(TransformationRule.class);

	public static void main(String[] args) throws Exception {
		StreamingEntity entity=new StreamingEntity();
		entity.setName("sadwd");
		new TransformationRule().addRule(entity);
	}
	public boolean addRule(StreamingEntity entity) throws Exception {
		LOG.info("Adding new rule " + entity.toString());
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {

			String sQuery = ConnectionUtility.getSQlProperty("ADD_TRANSFORMATION_RULE");
			connection = ConnectionUtility.getConnection();
			preparedStatement = connection.prepareStatement(sQuery);
			preparedStatement.setString(1, entity.getName());
			preparedStatement.setString(2, entity.getJsonBlob().get("Output_Location").textValue());
			preparedStatement.setString(3, entity.getCreatedBy());
			preparedStatement.setString(4, entity.getJsonBlob().get("className").textValue());
			preparedStatement.setString(5, entity.getJsonBlob().get("methodName").textValue());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error("Exception occured while adding new Rule " + e.getMessage());
			return false;
		} finally {
			ConnectionUtility.releaseConnectionResources(preparedStatement, connection);
		}
		return true;
	}

	public List<String> getRulesName() throws Exception {
		List<String> rulesName = new ArrayList<>();
		LOG.info("Getting list of Transformation Rules");
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sQuery = ConnectionUtility.getSQlProperty("GET_TRANSFORMATION_RULE_LIST");
			connection = ConnectionUtility.getConnection();
			stmt = connection.createStatement();

			rs = stmt.executeQuery(sQuery);
			while (rs.next()) {
				rulesName.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			LOG.error("Exception while getting rules name " + e.getMessage());
			e.printStackTrace();
		} finally {
			ConnectionUtility.releaseConnectionResources(rs, stmt, connection);
		}

		return rulesName;
	}

	
}
