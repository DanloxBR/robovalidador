package com.bringto.report;

import com.bringto.model.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvReportGenerator {

    public static void generate(List<Person> valid,
                                List<Person> invalid,
                                String fileName) throws IOException {

        StringBuilder csv = new StringBuilder();
        csv.append("status,nome,email,telefone,motivo\n");

        for (Person p : valid) {
            csv.append("VALIDO,")
                    .append(p.getName()).append(",")
                    .append(p.getEmail()).append(",")
                    .append(p.getPhone()).append(",-\n");
        }

        for (Person p : invalid) {
            csv.append("INVALIDO,")
                    .append(p.getName()).append(",")
                    .append(p.getEmail()).append(",")
                    .append(p.getPhone()).append(",")
                    .append(p.getErrorReason()).append("\n");
        }

        Files.write(Paths.get(fileName), csv.toString().getBytes());
    }
}
