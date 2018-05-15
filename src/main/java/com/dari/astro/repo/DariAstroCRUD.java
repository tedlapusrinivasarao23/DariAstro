package com.dari.astro.repo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.dari.astro.bos.BirthChartDetails;

@Component("dariAstroCRUD")
public class DariAstroCRUD {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
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

	public List<BirthChartDetails> loadAll(List<Integer> birthChartIds) {
		session = sessionFactory.openSession();
		List<BirthChartDetails> birthChartDetails = new ArrayList<BirthChartDetails>();
		List<BirthChartDetails> fetchedSongs = session.createCriteria(BirthChartDetails.class).add(Restrictions.in("id",birthChartIds)).list();
		birthChartDetails.addAll(fetchedSongs);
		session.close();
		 return birthChartDetails;
	}
	
	public void methodForDeleteAll(List<BirthChartDetails> birthChartDetailsList) {
		hibernateTemplate.deleteAll(birthChartDetailsList);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}


}
