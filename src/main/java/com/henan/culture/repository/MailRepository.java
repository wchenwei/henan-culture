package com.henan.culture.repository;

import com.henan.culture.domain.entity.mail.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailRepository extends JpaRepository<Mail, Integer> {

    List<Mail> findByOldMailFalse();

}
