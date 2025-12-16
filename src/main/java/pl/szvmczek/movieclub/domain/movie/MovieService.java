package pl.szvmczek.movieclub.domain.movie;

import org.springframework.stereotype.Service;
import pl.szvmczek.movieclub.domain.genre.Genre;
import pl.szvmczek.movieclub.domain.genre.GenreRepository;
import pl.szvmczek.movieclub.domain.movie.dto.MovieDto;
import pl.szvmczek.movieclub.domain.movie.dto.MovieSaveDto;
import pl.szvmczek.movieclub.storage.FileStorageService;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final FileStorageService fileStorageService;

    public MovieService(MovieRepository movieRepository,
                        GenreRepository genreRepository,
                        FileStorageService fileStorageService) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<MovieDto> findAllPromotedMovies() {
        return movieRepository.findAllByPromotedIsTrue().stream()
                .map(MovieDtoMapper::map)
                .toList();
    }

    public Optional<MovieDto> findMovieById(long id){
        return movieRepository.findById(id).map(MovieDtoMapper::map);
    }

    public List<MovieDto> findMoviesByGenreName(String genre){
        return movieRepository.findAllByGenreNameIgnoreCase(genre).stream()
                .map(MovieDtoMapper::map)
                .toList();
    }

    public void addMovie(MovieSaveDto movieToSave){
        Movie movie = new Movie();
        movie.setTitle(movieToSave.getTitle());
        movie.setOriginalTitle(movieToSave.getOriginalTitle());
        movie.setPromoted(movieToSave.isPromoted());
        movie.setReleaseYear(movieToSave.getReleaseYear());
        movie.setShortDescription(movieToSave.getShortDescription());
        movie.setDescription(movieToSave.getDescription());
        movie.setYoutubeTrailerId(movieToSave.getYoutubeTrailerId());
        Genre genre = genreRepository.findByNameIgnoreCase(movieToSave.getGenre()).orElseThrow();
        movie.setGenre(genre);
        if(movieToSave.getPoster() != null && !movieToSave.getPoster().isEmpty()){
            String savedFileName = fileStorageService.saveImage(movieToSave.getPoster());
            movie.setPoster(savedFileName);
        }
        movieRepository.save(movie);
    }
}
