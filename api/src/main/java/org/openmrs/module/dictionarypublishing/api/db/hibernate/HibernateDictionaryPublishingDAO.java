/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.dictionarypublishing.api.db.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.module.dictionarypublishing.api.db.DictionaryPublishingDAO;

/**
 * It is a default implementation of {@link DictionaryPublishingDAO}.
 */
public class HibernateDictionaryPublishingDAO implements DictionaryPublishingDAO {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * @see org.openmrs.module.dictionarypublishing.api.db.DictionaryPublishingDAO#getConceptsToExport(Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Concept> getConceptsToExport(Date fromDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Concept.class);
		criteria.add(Restrictions.eq("retired", false));
		if (fromDate != null) {
			criteria.add(Restrictions.or(Restrictions.gt("dateCreated", fromDate),
			    Restrictions.gt("dateChanged", fromDate)));
		}
		
		return criteria.list();
	}
}