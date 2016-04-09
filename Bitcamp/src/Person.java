import java.util.ArrayList;
import java.util.Scanner;

public class Person
{
	private String name;
	private ArrayList<Person> friends;
	private ArrayList<Debt> debts;
	private ArrayList<Debt> loans;
	private String password;
	
	Person(String nm, String pw)
	{
		name = nm;
		password = pw;
		friends = new ArrayList<Person>();
		debts = new ArrayList<Debt>();
		loans = new ArrayList<Debt>();
	}
	
	void changeName(String nm)
	{
		name = nm;
	}
	
	void addFriend(Person friend)
	{
		if(!friends.contains(friend))
			friends.add(friend);
	}
	
	void addLoan(Debt loan)
	{
		if(!loans.contains(loan))
			loans.add(loan);
	}
	
	void addDebt(Debt debt)
	{
		if(!debts.contains(debt))
			debts.add(debt);
	}
	
	@SuppressWarnings("resource")
	boolean changePassword()
	{
		System.out.println("Please enter your old password: ");
		Scanner scan = new Scanner(System.in);
		String temp = scan.next();
		
		if(!temp.equals(password))
			return false;
		
		System.out.println("Please enter your new password: ");
		String temp1 = scan.next();
		System.out.println("Please re-enter your new password: ");
		String temp2 = scan.next();
		
		if(!temp1.equals(temp2))
			return false;
		
		scan.close();
		password = temp1;
		return true;
	}
	
	int getScore()
	{
		return 850;
	}
	
	void deleteLoan(Debt loan)
	{
			loans.remove(loan);
	}
	
	void deleteDebt(Debt debt)
	{
			debts.remove(debt);
	}
	
	void poke(Person p)
	{
		p.getPoked();
	}
	
	void getPoked()
	{
		
	}
}
