package com.examly.springapp.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.examly.springapp.model.Notice;
import com.examly.springapp.repository.NoticeRepository;

/** Adds sample data when the table is empty (provided; not part of the task). */
@Component
public class DataLoader implements CommandLineRunner {

    private final NoticeRepository noticeRepository;

    public DataLoader(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public void run(String... args) {
        if (noticeRepository.count() > 0) {
            return;
        }
        noticeRepository.save(create("Semester Exam Schedule", "End semester exams start on 1st December.", "Exam Cell"));
        noticeRepository.save(create("Library Timings Extended", "The library stays open until 9 PM this month.", "Librarian"));
        noticeRepository.save(create("Sports Day Registration", "Register for Sports Day events by Friday.", "Physical Education"));
    }

    private Notice create(String title, String message, String postedBy) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setMessage(message);
        notice.setPostedBy(postedBy);
        return notice;
    }
}
