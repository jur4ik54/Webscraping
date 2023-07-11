package com.keycon.spring.scraping.service;

import com.keycon.spring.scraping.model.JobEntity;
import com.keycon.spring.scraping.model.json.Job;
import com.keycon.spring.scraping.model.xml.Item;
import com.keycon.spring.scraping.model.xml.Rss;
import com.keycon.spring.scraping.repository.JobRepository;
import com.keycon.spring.scraping.utils.Portal;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PortalService {

    @Autowired
    JobRepository jobRepository;
    Logger logger = LoggerFactory.getLogger(PortalService.class);

    public void html(){
        //fetch each description as html
    }
    public void all() {
        final long startTime = System.nanoTime();
        freelancermap();
        computerfutures();
        visitLinkReadDescriptionForComputerfutures();
        etengo();
        visitLinkReadDescriptionForEtengo();
        solcom();
        gulp();
        visitLinkReadDescriptionForGulp();
        final long duration = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
        System.out.println("All pages succesfully fetched seconds: "+ duration);
    }
    public void allTitle() {
        freelancermap();
        computerfutures();
        etengo();
        solcom();
        gulp();
        agex();
        avantguardExperts();
        bosshardpartner();
        projektBroker();
        comSoftware();
        cegeka();
        darwinrecruitment();
        logger.info("done");
    }

    public void avantguardExperts() {
        final String AVANTGUARD_URL = "https://www.avantgarde-experts.de/de/jobangebote/?jobCategory=16,23&jobEmploymentType=30&remote=98";

        WebDriver driver = new ChromeDriver(getOptions(false));
        logger.info("start avantguard");
        try {
            driver.get(AVANTGUARD_URL);
            Thread.sleep(2000);

            driver.findElement(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();
            WebElement jobs = driver.findElement(By.className("job-items"));
            List<WebElement> itemList = driver.findElements(By.className("job-items__item-wrapper"));
            for(WebElement item:itemList){
                String link  = item.findElement(By.tagName("a")).getAttribute("href");
                String title = item.findElement(By.tagName("a")).getAttribute("title");

                if (jobRepository.findByLink(link).size() == 0) {
                    JobEntity jobEntity = new JobEntity();
                    jobEntity.setTitle(title);
                    jobEntity.setLink(link);
                    jobEntity.setPortalId(Portal.AVANTGUARD_EXPERTS.name());
                    jobRepository.save(jobEntity);
                }
                System.out.println(title+" - "+link);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            driver.quit();
            logger.info("avantguard close");
        }
    }

    public void bosshardpartner(){
        final String URL="https://www.bosshardpartner.ch/de/jobs.html";

        WebDriver driver = new ChromeDriver(getOptions(false));
        logger.info("start");
        try {
            driver.get(URL);
            Thread.sleep(2000);

            List<WebElement> itemList = driver.findElements(By.className("joblist-entry-container"));
            for(WebElement item:itemList){
                String link  = item.findElement(By.tagName("a")).getAttribute("href");
                String title = item.findElement(By.tagName("h2")).getText();

                if (jobRepository.findByLink(link).size() == 0) {
                    JobEntity jobEntity = new JobEntity();
                    jobEntity.setTitle(title);
                    jobEntity.setLink(link);
                    jobEntity.setPortalId(Portal.BOSSHARDPARTNER.name());
                    jobRepository.save(jobEntity);
                }
                System.out.println(title+" - "+link);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            driver.quit();
            logger.info("close");
        }

    }
    public void projektBroker(){
        final String URL="https://jobboard.online/projektbroker30/";

        WebDriver driver = new ChromeDriver(getOptions(false));
        logger.info("start");
        try {
            driver.get(URL);
            Thread.sleep(2000);

            List<WebElement> itemList = driver.findElements(By.cssSelector("div.card.d-show"));
            for(WebElement item:itemList){
                String link = URL+item.getAttribute("data-url");
                String title  = item.findElement(By.className("card-title")).getText();
                String desc = item.findElement(By.className("jobdescription")).findElement(By.tagName("span")).getText();

                if (jobRepository.findByLink(link).size() == 0) {
                    JobEntity jobEntity = new JobEntity();
                    jobEntity.setTitle(title);
                    jobEntity.setLink(link);
                    jobEntity.setDescription(desc);
                    jobEntity.setPortalId(Portal.PROJEKTBROKER.name());
                    jobRepository.save(jobEntity);
                }
                System.out.println(title+" - "+link + " - "+desc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            driver.quit();
            logger.info("end");
        }

    }
    public void cegeka(){
        final String URL="https://jobs.cegeka.de/vacancy/";

        WebDriver driver = new ChromeDriver(getOptions(false));
        logger.info("start");
        try {
            driver.get(URL);
            Thread.sleep(2000);

            List<WebElement> itemList = driver.findElements(By.cssSelector("div.list-item"));
            for(WebElement item:itemList){
                String link  = item.findElement(By.tagName("h2")).findElement(By.tagName("a")).getAttribute("href");
                String title = item.findElement(By.tagName("h2")).findElement(By.tagName("a")).getText();

                if (jobRepository.findByLink(link).size() == 0) {
                    JobEntity jobEntity = new JobEntity();
                    jobEntity.setTitle(title);
                    jobEntity.setLink(link);
                    jobEntity.setPortalId(Portal.CEGEKA.name());
                    jobRepository.save(jobEntity);
                }
                System.out.println(title+" - "+link);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            driver.quit();
            logger.info("end");
        }

    }
    public void comSoftware(){
        final String URL="https://jobs.com-software.de/de";

        WebDriver driver = new ChromeDriver(getOptions(false));
        logger.info("start");
        try {
            driver.get(URL);
            Thread.sleep(2000);

            List<WebElement> itemList = driver.findElements(By.cssSelector("div.col-xs-12.col-sm-12-col-md-12.col-lg-12"));
            for(WebElement item:itemList){
                String link  = item.findElement(By.className("portlet-title")).findElement(By.tagName("a")).getAttribute("href");
                String title = item.findElement(By.className("portlet-title")).findElement(By.tagName("a")).getAttribute("title");

                if (jobRepository.findByLink(link).size() == 0) {
                    JobEntity jobEntity = new JobEntity();
                    jobEntity.setTitle(title);
                    jobEntity.setLink(link);
                    jobEntity.setPortalId(Portal.COMSOFTWARE.name());
                    jobRepository.save(jobEntity);
                }
                System.out.println(title+" - "+link);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            driver.quit();
            logger.info("end");
        }

    }

    public void darwinrecruitment(){
        final String URL="https://www.darwinrecruitment.de/search-jobs/?_remote_selection=fully-remote";

        WebDriver driver = new ChromeDriver(getOptions(false));
        logger.info("start");
        try {
            driver.get(URL);
            Thread.sleep(2000);

            Alert alert = driver.switchTo().alert();
            alert.accept();
            Thread.sleep(2000);
            Alert salert = driver.switchTo().alert();
            salert.accept();

            String page = driver.findElement(By.cssSelector("a.facetwp-page.last")).getAttribute("data-page");
            int pages = Integer.parseInt(page);
            System.out.println("page in total"+ pages);

            for(int i=1;i<=pages; i++) {
                List<WebElement> itemList = driver.findElements(By.cssSelector("ul.job_listings a"));
                for (WebElement item : itemList) {
                    String link = item.getAttribute("href");
                    String title = item.findElement(By.className("darwin_job_search_page_job_title")).getText();
                    System.out.println(title + " - " + link);
                    if (jobRepository.findByLink(link).size() == 0) {
                        JobEntity jobEntity = new JobEntity();
                        jobEntity.setTitle(title);
                        jobEntity.setLink(link);
                        jobEntity.setPortalId(Portal.DARWINRECRUITMENT.name());
                        jobRepository.save(jobEntity);
                    }
                }
                logger.info("this was page "+i + " from "+ page);

                if(i<pages){
                    WebElement element=driver.findElement(By.cssSelector("a.facetwp-page.next"));
                    JavascriptExecutor executor = (JavascriptExecutor)driver;
                    executor.executeScript("arguments[0].click();", element);
                    Thread.sleep(1000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            driver.quit();
            logger.info("end");
        }

    }
    public void freelancermap() {
        final String FREELANCERCAMP_URL = "https://www.freelancermap.de/feeds/projekte/de-deutschland.xml";
        logger.info("start freelancermap");
        try {
            Content content = Request.Get(FREELANCERCAMP_URL).execute().returnContent();
            JAXBContext jaxbContext = JAXBContext.newInstance(Rss.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Rss rss = (Rss) unmarshaller.unmarshal(new StringReader(content.asString()));

            rss.channel.item.forEach(i -> {

                Job job = convertItemToJob(i);

                if (jobRepository.findByLink(job.getLink()).size() == 0) {
                    JobEntity db_jobEntity = new JobEntity();
                    db_jobEntity.setTitle(job.getTitle());
                    db_jobEntity.setLink(job.getLink());
                    db_jobEntity.setDescription(job.getJob_description());
                    db_jobEntity.setPortalId(Portal.FRELANCERMAP.name());

                    jobRepository.save(db_jobEntity);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("End freelancermap");
    }
    public void computerfutures() {

        WebDriver driver = new ChromeDriver(getOptions(true));
        logger.info("start computerfutures");
        try {
            driver.get("https://www.computerfutures.com/de-de/stellensuche/?country=Deutschland&searchRadius=50km&type=Freelance");
            Thread.sleep(2000);

            driver.findElement(By.id("onetrust-accept-btn-handler")).click();
            WebElement jobs = driver.findElement(By.id("jobsPages"));
            List<WebElement> itemList = jobs.findElements(By.className("pagination__item")); //including prev,next

            int lastPage = itemList.size()-2;
            logger.info("Pages in total: "+lastPage);

            for (int i = 1; i <= lastPage; i++) {
                jobs.findElement(By.className("next")).click();
                Thread.sleep(2000);
                WebElement nextList = driver.findElement(By.id("jobListing"));

                List<WebElement> elements = nextList.findElements(By.className("job-search__item"));
                elements.forEach(e -> {
                    String title = e.findElement(By.className("job-search__title")).getText();
                    String link = e.findElement(By.tagName("a")).getAttribute("href");
                    String location = e.findElement(By.className("job-search__location")).getText();
                    String sub_location = e.findElement(By.className("job-search__location-sub")).getText();
                    //String updated = e.findElement(By.className("job-search__updated")).getText();
                    String startdate = e.findElement(By.className("job-search__details-title--date")).getText();

                    if (jobRepository.findByLink(link).size() == 0) {
                        JobEntity jobEntity = new JobEntity();
                        jobEntity.setTitle(title);
                        jobEntity.setLink(link);
                        jobEntity.setDescription("");
                        jobEntity.setPortalId(Portal.CUMPUTERFUTURES.name());
                        jobRepository.save(jobEntity);
                    }

                    System.out.println(title + " - " + link + " - " + location + " - " + sub_location + " - " + startdate);
                });

                System.out.println("this was page " + i + " from " + lastPage);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
            logger.info("end computerfutures");
        }


    }
    public void etengo() {

        WebDriver driver = new ChromeDriver(getOptions(true));
        logger.info("start etengo");
        try {
            driver.get("https://www.etengo.de/it-projektsuche/");
            Thread.sleep(2000);
            driver.findElement(By.className("btn-accept")).click();

            String counts = driver.findElement(By.className("projects-count")).getText();
            System.out.println(counts);
            int steps = Integer.parseInt(counts) / 12;
            for (int i = 1; i < steps; i++) {
                Thread.sleep(1000);
                driver.findElement(By.className("loadMore")).findElement(By.tagName("button")).sendKeys(Keys.ENTER);
            }

            List<WebElement> elements = driver.findElements(By.className("card-project"));
            elements.forEach(e -> {
                String title = e.findElement(By.tagName("h3")).getText();
                String link = e.findElement(By.tagName("a")).getAttribute("href");
                System.out.println(title + " - " + link);

                if (jobRepository.findByLink(link).size() == 0) {
                    JobEntity job = new JobEntity();
                    job.setTitle(title);
                    job.setLink(link);
                    job.setDescription("");
                    job.setPortalId(Portal.ETENGO.name());
                    jobRepository.save(job);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        logger.info("end etengo");

    }
    public void solcom() {

        WebDriver driver = new ChromeDriver(getOptions(true));
        logger.info("start solcom");
        try {
            String weblink = "https://www.solcom.de/de/projektportal/projektangebote?--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Blocation%5D%5BALL%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Blocation%5D%5BRemote%5D=Remote&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Blocation%5D%5BA%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Blocation%5D%5BCH%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Blocation%5D%5BDE%5D=DE&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Blocation%5D%5BInternational%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Bzip%5D%5B0%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Bzip%5D%5B1%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Bzip%5D%5B2%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Bzip%5D%5B3%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Bzip%5D%5B4%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Bzip%5D%5B5%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Bzip%5D%5B6%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Bzip%5D%5B7%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Bzip%5D%5B8%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Bzip%5D%5B9%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5BsearchParameter%5D=&--contenance_solcom_portal_project-index%5BsearchArguments%5D%5Bbusiness%5D=0&--contenance_solcom_portal_project-index%5BitemsPerPage%5D=100&--contenance_solcom_portal_project-index%5B%40package%5D=Contenance.Solcom&--contenance_solcom_portal_project-index%5B%40controller%5D=Project&--contenance_solcom_portal_project-index%5B%40action%5D=index&--contenance_solcom_portal_project-index%5B%40format%5D=html&--contenance_solcom_portal_project-index%5B--neos-fluidadaptor-viewhelpers-widget-paginateviewhelper%5D%5B%40package%5D=&--contenance_solcom_portal_project-index%5B--neos-fluidadaptor-viewhelpers-widget-paginateviewhelper%5D%5B%40subpackage%5D=&--contenance_solcom_portal_project-index%5B--neos-fluidadaptor-viewhelpers-widget-paginateviewhelper%5D%5B%40controller%5D=&--contenance_solcom_portal_project-index%5B--neos-fluidadaptor-viewhelpers-widget-paginateviewhelper%5D%5B%40action%5D=index&--contenance_solcom_portal_project-index%5B--neos-fluidadaptor-viewhelpers-widget-paginateviewhelper%5D%5B%40format%5D=html&--contenance_solcom_portal_project-index%5B--neos-fluidadaptor-viewhelpers-widget-paginateviewhelper%5D%5BcurrentPage%5D=";
            driver.get(weblink + "1");
            Thread.sleep(2000);
            driver.findElement(By.className("acceptall")).click();
            Thread.sleep(2000);
            //driver.findElement(By.className("selectric")).findElement(By.className("label")).click();
            //driver.findElement(By.className("selectric-items")).findElement(By.className("last")).click();
            List<WebElement> list = driver.findElement(By.className("neos-fluid-widget-paginator-menu")).findElements(By.tagName("li"));
            String lastPage = list.get(list.size() - 3).getText();
            int lastpage = Integer.parseInt(lastPage);
            System.out.println("lastPage " + lastPage);
            List<WebElement> elements = driver.findElements(By.className("contenance-solcom-portal-project-item"));
            solcom_readAndSaveElements(elements);
            for (int i = 2; i <= lastpage; i++) {
                driver.get(weblink + i);
                Thread.sleep(2000);
                List<WebElement> pageElements = driver.findElements(By.className("contenance-solcom-portal-project-item"));
                solcom_readAndSaveElements(pageElements);
                System.out.println("this was page " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        logger.info("end solcom");
    }
    public void gulp() {
        String weblink = "https://www.gulp.de/gulp2/g/projekte?order=DATE_DESC&region=D0&region=D1&region=D2&region=D3&region=D4&region=D5&region=D6&region=D7&region=D8&region=D9&page=8";
        WebDriver driver = new ChromeDriver(getOptions(true));
        logger.info("start gulp");
        try {
            driver.get(weblink);
            Thread.sleep(2000);
            driver.findElement(By.id("onetrust-accept-btn-handler")).click();
            Thread.sleep(2000);
            List<WebElement> webElements = driver.findElement(By.className("pagination")).findElements(By.tagName("li"));
            WebElement nextLi = webElements.get(webElements.size() - 2);
            while (!nextLi.getAttribute("class").contains("disabled")) {
                //driver.get(weblink+"&page="+i);

                Thread.sleep(1000);
                List<WebElement> elements = driver.findElements(By.tagName("app-project-view"));

                elements.forEach(e -> {
                    String title = e.findElement(By.tagName("h1")).findElement(By.tagName("a")).getText();
                    String link = e.findElement(By.tagName("h1")).findElement(By.tagName("a")).getAttribute("href");

                    if (jobRepository.findByLink(link).size() == 0) {
                        JobEntity dbJobEntity = new JobEntity();
                        dbJobEntity.setTitle(title);
                        dbJobEntity.setLink(link);
                        dbJobEntity.setPortalId(Portal.GULP.name());
                        jobRepository.save(dbJobEntity);
                    }
                    System.out.println(title + " - " + link);
                });
                nextLi.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        logger.info("end gulp");
    }
    public void agex(){
        String weblink = "https://agex-it.de/jobs-projekte/#";
        WebDriver driver = new ChromeDriver(getOptions(false));
        logger.info("start agex");
        try {
            driver.get(weblink);
            Thread.sleep(2000);
            driver.findElement(By.className("cookie-box")).findElement(By.className("_brlbs-btn-accept-all")).click();
            Thread.sleep(2000);

            String link="";
            String title="";
            List<WebElement> webElements = driver.findElements(By.className("list-item"));

            for(WebElement e:webElements){
                if(e.getAttribute("data-specialty").equals("Freiberuflich")){
                    title = e.findElement(By.className("ax-search__results")).findElements(By.tagName("a")).get(1).getText().split("\n")[0];
                    link = e.findElement(By.tagName("a")).getAttribute("href");
                    if (jobRepository.findByLink(link).size() == 0) {
                        JobEntity dbJobEntity = new JobEntity();
                        dbJobEntity.setTitle(title);
                        dbJobEntity.setLink(link);
                        dbJobEntity.setPortalId(Portal.AGEX.name());
                        jobRepository.save(dbJobEntity);
                    }
                    System.out.println(title+" - "+link);
                }
            }

        }catch (Exception e){
        e.printStackTrace();
        }finally {
            driver.quit();
            logger.info("end agex");
        }
    }
    private void solcom_readAndSaveElements(List<WebElement> elements) {
        elements.forEach(e -> {
            String title = e.findElement(By.className("project-header")).findElement(By.tagName("a")).getAttribute("data-projectname");
            String link = e.findElement(By.className("project-header")).findElement(By.tagName("a")).getAttribute("href");
            String desc = e.findElement(By.className("projekt-body")).findElement(By.className("projekt-desc")).getText();
            //String updated = e.findElement(By.className("project-infos")).findElement(By.className("clock-icon")).getText();
            String startdate = e.findElement(By.className("project-infos")).findElement(By.className("calendar-icon")).getText();
            String location = e.findElement(By.className("project-infos")).findElement(By.className("pin-icon")).getText();
            if (jobRepository.findByLink(link).size() == 0) {
                JobEntity dbJobEntity = new JobEntity();
                dbJobEntity.setTitle(title);
                dbJobEntity.setLink(link);
                dbJobEntity.setDescription(desc);
                dbJobEntity.setStartdate(startdate);
                dbJobEntity.setLocation(location);
                dbJobEntity.setPortalId(Portal.SOLCOM.name());
                jobRepository.save(dbJobEntity);
                System.out.println(title + " - " + link + " - " + desc + " - " + startdate + " - " + location);
            }
        });
    }
    public void visitLinkReadDescriptionForComputerfutures() {
        logger.info("start desc computerfutures");
        List<JobEntity> jobsByPortalId = jobRepository.findAllByPortalId(Portal.CUMPUTERFUTURES.name());
        jobsByPortalId.forEach(j -> {
            JobEntity up_jobEntity = jobRepository.findOneByLink(j.getLink());

            if (up_jobEntity.getDescription().equals("")) {
                System.out.println(j.getLink());
                WebDriver descDriver = new ChromeDriver(getOptions(true));
                descDriver.get(j.getLink());
                try {
                    Thread.sleep(2000);
                    descDriver.findElement(By.id("onetrust-accept-btn-handler")).click();
                    String desc = descDriver.findElement(By.className("main-content")).getText();
                    System.out.println(desc);
                    up_jobEntity.setDescription(desc);
                    jobRepository.save(up_jobEntity);
                } catch (Exception e) {
                    System.out.println("maybe some element couldend be found or webstruckture has changed");
                    e.printStackTrace();
                }

                descDriver.quit();
            } else {
                System.out.println(j.getLink() + " has allready a description.");
            }


        });

        logger.info("end desc computerfutures");
    }
    public void visitLinkReadDescriptionForEtengo() {
        logger.info("start desc etengo");
        WebDriver descDriver = new ChromeDriver(getOptions(true));
        try {
            List<JobEntity> jobsByPortalId = jobRepository.findAllByPortalId(Portal.ETENGO.name());
            jobsByPortalId.forEach(j -> {

                if (j.getDescription() == null || "".equals(j.getDescription())) {
                    descDriver.get(j.getLink());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //descDriver.findElement(By.id("onetrust-accept-btn-handler")).click();
                    List<WebElement> elements = descDriver.findElement(By.tagName("article")).findElements(By.tagName("li"));
                    System.out.println(j.getLink());
                    elements.forEach(e -> {
                        System.out.println(" li tag: " + e.getText());
                    });
                    //up_job.setDescription(desc);
                    //jobRepository.save(up_job);
                    //System.out.println(desc);

                } else {
                    System.out.println(j.getLink() + " has allready a description.");
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            descDriver.quit();
        }

        logger.info("end desc etengo");
    }
    public void visitLinkReadDescriptionForGulp() {
        logger.info("start desc gulp");
        WebDriver descDriver = new ChromeDriver(getOptions(true));
        try {
            List<JobEntity> jobsByPortalId = jobRepository.findAllByPortalId(Portal.GULP.name());
            for (JobEntity j : jobsByPortalId) {
                try {
                    if (j.getDescription() == null || "".equals(j.getDescription())) {

                        System.out.println("Link.: https://www.gulp.de" + j.getLink());
                        descDriver.get("https://www.gulp.de" + j.getLink());
                        Thread.sleep(2000);
                        //descDriver.findElement(By.id("onetrust-accept-btn-handler")).click();
                        String desc = descDriver.findElement(By.tagName("app-display-readonly-value")).findElement(By.className("form-value")).findElement(By.tagName("span")).getText();
                        List<WebElement> li = descDriver.findElement(By.tagName("app-icon-info-list")).findElements(By.tagName("li"));
                        String startDate = li.get(1).findElement(By.className("font-weight-semibold")).getText();
                        String dauer = li.get(2).findElement(By.className("font-weight-semibold")).getText();

                        System.out.println(desc + " - " + startDate);
                        //up_job.setDescription(desc);
                        jobRepository.save(j);
                        //System.out.println(desc);

                    } else {
                        System.out.println(j.getLink() + " has allready a description.");
                    }
                } catch (RuntimeException e) {
                    System.out.println("webelement changed or maybi link disposed");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            descDriver.quit();
        }
        logger.info("end desc gulp");
    }
    private Job convertItemToJob(Item i) {
        Job job = new Job();
        job.setTitle(i.title);
        job.setLink(i.link);
        job.setJob_description(i.description);
        job.setLocation(i.pubPlace);
        //job.setStartdat(i.pubDate.toString());
        job.setLocation_sub(i.pubIso);
        job.setSalery(i.encoded);
        job.setUpdated(i.guid);
        return job;
    }
    private ChromeOptions getOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        return headless ? options.addArguments("--headless=new") : options;
    }
}
