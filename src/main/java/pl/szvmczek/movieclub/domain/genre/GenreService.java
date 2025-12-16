package pl.szvmczek.movieclub.domain.genre;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szvmczek.movieclub.domain.genre.dto.GenreDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Optional<GenreDto> findGenreByName(String name){
        return genreRepository.findByNameIgnoreCase(name)
                .map(GenreDtoMapper::map);
    }

    public List<GenreDto> findAllGenres(){
        return StreamSupport.stream(genreRepository.findAll().spliterator(),false)
                .map(GenreDtoMapper::map)
                .toList();
    }

    @Transactional
    public void addGenre(GenreDto genreDto){
        Genre genreToSave = new Genre();
        genreToSave.setName(genreDto.getName());
        genreToSave.setDescription(genreDto.getDescription());
        genreRepository.save(genreToSave);
    }
}
