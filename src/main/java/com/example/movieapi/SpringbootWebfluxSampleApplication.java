package com.example.movieapi;

import com.example.movieapi.model.Movie;
import com.example.movieapi.model.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@SpringBootApplication
public class SpringbootWebfluxSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebfluxSampleApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ReactiveMongoOperations operations, MovieRepository movieRepository) {
		return args -> {
			Flux<Movie> productFlux = Flux.just(
							new Movie(null, "Avenger: Infinity Wars", "Action", LocalDateTime.now()),
							new Movie(null, "Gladiator", "Drama/Action", LocalDateTime.now()),
							new Movie(null, "Black Panther", "Action", LocalDateTime.now()))
					.flatMap(movieRepository::save);

			productFlux.thenMany(movieRepository.findAll())
					.subscribe(System.out::println);
		};
	}
}
