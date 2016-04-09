import java.util.ArrayList;
import java.util.Scanner;

public class Person
{
	@SuppressWarnings("unused")
	private String name;
	private ArrayList<Person> friends;
	private ArrayList<Debt> debts;
	private ArrayList<Debt> loans;
	private String password;
	private long time;

	Person(String nm, String pw)
	{
		name = nm;
		password = pw;
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

	@SuppressWarnings("resource")
	boolean changePassword()
	{
		System.out.println("Please enter your old password: ");
		Scanner scan = new Scanner(System.in);
		String temp = scan.next();

		if (!temp.equals(password))
			return false;

		System.out.println("Please enter your new password: ");
		String temp1 = scan.next();
		System.out.println("Please re-enter your new password: ");
		String temp2 = scan.next();

		if (!temp1.equals(temp2))
			return false;

		scan.close();
		password = temp1;
		return true;
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
}
