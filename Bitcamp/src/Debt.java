
public class Debt
{
	private Person creditor;
	private Person debitor;
	private int principal;
	private int rate;
	private String date;
	private String note;

	Person getCreditor()
	{
		return creditor;
	}

	Person getDebitor()
	{
		return debitor;
	}

	int getPrincipal()
	{
		return principal;
	}

	int getRate()
	{
		return rate;
	}

	String getDate()
	{
		return date;
	}

	String getNote()
	{
		return note;
	}

	int getCurrent()
	{
		return principal * (1 + rate / 100);
	}
}
