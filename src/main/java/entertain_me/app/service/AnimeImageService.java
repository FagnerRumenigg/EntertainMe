package entertain_me.app.service;

import entertain_me.app.model.Anime.AnimeImages;
import entertain_me.app.repository.anime.AnimeImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimeImageService {

    @Autowired
    AnimeImagesRepository animeImagesRepository;

    public void saveImages(Long animeId, String imageUrl, String smallImageUrl, String largeImageUrl){
        AnimeImages animeImages = new AnimeImages();

        animeImages.setIdAnime(animeId);
        animeImages.setImageUrl(imageUrl);
        animeImages.setSmallImageUrl(smallImageUrl);
        animeImages.setLargeImageUrl(largeImageUrl);

        animeImagesRepository.save(animeImages);
    }
}
