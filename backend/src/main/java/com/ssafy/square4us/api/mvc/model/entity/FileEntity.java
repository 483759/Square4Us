package com.ssafy.square4us.api.mvc.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "file")
public class FileEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @OneToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_origin_name")
    private String fileOriginName;

    @Column(name = "content_type")
    private String contentType;

    @Builder
    public FileEntity(Member member, Article article, Meeting meeting, String fileName, String filePath, String fileOriginName, String contentType) {
        this.member = member;
        this.article = article;
        this.meeting = meeting;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.contentType = contentType;
    }
}
