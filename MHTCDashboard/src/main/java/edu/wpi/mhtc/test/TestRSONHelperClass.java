package edu.wpi.mhtc.test;

public class TestRSONHelperClass
{
	private Integer accountID;
	private String name;
	transient private Integer lastAccess; //not serialized
	
	/**
	 * default constructor
	 */
	public TestRSONHelperClass()
	{
		
	}
	
	/**
	 * constructor for setting all but the transient vars
	 * 
	 * @param aid the act id
	 * @param n the customer name
	 */
	public TestRSONHelperClass(int aid, String n)
	{
		this.accountID = aid;
		this.name = n;
	}

	/**
	 * @return the accountID
	 */
	public int getAccountID() {
		return accountID;
	}

	/**
	 * @param accountID the accountID to set
	 */
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the lastAccess
	 */
	public int getLastAccess() {
		return lastAccess;
	}

	/**
	 * @param lastAccess the lastAccess to set
	 */
	public void setLastAccess(int lastAccess) {
		this.lastAccess = lastAccess;
	}
}
