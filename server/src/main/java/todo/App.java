package todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import todo.data.NoteRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = NoteRepository.class)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}