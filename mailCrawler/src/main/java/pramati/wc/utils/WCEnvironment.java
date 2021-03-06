package pramati.wc.utils;

/**
 * singleton class
 * 
 * purpose:- Act as interface between environment and web crawler JVM
 * by reading all the property files for Web Crawler
 * @author himanshuk
 *
 */
public class WCEnvironment {
	private static WCEnvironment instance;
	private static final String WC_NUMBER_OF_THREADS="pramati.wc.numberOfThreadsConfigured";
	
	private WCEnvironment(){
	}
	
	/**
	 * 
	 * @return
	 */
	public static WCEnvironment getInstance(){
		if(instance!=null)
			return instance;
		else
			return instance=new WCEnvironment();
	}

	public int getNoOfThreadsForWebCrawler() {
		// TODO add logic for retrieving value from property file
		return 8;
	}
	
	public String getCrawlerImplementation(){
		// TODO add logic for retrieving value from property file
		return "pramati.wc.startup.WCStartup";
	}
	
	public boolean isRunningInRecoveryMode() {
		return true;
	}

	public String getYearTagNameBegStrng() {
		return "Year";
	}

	public String getEndingTagForAtableEnd() {
		return "</table>";
	}

	public String getMsgListTagNameForMonth() {
		return "msglist\">";
	}

	public String getRawMsgTxtTag() {
		return "class=\"raw\"";
	}

	public String getTagForPagination() {
		return "class=\"pages\"";
	}

	public String getEndingTagForAthEnd() {
		return "</th>";
	}

	public int getNoOfMsgDownloaderThread() {
		// TODO Auto-generated method stub
		return 10;
	}
	
	

}
