package br.com.ifpe.demo;

import br.com.ifpe.demo.ConsoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private ConsoleMenu consoleMenu;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("Projeto funcionando");
    }

    @Override
    public void run(String... args) throws Exception {
        // Inicia o menu no console
        consoleMenu.showMenu();
    }
}
