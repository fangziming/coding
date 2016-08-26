package com.xbao.common.filter;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.jasig.cas.client.session.SessionMappingStorage;

/**
 * Listener to detect when an HTTP session is destroyed and remove it from the map of
 * managed sessions.  Also allows for the programmatic removal of sessions.
 * <p>
 * Enables the CAS Single Sign out feature.
 *
 * Scott Battaglia
 * @version $Revision$ Date$
 * @since 3.1
 */
public final class SingleSignOutHttpSessionListener implements HttpSessionListener {

	private SessionMappingStorage sessionMappingStorage;
	
    public void sessionCreated(final HttpSessionEvent event) {
        // nothing to do at the moment
    }

    public void sessionDestroyed(final HttpSessionEvent event) {
    	if (sessionMappingStorage == null) {
    	    sessionMappingStorage = getSessionMappingStorage();
    	}
        final HttpSession session = event.getSession();
        sessionMappingStorage.removeBySessionById(session.getId());
    }

    /**
     * Obtains a {@link SessionMappingStorage} object. Assumes this method will always return the same
     * instance of the object.  It assumes this because it generally lazily calls the method.
     * 
     * @return the SessionMappingStorage
     */
    protected static SessionMappingStorage getSessionMappingStorage() {
    	return SingleSignOutFilter.getSingleSignOutHandler().getSessionMappingStorage();
    }
}

