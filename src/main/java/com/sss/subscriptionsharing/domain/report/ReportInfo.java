package com.sss.subscriptionsharing.domain.report;

import com.sss.subscriptionsharing.domain.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ReportInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_info_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Reason reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static ReportInfo create(User user, Reason reason, Report report) {
        ReportInfo reportInfo = new ReportInfo();
        reportInfo.user = user;
        reportInfo.reason = reason;
        reportInfo.report = report;

        return reportInfo;
    }
}
