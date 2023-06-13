package com.keycon.spring.scraping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keycon.spring.scraping.model.JobEntity;
import com.keycon.spring.scraping.model.json.Job;
import com.keycon.spring.scraping.model.json.Post;
import com.keycon.spring.scraping.model.xml.Item;
import com.keycon.spring.scraping.model.xml.Rss;
import com.keycon.spring.scraping.repository.JobRepository;
import com.keycon.spring.scraping.utils.Portal;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringReader;

import static com.keycon.spring.scraping.utils.Utils.readFileAsString;

@RestController
@RequestMapping("/sapi")
public class ScrapingBee {

    private static final String FREELANCERCAMP_URL = "https://www.freelancermap.de/feeds/projekte/de-deutschland.xml";

    @Autowired
    JobRepository jobRepository;

    @RequestMapping("/1")
    public void computerfutures() throws Exception {

        // Create request
        //Content content = Request.Get(URL).execute().returnContent();
        //System.out.println(content);

        String json = readFileAsString("src/main/resources/computerfutures/jobs.json");
        ObjectMapper mapper = new ObjectMapper();
        Post post = mapper.readValue(json, Post.class);

        post.getAll_post().forEach(xmlJob -> {
            String scraping_link = getScrapingLink(xmlJob);
            System.out.println(scraping_link);
            //Content desc = Request.Get(new URI(scraping_link)).execute().returnContent();
            String desc = "asd";

            if (jobRepository.findByLink(xmlJob.getLink()).size() == 0) {
                JobEntity jobEntity = new JobEntity();
                jobEntity.setTitle(xmlJob.getTitle());
                jobEntity.setLink(xmlJob.getLink());
                jobEntity.setDescription(xmlJob.getJob_description());
                jobEntity.setPortalId(Portal.CUMPUTERFUTURES.id);

                jobRepository.save(jobEntity);
            }

        });
    }

    @RequestMapping("/2")
    public void freelancermap() throws IOException, JAXBException {
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
                db_jobEntity.setPortalId(Portal.FRELANCERMAP.id);

                jobRepository.save(db_jobEntity);
            }
        });

    }

    @RequestMapping("/3")
    public void etengo() throws Exception {
        String json = readFileAsString("src/main/resources/etengo/jobs.json");
        ObjectMapper mapper = new ObjectMapper();
        Post post = mapper.readValue(json, Post.class);

        post.getAll_post().forEach(xmlJob -> {
            //String scraping_link = getScrapingLink(xmlJob);
            //System.out.println(scraping_link);
            //Content desc = Request.Get(new URI(scraping_link)).execute().returnContent();
            String desc = "asd";

            if (jobRepository.findByLink(xmlJob.getLink()).size() == 0) {
                JobEntity jobEntity = new JobEntity();
                jobEntity.setTitle(xmlJob.getTitle());
                jobEntity.setLink(xmlJob.getLink());
                jobEntity.setDescription(xmlJob.getJob_description());
                jobEntity.setPortalId(Portal.ETENGO.id);
                jobRepository.save(jobEntity);
            }
        });
    }

    @RequestMapping("/4")
    public void solcom() throws Exception {
        String json = readFileAsString("src/main/resources/solcom/jobs.json");
        ObjectMapper mapper = new ObjectMapper();
        Post post = mapper.readValue(json, Post.class);

        post.getAll_post().forEach(xmlJob -> {
            //String scraping_link = getScrapingLink(xmlJob);
            //System.out.println(scraping_link);
            //Content desc = Request.Get(new URI(scraping_link)).execute().returnContent();
            String desc = "asd";

            if (jobRepository.findByLink(xmlJob.getLink()).size() == 0) {
                JobEntity jobEntity = new JobEntity();
                jobEntity.setTitle(xmlJob.getTitle());
                jobEntity.setLink(xmlJob.getLink());
                jobEntity.setDescription(xmlJob.getJob_description());
                jobEntity.setPortalId(Portal.SOLCOM.id);
                jobRepository.save(jobEntity);
            }
        });
    }

    @RequestMapping("/5")
    public void gulp() throws Exception {
        String json = readFileAsString("src/main/resources/gulp/jobs.json");
        ObjectMapper mapper = new ObjectMapper();
        Post post = mapper.readValue(json, Post.class);

        post.getAll_post().forEach(xmlJob -> {
            //String scraping_link = getScrapingLink(xmlJob);
            //System.out.println(scraping_link);
            //Content desc = Request.Get(new URI(scraping_link)).execute().returnContent();
            String desc = "asd";

            if (jobRepository.findByLink(xmlJob.getLink()).size() == 0) {
                JobEntity jobEntity = new JobEntity();
                jobEntity.setTitle(xmlJob.getTitle());
                jobEntity.setLink(xmlJob.getLink());
                jobEntity.setDescription(xmlJob.getJob_description());
                jobEntity.setPortalId(Portal.GULP.id);
                jobRepository.save(jobEntity);
            }
        });
    }

    @RequestMapping("/6")
    public void freelancer() throws Exception {
        String json = readFileAsString("src/main/resources/freelancer/jobs.json");
        ObjectMapper mapper = new ObjectMapper();
        Post post = mapper.readValue(json, Post.class);

        post.getAll_post().forEach(xmlJob -> {
            //String scraping_link = getScrapingLink(xmlJob);
            //System.out.println(scraping_link);
            //Content desc = Request.Get(new URI(scraping_link)).execute().returnContent();
            String desc = "asd";

            if (jobRepository.findByLink(xmlJob.getLink()).size() == 0) {
                JobEntity jobEntity = new JobEntity();
                jobEntity.setTitle(xmlJob.getTitle());
                jobEntity.setLink(xmlJob.getLink());
                jobEntity.setDescription(xmlJob.getJob_description());
                jobEntity.setPortalId(Portal.FRELANCER.id);
                jobRepository.save(jobEntity);
            }
        });
    }
    private String getScrapingLink(Job job) {
        String scraping_link = "https://app.scrapingbee.com/api/v1/?api_key=7JWA4APXUMFSUCI1Q0O1SETH6OA6AJLWHJXK3RJ7RWWJB1XA08DW1JGCPFFBVTKVWKEJDFZPOTBMF3X3&" +
                "url=https%3A%2F%2Fwww.computerfutures.com%2Fde-de%2Fjob%2F" + job.getLink().replace("/de-de/job/", "").replace("/", "%2F") +
                "&wait=500&extract_rules=%7B%22job_description%22%3A%22body%20%3E%20section%20%3E%20article%20%3E%20div%20%3E%20section%22%7D";
        return scraping_link;
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
}
