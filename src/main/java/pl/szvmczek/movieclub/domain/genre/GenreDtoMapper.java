package pl.szvmczek.movieclub.domain.genre;

import pl.szvmczek.movieclub.domain.genre.dto.GenreDto;

public class GenreDtoMapper {
    static GenreDto map(Genre genre){
        return new GenreDto(
                genre.getId(),
                genre.getName(),
                genre.getDescription()
        );
    }
}
