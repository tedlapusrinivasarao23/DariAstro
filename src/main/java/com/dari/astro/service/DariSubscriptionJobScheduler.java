package com.dari.astro.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dari.astro.bos.HoroscopeMatching;
import com.dari.astro.bos.KPEphemeris;
import com.dari.astro.bos.KPHoraryHoroscope;
import com.dari.astro.bos.KPHoraryHoroscopePredictions;
import com.dari.astro.bos.KPHoroscopeMatching;
import com.dari.astro.bos.KPMuhurta;
import com.dari.astro.bos.KPNatalHoroscope;
import com.dari.astro.bos.KPNatalHoroscopePredictions;
import com.dari.astro.bos.MundaneAstrology;
import com.dari.astro.bos.Varshphal;
import com.dari.astro.bos.VedicHoroscope;
import com.dari.astro.bos.VedicHoroscopePredictions;
import com.dari.astro.bos.VedicMuhurta;
import com.dari.astro.bos.VedicPanchanga;
import com.dari.astro.mappers.SendEmail;
import com.dari.astro.mappers.SendSms;
import com.dari.astro.repo.DariAstroCRUD;
import com.dari.astro.repo.GetAllRepo;


@Component
public class DariSubscriptionJobScheduler {
	
	@Autowired
	private SendEmail sendEmail;

	@Autowired
	private SendSms sendSms;
	
	@Autowired
	private DariAstroCRUD dariAstroCRUD;
	
	@Autowired
	private GetAllRepo getAllRepo;
	
	private static final DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh.mm.ss aa");

	// 2 second.
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void kpNatalHoroscopeScheduler() {
		try {
			List<KPNatalHoroscope> kpNatalHoroscopeList = getAllRepo.getAllKPNatalHoroscope();

			for (KPNatalHoroscope kpNatalHoroscope : kpNatalHoroscopeList) {
				if (!kpNatalHoroscope.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), kpNatalHoroscope.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(kpNatalHoroscope.getOwnerEmail(), "",
								" Your Subscription for DARI KPNatalHoroscope is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(kpNatalHoroscope.getOwnerNumber(), "", " Your Subscription for DARI KPNatalHoroscope is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(kpNatalHoroscope.getOwnerEmail(), "",
								" Your Subscription for DARI KPNatalHoroscope is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(kpNatalHoroscope.getOwnerNumber(), "",
						  " Your Subscription for DARI KPNatalHoroscope is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						kpNatalHoroscope.setChartStatus(false);
					}
					kpNatalHoroscope.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(kpNatalHoroscope);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void kpHoraryHoroscopeScheduler() {
		try {
			List<KPHoraryHoroscope> kpHoraryHoroscopeList = getAllRepo.getAllKPHoraryHoroscope();

			for (KPHoraryHoroscope kpHoraryHoroscope : kpHoraryHoroscopeList) {
				if (!kpHoraryHoroscope.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), kpHoraryHoroscope.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(kpHoraryHoroscope.getOwnerEmail(), "",
								" Your Subscription for DARI KPHoraryHoroscope is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(kpHoraryHoroscope.getOwnerNumber(), "", " Your Subscription for DARI kpHoraryHoroscope is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(kpHoraryHoroscope.getOwnerEmail(), "",
								" Your Subscription for DARI KPHoraryHoroscope is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(kpHoraryHoroscope.getOwnerNumber(), "",
						  " Your Subscription for DARI kpHoraryHoroscope is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						kpHoraryHoroscope.setChartStatus(false);
					}
					kpHoraryHoroscope.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(kpHoraryHoroscope);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void kpNatalHoroscopePredictionsScheduler() {
		try {
			List<KPNatalHoroscopePredictions> kpNatalHoroscopePredictionsList = getAllRepo.getAllKPNatalHoroscopePredictions();

			for (KPNatalHoroscopePredictions kpNatalHoroscopePredictions : kpNatalHoroscopePredictionsList) {
				if (!kpNatalHoroscopePredictions.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), kpNatalHoroscopePredictions.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(kpNatalHoroscopePredictions.getOwnerEmail(), "",
								" Your Subscription for DARI KPNatalHoroscopePredictions is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(kpNatalHoroscopePredictions.getOwnerNumber(), "", " Your Subscription for DARI kpNatalHoroscopePredictions is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(kpNatalHoroscopePredictions.getOwnerEmail(), "",
								" Your Subscription for DARI KPNatalHoroscopePredictions is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(kpNatalHoroscopePredictions.getOwnerNumber(), "",
						  " Your Subscription for DARI kpNatalHoroscopePredictions is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						kpNatalHoroscopePredictions.setChartStatus(false);
					}
					kpNatalHoroscopePredictions.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(kpNatalHoroscopePredictions);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void kpHoraryHoroscopePredictionsScheduler() {
		try {
			List<KPHoraryHoroscopePredictions> kpHoraryHoroscopePredictionsList = getAllRepo.getAllKPHoraryHoroscopePredictions();

			for (KPHoraryHoroscopePredictions kpHoraryHoroscopePredictions : kpHoraryHoroscopePredictionsList) {
				if (!kpHoraryHoroscopePredictions.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), kpHoraryHoroscopePredictions.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(kpHoraryHoroscopePredictions.getOwnerEmail(), "",
								" Your Subscription for DARI KPHoraryHoroscopePredictions is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(kpHoraryHoroscopePredictions.getOwnerNumber(), "", " Your Subscription for DARI kpHoraryHoroscopePredictions is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(kpHoraryHoroscopePredictions.getOwnerEmail(), "",
								" Your Subscription for DARI KPHoraryHoroscopePredictions is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(kpHoraryHoroscopePredictions.getOwnerNumber(), "",
						  " Your Subscription for DARI kpHoraryHoroscopePredictions is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						kpHoraryHoroscopePredictions.setChartStatus(false);
					}
					kpHoraryHoroscopePredictions.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(kpHoraryHoroscopePredictions);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void kpEphemerisScheduler() {
		try {
			List<KPEphemeris> kpEphemerisList = getAllRepo.getAllKPEphemeris();

			for (KPEphemeris kpEphemeris : kpEphemerisList) {
				if (!kpEphemeris.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), kpEphemeris.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(kpEphemeris.getOwnerEmail(), "",
								" Your Subscription for DARI KPEphemeris is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(kpEphemeris.getOwnerNumber(), "", " Your Subscription for DARI kpEphemeris is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(kpEphemeris.getOwnerEmail(), "",
								" Your Subscription for DARI KPEphemeris is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(kpEphemeris.getOwnerNumber(), "",
						  " Your Subscription for DARI kpEphemeris is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						kpEphemeris.setChartStatus(false);
					}
					kpEphemeris.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(kpEphemeris);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void kpMuhurtaScheduler() {
		try {
			List<KPMuhurta> kpMuhurtaList = getAllRepo.getAllKPMuhurta();

			for (KPMuhurta kpMuhurta : kpMuhurtaList) {
				if (!kpMuhurta.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), kpMuhurta.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(kpMuhurta.getOwnerEmail(), "",
								" Your Subscription for DARI KPMuhurta is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(kpMuhurta.getOwnerNumber(), "", " Your Subscription for DARI kpMuhurta is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(kpMuhurta.getOwnerEmail(), "",
								" Your Subscription for DARI KPMuhurta is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(kpMuhurta.getOwnerNumber(), "",
						  " Your Subscription for DARI kpMuhurta is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						kpMuhurta.setChartStatus(false);
					}
					kpMuhurta.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(kpMuhurta);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void kpHoroscopeMatchingScheduler() {
		try {
			List<KPHoroscopeMatching> kpHoroscopeMatchingList = getAllRepo.getAllKPHoroscopeMatching();

			for (KPHoroscopeMatching kpHoroscopeMatching : kpHoroscopeMatchingList) {
				if (!kpHoroscopeMatching.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), kpHoroscopeMatching.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(kpHoroscopeMatching.getOwnerEmail(), "",
								" Your Subscription for DARI KPHoroscopeMatching is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(kpHoroscopeMatching.getOwnerNumber(), "", " Your Subscription for DARI kpHoroscopeMatching is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(kpHoroscopeMatching.getOwnerEmail(), "",
								" Your Subscription for DARI KPHoroscopeMatching is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(kpHoroscopeMatching.getOwnerNumber(), "",
						  " Your Subscription for DARI kpHoroscopeMatching is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						kpHoroscopeMatching.setChartStatus(false);
					}
					kpHoroscopeMatching.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(kpHoroscopeMatching);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void vedicHoroscopeScheduler() {
		try {
			List<VedicHoroscope> vedicHoroscopeList = getAllRepo.getAllVedicHoroscope();

			for (VedicHoroscope vedicHoroscope : vedicHoroscopeList) {
				if (!vedicHoroscope.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), vedicHoroscope.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(vedicHoroscope.getOwnerEmail(), "",
								" Your Subscription for DARI VedicHoroscope is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(vedicHoroscope.getOwnerNumber(), "", " Your Subscription for DARI vedicHoroscope is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(vedicHoroscope.getOwnerEmail(), "",
								" Your Subscription for DARI VedicHoroscope is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(vedicHoroscope.getOwnerNumber(), "",
						  " Your Subscription for DARI vedicHoroscope is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						vedicHoroscope.setChartStatus(false);
					}
					vedicHoroscope.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(vedicHoroscope);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void vedicHoroscopePredictionsScheduler() {
		try {
			List<VedicHoroscopePredictions> vedicHoroscopePredictionseList = getAllRepo.getAllVedicHoroscopePredictions();

			for (VedicHoroscopePredictions vedicHoroscopePredictionse : vedicHoroscopePredictionseList) {
				if (!vedicHoroscopePredictionse.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), vedicHoroscopePredictionse.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(vedicHoroscopePredictionse.getOwnerEmail(), "",
								" Your Subscription for DARI VedicHoroscopePredictionse is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(vedicHoroscopePredictionse.getOwnerNumber(), "", " Your Subscription for DARI vedicHoroscopePredictionse is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(vedicHoroscopePredictionse.getOwnerEmail(), "",
								" Your Subscription for DARI VedicHoroscopePredictionse is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(vedicHoroscopePredictionse.getOwnerNumber(), "",
						  " Your Subscription for DARI vedicHoroscopePredictionse is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						vedicHoroscopePredictionse.setChartStatus(false);
					}
					vedicHoroscopePredictionse.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(vedicHoroscopePredictionse);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void vedicMuhurtaScheduler() {
		try {
			List<VedicMuhurta> vedicMuhurtaList = getAllRepo.getAllVedicMuhurta();

			for (VedicMuhurta vedicMuhurta : vedicMuhurtaList) {
				if (!vedicMuhurta.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), vedicMuhurta.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(vedicMuhurta.getOwnerEmail(), "",
								" Your Subscription for DARI VedicMuhurta is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(vedicMuhurta.getOwnerNumber(), "", " Your Subscription for DARI vedicMuhurta is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(vedicMuhurta.getOwnerEmail(), "",
								" Your Subscription for DARI VedicMuhurta is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(vedicMuhurta.getOwnerNumber(), "",
						  " Your Subscription for DARI vedicMuhurta is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						vedicMuhurta.setChartStatus(false);
					}
					vedicMuhurta.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(vedicMuhurta);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void vedicPanchangaScheduler() {
		try {
			List<VedicPanchanga> vedicPanchangaList = getAllRepo.getAllVedicPanchanga();

			for (VedicPanchanga vedicPanchanga : vedicPanchangaList) {
				if (!vedicPanchanga.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), vedicPanchanga.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(vedicPanchanga.getOwnerEmail(), "",
								" Your Subscription for DARI VedicPanchanga is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(vedicPanchanga.getOwnerNumber(), "", " Your Subscription for DARI vedicPanchanga is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(vedicPanchanga.getOwnerEmail(), "",
								" Your Subscription for DARI VedicPanchanga is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(vedicPanchanga.getOwnerNumber(), "",
						  " Your Subscription for DARI vedicPanchanga is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						vedicPanchanga.setChartStatus(false);
					}
					vedicPanchanga.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(vedicPanchanga);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void horoscopeMatchingScheduler() {
		try {
			List<HoroscopeMatching> horoscopeMatchingList = getAllRepo.getAllHoroscopeMatching();

			for (HoroscopeMatching horoscopeMatching : horoscopeMatchingList) {
				if (!horoscopeMatching.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), horoscopeMatching.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(horoscopeMatching.getOwnerEmail(), "",
								" Your Subscription for DARI HoroscopeMatching is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(horoscopeMatching.getOwnerNumber(), "", " Your Subscription for DARI horoscopeMatching is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(horoscopeMatching.getOwnerEmail(), "",
								" Your Subscription for DARI HoroscopeMatching is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(horoscopeMatching.getOwnerNumber(), "",
						  " Your Subscription for DARI horoscopeMatching is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						horoscopeMatching.setChartStatus(false);
					}
					horoscopeMatching.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(horoscopeMatching);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void mundaneAstrologyScheduler() {
		try {
			List<MundaneAstrology> mundaneAstrologyList = getAllRepo.getAllMundaneAstrology();

			for (MundaneAstrology mundaneAstrology : mundaneAstrologyList) {
				if (!mundaneAstrology.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), mundaneAstrology.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(mundaneAstrology.getOwnerEmail(), "",
								" Your Subscription for DARI MundaneAstrology is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(mundaneAstrology.getOwnerNumber(), "", " Your Subscription for DARI mundaneAstrology is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(mundaneAstrology.getOwnerEmail(), "",
								" Your Subscription for DARI MundaneAstrology is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(mundaneAstrology.getOwnerNumber(), "",
						  " Your Subscription for DARI mundaneAstrology is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						mundaneAstrology.setChartStatus(false);
					}
					mundaneAstrology.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(mundaneAstrology);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void varshphalScheduler() {
		try {
			List<Varshphal> varshphalList = getAllRepo.getAllVarshphal();

			for (Varshphal varshphal : varshphalList) {
				if (!varshphal.getSubscription_EndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), varshphal.getSubscription_EndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(varshphal.getOwnerEmail(), "",
								" Your Subscription for DARI Varshphal is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
					/*	sendSms.sendSMS(varshphal.getOwnerNumber(), "", " Your Subscription for DARI varshphal is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");*/
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(varshphal.getOwnerEmail(), "",
								" Your Subscription for DARI Varshphal is Ended. Please Subscribe to get Server Access ");
						/*
						  sendSms.sendSMS(varshphal.getOwnerNumber(), "",
						  " Your Subscription for DARI varshphal is Ended. Please Subscribe to get Server Access  "
						  );
						 */
					}
					if (l <= 0) {
						varshphal.setChartStatus(false);
					}
					varshphal.setSubscription_Days_Left((int) l);
					dariAstroCRUD.methodForUpdate(varshphal);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}
	
	
	
	
	
	private String getCurrentSystemDateAndTime() {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh.mm.ss aa");

		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		return reportDate;

	}

	private static long getScheduleDays(String strDate, String strDate2) {
		long l = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date date1 = null;
		Date date2 = null;
		if ((!strDate.equals("") || !strDate.isEmpty()) && (!strDate2.equals("") || !strDate2.isEmpty())) {
			try {
				date1 = sdf.parse(strDate);

				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);
				Date dt = cal.getTime();

				date2 = sdf.parse(strDate2);
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(date2);
				Date dt2 = cal1.getTime();

				l = (dt2.getTime() - dt.getTime()) / 86400000;
				return l;
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		return l;
	}


}



