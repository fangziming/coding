/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.cas.authentication.handler.sxd;

import java.security.GeneralSecurityException;
import java.util.Map;

import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.services.persondir.support.sxd.LoginNameUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.validation.constraints.NotNull;

/**
 * cas数据库认证处理器
 * @author sheungxin
 *
 */
public class QueryDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

    @NotNull
    private String sql;
    @NotNull
    private String sqlTel;
    @NotNull
    private String sqlEmail;
    
    /** {@inheritDoc} */
	@Override
    protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential)
            throws GeneralSecurityException, PreventedException {

        final String username = credential.getUsername();
        try {
        	final Map<String, Object> queryMap;
        	if (username.length()==11&&LoginNameUtils.isMobile(username)) {  // 如果是手机号 
        		queryMap=getJdbcTemplate().queryForMap(this.sqlTel, username); 
            } else if(LoginNameUtils.isMobile(username)){// 如果是邮箱 
            	queryMap=getJdbcTemplate().queryForMap(this.sqlEmail, username); 
            }else {  
            	queryMap=getJdbcTemplate().queryForMap(this.sql, username); 
            }
            final String dbPassword=(String)queryMap.get("password");
            final String loginFlag=(String)queryMap.get("login_flag");
            final String encryptedPassword = this.getPasswordEncoder().encode(dbPassword.substring(0,16)+credential.getPassword());
            if (!dbPassword.substring(16).equals(encryptedPassword)) {
                throw new FailedLoginException("Password does not match value on record.");
            }else if(loginFlag.equals("0")){
            	throw new FailedLoginException("This user is not allowed to login.");
            }
        } catch (final IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                throw new AccountNotFoundException(username + " not found with SQL query");
            } else {
                throw new FailedLoginException("Multiple records found for " + username);
            }
        } catch (final DataAccessException e) {e.printStackTrace();
            throw new PreventedException("SQL exception while executing query for " + username, e);
        }
        return createHandlerResult(credential, new SimplePrincipal(username), null);
    }

    /**
     * @param sql The sql to set.
     */
    public void setSql(final String sql) {
        this.sql = sql;
    }
    
    public void setSqlTel(final String sqlTel) {
        this.sqlTel = sqlTel;
    }
    
    public void setSqlEmail(final String sqlEmail) {
        this.sqlEmail = sqlEmail;
    }
}
