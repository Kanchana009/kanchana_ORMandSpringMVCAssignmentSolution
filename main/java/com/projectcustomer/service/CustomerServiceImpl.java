package com.projectcustomer.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectcustomer.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	private SessionFactory sessionFactory;

	private Session session;

	@Autowired
	public CustomerServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException ex) {
			session = sessionFactory.openSession();
		}
	}

	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		Transaction tx = session.beginTransaction();
		List<Customer> customers = session.createQuery("from Customer").list();
		tx.commit();
		return customers;
	}

	@Override
	@Transactional
	public Customer findById(int id) {
		// TODO Auto-generated method stub
		Transaction tx = session.beginTransaction();
		Customer customer = session.get(Customer.class, id);
		tx.commit();
		return customer;
	}

	@Override
	public void save(Customer customer) {
		// TODO Auto-generated method stub
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(customer);
		tx.commit();

	}

	@Transactional
	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		Transaction tx = session.beginTransaction();
		Customer customer = session.get(Customer.class, id);
		if (customer != null)
			session.delete(customer);
		tx.commit();

	}

}
