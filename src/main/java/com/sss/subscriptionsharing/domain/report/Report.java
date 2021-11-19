package com.sss.subscriptionsharing.domain.report;

import com.sss.subscriptionsharing.domain.Comment;
import com.sss.subscriptionsharing.domain.Post;
import com.sss.subscriptionsharing.domain.user.User;
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
    private Comment comment;

    public static Report createOfPost(Post post, User user, Reason reason) {
        Report report = new Report();
        report.post = post;

        ReportInfo reportInfo = ReportInfo.create(user, reason, report);
        report.infos.add(reportInfo);

        return report;
    }

    public static Report createOfComment(Comment comment, User user, Reason reason) {
        Report report = new Report();
        report.comment = comment;

        ReportInfo reportInfo = ReportInfo.create(user, reason, report);
        report.infos.add(reportInfo);

        return report;
    }

    public void addInfo(User user, Reason reason) {
        ReportInfo reportInfo = ReportInfo.create(user, reason, this);
        this.infos.add(reportInfo);
    }
}
