package com.keycon.spring.scraping.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Jobs")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class JobEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "portalId")
  private String portalId;

  @Column(name = "title")
  private String title;

  @Column(name = "link")
  private String link;

  @Column(name = "location")
  private String location;

  @Column(name = "startdate")
  private String startdate;

  @Column(name="description" , length = 65535, columnDefinition="TEXT")
  private String description;

}
