package pramati.wc.startup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import pramati.wc.datatypes.MonthAndLinkDatatype;
import pramati.wc.processor.WorkerForMonths;
import pramati.wc.startup.mailCrawler.BasicStartup;
import pramati.wc.utils.URLHelper;
import pramati.wc.utils.WCEnvironment;
import pramati.wc.utils.WCFileHandler;

/**
 * this class gives the web crawler implementation which can download mails of particular 
 * year from the URL provided
 * 
 * @author himanshuk
 *
 */
public class WCStartup extends BasicStartup{
	private static final Logger log=Logger.getLogger(WCStartup.class);

	@Override
	protected void runWebCrawler(String[] args) throws Exception {
		WCStartupManager manager=new WCStartupManager();
		manager.runWebCrawler(args);
	}
	

	/**
	 * manager will manage all kind of functioning of web Crawler
	 *  1) Perform actual execution with help of WCstartupHelper
	 *  1) deciding the multi-threaded behavior of crawler (create executor framework)
	 *  2) create and start the logging behavior
	 *  
	 * @author himanshuk
	 *
	 */
	private class WCStartupManager extends WCstartupHelper {
		ExecutorService executor;
		@Override
		public void runWebCrawler(String[] args) throws Exception{
			int noOfThreads=WCEnvironment.getInstance().getNoOfThreadsForWebCrawler();
			log.debug("No of Threads: "+noOfThreads);
			executor=Executors.newFixedThreadPool(noOfThreads);
			super.runWebCrawler(args);
		}
		
		protected void prepareProcess() throws Exception {
			super.prepareProcess();// after this call "list of month url" is already gets prepared
			if(mnthAndLink.size()>0)
			this.createBasicDirStrctr();
			
			Iterator<MonthAndLinkDatatype> requests=this.mnthAndLink.iterator();
			while(requests.hasNext()){
				MonthAndLinkDatatype singleRqst=requests.next();
				WorkerForMonths requestProcessor=null;
				
				try {
					requestProcessor = new WorkerForMonths(
							new URL(url, singleRqst.getHyprlynk()),
							singleRqst.getMnthYear(),yrNeedsToBeInspctd);
				} catch (MalformedURLException e) {
					log.error("PROBLEM_IN_URL: ", e);
				}


				executor.execute(requestProcessor);
				
			}
		}

		private void createBasicDirStrctr() throws Exception {
			WCFileHandler.getInstance().createDir("web_crawler/downloads/"+"Year_"+yrNeedsToBeInspctd);
		}
		
	}
	
}
