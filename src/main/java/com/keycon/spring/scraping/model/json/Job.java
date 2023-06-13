package com.keycon.spring.scraping.model.json;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Job {
    String title;
    String location;
    String location_sub;
    String updated;
    String salery;
    String startdat;
    String link;
    String job_description;
}