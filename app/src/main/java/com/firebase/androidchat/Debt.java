
public class Debt
{
	private Person creditor;
	private Person debitor;
	private float principal;
	private float rate;
	private long date;
	private String note;
	@SuppressWarnings("unused")
	private boolean open;
	private boolean ontime;

	Debt(Person c, Person d, float pr, float r, String memo)
	{
		creditor = c;
		debitor = d;
		principal = pr;
		rate = r;
		date = System.currentTimeMillis();
		note = memo;
		open = true;
		ontime = false;
	}

	Person getCreditor()
	{
		return creditor;
	}

	Person getDebitor()
	{
		return debitor;
	}

	float getPrincipal()
	{
		return principal;
	}

	float getRate()
	{
		return rate;
	}

	long getDate()
	{
		return date;
	}

	String getNote()
	{
		return note;
	}

	float getCurrent()
	{
		return principal * (1 + rate);
	}

	void clear()
	{
		open = false;

		if (!(date + 2628000000L < System.currentTimeMillis()))
			ontime = true;
	}
	
	boolean getontime()
	{
		return ontime;
	}
}