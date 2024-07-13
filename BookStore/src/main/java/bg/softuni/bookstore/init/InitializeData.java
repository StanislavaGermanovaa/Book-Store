package bg.softuni.bookstore.init;

import bg.softuni.bookstore.model.entity.Category;
import bg.softuni.bookstore.model.enums.CategoryType;
import bg.softuni.bookstore.repo.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InitializeData implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    private final Map<CategoryType,String> categoryTypeStringMap=Map.of(
            CategoryType.CRIME_STORY,"Crime story is term used to describe narratives that centre on criminal acts and especially on the investigation, either by an amateur or a professional detective, of a crime, often a murder.",
            CategoryType.HORROR,"Horror stories are designed to evoke fear, fascination, or revulsion in the reader. This is done either through supernatural elements or psychological circumstances. Sometimes horror is also considered dark fantasy because the laws of nature must be violated in some way, qualifying the story as fantastic.",
            CategoryType.THRILLER,"Thriller is a genre of fiction with numerous, often overlapping, subgenres, including crime, horror, and detective fiction. Thrillers are characterized and defined by the moods they elicit, giving their audiences heightened feelings of suspense, excitement, surprise, anticipation and anxiety.",
            CategoryType.CLASSIC,"A classic is a book accepted as being exemplary or particularly noteworthy.",
            CategoryType.LOVE_STORY,"A love story is a narrative centered around the progression of two characters' relationship as they deal with internal and external obstacles to be together.",
            CategoryType.SCIENCE_FICTION,"Science fiction, also often known as 'sci-fi', is a genre of literature that is imaginative and based around science. It relies heavily on scientific facts, theories, and principles as support for its settings, characters, themes, and plot.",
            CategoryType.FANTASY,"Fantasy literature is literature set in an imaginary universe, often but not always without any locations, events, or people from the real world. Magic, the supernatural and magical creatures are common in many of these imaginary worlds. Fantasy literature may be directed at both children and adults.",
            CategoryType.FAIRY_TALE,"A fairytale is a Genre of magical story, usually originating in folklore. Typically in European fairy tales, a poor, brave, and resourceful hero or heroine goes through testing adventures to eventual good fortune."
    );

    public InitializeData(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count()==0){
            for (Map.Entry<CategoryType, String> entry : categoryTypeStringMap.entrySet()) {
                Category category = new Category();
                category.setName(entry.getKey());
                category.setDescription(entry.getValue());
                categoryRepository.save(category);
            }
        }
    }
}
