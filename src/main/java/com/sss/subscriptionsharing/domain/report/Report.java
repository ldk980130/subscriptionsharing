package com.sss.subscriptionsharing.domain.report;

import com.sss.subscriptionsharing.domain.Post;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @OneToMany(mappedBy = "report")
    private List<ReportInfo> infos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Post comment;
}
