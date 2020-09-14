package com.hcl.intranet.springboot2authserver.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;





@Repository
public class UserJDBCRepository {

	@Autowired
	private DataSource dataSource;
	
	public String checkUser(String clientId) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("CheckContactCity");
		SqlParameterSource in = new MapSqlParameterSource().addValue("CLIENT_ID", clientId);
		Map<String, Object> out = jdbcCall.execute(in);
		List<String> result = new ArrayList<String>();
		for(Entry<String, Object> row:out.entrySet()){
			result.addAll((Collection<? extends String>) row.getValue());
			}
		try {
			 Object l_hmColmnData = result.get(0);
			if(l_hmColmnData.toString().equalsIgnoreCase("{result=1}")) {
				return "valid";
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "invalid";
		
		
	}
}
