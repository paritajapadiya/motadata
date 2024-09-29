package com.example.practical.service;

import com.example.practical.model.AuditLog;
import com.example.practical.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuditListener {
    @Autowired
    private AuditLogRepository auditLogRepository;

    @KafkaListener(topics = "audit-topic", groupId = "audit_group")
    public void listen(String message) {
        System.out.println("Received audit message: " + message);
        AuditLog auditLog = new AuditLog();
        auditLog.setMessage(message);
        auditLogRepository.save(auditLog);
    }
}
