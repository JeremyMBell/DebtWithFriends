package com.firebase.androidchat;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class Person
{
	@SuppressWarnings("unused")
	private String name;
	private ArrayList<Person> friends;
	private ArrayList<Debt> debts;
	private ArrayList<Debt> loans;
	private final long time;

	Person(String nm)
	{
		name = nm;
		friends = new ArrayList<Person>();
		debts = new ArrayList<Debt>();
		loans = new ArrayList<Debt>();
		time = System.currentTimeMillis();
	}

	String getName()
	{
		return name;
	}

	void changeName(String nm)
	{
		name = nm;
	}

	void addFriend(Person friend)
	{
		if (!friends.contains(friend))
			friends.add(friend);
	}

	void removeFriend(Person friend)
	{
		friends.remove(friend);
	}

	void addLoan(Debt loan)
	{
		if (!loans.contains(loan))
			loans.add(loan);
	}

	void addDebt(Debt debt)
	{
		if (!debts.contains(debt))
			debts.add(debt);
	}

	int getScore()
	{
		int total = 0, ontime = 0;
		for (Debt d : debts)
		{
			total++;
			if (d.getontime())
				ontime++;
		}

		int size = debts.size();
		long age = System.currentTimeMillis() - time;

		double score = 70 * ontime / total;

		if (size >= 10)
			score += 20;
		else
			score += 2 * size;

		if (age > 2628000000L * 12)
			score += 10;
		else
			score += 10 * (age / (2628000000L * 12));

		int scr = (int) (300 + score * 5.5);

		return scr;
	}

	void deleteLoan(Debt loan)
	{
		for (Debt l : loans)
			if (l.equals(loan))
				l.clear();
	}

	void deleteDebt(Debt debt)
	{
		for (Debt d : debts)
			if (d.equals(debt))
				d.clear();
	}

	void poke(Person p)
	{
		p.getPoked();
	}

	void getPoked()
	{

	}

	boolean equals(Person p)
	{
		return (p.name.equals(name) && p.time == time);
	}

	Map<String, Object> map()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("friends", friends);
		map.put("debts", debts);
		map.put("loans", loans);
		map.put("time", time);
		return map;
	}
}
