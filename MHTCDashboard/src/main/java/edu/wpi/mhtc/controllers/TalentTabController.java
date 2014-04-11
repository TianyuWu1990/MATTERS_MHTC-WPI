package edu.wpi.mhtc.controllers;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.mhtc.model.TalentTab;

public class TalentTabController
{
	public List<TalentTab> getTalents()
	{
		TalentTab t1 = new TalentTab();
		t1.setName("State Science and Technology Index");
		t1.setMass_rank(1);
		t1.setDatasourceURL("melkininstitute.org");
		t1.setDatasource("Melkin Institute");

		TalentTab t2 = new TalentTab();
		t2.setName("Fourth and Eighth Grade Math and Reading");
		t2.setMass_rank(1);
		t2.setDatasourceURL("http://nces.ed.gov/nationsreportcard/");
		t2.setDatasource("NAEP");

		TalentTab t3 = new TalentTab();
		t3.setName("Science and Engineering Indicators");
		t3.setMass_rank(1);
		t3.setDatasourceURL("mtc.gov");
		t3.setDatasource("MTC");

		TalentTab t4 = new TalentTab();
		t4.setName("Percent High Tech Workers in Workforce");
		t4.setMass_rank(1);
		t4.setDatasourceURL("http://www.nsf.gov/ns/");
		t4.setDatasource("National Science Board");

		TalentTab t5 = new TalentTab();
		t5.setName("State Tax Cost on Business");
		t5.setMass_rank(44);
		t5.setDatasourceURL("taxfoundation.org");
		t5.setDatasource("KPMG / Tax Foundation");

		TalentTab t6 = new TalentTab();
		t6.setName("State Tax Business Climate");
		t6.setMass_rank(25);
		t6.setDatasourceURL("taxfoundation.org");
		t6.setDatasource("Tax Foundation");
		
		
		
		List<TalentTab> talents = new LinkedList<TalentTab>();
		talents.add(t1);
		talents.add(t2);
		talents.add(t3);
		talents.add(t4);
		talents.add(t5);
		talents.add(t6);
		
		return talents;
	}
}
