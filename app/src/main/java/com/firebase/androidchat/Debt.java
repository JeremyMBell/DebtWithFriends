package com.firebase.androidchat;

public class Debt
{
	private Person creditor;
	private Person debitor;
	private float principal;
	private float rate;
	private final long date;
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
		if (date + 2628000000L > System.currentTimeMillis())
			return principal;
		else
			return (principal * (1 + rate) * (System.currentTimeMillis() - date)
					/ 2628000000);
	}

	void clear()
	{
		open = false;

		if (date + 2628000000L > System.currentTimeMillis())
			ontime = true;
	}

	boolean getontime()
	{
		return ontime;
	}
}
