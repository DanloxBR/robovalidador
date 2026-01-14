package com.bringto;

import com.bringto.model.Person;
import com.bringto.report.CsvReportGenerator;
import com.bringto.validator.DataValidator;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        URL resource = Main.class.getClassLoader().getResource("dados.txt");
        if (resource == null) {
            throw new RuntimeException("Arquivo dados.txt não encontrado");
        }

        List<String> lines = Files.readAllLines(Paths.get(resource.toURI()));

        List<Person> valid = new ArrayList<>();
        List<Person> invalid = new ArrayList<>();

        for (String line : lines) {
            String[] data = line.split(";");

            Person person = new Person(data[0], data[1], data[2]);

            boolean emailValid = DataValidator.isValidEmail(person.getEmail());
            boolean phoneValid = DataValidator.isValidPhone(person.getPhone());

            if (emailValid && phoneValid) {
                valid.add(person);
            } else {
                if (!emailValid && !phoneValid) {
                    person.setErrorReason("Email e telefone inválidos");
                } else if (!emailValid) {
                    person.setErrorReason("Email inválido");
                } else {
                    person.setErrorReason("Telefone inválido");
                }
                invalid.add(person);
            }
        }

        CsvReportGenerator.generate(valid, invalid, "relatorio.csv");

        System.out.println("Relatório CSV gerado com sucesso!");
    }
}
