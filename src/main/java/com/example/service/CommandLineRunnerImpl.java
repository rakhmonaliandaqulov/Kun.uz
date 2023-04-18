package com.example.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("App started");
    }
}
