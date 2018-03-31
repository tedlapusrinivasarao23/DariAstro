package com.dari.astro.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component("dariAstroCRUD")
public class DariAstroCRUD {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	public void methodForSave(Object object) {
		hibernateTemplate.save(object);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}

	public void methodForUpdate(Object object) {
		hibernateTemplate.update(object);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}

	public void methodForSaveORUpdate(Object object) {
		hibernateTemplate.saveOrUpdate(object);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}

	@SuppressWarnings("unchecked")
	public Object getFromDb(Class object, int id) {
		Object addContact = hibernateTemplate.get(object, id);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return addContact;
	}

	public void methodForDelete(Object object) {
		hibernateTemplate.delete(object);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}

}
