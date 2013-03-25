package edu.cmu.andrew.vravuri.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class DataStoreUtils {

	public static void addPerson(Person p) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key k = KeyFactory.createKey("person", p.getId());
		Entity e = new Entity("person", k);
		e.setProperty("id", p.getId());
		e.setProperty("firstname", p.getFirstName());
		e.setProperty("lastname", p.getLastName());
		e.setProperty("picture", p.getProfilePicture());
		e.setProperty("url", p.getProfileURL());
		datastore.put(e);
	}

	public static void addSkill(Skill s) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key k = KeyFactory.createKey("skill", s.getName());
		Entity e = new Entity("skill", k);
		e.setProperty("name", s.getName());
		e.setProperty("count", new Integer(s.getCount()));
		String peopleIds = new String();
		for (int i = 0; i < s.getPerson_ids().size(); i++)
			peopleIds += (s.getPerson_ids().get(i) + " ");
		e.setProperty("people", peopleIds);
		datastore.put(e);
	}

	public static List<Skill> getSkills() {
		List<Skill> skills = new ArrayList<Skill>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query q = new Query("skill");
		PreparedQuery pq = datastore.prepare(q);
		for (Entity e : pq.asIterable()) {
			Skill s = new Skill();
			s.setName((String) e.getProperty("name"));
			String people = (String) e.getProperty("people");
			StringTokenizer sToken = new StringTokenizer(people, " ");
			while (sToken.hasMoreTokens())
				s.addPerson(sToken.nextToken());
			skills.add(s);
		}
		return skills;
	}

	public static List<Person> getPersonsWithSkill(String skill) {
		List<Person> persons = new ArrayList<Person>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query q = new Query("skill");
		FilterPredicate fp = new FilterPredicate("name", FilterOperator.EQUAL,
				skill);
		q.setFilter(fp);

		PreparedQuery pq = datastore.prepare(q);
		for (Entity e : pq.asIterable()) {

			String people = (String) e.getProperty("people");
			StringTokenizer sToken = new StringTokenizer(people, " ");
			while (sToken.hasMoreTokens()) {
				Query q2 = new Query("person");
				FilterPredicate fp2 = new FilterPredicate("id",
						FilterOperator.EQUAL, sToken.nextToken());
				q2.setFilter(fp2);
				PreparedQuery pq2 = datastore.prepare(q2);
				for (Entity e2 : pq2.asIterable()) {

					Person p = new Person((String) e2.getProperty("id"),
							(String) e2.getProperty("firstname"),
							(String) e2.getProperty("lastname"),
							(String) e2.getProperty("picture"),
							(String) e2.getProperty("url"));
					persons.add(p);
				}
			}
		}

		return persons;
	}

	public static void clearDataStore() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query q = new Query();
		PreparedQuery pq = datastore.prepare(q);
		for (Entity e : pq.asIterable()) {
			datastore.delete(e.getKey());
		}
	}
}
