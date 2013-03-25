package edu.cmu.andrew.vravuri.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FriendsSkillsRetriever {

	Map<String, Skill> skillMap = new HashMap<String, Skill>();
	
	public void retrieveSkills(String connectionData, HttpServletRequest request) {
	
		List<Skill> skills = new ArrayList<Skill>();
		List<Person> persons = new ArrayList<Person>();

		System.out.println(connectionData);
		
		Document doc = Jsoup.parse(connectionData);

		Elements people = doc.getElementsByTag("person");

		for (int i = 0; i < people.size(); i++) {
			Element person = people.get(i);

			String id = new String();
			Elements nodes = person.getElementsByTag("id");
			if (nodes != null && nodes.size() > 0)
				id = nodes.get(0).text().trim();

			String firstName = new String();
			nodes = person.getElementsByTag("first-name");
			if (nodes != null && nodes.size() > 0)
				firstName = nodes.get(0).text().trim();

			String lastName = new String();
			nodes = person.getElementsByTag("last-name");
			if (nodes != null && nodes.size() > 0)
				lastName = nodes.get(0).text().trim();

			String profileURL = new String();
			nodes = person.getElementsByTag("public-profile-url");
			if (nodes != null && nodes.size() > 0)
				profileURL = nodes.get(0).text().trim();

			String profilePicture = new String();
			nodes = person.getElementsByTag("picture-url");
			if (nodes != null && nodes.size() > 0)
				profilePicture = nodes.get(0).text().trim();

			Person p = new Person(id, firstName, lastName, profilePicture,
					profileURL);
			// DataStoreUtils.addPerson(p);
			persons.add(p);

			if (profileURL.length() == 0)
				continue;

			/*List<String> skillNames = getSkillsFromProfile(profileURL);

			for (int j = 0; j < skillNames.size(); j++) {
				String skillName = skillNames.get(j);
				Skill skill;
				if ((skill = skillMap.get(skillName)) != null) {
					skill.addPerson(id);
				} else {
					skill = new Skill(skillName, id);
					skillMap.put(skillName, skill);
				}
			}*/
		}

		for (String skill : skillMap.keySet()) {
			// DataStoreUtils.addSkill(skillMap.get(skill));
			skills.add(skillMap.get(skill));
		}

		request.setAttribute("skills", skills);
		request.setAttribute("persons", persons);
	}

	private List<String> getSkillsFromProfile(String profileURL) {

		Document doc = null;
		List<String> skills = new ArrayList<String>();
		try {
			doc = Jsoup.connect(profileURL).get();
		} catch (IOException e) {
			// Ignore and return
			return skills;
		}

		Element skillsNode = doc.getElementById("profile-skills");
		if (skillsNode == null)
			return skills;

		Elements skillList = skillsNode.getElementsByTag("a");

		for (int i = 0; i < skillList.size(); i++) {
			Element skillNode = skillList.get(i);
			String skillText = skillNode.text().trim();
			if (skillText.length() > 0 && !skillText.contains("View All"))
				skills.add(skillNode.text());
		}

		return skills;
	}

}
