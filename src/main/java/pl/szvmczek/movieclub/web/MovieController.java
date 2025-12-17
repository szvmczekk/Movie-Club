package pl.szvmczek.movieclub.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import pl.szvmczek.movieclub.domain.movie.MovieService;
import pl.szvmczek.movieclub.domain.movie.dto.MovieDto;
import pl.szvmczek.movieclub.domain.rating.RatingService;

import java.util.List;
import java.util.Optional;

@Controller
public class MovieController {
    private final MovieService movieService;
    private final RatingService ratingService;

    public MovieController(MovieService movieService, RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @GetMapping("/film/{id}")
    public String getMovie(@PathVariable long id, Model model, Authentication authentication){
        MovieDto optionalMovie = movieService.findMovieById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("movie", optionalMovie);
        if(authentication != null){
            String currentUserEmail = authentication.getName();
            Integer rating = ratingService.getUserRatingForMovie(currentUserEmail, id).orElse(0);
            model.addAttribute("userRating", rating);
        }
        return "movie";
    }

    @GetMapping("/top10")
    public String findTop10(Model model){
        List<MovieDto> top10Movies = movieService.findAllMovies(10);
        model.addAttribute("heading","Filmowe TOP10");
        model.addAttribute("description","Filmy najlepiej oceniane przez uzytkownikow");
        model.addAttribute("movies",top10Movies);
        return "movie-listing";
    }
}
